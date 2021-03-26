package datasource.resultsetMappers;

import dto.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MapResultsetToTrackDTOTest {
    private MapResultsetToTrackDTO sut;
    private ResultSet mockedResultSet;
    private static final int fakeID = 9;
    private static final String fakeTitle = "fakeTitle";
    private static final String fakePerformer = "fakePerformer";
    private static final int fakeDuration = 3;
    private static final String fakeAlbum = "fakeAlbum";
    private static final int fakePlaycount = 6;
    private static final String fakePublicationDate = "01-01-01";
    private static final String fakeDescription = "fakeDescription";
    private static final boolean fakeOfflineAvailable = true;

    @BeforeEach
    void setUp() {
        sut = new MapResultsetToTrackDTO();
        mockedResultSet = mock(ResultSet.class);

        try {
            when(mockedResultSet.getInt("id")).thenReturn(fakeID);
            when(mockedResultSet.getString("titel")).thenReturn(fakeTitle);
            when(mockedResultSet.getString("performer")).thenReturn(fakePerformer);
            when(mockedResultSet.getInt("duration")).thenReturn(fakeDuration);
            when(mockedResultSet.getString("album")).thenReturn(fakeAlbum);
            when(mockedResultSet.getInt("playcount")).thenReturn(fakePlaycount);
            when(mockedResultSet.getString("publicationDate")).thenReturn(fakePublicationDate);
            when(mockedResultSet.getString("description")).thenReturn(fakeDescription);
            when(mockedResultSet.getBoolean("offlineAvailable")).thenReturn(fakeOfflineAvailable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapInstanceOfTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertTrue(trackDTO instanceof TrackDTO);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapIDTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertEquals(9, trackDTO.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapTitelTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertEquals("fakeTitle", trackDTO.getTitle());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapPerformerTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertEquals("fakePerformer", trackDTO.getPerformer());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapDurationTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertEquals(3, trackDTO.getDuration());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapAlbumTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertEquals("fakeAlbum", trackDTO.getAlbum());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapPlaycountTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertEquals(6, trackDTO.getPlaycount());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapPublicationDateTest(){

        Date expectedPublicationDate = null;
        try { expectedPublicationDate = new SimpleDateFormat("yyyy-MM-dd").parse("01-01-01");
        } catch (ParseException e) { e.printStackTrace(); }

        try{
            var trackDTO = sut.map(mockedResultSet);
            assertEquals(expectedPublicationDate, (trackDTO.getPublicatationDate()));
        } catch (SQLException throwables) { throwables.printStackTrace(); }
    }

    @Test
    void mapDescriptionTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertEquals("fakeDescription", trackDTO.getDescription());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapOfflineAvailableTest(){
        try{
            var trackDTO = sut.map(mockedResultSet);
            assertTrue(trackDTO.getOfflineAvailable());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
