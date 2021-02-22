package services;

import dto.LoginRequestDTO;
import datasource.connection.JDBCConnection;
import datasource.DAO.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotAuthorizedException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginServiceTest {
    private UserDAO fakeUserDAO;
    private LoginService sut;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        var userDAO = new UserDAO();
        var jdbcConnection = new JDBCConnection();
        loginRequestDTO = new LoginRequestDTO();
        sut = new LoginService();

        try {
            var fakeConn = jdbcConnection.createConnection();
            var fakeJDBCConnection = mock(JDBCConnection.class);
            when(fakeJDBCConnection.createConnection()).thenReturn(fakeConn);

            userDAO.setJDBCConnection(fakeJDBCConnection);
            var fakeUser = userDAO.getUser("Valerie");

            fakeUserDAO = mock(UserDAO.class);
            when(fakeUserDAO.getUser("Valerie")).thenReturn(fakeUser);

        } catch (SQLException e) { e.printStackTrace(); }

    }

    @Test
    void testCorrectLoginRequest(){
        sut.userDAO = fakeUserDAO;

        loginRequestDTO.setUser("Valerie");
        loginRequestDTO.setPassword("blaat");
        var loginRequest = sut.login(loginRequestDTO);

        Assertions.assertEquals("Hello", loginRequest.getToken());
    }

    @Test
    void testInCorrectLogInRequest(){
        sut.userDAO = fakeUserDAO;

        loginRequestDTO.setUser("Lauren");
        loginRequestDTO.setPassword("123");

        assertThrows(NotAuthorizedException.class, () -> {
            sut.login(loginRequestDTO);
        });    }
}
