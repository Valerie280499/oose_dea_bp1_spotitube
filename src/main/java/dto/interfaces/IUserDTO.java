package dto.interfaces;

public interface IUserDTO {
    String getUsername();
    String getPassword();
    void setPassword(String password);
    int getToken();
}
