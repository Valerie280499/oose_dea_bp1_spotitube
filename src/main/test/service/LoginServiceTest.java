package service;

import controllers.DTO.login.LoginRequestDTO;
import controllers.DTO.login.LoginResponseDTO;
import controllers.LoginController;
import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotAuthorizedException;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private LoginService sut;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    public void setUp(){
        sut = new LoginService();
        loginRequestDTO = new LoginRequestDTO();
    }
    @Test
    public void testCorrectLoginRequest(){
        loginRequestDTO.setUser("valerie");
        loginRequestDTO.setPassword("blaat");
        var loginRequest = sut.login(loginRequestDTO);

        Assertions.assertEquals("Hello", loginRequest.getToken());
    }

    @Test
    public void testInCorrectLogInRequest(){
        loginRequestDTO.setUser("Lauren");
        loginRequestDTO.setPassword("123");

        assertThrows(NotAuthorizedException.class, () -> {
            sut.login(loginRequestDTO);
        });    }
}