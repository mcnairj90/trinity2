package com.matrixcare.trinity.Models;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by nates on 12/20/2017.
 * note: ran through http://www.parcelabler.com/ to implements Parcelable
 */

public class Schedule implements Serializable {
    @com.google.gson.annotations.SerializedName("startTime")
    private String startTime;
    private String clientFirstName;
    private String clientLastName;
    private String address;
    private String phone;
    private String caregiverPicUrl;
    private String clientPicUrl;
    private String service;

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    public String getPrintedDate() {
       // DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime start = DateTime.parse(getStartTime());
        return start.toString("MM/dd/yyyy K:mm a");// + " - " + end.toString("K:mm a");
    }

    public String getClientFullName() {
        return getClientFirstName() + " " + getClientLastName();
    }
    public String getId() {
        return getmId();
    }

    public void setId(String mId) {
        this.setmId(mId);
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof Schedule && ((Schedule) o).getmId() == getmId();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCaregiverPicUrl() {
        return caregiverPicUrl;
    }

    public void setCaregiverPicUrl(String caregiverPicUrl) {
        this.caregiverPicUrl = caregiverPicUrl;
    }

    public String getClientPicUrl() {
        return clientPicUrl;
    }

    public void setClientPicUrl(String clientPicUrl) {
        this.clientPicUrl = clientPicUrl;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
