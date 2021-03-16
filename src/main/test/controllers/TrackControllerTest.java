package controllers;
import datasource.dao.TrackDAO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackControllerTest {
    private TrackController sut;
    private TrackDAO mockedTrackDAO;
    private static final String TOKEN = "hello";
    private static final String QUERY = "SELECT T.* FROM track T where id not in (select P.idTrack FROM playlistTracks P WHERE P.idPlaylist = ?)";

    @BeforeEach
    void setUp() {
        sut = new TrackController();

        var track = new TrackDTO();
        track.setId(2);
        track.setTitle("Title");
        track.setPerformer("Performer");
        track.setDuration(10);
        track.setAlbum("album");
        track.setPlaycount(2);
        track.setPublicatationDate("01-01-01");
        track.setDescription("Description");
        track.setOfflineAvailable(false);

        var tracks = new ArrayList<TrackDTO>();
        tracks.add(track);

        var tracksDTO = new TracksDTO();
        tracksDTO.setTracks(tracks);

        mockedTrackDAO = mock(TrackDAO.class);
        when(mockedTrackDAO.getTracksWhichAreNotInAPlaylist(TOKEN, 2)).thenReturn(tracksDTO);
        sut.setTrackDAO(mockedTrackDAO);

    }

    @Test
    void getAllTracksVerifyTest() {
        sut.getAllTracks(TOKEN, 2);
        verify(mockedTrackDAO).getTracksWhichAreNotInAPlaylist(TOKEN, 2);
    }

    @Test
    void getAllTracksResponsecodeTest(){
        var response = sut.getAllTracks(TOKEN, 2);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void getAllTracksHasEntityTest() {
        var response = sut.getAllTracks(TOKEN, 2);
        assertTrue(response.hasEntity());
    }
}
