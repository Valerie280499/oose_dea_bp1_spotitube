package controllers.rest;

import controllers.DTO.playlist.PlaylistDTO;
import controllers.DTO.playlist.PlaylistsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PlaylistService;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistControllerTest {
    private PlaylistsDTO fakePlaylistsDTO;
    private ArrayList<PlaylistDTO> fakePlaylists;
    private PlaylistService fakeService;
    private PlaylistController sut;

    @BeforeEach
    void setUp(){
        fakePlaylistsDTO = new PlaylistsDTO();
        fakePlaylistsDTO.setLength(3);

        fakePlaylists = new ArrayList<>();
        fakePlaylists.add(new PlaylistDTO(1, "country", "Valerie"));
        fakePlaylists.add(new PlaylistDTO(2, "country rock", "Valerie"));
        fakePlaylists.add(new PlaylistDTO(3, "love country", "Valerie"));
        fakePlaylistsDTO.setPlaylists(fakePlaylists);

        fakeService = mock(PlaylistService.class);
        when(fakeService.generatePlaylists()).thenReturn(fakePlaylistsDTO);

        sut = new PlaylistController();
    }

    @Test
    void createPlaylistsTest() {

        sut.setPlaylistService(fakeService);

        var response = sut.createPlaylists("Hello");

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void wrongTokenWhileCreatingPlaylistsTest(){
        sut.setPlaylistService(fakeService);

        var response = sut.createPlaylists("Hey");

        Assertions.assertEquals(401, response.getStatus());
    }
}
