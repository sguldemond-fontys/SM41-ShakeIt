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

import java.util.ArrayList;
import java.util.List;

import database.AsyncResponce;
import database.BackgroundWorker;
import domain.Activiteit;
import domain.Locatie;
import logic.JSONDecoder;

/**
 * Created by Sander on 6-10-2016.
 */

public class activityActivity extends AppCompatActivity implements AsyncResponce {

    private GestureDetector gestureDetector;
    private List<Activiteit> activities;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        activities = new ArrayList<>();
        getActivities();

        Button deny = (Button) findViewById(R.id.denyActivity);
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activityActivity.this, ShakeActivity.class);
                startActivity(intent);
            }
        });

        Button accept = (Button) findViewById(R.id.acceptActivity);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        gestureDetector = new GestureDetector(new SwipeGestureDetector());
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
        Toast toast = Toast.makeText(getApplicationContext(), "rightswipe", Toast.LENGTH_LONG);
        toast.show();
        if(activities.size() > index)
        {
            ImageView afbeelding = (ImageView) findViewById(R.id.activiteitAfbeelding);
            Picasso.with(getApplicationContext()).load(activities.get(index).getAfbeeldingUrl()).into(afbeelding);
            TextView naam = (TextView) findViewById(R.id.activiteitNaam);
            naam.setText(activities.get(index).getLocatie().getNaam());
            TextView straat = (TextView) findViewById(R.id.activiteitStraat);
            straat.setText(activities.get(index).getLocatie().getStraat() + " " +activities.get(index).getLocatie().getHuisnummer());
            TextView postcode = (TextView) findViewById(R.id.activiteitPostcode);
            postcode.setText(activities.get(index).getLocatie().getPostcode() + " " + activities.get(index).getLocatie().getPlaats());
            TextView telnr = (TextView) findViewById(R.id.activiteitTelnr);
            telnr.setText(activities.get(index).getNaam());
            TextView site = (TextView) findViewById(R.id.activiteitSite);
            site.setText(activities.get(index).getNaam());
            TextView prijs = (TextView) findViewById(R.id.activiteitPrijs);
            prijs.setText(Double.toString(activities.get(index).getPrijs()));
            index++;
        }

    }

    @Override
    public void processFinish(Object output) {
        try {
            activities = JSONDecoder.decodeAllActivitiesJSON((String)output);
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
