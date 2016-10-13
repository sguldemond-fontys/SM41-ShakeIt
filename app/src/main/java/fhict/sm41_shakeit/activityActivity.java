package fhict.sm41_shakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sander on 6-10-2016.
 */

public class activityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

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
                Intent intent = new Intent(activityActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
