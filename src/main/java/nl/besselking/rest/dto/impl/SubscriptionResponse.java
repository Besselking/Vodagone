package nl.besselking.rest.dto.impl;

import nl.besselking.rest.dto.DTO;

public class SubscriptionResponse implements DTO {
    protected final Integer id;
    protected final String aanbieder;
    protected final String dienst;

    public SubscriptionResponse(Integer id, String aanbieder, String dienst) {
        this.id = id;
        this.aanbieder = aanbieder;
        this.dienst = dienst;
    }

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
