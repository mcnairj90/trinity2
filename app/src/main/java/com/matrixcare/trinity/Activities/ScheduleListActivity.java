package com.matrixcare.trinity.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.matrixcare.trinity.Models.Schedule;
import com.matrixcare.trinity.Models.ScheduleDetail;
import com.matrixcare.trinity.R;

public class ScheduleListActivity extends AppCompatActivity {
    private static final String ScheduleLI = "Schedule";

    private ImageView mImg;
    private TextView mName;
    private TextView mAppt;
    private TextView mCert;
    private FrameLayout mTasks;
    private RecyclerView mTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        mImg = findViewById(R.id.cgImage);
        mName = findViewById(R.id.cgName);
        mAppt = findViewById(R.id.appt);
        mCert = findViewById(R.id.cert);
        mTaskList = findViewById(R.id.taskList);

        mTaskList.setLayoutManager(new LinearLayoutManager(ScheduleListActivity.this));
       // new GetScheduleDetail(ScheduleListActivity.this).execute(mScheduleListItem.getId());

    }

    private void updateUi() {

    }
    public static Intent newIntent(Context packageContext, Schedule sched) {
        Intent intent = new Intent(packageContext,ScheduleListActivity.class);
        intent.putExtra(ScheduleLI, sched);
        return intent;
    }

    private class GetScheduleDetail extends AsyncTask<Integer, Void, ScheduleDetail> {
        private Context mContext;
        GetScheduleDetail(Context context){
            mContext = context;
        }
        @Override
        protected ScheduleDetail doInBackground(Integer... params){
           // ScheduleApi api = new ScheduleApi(mContext);
           // return api.Get(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(ScheduleDetail result) {
           // mScheduleDetail = result;
           // new GetCaregiverPicture(mContext).execute(mScheduleDetail.getCaregiverId());
            updateUi();
        }
    }


    private class GetCaregiverPicture extends AsyncTask<Integer, Void, Bitmap> {
        private Context mContext;
        GetCaregiverPicture(Context context){
            mContext = context;
        }
        @Override
        protected Bitmap doInBackground(Integer... params){
           // ImageApi api = new ImageApi(mContext);
           // return api.GetCaregiverImage(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            mImg.setImageBitmap(result);

        }
    }



}
