package datasource.DAO.interfaces;

import dto.interfaces.IUserDTO;

public interface IUserDAO {
    IUserDTO getUser(String username);
}
