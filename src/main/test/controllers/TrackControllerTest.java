package controllers;

import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


class TrackControllerTest {
    private TrackController sut;

    @BeforeEach
    void setUp(){
        var fakeTracksDTO = new TracksDTO();
        fakeTracksDTO.setLength(2);

        var fakeTracks = new ArrayList<TrackDTO>();
        var track = new TrackDTO();
        track.setId(1);
        track.setTitle("title");
        track.setPerformer("performer");
        track.setDuration(10);
        track.setAlbum("album");
        track.setPlaycount(4);
        track.setPublicatationDate("publicationDate");
        track.setDescription("description");
        track.setOfflineAvailable(false);

        fakeTracks.add(track);
        fakeTracksDTO.setTracks(fakeTracks);

        sut = new TrackController();
    }

        @Test
    void getAllTracksCorrectTest() {
        var response = sut.getAllTracks("Hello", 1);

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void wrongTokenWhileCreatingPlaylistsTest() {
        var response = sut.getAllTracks("Hey", 1);

        Assertions.assertEquals(401, response.getStatus());
    }
}
