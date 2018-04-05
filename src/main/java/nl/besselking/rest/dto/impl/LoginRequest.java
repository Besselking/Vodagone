package nl.besselking.rest.dto.impl;

import nl.besselking.rest.dto.DTO;

public class LoginRequest implements DTO {
    private String user;
    private String password;

    public LoginRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public LoginRequest() {

    }

    public String getPassword() {
        return password;
    }
    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
