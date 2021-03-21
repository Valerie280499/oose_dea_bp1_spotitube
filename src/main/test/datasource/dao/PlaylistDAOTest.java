package datasource.dao;

import com.mysql.jdbc.JDBC4Connection;
import datasource.connection.JDBCDatabaseConnection;
import datasource.resultsetMappers.MapResultsetToPlaylistDTO;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PlaylistService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistDAOTest {
    private PlaylistDAO sut;
    private JDBCDatabaseConnection mockedJDBCDatabaseConnection;
    private PlaylistService mockedPlaylistService;
    private MapResultsetToPlaylistDTO mockedMapResultsetToPlaylistDTO;
    private TrackDAOService mockedTrackDAOService;
    private ResultSet fakeResultset;
    private PlaylistDTO newPlaylist;
    private TrackDTO newTrack;
    private TracksDTO tracksDTO;
    private static final String SELECT_TRACKS_FROM_PLAYLIST = "SELECT T.* FROM track T INNER JOIN playlistTracks P ON T.id = P.idTrack WHERE P.idPlaylist = ?";

    @BeforeEach
    void setUp() {

        newPlaylist = new PlaylistDTO();
        newPlaylist.setId(4);
        newPlaylist.setName("fourth list");
        newPlaylist.setOwner(true);

        var fakePlaylistDTO = new PlaylistDTO();
        fakePlaylistDTO.setId(5);
        fakePlaylistDTO.setName("null");
        fakePlaylistDTO.setOwner(false);

        var tracks = new ArrayList<TrackDTO>();
        newTrack = new TrackDTO();

        newTrack.setId(5);
        newTrack.setTitle("titel");
        newTrack.setPerformer("performer");
        newTrack.setDuration(2);
        newTrack.setAlbum("album");
        newTrack.setPlaycount(5);
        newTrack.setPublicatationDate("01-01-01");
        newTrack.setDescription("description");
        newTrack.setOfflineAvailable(true);

        tracks.add(newTrack);
        fakePlaylistDTO.setTracks(tracks);

        tracksDTO = new TracksDTO();
        tracksDTO.setTracks(tracks);

        try {
            mockedJDBCDatabaseConnection = mock(JDBCDatabaseConnection.class);
            var fakeConn = mock(JDBC4Connection.class);
            when(mockedJDBCDatabaseConnection.createConnection()).thenReturn(fakeConn);
//
//            mockedMapResultsetToPlaylistDTO = mock(MapResultsetToPlaylistDTO.class);
//            when(mockedMapResultsetToPlaylistDTO.map(fakeResultset, tracksDTO)).thenReturn(fakePlaylistDTO);

            mockedTrackDAOService = mock(TrackDAOService.class);
            when(mockedTrackDAOService.getTracks(5, SELECT_TRACKS_FROM_PLAYLIST)).thenReturn(tracksDTO);

            mockedPlaylistService = mock(PlaylistService.class);
            when(mockedPlaylistService.lengthPlaylist(fakePlaylistDTO)).thenReturn(5);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        sut = new PlaylistDAO();

        sut.setPlaylistService(mockedPlaylistService);
        sut.setTrackDAOService(mockedTrackDAOService);
//        sut.setMapResultsetToPlaylistDTO(mockedMapResultsetToPlaylistDTO);
        sut.setJDBCConnection(mockedJDBCDatabaseConnection);
    }

    @Test
    void getAllPlaylistsInstanceOfTest(){
        var playlistsDTO = sut.getAllPlaylists();
        assertTrue(playlistsDTO instanceof PlaylistsDTO);
    }

    @Test
    void getAllPlaylistsAantalPlaylistsTest(){
        var playlistsDTO = sut.getAllPlaylists();
        assertEquals(1, playlistsDTO.getPlaylists().size());
    }

    @Test
    void deleltePlaylistInstanceOfTest(){
        var playlistsDTO = sut.deletePlaylist(5);
        assertTrue(playlistsDTO instanceof PlaylistsDTO);
    }
    @Test
    void deletePlaylistCountTest(){
        var oldPlaylistsDTO = sut.getAllPlaylists();
        var newPlaylistsDTO = sut.deletePlaylist(5);

        assertNotEquals(oldPlaylistsDTO.getPlaylists().size(), newPlaylistsDTO.getPlaylists().size());
    }

    @Test
    void addPlaylistCountTest(){
        var playlistsDTO = sut.addPlaylist(newPlaylist);
        assertEquals(2, playlistsDTO);
    }

    @Test
    void editPlaylistInstanceOfTest(){
        var playlistsDTO = sut.editPlaylist(newPlaylist);
        assertTrue(playlistsDTO instanceof PlaylistsDTO);
    }

    @Test
    void editPlaylistCountTest(){
        var playlistsDTO = sut.editPlaylist(newPlaylist);
        assertEquals("fourth list", playlistsDTO.getPlaylists().stream().filter(playlistDTO -> playlistDTO.getId() == 4));
    }

    @Test
    void getTracksInPlaylistInstanceOfTest(){
        var tracksDTO = sut.getTracksInPlaylist(5);
        assertTrue(tracksDTO instanceof TracksDTO);
    }

    @Test
    void getTracksInPlaylistTest(){
        var tracksDTO = sut.getTracksInPlaylist(5);
        assertEquals(1, tracksDTO.getTracks().size());
    }

    @Test
    void addTrackToPlaylistInstanceOfTest(){
        var tracksDTO = sut.getTracksInPlaylist(5);
        assertTrue(tracksDTO instanceof TracksDTO);
    }
    @Test
    void addTrackToPlaylistTest(){
        sut.addTrackToPlaylist(5, newTrack);
        var tracksDTO = sut.getTracksInPlaylist(5);
        assertEquals(2, tracksDTO.getTracks().size());
    }

}
