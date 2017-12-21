package com.matrixcare.trinity.Models;

/**
 * Created by nates on 12/20/2017.
 */

public class Caregiver {
    private String FirstName;
    private String LastName;
    private String Phone;
    private String id;
    private String PicUrl;
    private String DeviceToken;

    @Override
    public boolean equals(Object o) {
        return o instanceof Caregiver && ((Caregiver) o).getId() == getId();
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) { PicUrl = picUrl; }

    public String getDeviceToken() {return DeviceToken; }

    public void setDeviceToken(String deviceToken) { DeviceToken = deviceToken; }

}
