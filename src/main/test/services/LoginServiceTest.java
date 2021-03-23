package services;

import datasource.dao.UserDAO;
import datasource.errors.UserNotFoundError;
import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {
    private LoginService sut;
    private UserDAO mockedUserDAO;
    private LoginRequestDTO fakeLoginRequestDTO;

    @BeforeEach
    void setUp() {
        fakeLoginRequestDTO = new LoginRequestDTO();
        fakeLoginRequestDTO.setUser("test name");
        fakeLoginRequestDTO.setPassword("test password");

        mockedUserDAO = mock(UserDAO.class);
        var mockedUserDTO = mock(UserDTO.class);

        when(mockedUserDAO.getUser("test name")).thenReturn(mockedUserDTO);
        when(mockedUserDTO.getUsername()).thenReturn("test name");
        when(mockedUserDTO.getPassword()).thenReturn("test password");

        sut = new LoginService();
        sut.setUserDAO(mockedUserDAO);
    }

    @Test
    void loginInstanceOfTest(){
        var loginResponse = sut.login(fakeLoginRequestDTO);
        assertTrue(loginResponse instanceof LoginResponseDTO);
    }

    @Test
    void loginUserTest(){
        var loginResponse = sut.login(fakeLoginRequestDTO);
        assertEquals("test name", loginResponse.getUser());
    }

    @Test
    void getUserInstanceOfTest(){
        var userDTO = sut.getUser("test name");
        assertTrue(userDTO instanceof UserDTO);
    }

    @Test
    void getUserPasswordTest(){
        var userDTO = sut.getUser("test name");
        assertEquals("test password", userDTO.getPassword());
    }

}
