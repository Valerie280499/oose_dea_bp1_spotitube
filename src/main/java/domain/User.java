package domain;

import domain.interfaces.IUser;

public class User implements IUser {
    private String username;
    private String password;
    private int token;

    public User(String username, String password, int token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) { this.password = password; }

    @Override
    public int getToken() { return token; }
}
