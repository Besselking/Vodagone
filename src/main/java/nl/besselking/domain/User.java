package nl.besselking.domain;

import java.security.SecureRandom;

public class User{
    private Integer id;
    private String user;
    private String password;
    private String token;
    private String firstName;
    private String lastname;

    public User(){
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void issueToken() {
        SecureRandom sr = new SecureRandom();
        token = String.format("%04d-%04d-%04d", sr.nextInt(1000), sr.nextInt(1000), sr.nextInt(1000));
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
