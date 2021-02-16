package controllers;

import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TrackService;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrackControllerTest {
    private TrackService fakeService;
    private TracksDTO fakeTracksDTO;
    private ArrayList<TrackDTO> fakeTracks;
    private TrackController sut;

    @BeforeEach
    void setUp(){
        fakeTracksDTO = new TracksDTO();
        fakeTracksDTO.setLength(2);

        fakeTracks = new ArrayList<>();
        fakeTracks.add(new TrackDTO(1, "song1", "singer1", 35, "pink album", 10, "12-07-2015", "undefined", false));
        fakeTracks.add(new TrackDTO(1, "song2", "singer2", 96, "brown album", 20, "03-07-2010", "undefined", false));
        fakeTracksDTO.setTracks(fakeTracks);

        fakeService = mock(TrackService.class);
        when(fakeService.generateTracks()).thenReturn(fakeTracksDTO);

        sut = new TrackController();
    }

    @Test
    void getAllTracksCorrectTest() {
        sut.setTrackService(fakeService);

        var response = sut.getAllTracks("Hello", 1);

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void wrongTokenWhileCreatingPlaylistsTest() {
        sut.setTrackService(fakeService);

        var response = sut.getAllTracks("Hey", 1);

        Assertions.assertEquals(401, response.getStatus());
    }
}
