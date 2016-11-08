package fhict.sm41_shakeit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import database.AsyncResponce;
import database.BackgroundWorker;

public class LoginActivity extends AppCompatActivity implements AsyncResponce {

    int gebruikersId;
    String bwType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gebruikersId = 0;

        Button btnSumbit = (Button)findViewById(R.id.btnInloggen);

        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = (EditText) findViewById(R.id.etName);
                EditText etPassword = (EditText) findViewById(R.id.etPassword);

                handleLogin(etName.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    private void handleLogin(String name, String password) {
        String[] input = new String[2];
        input[0] = name;
        input[1] = password;

        BackgroundWorker bw = new BackgroundWorker(this, this);

        bwType = "login";

        bw.execute(bwType, input, null);
    }

    @Override
    public void processFinish(String type, Object output) {
        if(type.equals(bwType)) {
            gebruikersId = Integer.parseInt((String)output);

            if(gebruikersId != 0) {
                handleExit();
            }
        }
    }

    private void handleExit() {
        Intent intent = new Intent(LoginActivity.this, ShakeActivity.class);
        intent.putExtra("gebruikersid", gebruikersId);
        startActivity(intent);
    }
}
