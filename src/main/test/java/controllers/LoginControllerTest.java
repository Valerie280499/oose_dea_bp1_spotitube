package java.controllers;

import controllers.DTO.LoginRequestDTO;
import controllers.LoginController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginControllerTest {
    private LoginController sut;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    public void setUp(){
        sut = new LoginController();
        loginRequestDTO = new LoginRequestDTO();
    }
    @Test
    public void testCorrectLoginRequest(){
        loginRequestDTO.setUser("valerie");
        loginRequestDTO.setPassword("blaat");
        var response = sut.login(loginRequestDTO);

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testInCorrectLogInRequest(){
        loginRequestDTO.setUser("Lauren");
        loginRequestDTO.setPassword("123");
        var response = sut.login(loginRequestDTO);

        Assertions.assertEquals(401, response.getStatus());
    }
}
