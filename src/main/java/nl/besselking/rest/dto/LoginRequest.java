package nl.besselking.rest.dto;

public class LoginRequest {
    private String user;
    private String password;

    public String getPassword() {
        return password;
    }
    public String getUser() {
        return user;
    }
}