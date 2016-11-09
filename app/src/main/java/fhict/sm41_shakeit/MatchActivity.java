package fhict.sm41_shakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import database.AsyncResponce;
import database.BackgroundWorker;

public class MatchActivity extends AppCompatActivity implements AsyncResponce {

    int MeetingID;
    int GebruikerID;
    int ActiviteitID;
    String Datum;
    String bwType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchactivity);

        MeetingID = 0;
        Intent intent = getIntent();
        GebruikerID = intent.getIntExtra("gebruikerid", 0);
        ActiviteitID = intent.getIntExtra("activiteitid", 0);
        handleFindMeeting(ActiviteitID);

//        Button btnSumbit = (Button)findViewById(R.id.btnInloggen);
//        Button btnRegister = (Button)findViewById(R.id.btnRegistreren);
//
//
//        btnSumbit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText etName = (EditText) findViewById(R.id.etName);
//                EditText etPassword = (EditText) findViewById(R.id.etPassword);
//
//
//            }
//        });
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MatchActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void handleFindMeeting(int activiteitID) {
        String[] input = new String[1];
        input[0] = String.valueOf(activiteitID);

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
        input[0] = String.valueOf(gebruikerID);
        input[1] = String.valueOf(activiteitID);
        input[2] = String.valueOf(datum);

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("meeting", input, null);
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
                    handleExit(1);

            }
        }
    }

    private void handleExit(int number) {
        if (number == 1) {
            TextView textView = (TextView) findViewById(R.id.tvMatch);
            textView.setText("Match gevonden!");
        }
        else if (number == 2){
            TextView textView = (TextView) findViewById(R.id.tvMatch);
            textView.setText("Wachten op match!");
        }
    }
}
