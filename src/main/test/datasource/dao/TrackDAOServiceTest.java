package datasource.dao;

import com.mysql.jdbc.JDBC4Connection;
import datasource.connection.JDBCDatabaseConnection;
import datasource.resultsetMappers.MapResultsetToTrackDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackDAOServiceTest {
    private TrackDAOService sut;
    private JDBCDatabaseConnection mockedJDBCDatabaseConnection;
    private JDBC4Connection mockedJDBCconnection;
    private MapResultsetToTrackDTO mockedMapResultsetToTrackDTO;
    private ResultSet fakeResultset;
    private static final String FAKE_QUERY = "SELECT T.* FROM track T where id = ?";
    private static final String ERROR_QUERY = "select nothing from nothing";
    private static final int PLAYLIST_ID = 5;

    @BeforeEach
    void setUp() {
        sut = new TrackDAOService();

//        mockedJDBCDatabaseConnection = new JDBCDatabaseConnection();
//        sut.setJDBCConnection(mockedJDBCDatabaseConnection);

        mockedJDBCDatabaseConnection = mock(JDBCDatabaseConnection.class);
        mockedJDBCconnection = mock(JDBC4Connection.class);
        when(mockedJDBCDatabaseConnection.createConnection()).thenReturn(mockedJDBCconnection);
        sut.setJDBCConnection(mockedJDBCDatabaseConnection);

        var fakeTrackDTO = new TrackDTO();
        fakeTrackDTO.setId(1);
        fakeTrackDTO.setTitle("titel");
        fakeTrackDTO.setPerformer("performer");
        fakeTrackDTO.setDuration(2);
        fakeTrackDTO.setAlbum("album");
        fakeTrackDTO.setPlaycount(5);
        fakeTrackDTO.setPublicatationDate("01-01-01");
        fakeTrackDTO.setDescription("description");
        fakeTrackDTO.setOfflineAvailable(true);

        try {
            mockedMapResultsetToTrackDTO = mock(MapResultsetToTrackDTO.class);
            when(mockedMapResultsetToTrackDTO.map(fakeResultset)).thenReturn(fakeTrackDTO);
            sut.setMapResultsetToTrackDTO(mockedMapResultsetToTrackDTO);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    @Test
    void getTracksThrowsError(){
        assertThrows(SQLException.class, () -> sut.getTracks(PLAYLIST_ID, ERROR_QUERY));
    }
}
