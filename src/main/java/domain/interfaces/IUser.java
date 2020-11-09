package domain.interfaces;

public interface IUser {
    String getUsername();
    String getPassword();
    void setPassword(String password);
    int getToken();
}
