package com.matrixcare.trinity.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.matrixcare.trinity.Api.ApplicationData;
import com.matrixcare.trinity.Api.Azure;
import com.matrixcare.trinity.Api.ScheduleListApi;
import com.matrixcare.trinity.Fragments.SchedulesFragment;
import com.matrixcare.trinity.Interfaces.OnFragmentInteractionListener;
import com.matrixcare.trinity.Models.Caregiver;
import com.matrixcare.trinity.Models.Schedule;
import com.matrixcare.trinity.R;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private static final String CaregiverIdKey = "CaregiverId";
    private static final String LOG = "MainActivity";
    private String mCaregiverId;
    private ImageView mCaregiverImage;
    private TextView mCaregiverName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Azure.Initialize(MainActivity.this);
        //mCaregiverId=getIntent().getStringExtra(CaregiverIdKey);
       // ApplicationData.CaregiverId = mCaregiverId;
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        CreateScheduleListFragment(fm);
        mCaregiverImage = findViewById(R.id.caregiver_image);
        mCaregiverName = findViewById(R.id.caregiver_name);
        new ScheduleList(MainActivity.this).execute(ApplicationData.CaregiverId);
        new CaregiverData(MainActivity.this).execute(ApplicationData.CaregiverId);
    }
    private void CreateScheduleListFragment(FragmentManager fm) {
        Fragment frag = fm.findFragmentById(R.id.schedule_container);
        if (frag!=null) {
            fm.beginTransaction().remove(frag).commit();
        }
        frag = new SchedulesFragment();
        fm.beginTransaction().add(R.id.schedule_container,frag).commit();

    }
    public static Intent newIntent(Context packageContext, String caregiverId) {
        Intent intent = new Intent(packageContext,MainActivity.class);
        intent.putExtra(CaregiverIdKey, caregiverId);
        return intent;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class GetCaregiverPicture extends AsyncTask<String, Void, Bitmap> {
        private Context mContext;
        GetCaregiverPicture(Context context){
            mContext = context;
        }
        @Override
        protected Bitmap doInBackground(String... params){
            ScheduleListApi api = new ScheduleListApi(mContext);
            return api.GetImage(params[0]);
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            mCaregiverImage.setImageBitmap(result);
        }
    }

    private class ScheduleList extends AsyncTask<String, Void, List<Schedule>> {
        private Context mContext;
        ScheduleList(Context context){
            mContext = context;
        }
        @Override
        protected List<Schedule> doInBackground(String... params){
            ScheduleListApi da = new ScheduleListApi(mContext);
            try {
                List<Schedule> sched = da.Get(params[0]);
                return sched;
            } catch (Exception ex) {
                Log.d(LOG,ex.getMessage());
            }
            /*
            ScheduleList
            MobileServiceTable<Schedule> scheduleTable;
            scheduleTable = Azure.getClient().getTable(Schedule.class);
            try {
                List<Schedule> sched = scheduleTable.where().field("CaregiverId").eq(params[0]).execute().get();
                return sched;
            } catch (Exception ex) {
                Log.d(LOG,ex.getMessage());
            }
            */
            return null;
        }

        @Override
        protected void onPostExecute(List<Schedule> result) {
            if (result.size()>0) {
                String picUrl = result.get(0).getCaregiverPicUrl();
                if (picUrl!=null) {
                    new GetCaregiverPicture(mContext).execute(picUrl);
                }
            }

        }
    }

    private class CaregiverData extends AsyncTask<String, Void, Caregiver> {
        private Context mContext;
        CaregiverData(Context context){
            mContext = context;
        }
        @Override
        protected Caregiver doInBackground(String... params){

            MobileServiceTable<Caregiver> table;
            table = Azure.getClient().getTable(Caregiver.class);
            try {
                List<Caregiver> car = table.where().field("id").eq(params[0]).execute().get();
                return car.get(0);
            } catch (Exception ex) {
                Log.d(LOG,ex.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Caregiver result) {
            mCaregiverName.setText(result.getFullName());
        }
    }

}
