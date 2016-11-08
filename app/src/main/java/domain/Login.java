package domain;

/**
 * Created by stasiuz on 20/10/16.
 */

public class Login {
    private String naam;
    private String wachtwoord;


    public Login(String naam, String wachtwoord) {
        this.naam = naam;
        this.wachtwoord = wachtwoord;
    }

    public String getNaam() {
        return naam;
    }


    public String getWachtwoord() {
        return wachtwoord;
    }
}
