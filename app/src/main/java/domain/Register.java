package domain;

import java.util.Date;

/**
 * Created by Marc on 08-11-2016.
 */

public class Register {
    private String naam;
    private String wachtwoord;
    private String geboorteDatum;


    public Register(String naam, String wachtwoord, String geboorteDatum) {

        this.naam = naam;
        this.wachtwoord = wachtwoord;
        this.geboorteDatum = geboorteDatum;
    }

    public String getNaam() {
        return naam;
    }

    public String getGeboorteDatum() {
        return geboorteDatum;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }
}
