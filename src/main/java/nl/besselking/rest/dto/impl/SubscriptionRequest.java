package nl.besselking.rest.dto.impl;

import nl.besselking.rest.dto.DTO;

public class SubscriptionRequest implements DTO {

    private Integer id;
    private String aanbieder;
    private String dienst;

    public Integer getId() {
        return id;
    }

    public String getAanbieder() {
        return aanbieder;
    }

    public String getDienst() {
        return dienst;
    }
}
