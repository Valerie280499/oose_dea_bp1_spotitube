package datasource.errors.mappers;

import datasource.errors.SomeSQLError;
import datasource.errors.TokenNotFoundError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class TokenNotFoundErrorMapperTest {
    private TokenNotFoundErrorMapper sut;

    @BeforeEach
    void setUp() {
        sut = new TokenNotFoundErrorMapper();
    }

    @Test
    void tokenNotFoundErrorMapperHasEntityTest(){
        var response = sut.toResponse(new TokenNotFoundError());
        assertTrue(response.hasEntity());
    }

    @Test
    void tokenNotFoundErrorMapperResponseCodeTest(){
        var response = sut.toResponse(new TokenNotFoundError());
        assertEquals(400, response.getStatus());
    }

    @Test
    void tokenNotFoundErrorMapperInstanceOfTest(){
        var response = sut.toResponse(new TokenNotFoundError());
        assertTrue(response instanceof Response);
    }
}
