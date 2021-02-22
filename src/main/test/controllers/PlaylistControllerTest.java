package controllers;

import datasource.DAO.PlaylistDAO;
import datasource.DAO.UserDAO;
import datasource.connection.JDBCConnection;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistControllerTest {
    private PlaylistDAO fakePlaylistDAO;
    private PlaylistController sut;

    @BeforeEach
    void setUp(){
        var playlistDAO = new PlaylistDAO();
        var jdbcConnection = new JDBCConnection();
        sut = new PlaylistController();


        try {
            var fakeConn = jdbcConnection.createConnection();
            var fakeJDBCConnection = mock(JDBCConnection.class);
            when(fakeJDBCConnection.createConnection()).thenReturn(fakeConn);

            playlistDAO.setJDBCConnection(fakeJDBCConnection);
            var fakePlaylistsDTO = playlistDAO.getAllPlaylists();

            fakePlaylistDAO = mock(PlaylistDAO.class);
            when(fakePlaylistDAO.getAllPlaylists()).thenReturn(fakePlaylistsDTO);

        } catch (SQLException e) { e.printStackTrace(); }

//        fakePlaylistsDTO = new PlaylistsDTO();
//        fakePlaylistsDTO.setLength(3);
//
//        playlists = new ArrayList<>();
//        playlists.add(new PlaylistDTO(1, "country", "Valerie"));
//        playlists.add(new PlaylistDTO(2, "country rock", "Valerie"));
//        playlists.add(new PlaylistDTO(3, "love country", "Valerie"));
//        fakePlaylistsDTO.setPlaylists(playlists);
//
//        fakePlaylistDAO = mock(PlaylistDAO.class);
//        when(fakePlaylistDAO.getAllPlaylists()).thenReturn(fakePlaylistsDTO);

    }

    @Test
    void createPlaylistsTest() {
        var response = sut.getAllPlaylists("Hello");

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void wrongTokenWhileCreatingPlaylistsTest(){
        var response = sut.getAllPlaylists("Hey");

        Assertions.assertEquals(401, response.getStatus());
    }
}
