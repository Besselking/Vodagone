package nl.besselking.rest.dto.impl;

import nl.besselking.domain.Subscription;
import nl.besselking.rest.dto.DTO;

import java.util.List;

public class SubscriptionListResponse implements DTO {
    private List<Subscription> abonnementen;
    private final double totalPrice;

    public SubscriptionListResponse(List<Subscription> abonnementen, double totalPrice) {
        this.abonnementen = abonnementen;
        this.totalPrice = totalPrice;
    }

    public List<Subscription> getAbonnementen() {
        return abonnementen;
    }

    public void setAbonnementen(List<Subscription> abonnementen) {
        this.abonnementen = abonnementen;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
