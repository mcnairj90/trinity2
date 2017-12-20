package com.matrixcare.trinity.Api;

import com.matrixcare.trinity.Models.Schedule;

/**
 * Created by nates on 12/20/2017.
 */

public class ApplicationData {
    private static ApplicationData instance = null;
    protected ApplicationData() {
    }
    public static ApplicationData getInstance() {
        if(instance == null) {
            instance = new ApplicationData();
        }
        return instance;
    }

    public static String CaregiverId;
    public static Schedule schedule;

}