package com.matrixcare.trinity.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.matrixcare.trinity.Activities.ScheduleListActivity;
import com.matrixcare.trinity.Api.Azure;
import com.matrixcare.trinity.Interfaces.OnFragmentInteractionListener;
import com.matrixcare.trinity.Models.Schedule;
import com.matrixcare.trinity.R;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.List;


public class SchedulesFragment extends Fragment {
    private static final String LOG = "SchedulesFragment";
    private static final String list_key = "ScheduleListData";
    private static final String CaregiverKey = "CaregiverKey";
    private static final Integer RequestCode = 0;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mScheduleList;
    ProgressBar mProgress;
    private String mCaregiverId;
    private List<Schedule> mSchedules;
    private ScheduleAdapter mAdapter;
    public SchedulesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            retrieveData(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_schedules, container, false);
        mScheduleList = v.findViewById(R.id.scheduleList);
        mProgress = v.findViewById(R.id.progress_loader);
        mScheduleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUI();
        mProgress.setVisibility(View.VISIBLE);
        new ScheduleList(getActivity().getBaseContext()).execute(mCaregiverId);
        return v;
    }

    private void updateUI() {
        mProgress.setVisibility(View.INVISIBLE);
        if (mSchedules!=null) {
            mAdapter = new ScheduleAdapter(mSchedules);
            mScheduleList.setAdapter(mAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
       // savedInstanceState.putSerializable(list_key,mScheduleList);
    }

    private void retrieveData(Bundle savedInstanceState) {
        mCaregiverId = savedInstanceState.getString(CaregiverKey);
    }

    public static SchedulesFragment newInstance() {
        SchedulesFragment fragment = new SchedulesFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class ScheduleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mService;
        private TextView mDate;
        private TextView mCaregiver;
        private TextView mStatus;

        private Schedule mScheduleData;
        public ScheduleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_schedule, parent, false  ));
            itemView.setOnClickListener(this);
            mService = itemView.findViewById(R.id.schedule_service);
            mDate = itemView.findViewById(R.id.schedule_date);
            mCaregiver = itemView.findViewById(R.id.schedule_caregiver);
            mStatus = itemView.findViewById(R.id.schedule_status);
        }
        public void bind(Schedule sched) {
            mScheduleData = sched;
            mService.setText(mScheduleData.getService());
            mStatus.setText(mScheduleData.getStatus());
            mCaregiver.setText(mScheduleData.getCaregiverId());
            mDate.setText(mScheduleData.getPrintedDate());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ScheduleListActivity.newIntent(getActivity(),mScheduleData); //gson.toJson(mScheduleData)
            startActivity(intent);
        }
    }

    private class ScheduleAdapter extends RecyclerView.Adapter<ScheduleHolder> {
        private List<Schedule> mSchedules;
        public ScheduleAdapter(List<Schedule> schedules) {
            mSchedules = schedules;
        }

        @Override
        public ScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ScheduleHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(ScheduleHolder holder, int position) {
            Schedule sched = mSchedules.get(position);
            holder.bind(sched);
        }
        @Override
        public int getItemCount() {
            return mSchedules.size();
        }
    }

    private class ScheduleList extends AsyncTask<String, Void, List<Schedule>> {
        private Context mContext;
        ScheduleList(Context context){
            mContext = context;
        }
        @Override
        protected List<Schedule> doInBackground(String... params){
            MobileServiceTable<Schedule> scheduleTable;
            scheduleTable = Azure.getClient().getTable(Schedule.class);
            try {
                List<Schedule> sched = scheduleTable.where().field("CaregiverId").eq(params[0]).execute().get();
                return sched;
            } catch (Exception ex) {
                Log.d(LOG,ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Schedule> result) {
            //load schedules after search
           // Arrays.sort(result, new ScheduleSort());
            mSchedules = result;
            updateUI();
        }
    }


}
