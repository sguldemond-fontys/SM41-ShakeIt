package fhict.sm41_shakeit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import database.AsyncResponce;
import database.BackgroundWorker;
import domain.Activiteit;
import domain.Gebruiker;
import domain.Locatie;
import logic.DistanceLogic;
import logic.JSONDecoder;

/**
 * Created by Sander on 6-10-2016.
 */

public class activityActivity extends AppCompatActivity implements AsyncResponce {

    private GestureDetector gestureDetector;
    private List<Activiteit> activities;
    private double shakelat;
    private double shakelon;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        shakelat = intent.getDoubleExtra("shakelat",0);
        shakelon = intent.getDoubleExtra("shakelon",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        activities = new ArrayList<>();
        getActivities();


        Button accept = (Button) findViewById(R.id.acceptActivity);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activityActivity.this, AcceptActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });

        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        Button btnTerug = (Button) findViewById(R.id.btnTerug);

        btnTerug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityActivity.this, ShakeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getActivities() {
        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("get_activities", null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(activityActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void onRightSwipe() {
        //Toast toast = Toast.makeText(getApplicationContext(), "rightswipe", Toast.LENGTH_LONG);
        //toast.show();
        if(activities.size() > index)
        {
            double activiteitlat = activities.get(index).getLocatie().getLat();
            System.out.println(shakelat);
            double activiteitlon = activities.get(index).getLocatie().getLon();
            System.out.println(shakelon);
            DecimalFormat df = new DecimalFormat("0.##");

            Double afstand = DistanceLogic.distance(activiteitlat,activiteitlon,shakelat ,shakelon);


            ImageView afbeelding = (ImageView) findViewById(R.id.activiteitAfbeelding);
            Picasso.with(getApplicationContext()).load(activities.get(index).getAfbeeldingUrl()).into(afbeelding);
            TextView naam = (TextView) findViewById(R.id.activiteitNaam);
            naam.setText(activities.get(index).getNaam());
            TextView straat = (TextView) findViewById(R.id.activiteitStraat);
            straat.setText(df.format(afstand) + " km");

            index++;
        }

    }

    @Override
    public void processFinish(String type, Object output) {
        try {
            activities = JSONDecoder.decodeAllActivitiesJSON((String)output);

            onRightSwipe();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class SwipeGestureDetector extends SimpleOnGestureListener {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            try {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;
                if (-diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    activityActivity.this.onRightSwipe();
                }
            } catch (Exception e) {

            }
            return false;
        }
    }
}
