package datasource.dao;

import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.ServerErrorException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class TrackDAOTest {
    private TrackDAO sut;
    private UserDAO mockedUserDAO;
    private TrackDAOService mockedTrackDAOService;
    private static final String FAKE_QUERY = "SELECT T.* FROM track T where id not in (select P.idTrack FROM playlistTracks P WHERE P.idPlaylist = ?)";
    private static final String TOKEN = "hello";
    private static final int PLAYLIST_ID = 2;
    private static final int ERROR_PLAYLIST_ID = 3;

    @BeforeEach
    void setUp() {
        sut = new TrackDAO();
        mockedUserDAO = mock(UserDAO.class);
        sut.setUserDAO(mockedUserDAO);

        var mockedTracksDTO = new TracksDTO();
        var tracks = new ArrayList<TrackDTO>();
        var trackDTO = new TrackDTO();

        trackDTO.setId(1);
        trackDTO.setTitle("titel");
        trackDTO.setPerformer("performer");
        trackDTO.setDuration(2);
        trackDTO.setAlbum("album");
        trackDTO.setPlaycount(5);
        try { trackDTO.setPublicatationDate(new SimpleDateFormat("yyyy-MM-dd").parse("01-01-01"));
        } catch (ParseException e) { e.printStackTrace(); }
        trackDTO.setDescription("description");
        trackDTO.setOfflineAvailable(true);

        tracks.add(trackDTO);
        mockedTracksDTO.setTracks(tracks);

        try {
            mockedTrackDAOService = mock(TrackDAOService.class);
            when(mockedTrackDAOService.getTracks(PLAYLIST_ID, FAKE_QUERY)).thenReturn(mockedTracksDTO);
            doThrow(ServerErrorException.class).when(mockedTrackDAOService).getTracks(ERROR_PLAYLIST_ID, FAKE_QUERY);
            sut.setTrackDAOService(mockedTrackDAOService);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getTracksWhichAreNotInAPlaylistVerifyTest(){
        sut.getTracksWhichAreNotInAPlaylist(TOKEN, PLAYLIST_ID);

        try {
            verify(mockedUserDAO).getUserByToken(TOKEN);
            verify(mockedTrackDAOService).getTracks(PLAYLIST_ID, FAKE_QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getTracksWhichAreNotInAPlaylistInstanceOfTest(){
        var tracksDTO = sut.getTracksWhichAreNotInAPlaylist(TOKEN, PLAYLIST_ID);
        assertTrue(tracksDTO instanceof TracksDTO);
    }

    @Test
    void getTracksWhichAreNotInAPlaylistThrowsError(){
        assertThrows(ServerErrorException.class,
                () -> sut.getTracksWhichAreNotInAPlaylist(TOKEN, ERROR_PLAYLIST_ID));
    }
}
