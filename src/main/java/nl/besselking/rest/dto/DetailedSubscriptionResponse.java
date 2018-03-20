package nl.besselking.rest.dto;

import nl.besselking.domain.UserSubscription;

import java.util.Date;

public class DetailedSubscriptionResponse {
    private final Integer id;
    private final String aanbieder;
    private final String dienst;
    private String prijs;
    private String startDatum;
    private String verdubbeling;
    private Boolean deelbaar;
    private String status;

    public DetailedSubscriptionResponse(
            Integer id, String aanbieder, String dienst,
            double prijs, Date startDatum, String verdubbeling, Boolean deelbaar, String status) {
        this.id = id;
        this.aanbieder = aanbieder;
        this.dienst = dienst;
        this.prijs = String.format("€%.2f per maand", prijs);
        this.startDatum = startDatum.toString();
        this.verdubbeling = verdubbeling;
        this.deelbaar = deelbaar;
        this.status = status;
    }
    public DetailedSubscriptionResponse(UserSubscription us) {
        this.id = us.getId();
        this.aanbieder = us.getAanbieder();
        this.dienst = us.getDienst();
        this.prijs = String.format("€%.2f per maand", us.getPrijs());
        this.startDatum = us.getStartDatum().toString();
        this.verdubbeling = us.getVerdubbeling();
        this.deelbaar = us.getDeelbaar();
        this.status = us.getStatus();
    }
}
