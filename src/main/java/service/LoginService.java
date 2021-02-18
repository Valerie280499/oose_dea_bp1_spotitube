package service;

import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import datasource.DAO.UserDAO;
import domain.interfaces.IUser;
import service.interfaces.ILoginService;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;

public class LoginService implements ILoginService {

    @Inject
    protected UserDAO userDAO = new UserDAO();

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var user = getUser(loginRequestDTO.getUser());

        if (user.getUsername().equals(loginRequestDTO.getUser()) && user.getPassword().equals(loginRequestDTO.getPassword())) {
            var loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setToken("Hello");
            loginResponseDTO.setUser(user.getUsername());

            return loginResponseDTO;
        }
        throw new NotAuthorizedException(401);
    }

    @Override
    public IUser getUser(String username){
        var user = userDAO.getUser(username);
        return user;
    }
}
