package service;

import controllers.DTO.login.LoginRequestDTO;
import controllers.DTO.login.LoginResponseDTO;

import javax.ws.rs.NotAuthorizedException;

public class LoginService {
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        if ("valerie".equals(loginRequestDTO.getUser()) && "blaat".equals(loginRequestDTO.getPassword())){

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setToken("Hello");
            loginResponseDTO.setUser("valerie");

            return loginResponseDTO;
        } else{
            throw new NotAuthorizedException(401);
        }
    }
}
