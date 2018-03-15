package nl.besselking.domain;

import java.sql.Date;

public class UserSubscription extends Subscription {
    private Date startDatum;
    private String verdubbeling;
    private String status;

    public UserSubscription(
            Integer id, String aanbieder, String dienst,
            Date startDatum, String verdubbeling, String status) {
        super(id, aanbieder, dienst);
        this.startDatum = startDatum;
        this.verdubbeling = verdubbeling;
        this.status = status;
    }
}
