package nl.besselking.rest.dto.impl;

import nl.besselking.rest.dto.DTO;

public class LoginResponse implements DTO {
    private String user;
    private String token;

    public LoginResponse(String firstname, String lastname, String token) {
        this.user = firstname + " " + lastname;
        this.token = token;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
