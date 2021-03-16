package controllers;

import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.LoginService;

import javax.ws.rs.core.Response;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {
    private LoginController sut;
    private LoginService mockedLoginService;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        sut = new LoginController();

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("TestUserRequest");
        loginRequestDTO.setPassword("TestPassword");

        var loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken("token");
        loginResponseDTO.setUser("TestUserResponse");

        mockedLoginService = mock(LoginService.class);
        when(mockedLoginService.login(loginRequestDTO)).thenReturn(loginResponseDTO);
        sut.setLoginService(mockedLoginService);
    }

    @Test
    void loginVerifyTest() {
        sut.login(loginRequestDTO);
        verify(mockedLoginService).login(loginRequestDTO);
    }

    @Test
    void loginResponseCodeTest() {
        var response = sut.login(loginRequestDTO);
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    void loginResponseHasEntityTest(){
        var response = sut.login(loginRequestDTO);
        assertTrue(response.hasEntity());
    }

    @Test
    void loginResponseInstanceOfTest(){
        var response = sut.login(loginRequestDTO);
        assertTrue(response instanceof Response);
    }
}
