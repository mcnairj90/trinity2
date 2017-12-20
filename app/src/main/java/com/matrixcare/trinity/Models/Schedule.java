package com.matrixcare.trinity.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

/**
 * Created by nates on 12/20/2017.
 * note: ran through http://www.parcelabler.com/ to implements Parcelable
 */

public class Schedule implements Serializable {
    @com.google.gson.annotations.SerializedName("StartTime")
    private String StartTime;
    @com.google.gson.annotations.SerializedName("EndTime")
    private String EndTime;
    @com.google.gson.annotations.SerializedName("ClientId")
    private String ClientId;
    @com.google.gson.annotations.SerializedName("Service")
    private String Service;
    @com.google.gson.annotations.SerializedName("CaregiverId")
    private String CaregiverId;
    private String status;
    @com.google.gson.annotations.SerializedName("id")
    private String mId;
    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getCaregiverId() {
        return CaregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        CaregiverId = caregiverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrintedDate() {
       // DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime start = DateTime.parse(getStartTime());
        DateTime end = DateTime.parse(getEndTime());
        return start.toString("MM/dd/yyyy K:mm a");// + " - " + end.toString("K:mm a");
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof Schedule && ((Schedule) o).mId == mId;
    }

}
