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
    private JDBC4Connection mockedJDBCconnection;
    private MapResultsetToUserDTO mockedMapResultsetToUserDTO;
    private PreparedStatement mockedPreparedStatement;
    public static final String UPDATE_TEST_QUERY = "UPDATE LOGIN SET token = ? WHERE username = ?";
    public static final String WHERE_TOKEN_QUERY = "SELECT * FROM login WHERE token = ?";


    @BeforeEach
    void setUp() {

        sut = new UserDAO();
        try {
            JDBCDatabaseConnection mockedJDBCDatabaseConnection = mock(JDBCDatabaseConnection.class);
            mockedJDBCconnection = mock(JDBC4Connection.class);
            mockedPreparedStatement = mock(PreparedStatement.class);
            var mockedResultSet = mock(ResultSet.class);
            var userDTO = mock(UserDTO.class);

            when(mockedJDBCDatabaseConnection.createConnection()).thenReturn(mockedJDBCconnection);
            when(mockedJDBCconnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
            when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
            sut.setJDBCConnection(mockedJDBCDatabaseConnection);

            mockedMapResultsetToUserDTO = mock(MapResultsetToUserDTO.class);
            when(mockedMapResultsetToUserDTO.map(any())).thenReturn(userDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sut.setMapResultsetToUserDTO(mockedMapResultsetToUserDTO);
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
