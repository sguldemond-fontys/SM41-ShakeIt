package fhict.sm41_shakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import database.AsyncResponce;
import database.BackgroundWorker;

public class MatchActivity extends AppCompatActivity implements AsyncResponce {

    int MeetingID;
    int GebruikerID;
    int ActiviteitID;
    Timer t = new Timer();
    String Naam;
    int timerindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchactivity);

        MeetingID = 0;
        Intent intent = getIntent();
        GebruikerID = intent.getIntExtra("gebruikerid", 0);
        ActiviteitID = intent.getIntExtra("activiteitid", 0);
        handleFindMeeting(ActiviteitID);

    }

    private void handleFindMeeting(int activiteitID) {
        String[] input = new String[1];
        input[0] = Integer.toString(activiteitID);

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("find_meeting", input, null);
    }

    private void handleUpdateMeeting(int gebruikerID, int meetingID) {
        String[] input = new String[2];
        input[0] = Integer.toString(gebruikerID);
        input[1] = Integer.toString(meetingID);
        System.out.println(input[0]);
        System.out.println(input[1]);

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("update_meeting", input, null);
    }

    private void handleCreateMeeting(int gebruikerID, int activiteitID, String datum) {
        String[] input = new String[3];
        input[0] = Integer.toString(gebruikerID);
        input[1] = Integer.toString(activiteitID);
        input[2] = datum;

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("meeting", input, null);
    }

    public void handleGetMeetingID(int gebruikerID , int activiteitID) {
        String[] input = new String[2];
        input[0] = Integer.toString(GebruikerID);
        input[1] = Integer.toString(ActiviteitID);

        BackgroundWorker bw = new BackgroundWorker(this, this);
        bw.execute("get_meetingid", input, null);
    }

    public void handleFindFirstuser(int meetingID) {

        BackgroundWorker bw = new BackgroundWorker(this, this);
        bw.execute("get_firstuser",Integer.toString(meetingID), null);
    }

    public void handleFindSeconduser(String meetingID) {

        BackgroundWorker bw = new BackgroundWorker(this, this);
        bw.execute("get_seconduser", meetingID, null);
    }

    @Override
    public void processFinish(String type, Object output) {
        if (type.equals("find_meeting")) {
            if (output == null){
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String currentTime = sdf.format(now);

                handleCreateMeeting(GebruikerID, ActiviteitID, currentTime);
                handleExit(2);
            }

            else {
                MeetingID = Integer.parseInt((String) output);
                handleUpdateMeeting(GebruikerID, MeetingID);
                handleFindFirstuser(MeetingID);
            }
        }


        else if (type.equals("check_meeting")){
            if (output != null) {
                System.out.println(output);
                t.cancel();
                t.purge();
                handleFindSeconduser((String)output);
            }
            else{
                handleExit(2);
            }
        }

        else if (type.equals("get_firstuser")){
            Naam = (String)output;
            System.out.println("firstuser: " + output);
            handleExit(1);
        }

        else if (type.equals("get_meetingid")){
            MeetingID = Integer.parseInt((String)output);
        }

        else if (type.equals("get_seconduser")){
            Naam = (String)output;
            System.out.println("seconduser: " +output);
            handleExit(1);
        }
    }

    private void handleExit(int number) {

        if (number == 1) {
            TextView textView = (TextView) findViewById(R.id.tvMatch);
            textView.setText("Match gevonden!");
            TextView textView2 = (TextView) findViewById(R.id.tvNaam);
            textView2.setText("je hebt een match met: " + Naam);
            System.out.println("je hebt een match met: " + Naam);
        }
        else if (number == 2) {
            TextView textView = (TextView) findViewById(R.id.tvMatch);
            textView.setText("Wachten op match!");
            //start timer
            t.schedule(new matchTimer(), 0, 5000);
        }

    }
    public class matchTimer extends TimerTask {
        public void run() {
            handleCheckMeeting(GebruikerID);
            timerindex++;
            System.out.println(timerindex);
        }
    }

    public void handleCheckMeeting(int gebruikerID) {
        String[] input = new String[1];
        input[0] = Integer.toString(gebruikerID);

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("check_meeting", input, null);
    }

}
