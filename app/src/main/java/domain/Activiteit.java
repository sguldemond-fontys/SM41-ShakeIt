package domain;

/**
 * Created by Stan Guldemond on 20/10/16.
 */

public class Activiteit {
    private int id;
    private String naam;
    private double prijs;
    private int duur;
    private Locatie locatie;
    private String afbeeldingUrl;

    public Activiteit(int id, String naam, double prijs, int duur, Locatie locatie, String afbeeldingUrl) {
        this.id = id;
        this.naam = naam;
        this.prijs = prijs;
        this.duur = duur;
        this.locatie = locatie;
        this.afbeeldingUrl = afbeeldingUrl;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public double getPrijs() {
        return prijs;
    }

    public int getDuur() {
        return duur;
    }

    public Locatie getLocatie() {
        return locatie;
    }

    public String getAfbeeldingUrl() {
        return afbeeldingUrl;
    }
}
