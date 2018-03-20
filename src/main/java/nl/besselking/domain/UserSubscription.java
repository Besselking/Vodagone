package nl.besselking.domain;

import java.sql.Date;

public class UserSubscription extends Subscription {
    private double prijs;
    private Date startDatum;
    private String verdubbeling;
    private Boolean deelbaar;
    private String status;

    public UserSubscription(
            Integer id, String aanbieder, String dienst,
            double prijs, Date startDatum, String verdubbeling, Boolean deelbaar, String status) {
        super(id, aanbieder, dienst);
        this.prijs = prijs;
        this.startDatum = startDatum;
        this.verdubbeling = verdubbeling;
        this.deelbaar = deelbaar;
        this.status = status;
    }

    public double getPrijs() {
        return prijs;
    }

    public Date getStartDatum() {
        return startDatum;
    }

    public String getVerdubbeling() {
        return verdubbeling;
    }

    public Boolean getDeelbaar() {
        return deelbaar;
    }

    public String getStatus() {
        return status;
    }
}
