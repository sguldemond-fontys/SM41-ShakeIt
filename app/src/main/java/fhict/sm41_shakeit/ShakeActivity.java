package fhict.sm41_shakeit;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

import database.BackgroundWorker;
import domain.Activiteit;
import domain.Gebruiker;
import domain.Locatie;
import domain.Meeting;
import domain.Shake;

/**
 * Created by Sander on 6-10-2016.
 */

public class ShakeActivity extends AppCompatActivity implements LocationListener{

    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Boolean startup = true;

    double currentLatitude;
    double currentLongitude;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {

                    if(startup != true) {
                        Intent intent = new Intent(ShakeActivity.this, activityActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        startup = false;
                    }
            }
        });

        Button btnShake = (Button) findViewById(R.id.button);

        btnShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addShake();
                addMeeting();
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

        BackgroundWorker bw = new BackgroundWorker(this);
        bw.execute(type, shake, null);
    }

    private void addMeeting() {
        Locatie locatie = new Locatie("karten centrum", "eindhoven", "1234AB", "kerkstraat", "1", 51.4555001, 5.4805959);
        Activiteit activiteit = new Activiteit(1, "karten", 2.00, 120, locatie);
        Gebruiker gebruiker = new Gebruiker(1, "Stan Guldemond", new Date(1991, 9, 3), 1, 1000, 100);


        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(now);

        Meeting meeting = new Meeting(gebruiker, activiteit, currentTime);

        BackgroundWorker bw = new BackgroundWorker(this);

        bw.execute("meeting", meeting, null);
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
    protected void onResume() {
        // OnResume is called any time that the application begins again.
        super.onResume();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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
}
