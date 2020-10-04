package controllers;

import controllers.DTO.login.LoginRequestDTO;
import controllers.DTO.login.LoginResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LoginService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginControllerTest {
    private LoginRequestDTO fakeLoginRequest;
    private LoginResponseDTO fakeLoginResponse;
    private LoginService fakeService;
    private LoginController sut;

    @BeforeEach
    void setUp(){
        fakeLoginRequest = new LoginRequestDTO();
        fakeLoginRequest.setUser("valerie");
        fakeLoginRequest.setPassword("blaat");

        fakeLoginResponse = new LoginResponseDTO();
        fakeLoginResponse.setUser("valerie");
        fakeLoginResponse.setToken("hello");

        sut = new LoginController();
    }

    @BeforeEach
    void setUpMockLoginService() {
        fakeService = mock(LoginService.class);
        when(fakeService.login(fakeLoginRequest)).thenReturn(fakeLoginResponse);
    }

    @Test
    void correctLoginTest() {
        sut.setLoginService(fakeService);

        var response = sut.login(fakeLoginRequest);

        Assertions.assertEquals(200, response.getStatus());

    }
}