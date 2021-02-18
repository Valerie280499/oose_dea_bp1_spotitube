package service;

import datasource.DAO.PlaylistsDAO;
import datasource.connection.JDBCConnection;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistServiceTest {
    private PlaylistsDTO fakePlaylistsDTO;
    private PlaylistsDAO fakePlaylistsDAO;
    private PlaylistService sut;

    @BeforeEach
    void setUp(){
        var jdbcConnection = new JDBCConnection();
        fakePlaylistsDAO = new PlaylistsDAO();
        sut = new PlaylistService();

        try {
            var fakeConn = jdbcConnection.createConnection();
            var fakeJDBCConnection = mock(JDBCConnection.class);
            when(fakeJDBCConnection.createConnection()).thenReturn(fakeConn);

            ArrayList<TrackDTO> tracks = new ArrayList<>();
            tracks.add(new TrackDTO(1, "test title", "test performer", 10, "test album", 5, "01-01-2001", "test description", true ));
            tracks.add(new TrackDTO(2, "test title", "test performer", 10, "test album", 5, "01-01-2001", "test description", true ));

            var fakeTracksDTO = new TracksDTO();
            fakeTracksDTO.setTracks(tracks);

            fakePlaylistsDAO.setJDBCConnection(fakeJDBCConnection);
            fakePlaylistsDAO = mock(PlaylistsDAO.class);
            when(fakePlaylistsDAO.getTracksFromPlaylist(1)).thenReturn(fakeTracksDTO);



            ArrayList<PlaylistDTO> fakePlaylists = new ArrayList<>();
            fakePlaylists.add(new PlaylistDTO(1, "sunday morning", "Valerie"));
            fakePlaylists.add(new PlaylistDTO(2, "monday morning", "Valerie"));

            fakePlaylistsDTO = mock(PlaylistsDTO.class);
            when(fakePlaylistsDTO.getPlaylists()).thenReturn(fakePlaylists);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void generatePlaylistsTest() {
        // de methode generatePlaylists wordt nu nog niet getest
        // omdat deze methode in een later stadium zal worden vervangen
        // door een methode die data ophaalt uit een database en dit netjes
        // omzet naar java objecten.
    }

    @Test
    void deleteAPlaylistTest(){
        sut.playlistsDTO = fakePlaylistsDTO;

        var responsePlaylistsDTO = sut.deleteAPlaylist(1);

        Assertions.assertEquals(1, responsePlaylistsDTO.getPlaylists().size());
    }

    @Test
    void addPlaylistTest(){
        sut.playlistsDTO = fakePlaylistsDTO;

        var responsePlaylistsDTO = sut.addPlaylist(new PlaylistDTO(3, "friday morning", "Valerie"));

        Assertions.assertEquals(3, responsePlaylistsDTO.getPlaylists().size());

    }

    @Test
    void editPlaylistTest(){
        sut.playlistsDTO = fakePlaylistsDTO;

        var responsePlaylistsDTO = sut.editPlaylist(new PlaylistDTO(3, "friday morning", "Valerie"));

        Assertions.assertEquals(3, responsePlaylistsDTO.getPlaylists().get(1).getId());

    }

    @Test
    void getAllTracksInAPlaylistTest() {
        sut.playlistsDAO = fakePlaylistsDAO;

        var responseTracksDTO = sut.getAllTracksInAPlaylist(1);

        Assertions.assertEquals(2, responseTracksDTO.getTracks().size());
    }
}
