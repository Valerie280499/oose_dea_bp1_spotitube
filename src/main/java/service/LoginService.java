package service;

import controllers.DTO.login.LoginRequestDTO;
import controllers.DTO.login.LoginResponseDTO;
import database.DatabaseConnection;
import database.Queries;

import javax.ws.rs.NotAuthorizedException;

public class LoginService {
    DatabaseConnection dbConn = new DatabaseConnection();
    Queries queries = new Queries();

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        var query = queries.selectAllFromUserTable();
        dbConn.connectToDatabase(query);

        if ("valerie".equals(loginRequestDTO.getUser()) && "blaat".equals(loginRequestDTO.getPassword())){

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setToken("Hello");
            loginResponseDTO.setUser("valerie");

            return loginResponseDTO;
        } else{
            throw new NotAuthorizedException(401);
        }
    }

//    if ("valerie".equals(loginRequestDTO.getUser()) && "blaat".equals(loginRequestDTO.getPassword())){
//
//        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
//        loginResponseDTO.setToken("Hello");
//        loginResponseDTO.setUser("valerie");
//
//        return loginResponseDTO;
//    } else{
//        throw new NotAuthorizedException(401);
//    }
}
