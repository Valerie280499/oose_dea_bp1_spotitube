package service;

import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistServiceTest {
    private PlaylistsDTO fakePlaylistsDTO;
    private PlaylistService sut;

    @BeforeEach
    void setUp(){

        ArrayList<PlaylistDTO> fakePlaylists = new ArrayList<>();
        fakePlaylists.add(new PlaylistDTO(1, "sunday morning", false));
        fakePlaylists.add(new PlaylistDTO(2, "monday morning", false));

        fakePlaylistsDTO = mock(PlaylistsDTO.class);
        when(fakePlaylistsDTO.getPlaylists()).thenReturn(fakePlaylists);

        sut = new PlaylistService();
    }

    @Test
    void generatePlaylistsTest() {
        // de methode generatePlaylists wordt nu nog niet getest
        // omdat deze methode in een later stadium zal worden vervangen
        // door een methode die data ophaalt uit een database en dit netjes
        // omzet naar java objecten.
    }

    @Test
    void deleteAPlaylistTest(){
        sut.playlistsDTO = fakePlaylistsDTO;

        var responsePlaylistsDTO = sut.deleteAPlaylist(1);

        Assertions.assertEquals(1, responsePlaylistsDTO.getPlaylists().size());
    }

    @Test
    void addPlaylistTest(){
        sut.playlistsDTO = fakePlaylistsDTO;

        var responsePlaylistsDTO = sut.addPlaylist(new PlaylistDTO(3, "friday morning", false));

        Assertions.assertEquals(3, responsePlaylistsDTO.getPlaylists().size());

    }

    @Test
    void editPlaylistTest(){
        sut.playlistsDTO = fakePlaylistsDTO;

        var responsePlaylistsDTO = sut.editPlayist(2, new PlaylistDTO(3, "friday morning", false));

        Assertions.assertEquals(3, responsePlaylistsDTO.getPlaylists().get(1).getId());

    }
}
