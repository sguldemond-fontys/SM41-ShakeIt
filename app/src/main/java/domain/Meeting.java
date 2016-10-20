package domain;

import java.util.Date;

/**
 * Created by stasiuz on 20/10/16.
 */

public class Meeting {
    private Gebruiker gebruiker1;
    private Gebruiker gebruiker2;
    private Activiteit activiteit;
    private String datumTijd;

    public Meeting(Gebruiker gebruiker, Activiteit activiteit, String datumTijd) {
        this.gebruiker1 = gebruiker;
        this.activiteit = activiteit;
        this.datumTijd = datumTijd;
    }

    public Gebruiker getGebruiker1() {
        return gebruiker1;
    }

    public Activiteit getActiviteit() {
        return activiteit;
    }

    public String getDatumTijd() {
        return datumTijd;
    }

    public Gebruiker getGebruiker2() {
        return gebruiker2;
    }
}
