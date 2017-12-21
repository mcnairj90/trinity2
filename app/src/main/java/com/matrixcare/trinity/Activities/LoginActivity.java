package com.matrixcare.trinity.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.matrixcare.trinity.Api.ApplicationData;
import com.matrixcare.trinity.Api.Azure;
import com.matrixcare.trinity.Models.Caregiver;
import com.matrixcare.trinity.R;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;


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
                String car = "73ebfedc-1460-4a17-a6fb-4c19d8ebcd2c";
                ApplicationData.CaregiverId = car;

                Intent intent = MainActivity.newIntent(LoginActivity.this,car);
                startActivityForResult(intent, RequestCode);

                Azure.Initialize(LoginActivity.this);
                //Once user has logged in get the FirebaseInstanceId and update the Caregiver record
                String deviceId = FirebaseInstanceId.getInstance().getId();
                new CaregiverDeviceTokenUpdate(LoginActivity.this).execute(ApplicationData.CaregiverId, deviceId);

            }
        });


    }

    private class CaregiverDeviceTokenUpdate extends AsyncTask<String, Void, Void> {
        private Context mContext;

        CaregiverDeviceTokenUpdate(Context context){
            mContext = context;
        }
        @Override
        protected Void doInBackground(String... params){

            MobileServiceTable<Caregiver> table;
            table = Azure.getClient().getTable(Caregiver.class);
            try {
                Caregiver car = table.where().field("id").eq(params[0]).execute().get().get(0);
                car.setDeviceToken(params[1]);
                table.update(car);

            } catch (Exception ex) {
                Log.d(LOG,ex.getMessage());
            }

            return null;
        }
    }


   // private String GetCaregiver(String name) {
   // }

}

