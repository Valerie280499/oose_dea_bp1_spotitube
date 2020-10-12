package domain.interfaces;

public interface IUser {
    int getUser_id();
    String getUsername();
    String getPassword();
    void setPassword(String password);
}
