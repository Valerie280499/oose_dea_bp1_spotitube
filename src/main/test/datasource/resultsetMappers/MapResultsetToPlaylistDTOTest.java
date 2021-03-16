package datasource.resultsetMappers;

import dto.PlaylistDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MapResultsetToPlaylistDTOTest {
    private MapResultsetToPlaylistDTO sut;
    private ResultSet mockedResultSet;
    private TracksDTO mockedTracksDTO;
    private static final int fakeID = 4;
    private static final String fakeName = "fakeName";
    private static final boolean fakeOwner = true;

    @BeforeEach
    void setUp() {
        sut = new MapResultsetToPlaylistDTO();
        mockedResultSet = mock(ResultSet.class);

        try {
            when(mockedResultSet.getInt("id")).thenReturn(fakeID);
            when(mockedResultSet.getString("name")).thenReturn(fakeName);
            when(mockedResultSet.getBoolean("owner")).thenReturn(fakeOwner);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        mockedTracksDTO = new TracksDTO();
        var tracks = new ArrayList<TrackDTO>();
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

        tracks.add(trackDTO);
        mockedTracksDTO.setTracks(tracks);
    }

    @Test
    void mapInstanceOfTest(){
        try {
            var playlistDTO = sut.map(mockedResultSet, mockedTracksDTO);
            assertTrue(playlistDTO instanceof PlaylistDTO);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapIDTest(){
        try {
            var playlistDTO = sut.map(mockedResultSet, mockedTracksDTO);
            assertEquals(4, playlistDTO.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapNameTest(){
        try {
            var playlistDTO = sut.map(mockedResultSet, mockedTracksDTO);
            assertEquals("fakeName", playlistDTO.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapOwnerTest(){
        try {
            var playlistDTO = sut.map(mockedResultSet, mockedTracksDTO);
            assertTrue(playlistDTO.getOwner());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapTracksTest(){
        try {
            var playlistDTO = sut.map(mockedResultSet, mockedTracksDTO);
            assertEquals(1, playlistDTO.getTracks().size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
