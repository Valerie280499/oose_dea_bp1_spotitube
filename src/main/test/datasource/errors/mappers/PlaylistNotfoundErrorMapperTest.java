package datasource.errors.mappers;

import datasource.errors.PlayListNotFoundError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistNotfoundErrorMapperTest {
    private PlaylistNotfoundErrorMapper sut;

    @BeforeEach
    void setUp() {
        sut = new PlaylistNotfoundErrorMapper();
    }

    @Test
    void playlistNotfoundErrorMapperHasEntityTest(){
        var response = sut.toResponse(new PlayListNotFoundError());
        assertTrue(response.hasEntity());

    }

    @Test
    void playlistNotfoundErrorMapperResponseCodeTest(){
        var response = sut.toResponse(new PlayListNotFoundError());
        assertEquals(400, response.getStatus());
    }

    @Test
    void playlistNotfoundErrorMapperInstanceOfTest(){
        var response = sut.toResponse(new PlayListNotFoundError());
        assertTrue(response instanceof Response);
    }
}
