package nl.besselking.rest.dto;

public class LoginResponse {
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
