package services;

import datasource.dao.PlaylistDAO;
import dto.PlaylistDTO;
import dto.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServiceTest {
    private PlaylistService sut;
    private PlaylistDTO fakePlaylistDTO;

    @BeforeEach
    void setUp() {
        fakePlaylistDTO = new PlaylistDTO();
        fakePlaylistDTO.setId(2);
        fakePlaylistDTO.setName("test name");
        fakePlaylistDTO.setOwner(true);

        var tracksDTO= new ArrayList<TrackDTO>();
        var track1 = new TrackDTO();
        track1.setDuration(2);
        tracksDTO.add(track1);

        var track2 = new TrackDTO();
        track2.setDuration(3);
        tracksDTO.add(track2);

        fakePlaylistDTO.setTracks(tracksDTO);

        sut = new PlaylistService();
    }

    @Test
    void generateLengthPlaylistInstanceOfTest(){
        var lengthPlaylist = sut.generateLengthPlaylist(fakePlaylistDTO);
        assertEquals(5, lengthPlaylist);
    }
}
