package logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import domain.Activiteit;
import domain.Locatie;

/**
 * Created by stasiuz on 21/10/16.
 */

public class JSONDecoder {

    public static List<Activiteit> decodeAllActivitiesJSON(String json) throws JSONException {
        List<Activiteit> activities = new ArrayList<>();

        JSONArray array = new JSONArray(json);

        int n = array.length();

        for(int i = 0; i < n; ++i) {
            JSONObject j = array.getJSONObject(i);
            Activiteit a = new Activiteit(j.getInt("ActiviteitID"), j.getString("Naam"), j.getDouble("Prijs"), j.getInt("Duur"), new Locatie(j.getString("Bedrijfsnaam"), j.getString("Plaats"), j.getString("Postcode"), j.getString("Straat"), j.getString("Huisnummer"), j.getDouble("Latitude"), j.getDouble("Longitude")));
            activities.add(a);
        }

        return activities;
    }
}
