package service;

import controllers.DTO.playlist.PlaylistDTO;
import controllers.DTO.playlist.PlaylistsDTO;
import controllers.DTO.track.TrackDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrackServiceTest {
    private PlaylistsDTO fakePlaylistsDTO;
    private TrackService sut;

    @BeforeEach
    void setUp(){
        ArrayList<PlaylistDTO> fakePlaylists = new ArrayList<>();
        fakePlaylists.add(new PlaylistDTO(1, "sunday morning", false));
        fakePlaylists.add(new PlaylistDTO(2, "monday morning", false));

        ArrayList<TrackDTO> fakeTracks = new ArrayList<>();
        fakeTracks.add(new TrackDTO(1, "song1", "singer1", 100, "red album", 5,
                "01-01-2001", "undefined", true));
        fakeTracks.add(new TrackDTO(2, "song2", "singer2", 50, "blue album", 10,
                "03-03-2003", "undefined", false));

        for (int i=0; i<=1; i++){
            fakePlaylists.get(i).setTracks(fakeTracks);
        }

        fakePlaylistsDTO = mock(PlaylistsDTO.class);
        when(fakePlaylistsDTO.getPlaylists()).thenReturn(fakePlaylists);

        sut = new TrackService();

    }
    @Test
    void generateTracks() {
        // de methode generateTracks wordt nu nog niet getest
        // omdat deze methode in een later stadium zal worden vervangen
        // door een methode die data ophaalt uit een database en dit netjes
        // omzet naar java objecten.
    }

    @Test
    void getAllTracksInAPlaylistTest() {
        sut.playlistsDTO = fakePlaylistsDTO;

        var responseTracksDTO = sut.getAllTracksInAPlaylist(1);

        Assertions.assertEquals(2, responseTracksDTO.getTracks().size());
    }

    @Test
    void addTrackToPlaylistTest() {
        sut.playlistsDTO = fakePlaylistsDTO;

        var responseTracksDTO = sut.addTrackToPlaylist(1, new TrackDTO(3, "new song", "me",
                3, "the best of me", 5, "15-10-2020", "the best till now",
                false));

        Assertions.assertEquals(3, responseTracksDTO.getTracks().size());
    }
}