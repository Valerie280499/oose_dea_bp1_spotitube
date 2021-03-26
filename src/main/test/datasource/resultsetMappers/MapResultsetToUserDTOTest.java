package datasource.resultsetMappers;

import dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MapResultsetToUserDTOTest {
    private MapResultsetToUserDTO sut;
    private ResultSet mockedResultSet;
    private static final String fakeUsername = "fakeUsername";
    private static final String fakePassword = "fakePassword";
    private static final String fakeToken = "fakeToken";

    @BeforeEach
    void setUp() {
        sut = new MapResultsetToUserDTO();
        mockedResultSet = mock(ResultSet.class);

        try {
            when(mockedResultSet.getString("username")).thenReturn(fakeUsername);
            when(mockedResultSet.getString("password")).thenReturn(fakePassword);
            when(mockedResultSet.getString("token")).thenReturn(fakeToken);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapInstanceOfTest(){
        try{
            var userDTO = sut.map(mockedResultSet);
            assertTrue(userDTO instanceof UserDTO);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapUsernameTest(){
        try{
            var userDTO = sut.map(mockedResultSet);
            assertEquals("fakeUsername", userDTO.getUsername());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapPasswordTest(){
        try{
            var userDTO = sut.map(mockedResultSet);
            assertEquals("fakePassword", userDTO.getPassword());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void mapTokenTest(){
        try{
            var userDTO = sut.map(mockedResultSet);
            assertEquals("fakeToken", userDTO.getToken());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
