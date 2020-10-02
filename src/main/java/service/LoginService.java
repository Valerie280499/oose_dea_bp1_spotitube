package service;

import controllers.DTO.LoginRequestDTO;
import controllers.DTO.LoginResponseDTO;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

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
