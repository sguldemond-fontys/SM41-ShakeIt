package fhict.sm41_shakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import database.AsyncResponce;
import database.BackgroundWorker;
import domain.Activiteit;
import logic.JSONDecoder;

/**
 * Created by Sander on 6-10-2016.
 */

public class AcceptActivity extends AppCompatActivity implements AsyncResponce {

    private GestureDetector gestureDetector;
    private List<Activiteit> activities;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceptactivity);
        activities = new ArrayList<>();
        getActivities();

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        index--;

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
                Intent intent = new Intent(AcceptActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

        }
        return false;
    }

    private void onRightSwipe() {
        //Toast toast = Toast.makeText(getApplicationContext(), "rightswipe", Toast.LENGTH_LONG);
        //toast.show();

        ImageView afbeelding = (ImageView) findViewById(R.id.activiteitAfbeelding);
        Picasso.with(getApplicationContext()).load(activities.get(index).getAfbeeldingUrl()).into(afbeelding);
        TextView naam = (TextView) findViewById(R.id.activiteitNaam);
        naam.setText(activities.get(index).getLocatie().getNaam());
        TextView straat = (TextView) findViewById(R.id.activiteitStraat);
        straat.setText(activities.get(index).getLocatie().getStraat() + " " +activities.get(index).getLocatie().getHuisnummer());
        TextView postcode = (TextView) findViewById(R.id.activiteitPostcode);
        postcode.setText(activities.get(index).getLocatie().getPostcode() + " " + activities.get(index).getLocatie().getPlaats());
        TextView TelNr = (TextView) findViewById(R.id.activiteitTelnr);
        TelNr.setText(activities.get(index).getLocatie().getTelefoonnummer());
        TextView site = (TextView) findViewById(R.id.activiteitSite);
        site.setText(activities.get(index).getLocatie().getWebsite());
        TextView prijs = (TextView) findViewById(R.id.activiteitPrijs);
        prijs.setText("€ " + Double.toString(activities.get(index).getPrijs()));

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
}
