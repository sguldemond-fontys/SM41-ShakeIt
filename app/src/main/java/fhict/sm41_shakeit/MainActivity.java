package fhict.sm41_shakeit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;

import database.AsyncResponce;
import database.BackgroundWorker;
import logic.JSONDecoder;

public class MainActivity extends AppCompatActivity implements AsyncResponce {

    int gebruikerID = 0;

    EditText range;
    EditText budget;
    EditText tijd;
    Switch ontmoeten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        gebruikerID = intent.getIntExtra("gebruikerid", 0);
        System.out.println("GebruikersID in MainActivity: " + gebruikerID);

        Button continueButton = (Button) findViewById(R.id.continueButton);

        getVoorkeuren(gebruikerID);

        range = (EditText) findViewById(R.id.range);
        budget = (EditText) findViewById(R.id.budget);
        tijd = (EditText) findViewById(R.id.tijd);
        ontmoeten = (Switch) findViewById(R.id.ontmoeten);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text;
                int duration = Toast.LENGTH_SHORT;

                if(range.length() < 1) {
                    text = "Vul het maximale aantal km in";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if(budget.length() < 1) {
                    text = "Vul het maximale bedrag in";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if(tijd.length() < 1) {
                    text = "Vul de maximale lengte in";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else
                {
                    int ontmoetenInt = 0;
                    if(ontmoeten.isChecked()) {
                        ontmoetenInt = 1;
                    }

                    updateGebruiker(gebruikerID, ontmoetenInt, range.getText().toString(), budget.getText().toString(), tijd.getText().toString());

                    Intent intent = new Intent(MainActivity.this, ShakeActivity.class);
                    intent.putExtra("gebruikerid", gebruikerID);
                    startActivity(intent);
                }
            }
        });
    }

    private void updateGebruiker(int gebruikersid, int wilontmoeten, String kilometerrange, String budget, String tijd) {
        BackgroundWorker bw = new BackgroundWorker(this, this);

        String[] input = new String[5];
        input[0] = Integer.toString(gebruikersid);
        input[1] = Integer.toString(wilontmoeten);
        input[2] = kilometerrange;
        input[3] = budget;
        input[4] = tijd;

        bw.execute("update_gebruiker", input, null);
    }

    private void getVoorkeuren(int gebruikersid) {
        BackgroundWorker bw = new BackgroundWorker(this, this);

        bw.execute("get_voorkeuren", Integer.toString(gebruikersid), null);
    }

    @Override
    public void processFinish(String type, Object output) {
        if(type.equals("get_voorkeuren")) {
            try {
                String[] voorkeuren = JSONDecoder.decodeVoorkeuren((String)output);
                setVoorkeuren(voorkeuren);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setVoorkeuren(String[] voorkeuren) {
        if(voorkeuren[0].equals("1")) {
            ontmoeten.setChecked(true);
        }

        else {
            ontmoeten.setChecked(false);
        }

        if(!voorkeuren[1].equals("null")) {
            range.setText(voorkeuren[1]);
        }

        if(!voorkeuren[2].equals("null")) {
            budget.setText(voorkeuren[2]);
        }

        if(!voorkeuren[3].equals("null")) {
            tijd.setText(voorkeuren[3]);
        }
    }
}
