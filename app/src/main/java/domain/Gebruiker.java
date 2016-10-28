package domain;

import java.util.Date;

/**
 * Created by stasiuz on 20/10/16.
 */

public class Gebruiker {
    private int id;
    private String naam;
    private Date geboorteDatum;
    private int wilOntmoeten;
    private int radius;
    private double budget;

    public Gebruiker(int id) { this.id = id; }

    public Gebruiker(int id, String naam, Date geboorteDatum, int wilOntmoeten, int radius, double budget) {
        this.id = id;
        this.naam = naam;
        this.geboorteDatum = geboorteDatum;
        this.wilOntmoeten = wilOntmoeten;
        this.radius = radius;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public int getWilOntmoeten() {
        return wilOntmoeten;
    }

    public int getRadius() {
        return radius;
    }

    public double getBudget() {
        return budget;
    }
}
