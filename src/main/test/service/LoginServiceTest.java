package service;

import com.mysql.jdbc.JDBC4Connection;
import controllers.DTO.login.LoginRequestDTO;
import datasource.connection.JDBCConnection;
import datasource.dao.UserDAO;
import domain.interfaces.IUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotAuthorizedException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginServiceTest {
    private UserDAO fakeUserDAO;
    private UserDAO userDAO;
    private Optional<IUser> fakeUser;

    private JDBC4Connection fakeConn;
    private JDBCConnection jdbcConnection;
    private JDBCConnection fakeJDBCConnection;

    private LoginService sut;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    public void setUp() {
        userDAO = new UserDAO();
        jdbcConnection = new JDBCConnection();
        loginRequestDTO = new LoginRequestDTO();
        sut = new LoginService();

        try {
            fakeConn = jdbcConnection.createConnection();
            fakeJDBCConnection = mock(JDBCConnection.class);
            when(fakeJDBCConnection.createConnection()).thenReturn(fakeConn);

            userDAO.setJDBCConnection(fakeJDBCConnection);
            fakeUser = userDAO.getUser("valerie");

            fakeUserDAO = mock(UserDAO.class);
            when(fakeUserDAO.getUser("valerie")).thenReturn(fakeUser);

        } catch (SQLException e) { e.printStackTrace(); }

    }

    @Test
    public void testCorrectLoginRequest(){
        sut.userDAO = fakeUserDAO;

        loginRequestDTO.setUser("valerie");
        loginRequestDTO.setPassword("valerie");
        var loginRequest = sut.login(loginRequestDTO);

        Assertions.assertEquals("Hello", loginRequest.getToken());
    }

    @Test
    public void testInCorrectLogInRequest(){
        sut.userDAO = fakeUserDAO;

        loginRequestDTO.setUser("Lauren");
        loginRequestDTO.setPassword("123");

        assertThrows(NotAuthorizedException.class, () -> {
            sut.login(loginRequestDTO);
        });    }
}