package fhict.sm41_shakeit;

import java.util.TimerTask;

/**
 * Created by Sander on 9-11-2016.
 */

public class matchTimer extends TimerTask {
    public void run() {
        MatchActivity ma = new MatchActivity();
        ma.handeCheckMeeting();
    }
}
