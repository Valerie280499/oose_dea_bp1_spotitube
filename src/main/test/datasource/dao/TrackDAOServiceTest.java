package datasource.dao;

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
    private MapResultsetToTrackDTO mockedMapResultsetToTrackDTO;
    private ResultSet fakeResultSet;
    private static final String FAKE_QUERY = "select * form track where idtrack = ?";
    private static final String ERROR_QUERY = "select nothing from nothing";
    private static final int PLAYLIST_ID = 5;

    @BeforeEach
    void setUp() {
        sut = new TrackDAOService();

        try {
            mockedJDBCDatabaseConnection = new JDBCDatabaseConnection();
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

            mockedMapResultsetToTrackDTO = mock(MapResultsetToTrackDTO.class);
            when(mockedMapResultsetToTrackDTO.map(fakeResultSet)).thenReturn(fakeTrackDTO);
            sut.setMapResultsetToTrackDTO(mockedMapResultsetToTrackDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getTracksVerifyTest(){
        try {
            sut.getTracks(PLAYLIST_ID, FAKE_QUERY);
            verify(mockedJDBCDatabaseConnection).createConnection();
            verify(mockedMapResultsetToTrackDTO).map(fakeResultSet);
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
