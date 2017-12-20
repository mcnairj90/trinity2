package com.matrixcare.trinity.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.matrixcare.trinity.Api.Azure;
import com.matrixcare.trinity.R;



public class LoginActivity extends AppCompatActivity {
    private static final String LOG = "LoginScreen";
    private static final Integer RequestCode = 0;
    EditText mUsername;
    EditText mPassword;
    ProgressBar mProgress;
    Button mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.usernameText);
        mPassword = findViewById(R.id.pwText);
        mLogin = findViewById(R.id.loginButton);
        mProgress = findViewById(R.id.progress_loader);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername.setError(null);
                mPassword.setError(null);
                String username = mUsername.getText().toString();

               // mProgress.setVisibility(View.VISIBLE);
              //  mLogin.setEnabled(false);
                String car = "471c7761-eb98-4832-a34e-15398957189e";
                Intent intent = MainActivity.newIntent(LoginActivity.this,car);
                startActivityForResult(intent, RequestCode);

            }
        });


    }


}

