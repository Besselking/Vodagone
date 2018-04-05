package nl.besselking.rest.dto.impl;

import nl.besselking.rest.dto.DTO;

public class SubscriberResponse implements DTO {
    private int id;
    private String name;
    private String email;

    public SubscriberResponse(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
