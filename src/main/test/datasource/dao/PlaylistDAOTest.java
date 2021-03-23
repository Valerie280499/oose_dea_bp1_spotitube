package datasource.dao;

import com.mysql.jdbc.JDBC4Connection;
import datasource.connection.JDBCDatabaseConnection;
import datasource.errors.PlayListNotFoundError;
import datasource.resultsetMappers.MapResultsetToPlaylistDTO;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PlaylistService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PlaylistDAOTest {
    private PlaylistDAO sut;
    private JDBCDatabaseConnection mockedJDBCDatabaseConnection;
    private PlaylistService mockedPlaylistService;
    private JDBC4Connection mockedJDBC4Connection;
    private MapResultsetToPlaylistDTO mockedMapResultsetToPlaylistDTO;
    private TrackDAOService mockedTrackDAOService;
    private ResultSet fakeResultset;
    private PlaylistDTO playlistDTO;
    private TrackDTO newTrack;
    private TracksDTO tracksDTO;
    private static final String SELECT_TRACKS_FROM_PLAYLIST = "SELECT T.* FROM track T INNER JOIN playlistTracks P ON T.id = P.idTrack WHERE P.idPlaylist = ?";
    private static final String DELETE_FROM_PLAYLIST_WHERE_ID = "DELETE FROM playlist WHERE id = ?";
    private static final String INSERT_NEW_TRACK_INTO_PLAYLIST = "INSERT INTO playlistTracks (idPlaylist, idTrack, offlineAvailable) VALUES (?, ?, ?)";

    @BeforeEach
    void setUp() {

        playlistDTO = new PlaylistDTO();
        playlistDTO.setId(4);
        playlistDTO.setName("fourth list");
        playlistDTO.setOwner(true);

        var fakePlaylistDTO = mock(PlaylistDTO.class);
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

        try {
            newTrack.setPublicatationDate(new SimpleDateFormat("yyyy-MM-dd").parse("01-01-01"));
        } catch (ParseException e) { e.printStackTrace(); }

        newTrack.setDescription("description");
        newTrack.setOfflineAvailable(true);

        tracks.add(newTrack);
        fakePlaylistDTO.setTracks(tracks);

        tracksDTO = new TracksDTO();
        tracksDTO.setTracks(tracks);

        try {
            mockedJDBCDatabaseConnection = mock(JDBCDatabaseConnection.class);
            mockedJDBC4Connection = mock(JDBC4Connection.class);
            var mockedPreparedStatement = mock(PreparedStatement.class);
            var mockedResultSet = mock(ResultSet.class);

            when(mockedJDBCDatabaseConnection.createConnection()).thenReturn(mockedJDBC4Connection);
            when(mockedJDBC4Connection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
            when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

            mockedMapResultsetToPlaylistDTO = mock(MapResultsetToPlaylistDTO.class);
            when(mockedMapResultsetToPlaylistDTO.map(fakeResultset, tracksDTO)).thenReturn(fakePlaylistDTO);

            mockedTrackDAOService = mock(TrackDAOService.class);
            when(mockedTrackDAOService.getTracks(5, SELECT_TRACKS_FROM_PLAYLIST)).thenReturn(tracksDTO);

            mockedPlaylistService = mock(PlaylistService.class);
            when(mockedPlaylistService.generateLengthPlaylist(fakePlaylistDTO)).thenReturn(5);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        sut = new PlaylistDAO();

        sut.setPlaylistService(mockedPlaylistService);
        sut.setTrackDAOService(mockedTrackDAOService);
        sut.setMapResultsetToPlaylistDTO(mockedMapResultsetToPlaylistDTO);
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
    void deletePlaylistThrowsErrorTest(){
        sut.getAllPlaylists();
        assertThrows(PlayListNotFoundError.class,
                () -> sut.deletePlaylist(5));
    }

    @Test
    void addPlaylistCountTest(){
        var playlistsDTO = sut.addPlaylist(playlistDTO);
        assertEquals(1, playlistsDTO.getPlaylists().size());
    }

    @Test
    void editPlaylistInstanceOfTest(){
        var playlistsDTO = sut.editPlaylist(playlistDTO);
        assertTrue(playlistsDTO instanceof PlaylistsDTO);
    }

    @Test
    void editPlaylistCountTest(){
        var playlistsDTO = sut.addPlaylist(playlistDTO);
        assertTrue(playlistsDTO instanceof PlaylistsDTO);
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
    void addTrackToPlaylistVerifyTest(){
        try {
            sut.addTrackToPlaylist(5, newTrack);
            verify(mockedJDBC4Connection).prepareStatement(INSERT_NEW_TRACK_INTO_PLAYLIST);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void deleteTrackFromPlaylistVerifyTest(){
        try {
            sut.deletePlaylist(5);
            verify(mockedJDBC4Connection).prepareStatement(DELETE_FROM_PLAYLIST_WHERE_ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
