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

import com.matrixcare.trinity.Api.ApplicationData;
import com.matrixcare.trinity.Api.ScheduleListApi;
import com.matrixcare.trinity.Models.Schedule;
import com.matrixcare.trinity.Models.ScheduleDetail;
import com.matrixcare.trinity.R;

public class ScheduleListActivity extends AppCompatActivity {
    private static final String ScheduleLI = "Schedule";

    private ImageView mclient_image;
    private TextView mclientName;
    private TextView mclientAddress;
    private TextView mclientPhone;
    private TextView mstartTime;
    private TextView mservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        mclient_image = findViewById(R.id.client_image);
        mclientName = findViewById(R.id.clientName);
        mclientAddress = findViewById(R.id.clientAddress);
        mclientPhone = findViewById(R.id.clientPhone);
        mstartTime = findViewById(R.id.startTime);
        mservice = findViewById(R.id.service);
        Schedule sched = ApplicationData.schedule;
        String picUrl =sched.getClientPicUrl();
        if (picUrl!=null) {
            new GetPicture(ScheduleListActivity.this).execute(picUrl);
        }

        mclientName.setText(sched.getClientFullName());
        mclientAddress.setText(sched.getAddress());
        mclientPhone.setText(sched.getPhone());
        mstartTime.setText(sched.getPrintedDate());
        mservice.setText(sched.getService());

    }

    public static Intent newIntent(Context packageContext, Schedule sched) {
        Intent intent = new Intent(packageContext,ScheduleListActivity.class);
        intent.putExtra(ScheduleLI, sched);
        return intent;
    }

    private class GetPicture extends AsyncTask<String, Void, Bitmap> {
        private Context mContext;
        GetPicture(Context context){
            mContext = context;
        }
        @Override
        protected Bitmap doInBackground(String... params){
            ScheduleListApi api = new ScheduleListApi(mContext);
            return api.GetImage(params[0]);
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            mclient_image.setImageBitmap(result);
        }
    }



}
