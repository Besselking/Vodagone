package nl.besselking.dto;

public class SubscriptionRequest {

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
