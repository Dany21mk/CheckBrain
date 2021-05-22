package space.mosk.checkbrain.Models;

import java.util.List;

public class User {
    private String email, username, pass;

    public User(){}

    public User(String email, String username, String pass) {
        this.email = email;
        this.username = username;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
