package service.interfaces;

import domain.interfaces.IUser;

public interface ILoginService {
    IUser getUser(String username);
}
