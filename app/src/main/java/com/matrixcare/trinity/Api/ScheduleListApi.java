package com.matrixcare.trinity.Api;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matrixcare.trinity.Models.Schedule;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nates on 12/20/2017.
 */

public class ScheduleListApi extends BaseApi {

    public ScheduleListApi(Context context) {
        super(context);
    }

    public List<Schedule> Get(String caregiverId) {
        String result = ExecuteRequest("api/CaregiverSchedules/"+ caregiverId, null);
        Gson gson = new GsonBuilder().create();
        Schedule[] scheds = gson.fromJson(result, Schedule[].class);
        return Arrays.asList(scheds);
    }

    public Bitmap GetImage(String url) {
        return ExecuteBitmapRequest(url);
    }
}
