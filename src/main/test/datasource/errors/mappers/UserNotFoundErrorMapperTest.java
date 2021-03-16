package datasource.errors.mappers;

import datasource.errors.TokenNotFoundError;
import datasource.errors.UserNotFoundError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundErrorMapperTest {
    private UserNotFoundErrorMapper sut;

    @BeforeEach
    void setUp() {
        sut = new UserNotFoundErrorMapper();
    }

    @Test
    void UserNotFoundErrorMapperHasEntityTest(){
        var response = sut.toResponse(new UserNotFoundError());
        assertTrue(response.hasEntity());
    }

    @Test
    void UserNotFoundErrorMapperResponseCodeTest(){
        var response = sut.toResponse(new UserNotFoundError());
        assertEquals(400, response.getStatus());
    }

    @Test
    void UserNotFoundErrorMapperInstanceOfTest(){
        var response = sut.toResponse(new UserNotFoundError());
        assertTrue(response instanceof Response);
    }
}
