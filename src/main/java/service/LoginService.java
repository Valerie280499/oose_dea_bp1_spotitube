package service;

import controllers.DTO.login.LoginRequestDTO;
import controllers.DTO.login.LoginResponseDTO;
import datasource.DAO.UserDAO;
import domain.interfaces.IUser;
import service.interfaces.ILoginService;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import java.util.Optional;

public class LoginService implements ILoginService {

    @Inject
    // protected want kan anders niet testen @Jailbreak werkte niet
    protected UserDAO userDAO = new UserDAO();

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        IUser user = getUser(loginRequestDTO.getUser());

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
        Optional<IUser> user = userDAO.getUser(username);
        return user.orElseThrow(() -> new NotAuthorizedException(401));
    }
}