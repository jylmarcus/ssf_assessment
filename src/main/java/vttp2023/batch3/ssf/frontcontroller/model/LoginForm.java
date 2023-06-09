package vttp2023.batch3.ssf.frontcontroller.model;

import jakarta.validation.constraints.Size;

public class LoginForm {

    @Size(min=2, message="username must be at least 2 characters")
    private String username;

    @Size(min=2, message="password must be at least 2 characters")
    private String password;

    public LoginForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
