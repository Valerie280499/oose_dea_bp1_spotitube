package controllers;
import datasource.dao.TrackDAO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackControllerTest {
    private TrackController sut;
    private TrackDAO mockedTrackDAO;
    private static final String TOKEN = "hello";
    public static final int PLAYLIST_ID = 2;
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
        try {
            track.setPublicatationDate(new SimpleDateFormat("yyyy-MM-dd").parse("01-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        track.setDescription("Description");
        track.setOfflineAvailable(false);

        var tracks = new ArrayList<TrackDTO>();
        tracks.add(track);

        var tracksDTO = new TracksDTO();
        tracksDTO.setTracks(tracks);

        mockedTrackDAO = mock(TrackDAO.class);
        when(mockedTrackDAO.getTracksWhichAreNotInAPlaylist(TOKEN, PLAYLIST_ID)).thenReturn(tracksDTO);
        sut.setTrackDAO(mockedTrackDAO);

    }

    @Test
    void getAllTracksVerifyTest() {
        sut.getAllTracks(TOKEN, PLAYLIST_ID);
        verify(mockedTrackDAO).getTracksWhichAreNotInAPlaylist(TOKEN, PLAYLIST_ID);
    }

    @Test
    void getAllTracksResponsecodeTest(){
        var response = sut.getAllTracks(TOKEN, PLAYLIST_ID);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void getAllTracksHasEntityTest() {
        var response = sut.getAllTracks(TOKEN, PLAYLIST_ID);
        assertTrue(response.hasEntity());
    }

    @Test
    void getAllTracksInstanceOfTest(){
        var response = sut.getAllTracks(TOKEN, PLAYLIST_ID);
        assertTrue(response instanceof Response);
    }
}
