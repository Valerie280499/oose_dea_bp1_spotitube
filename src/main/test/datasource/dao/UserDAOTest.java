package datasource.dao;

import com.mysql.jdbc.JDBC4Connection;
import datasource.connection.JDBCDatabaseConnection;
import datasource.errors.TokenNotFoundError;
import datasource.resultsetMappers.MapResultsetToUserDTO;
import dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {
    private UserDAO sut;
    private JDBCDatabaseConnection mockedJDBCDatabaseConnection;
    private JDBC4Connection mockedJDBCconnection;
    private MapResultsetToUserDTO mockedMapResultsetToUserDTO;
    private ResultSet fakeResultset;
    public static final String UPDATE_TEST_QUERY = "UPDATE LOGIN SET token = ? WHERE username = ?";
    public static final String WHERE_TOKEN_QUERY = "SELECT * FROM login WHERE token = ?";
    public static final String WHERE_USERNAME_QUERY = "SELECT * FROM login WHERE username = ?";


    @BeforeEach
    void setUp() {
        mockedJDBCDatabaseConnection = mock(JDBCDatabaseConnection.class);

        var fakeUserDTO = new UserDTO();
        fakeUserDTO.setUsername("fakeName");
        fakeUserDTO.setPassword("fakePassword");
        fakeUserDTO.setToken("fakeToken");

        try {
            mockedJDBCconnection = mock(JDBC4Connection.class);
            when(mockedJDBCDatabaseConnection.createConnection()).thenReturn(mockedJDBCconnection);

            mockedMapResultsetToUserDTO = mock(MapResultsetToUserDTO.class);
            when(mockedMapResultsetToUserDTO.map(fakeResultset)).thenReturn(fakeUserDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        sut = new UserDAO();

        sut.setJDBCConnection(mockedJDBCDatabaseConnection);
        sut.setMapResultsetToUserDTO(mockedMapResultsetToUserDTO);
    }

    @Test
    void getUserPasswordTest(){
        var userDTO = sut.getUser("fakeName");
        assertEquals("fakePassword", userDTO.getPassword());
    }

    @Test
    void getUserInstanceOfTest(){
        var userDTO = sut.getUser("fakeName");
        assertTrue(userDTO instanceof UserDTO);
    }

    @Test
    void updateTokenFromUserVerifyTest(){
        try {
            sut.updateTokenForUser("fakeName", "newToken");
            verify(mockedJDBCconnection).prepareStatement(UPDATE_TEST_QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getUserByTokenVerifyTest(){
        try{
            sut.getUserByToken("fakeToken");
            verify(mockedJDBCconnection).prepareStatement(WHERE_TOKEN_QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getUserbyTokenUnknownTokenTest(){
        assertThrows(TokenNotFoundError.class, () -> sut.getUserByToken("unknownToken"));
    }

    @Test
    void executeQueryVerifyTest(){
        try {
            var preparedStatement = mockedJDBCconnection.prepareStatement(WHERE_USERNAME_QUERY);
            sut.executeQuery(preparedStatement);
            verify(mockedMapResultsetToUserDTO).map(fakeResultset);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void executeQueryInstanceOfTest(){
        try{
            var preparedStatement = mockedJDBCconnection.prepareStatement(WHERE_TOKEN_QUERY);
            var userDTOArray = sut.executeQuery(preparedStatement);
            assertTrue(userDTOArray.get(0) instanceof UserDTO);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
