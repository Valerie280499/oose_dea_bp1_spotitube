package datasource.dao;

import com.mysql.jdbc.JDBC4Connection;
import datasource.connection.JDBCDatabaseConnection;
import datasource.resultsetMappers.MapResultsetToTrackDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackDAOServiceTest {
    private TrackDAOService sut;
    private JDBCDatabaseConnection mockedJDBCDatabaseConnection;
    private JDBC4Connection mockedJDBC4Connection;
    private MapResultsetToTrackDTO mockedMapResultsetToTrackDTO;
    private ResultSet fakeResultset;
    private static final String FAKE_QUERY = "SELECT T.* FROM track T where id = ?";
    private static final String ERROR_QUERY = "select nothing from nothing";
    private static final int PLAYLIST_ID = 5;

    @BeforeEach
    void setUp() {

        var fakeTrackDTO = new TrackDTO();
        fakeTrackDTO.setId(1);
        fakeTrackDTO.setTitle("titel");
        fakeTrackDTO.setPerformer("performer");
        fakeTrackDTO.setDuration(2);
        fakeTrackDTO.setAlbum("album");
        fakeTrackDTO.setPlaycount(5);
        try { fakeTrackDTO.setPublicatationDate(new SimpleDateFormat("yyyy-MM-dd").parse("01-01-01"));
        } catch (ParseException e) { e.printStackTrace(); }
        fakeTrackDTO.setDescription("description");
        fakeTrackDTO.setOfflineAvailable(true);

        try {
            mockedJDBCDatabaseConnection = mock(JDBCDatabaseConnection.class);
            mockedJDBC4Connection = mock(JDBC4Connection.class);
            var mockedPreparedStatement = mock(PreparedStatement.class);
            var mockedResultSet = mock(ResultSet.class);

            when(mockedJDBCDatabaseConnection.createConnection()).thenReturn(mockedJDBC4Connection);
            when(mockedJDBC4Connection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
            when(mockedResultSet.next()).thenReturn(true).thenReturn(false);

            mockedMapResultsetToTrackDTO = mock(MapResultsetToTrackDTO.class);
            when(mockedMapResultsetToTrackDTO.map(fakeResultset)).thenReturn(fakeTrackDTO);

        } catch (SQLException throwables) { throwables.printStackTrace(); }

        sut = new TrackDAOService();
        sut.setJDBCConnection(mockedJDBCDatabaseConnection);
        sut.setMapResultsetToTrackDTO(mockedMapResultsetToTrackDTO);
    }

    @Test
    void getTracksInstanceOfTest(){
        try {
            var tracksDTO = sut.getTracks(PLAYLIST_ID, FAKE_QUERY);
            assertTrue(tracksDTO instanceof TracksDTO);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getTracksSizeTest(){
        try {
            var tracksDTO = sut.getTracks(PLAYLIST_ID, FAKE_QUERY);
            assertEquals(1, tracksDTO.getTracks().size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
