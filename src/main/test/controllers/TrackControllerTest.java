package controllers;

import datasource.DAO.PlaylistDAO;
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
    private PlaylistDAO mockedPlaylistDAO;
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

        mockedPlaylistDAO = mock(PlaylistDAO.class);
        when(mockedPlaylistDAO.getTracks(TOKEN, 2, QUERY)).thenReturn(tracksDTO);
        sut.setPlaylistDAO(mockedPlaylistDAO);

    }

    @Test
    void getAllTracksVerifyTest() {
        sut.getAllTracks(TOKEN, 2);
        verify(mockedPlaylistDAO).getTracks(TOKEN, 2, QUERY);
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
