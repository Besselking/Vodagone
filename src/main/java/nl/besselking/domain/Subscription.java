package nl.besselking.domain;

public class Subscription {


    protected final Integer id;
    protected final String aanbieder;
    protected final String dienst;

    public Subscription(Integer id, String aanbieder, String dienst) {
        this.id = id;
        this.aanbieder = aanbieder;
        this.dienst = dienst;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", aanbieder='" + aanbieder + '\'' +
                ", dienst='" + dienst + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Subscription other = (Subscription) obj;
        return (id == other.id
        && aanbieder.equals(other.aanbieder)
        && dienst.equals(other.dienst));
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
