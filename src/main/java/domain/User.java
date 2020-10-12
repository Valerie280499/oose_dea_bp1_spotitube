package domain;

import domain.interfaces.IUser;

public class User implements IUser {
    private int user_id;
    private String username;
    private String password;


    public User(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    @Override
    public int getUser_id(){ return user_id; }

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
}
