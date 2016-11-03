package fhict.sm41_shakeit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.view.GestureDetector;

import database.AsyncResponce;
import database.BackgroundWorker;
import domain.Activiteit;
import domain.Gebruiker;
import domain.Locatie;
import domain.Meeting;
import domain.Shake;
import logic.JSONDecoder;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by Sander on 6-10-2016.
 */

public class ShakeActivity extends AppCompatActivity implements LocationListener, AsyncResponce {

    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private List<Activiteit> activities;
    double currentLatitude;
    double currentLongitude;
    private Boolean startup = true;
    private int index;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake);
        activities = new ArrayList<>();
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                        Intent intent = new Intent(ShakeActivity.this, activityActivity.class);
                        intent.putExtra("shakelat",currentLatitude);
                        intent.putExtra("shakelon",currentLatitude);
                        startActivity(intent);
            }
        });

        Button btnShake = (Button) findViewById(R.id.button);

        btnShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startup != true) {
                //addShake();
                //addMeeting();
                //findMeeting();
                getActivities();

                Intent intent = new Intent(ShakeActivity.this, activityActivity.class);
                intent.putExtra("shakelat",currentLatitude);
                System.out.println(currentLatitude);
                intent.putExtra("shakelon",currentLatitude);
                System.out.println(currentLatitude);
                startActivity(intent);
                }
                else
                {
                    startup = false;
                }
            }
        });

        Button btnVoorkeuren = (Button) findViewById(R.id.button2);

        btnVoorkeuren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShakeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void addShake() {

        String type = "shake";

        Date now = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(now);

        System.out.println(currentTime);
        System.out.println(currentLatitude);
        System.out.println(currentLongitude);

        Shake shake = new Shake(2, (float)currentLatitude, (float)currentLongitude, currentTime);

        BackgroundWorker bw = new BackgroundWorker(this, this);
        bw.execute(type, shake, null);
    }

    private void addMeeting() {
        Locatie locatie = new Locatie("karten centrum", "eindhoven", "1234AB", "kerkstraat", "1", 51.4555001, 5.4805959 , "karten.nl" , "040123532");
        Activiteit activiteit = new Activiteit(1, "karten", 2.00, 120, locatie, "http://imageshack.com/a/img923/851/INsNtf.jpg");
        Gebruiker gebruiker = new Gebruiker(1, "Stan Guldemond", new Date(1991, 9, 3), 1, 1000, 100);


        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(now);

        Meeting meeting = new Meeting(gebruiker, activiteit, currentTime);

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("meeting", meeting, null);
    }

    private void findMeeting() {
        Locatie locatie = new Locatie("karten centrum", "eindhoven", "1234AB", "kerkstraat", "1", 51.4555001, 5.4805959, "karten.nl" , "040123532");
        Activiteit activiteit1 = new Activiteit(1, "karten", 2.00, 120, locatie, null);

        Activiteit activiteit2 = new Activiteit(2, "bowlen", 2.00, 120, locatie, null);

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("find_meeting", activiteit1, null);

    }

    private void getActivities() {
        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("get_activities", null, null);

    }


    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        System.out.println("Location changed to: " + currentLatitude + ", " + currentLongitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
                Intent intent = new Intent(ShakeActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

        }
        return false;
    }

    @Override
    protected void onResume() {
        // OnResume is called any time that the application begins again.
        super.onResume();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onStop() {
        // OnStop is called whenever a new activity is started.
        super.onStop();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.removeUpdates(this);
    }


    @Override
    public void processFinish(String type, Object output) {
        System.out.println((String) output);

    }
}
