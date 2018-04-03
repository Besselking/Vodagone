package nl.besselking.rest.dto;

public class SubscriberResponse {
    private int id;
    private String name;
    private String email;

    public SubscriberResponse(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
