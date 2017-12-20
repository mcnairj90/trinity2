package com.matrixcare.trinity.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.matrixcare.trinity.Api.ApplicationData;
import com.matrixcare.trinity.Api.Azure;
import com.matrixcare.trinity.Fragments.SchedulesFragment;
import com.matrixcare.trinity.Interfaces.OnFragmentInteractionListener;
import com.matrixcare.trinity.R;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private static final String CaregiverIdKey = "CaregiverId";
    private String mCaregiverId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Azure.Initialize(this);
        mCaregiverId=getIntent().getStringExtra(CaregiverIdKey);
        ApplicationData.CaregiverId = mCaregiverId;
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        CreateScheduleListFragment(fm);
    }
    private void CreateScheduleListFragment(FragmentManager fm) {
        Fragment frag = fm.findFragmentById(R.id.schedule_container);
        if (frag==null) {
            frag = new SchedulesFragment();
            fm.beginTransaction().add(R.id.schedule_container,frag).commit();
        }
    }
    public static Intent newIntent(Context packageContext, String caregiverId) {
        Intent intent = new Intent(packageContext,MainActivity.class);
        intent.putExtra(CaregiverIdKey, caregiverId);
        return intent;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
