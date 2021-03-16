package datasource.errors.mappers;
import datasource.errors.SomeSQLError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.*;

class SomeSQLErrorMapperTest {
    private SomeSQLErrorMapper sut;

    @BeforeEach
    void setUp() {
        sut = new SomeSQLErrorMapper();
    }

    @Test
    void someSQLErrorMapperHasEntityTest(){
        var response = sut.toResponse(new SomeSQLError());
        assertTrue(response.hasEntity());
    }

    @Test
    void someSQLErrorMapperResponseCodeTest(){
        var response = sut.toResponse(new SomeSQLError());
        assertEquals(400, response.getStatus());
    }

    @Test
    void someSQLErrorMapperInstanceOfTest(){
        var response = sut.toResponse(new SomeSQLError());
        assertTrue(response instanceof Response);
    }
}


