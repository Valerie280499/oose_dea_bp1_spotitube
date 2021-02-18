package datasource.DAO.interfaces;

import domain.interfaces.IUser;

public interface IUserDAO {
    IUser getUser(String username);
}
