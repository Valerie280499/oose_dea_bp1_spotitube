package services;

import datasource.errors.UserNotFoundError;
import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import datasource.dao.UserDAO;
import dto.UserDTO;
import javax.inject.Inject;

import static java.util.UUID.randomUUID;

public class LoginService {

    @Inject
    protected UserDAO userDAO = new UserDAO();

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var user = getUser(loginRequestDTO.getUser());

        if (user.getUsername().equals(loginRequestDTO.getUser()) && user.getPassword().equals(loginRequestDTO.getPassword())) {
            var token = updateTokenForUser(user.getUsername());

            var loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setToken(token);
            loginResponseDTO.setUser(user.getUsername());

            return loginResponseDTO;
        }
        throw new UserNotFoundError();
    }

    private String updateTokenForUser(String username) {
        var token = "uuid"+randomUUID().toString();
        userDAO.updateTokenForUser(username, token);
        return token;
    }

    public UserDTO getUser(String username){
        return userDAO.getUser(username);
    }
}
