package domain;

import android.location.Location;

/**
 * Created by stasiuz on 20/10/16.
 */
public class Locatie {

    private String naam;
    private String plaats;
    private String postcode;
    private String straat;
    private String huisnummer;
    private double lat;
    private double lon;

    public Locatie(String naam, String plaats, String postcode, String straat, String huisnummer, double lat, double lon) {
        this.naam = naam;
        this.plaats = plaats;
        this.postcode = postcode;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.lat = lat;
        this.lon = lon;
    }

    public String getNaam() {
        return naam;
    }

    public String getPlaats() {
        return plaats;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStraat() {
        return straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }
}
