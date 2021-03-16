package controllers;

import datasource.dao.PlaylistDAO;
import datasource.dao.UserDAO;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistControllerTest {
    private PlaylistController sut;
    private PlaylistDTO newPlaylistDTO;
    private TrackDTO newTrackDTO;
    private PlaylistDAO mockedPlaylistDAO;
    private UserDAO mockedUserDAO;
    private static final String TOKEN = "hello";
    private static final int PLAYLIST_ID = 1;
    private static final int TRACK_ID = 10;

    @BeforeEach
    void setUp() {
        newPlaylistDTO = new PlaylistDTO(PLAYLIST_ID, "playlistONE", true);
        newTrackDTO = new TrackDTO();

        ArrayList<PlaylistDTO> playlistsDTO = new ArrayList<>();
        playlistsDTO.add(new PlaylistDTO(1, "playlistONE", true));
        playlistsDTO.add(new PlaylistDTO(2, "playlistTWO", false));

        var fakePlaylistsDTO = new PlaylistsDTO();
        fakePlaylistsDTO.setPlaylists(playlistsDTO);
        fakePlaylistsDTO.setLength(10);

        var fakeTracksDTO = new TracksDTO();
        var tracksDTO = new ArrayList<TrackDTO>();
        var trackDTO = new TrackDTO();

        trackDTO.setId(1);
        trackDTO.setTitle("titel");
        trackDTO.setPerformer("performer");
        trackDTO.setDuration(2);
        trackDTO.setAlbum("album");
        trackDTO.setPlaycount(5);
        trackDTO.setPublicatationDate("01-01-01");
        trackDTO.setDescription("description");
        trackDTO.setOfflineAvailable(true);

        tracksDTO.add(trackDTO);
        fakeTracksDTO.setTracks(tracksDTO);

        sut = new PlaylistController();

        mockedPlaylistDAO = mock(PlaylistDAO.class);
        when(mockedPlaylistDAO.getAllPlaylists()).thenReturn(fakePlaylistsDTO);
        when(mockedPlaylistDAO.getTracksInPlaylist(PLAYLIST_ID)).thenReturn(fakeTracksDTO);
        when(mockedPlaylistDAO.addPlaylist(newPlaylistDTO)).thenReturn(fakePlaylistsDTO);
        when(mockedPlaylistDAO.deletePlaylist(PLAYLIST_ID)).thenReturn(fakePlaylistsDTO);
        when(mockedPlaylistDAO.editPlaylist(newPlaylistDTO)).thenReturn(fakePlaylistsDTO);
        sut.setPlaylistDAO(mockedPlaylistDAO);

        mockedUserDAO = mock(UserDAO.class);
        sut.setUserDAO(mockedUserDAO);

    }

    @Test
    void getAllPlaylistsVerifyTest(){
        sut.getAllPlaylists(TOKEN);
        verify(mockedPlaylistDAO).getAllPlaylists();
        verify(mockedUserDAO).getUserByToken(TOKEN);
    }

    @Test
    void getAllPlaylistsHasEntityTest(){
        var response = sut.getAllPlaylists(TOKEN);
        assertTrue(response.hasEntity());
    }

    @Test
    void getAllPlaylistsResponseCodeTest(){
        var response = sut.getAllPlaylists(TOKEN);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void getAllTracksInAPlaylistVerifyTest(){
        sut.getAllTracksInAPlaylist(PLAYLIST_ID, TOKEN);
        verify(mockedPlaylistDAO).getTracksInPlaylist(PLAYLIST_ID);
        verify(mockedUserDAO).getUserByToken(TOKEN);
    }

    @Test
    void getAllTracksInPlaylistHasEntityTest(){
        var response = sut.getAllTracksInAPlaylist(PLAYLIST_ID,TOKEN);
        assertTrue(response.hasEntity());
    }

    @Test
    void getAllTracksInPlaylistResponseCodeTest(){
        var response = sut.getAllTracksInAPlaylist(PLAYLIST_ID, TOKEN);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void addPlaylistVerifyTest(){
        sut.addPlaylist(TOKEN, newPlaylistDTO);
        verify(mockedPlaylistDAO).addPlaylist(newPlaylistDTO);
        verify(mockedUserDAO).getUserByToken(TOKEN);
    }

    @Test
    void addPlaylistHasEntityTest(){
        var response = sut.addPlaylist(TOKEN, newPlaylistDTO);
        assertTrue(response.hasEntity());
    }

    @Test
    void addPlaylistResponseCodeTest(){
        var response = sut.addPlaylist(TOKEN, newPlaylistDTO);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void addTrackToPlaylistVerifyTest(){
        sut.addTrackToPlaylist(PLAYLIST_ID, TOKEN, newTrackDTO);
        verify(mockedPlaylistDAO).addTrackToPlaylist(PLAYLIST_ID, newTrackDTO);
        verify(mockedUserDAO).getUserByToken(TOKEN);
    }

    @Test
    void addTrackToPlaylistHasEntityTest(){
        var response = sut.addTrackToPlaylist(PLAYLIST_ID, TOKEN, newTrackDTO);
        assertTrue(response.hasEntity());
    }

    @Test
    void addTrackToPlaylistResponseCodeTest(){
        var response = sut.addTrackToPlaylist(PLAYLIST_ID, TOKEN, newTrackDTO);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void deletePlaylistVerifyTest(){
        sut.deletePlaylist(PLAYLIST_ID, TOKEN);
        verify(mockedPlaylistDAO).deletePlaylist(PLAYLIST_ID);
        verify(mockedUserDAO).getUserByToken(TOKEN);
    }

    @Test
    void deletePlaylistHasEntityTest(){
        var response = sut.deletePlaylist(PLAYLIST_ID, TOKEN);
        assertTrue(response.hasEntity());
    }

    @Test
    void deletePlaylistResponseCodeTest(){
        var response = sut.deletePlaylist(PLAYLIST_ID, TOKEN);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void editPlaylistVerifyTest(){
        sut.editPlaylist(TOKEN, newPlaylistDTO);
        verify(mockedPlaylistDAO).editPlaylist(newPlaylistDTO);
        verify(mockedUserDAO).getUserByToken(TOKEN);
    }

    @Test
    void editPlaylistHasEntityTest(){
        var response = sut.editPlaylist(TOKEN, newPlaylistDTO);
        assertTrue(response.hasEntity());
    }

    @Test
    void editPlaylistResponseCodeTest(){
        var response = sut.editPlaylist(TOKEN, newPlaylistDTO);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void deleteTrackFromPlaylistVerifyTest(){
        sut.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);
        verify(mockedPlaylistDAO).deleteTrackFromPlaylist(TRACK_ID);
        verify(mockedUserDAO).getUserByToken(TOKEN);
    }

    @Test
    void deleteTrackFromPlaylistHasEntityTest(){
        var response = sut.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);
        assertTrue(response.hasEntity());
    }

    @Test
    void deleteTrackFromPlaylistResponseCodeTest(){
        var response = sut.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);
        assertEquals(HTTP_OK, response.getStatus());
    }
}
