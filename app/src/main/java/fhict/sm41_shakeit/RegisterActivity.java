package fhict.sm41_shakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import database.AsyncResponce;
import database.BackgroundWorker;
import domain.Register;

public class RegisterActivity extends AppCompatActivity implements AsyncResponce {

    String bwType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button btnRegistreren = (Button)findViewById(R.id.btnInloggen);
        Button btnTerug = (Button)findViewById(R.id.btnRegistreren);

        btnRegistreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = (EditText) findViewById(R.id.etName);
                EditText etPassword = (EditText) findViewById(R.id.etPassword);
                EditText etGeboortedatum = (EditText) findViewById(R.id.etGeboortedatum);

                handleRegister(etName.getText().toString(), etPassword.getText().toString(),etGeboortedatum.getText().toString());
            }
        });

        btnTerug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExit();
            }
        });
    }

    private void handleRegister(String name, String password , String geboortedatum) {
//        String[] input = new String[3];
//        input[0] = name;
//        input[1] = password;
//        input[2] = geboortedatum;

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String datum = sdf.format(geboortedatum);

        Register register = new Register(name,password,geboortedatum);

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bwType = "Register";

        bw.execute(bwType, register, null);
        handleExit();
    }

    @Override
    public void processFinish(String type, Object output) {
        System.out.println((String) output);
    }

    private void handleExit() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
