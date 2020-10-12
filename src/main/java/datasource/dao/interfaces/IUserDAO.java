package datasource.dao.interfaces;

import domain.interfaces.IUser;

import java.util.Optional;

public interface IUserDAO {
    Optional<IUser> getUser(String username);
}
