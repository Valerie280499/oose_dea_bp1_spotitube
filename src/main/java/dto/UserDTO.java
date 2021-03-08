package dto;

public class UserDTO {
    private String username;
    private String password;
    private String token;

    public UserDTO(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getToken() { return token; }
}
