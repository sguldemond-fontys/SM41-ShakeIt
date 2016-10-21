package logic;

import java.util.List;
import domain.Activiteit;
import domain.Gebruiker;
import domain.Shake;

/**
 * Created by Marc on 20-10-2016.
 */
public class DistanceLogic {

    public List<Activiteit> distancecheck(List<Activiteit> activiteitlist , Shake shake , Gebruiker gebruiker) {
        for (Activiteit activiteit : activiteitlist) {
            double activiteitlat = activiteit.getLocatie().getLat();
            double activiteitlon = activiteit.getLocatie().getLon();
            double gebruikerlat = shake.getLatitude();
            double gebruikerlon = shake.getLongitude();

            double distance = distance(activiteitlat, activiteitlon, gebruikerlat, gebruikerlon);

            if (distance > gebruiker.getRadius()) {
                activiteitlist.remove(activiteit);
            }
        }
        return activiteitlist;
    }


    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

}