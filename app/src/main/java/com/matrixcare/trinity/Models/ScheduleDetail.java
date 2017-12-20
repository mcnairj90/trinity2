package com.matrixcare.trinity.Models;

/**
 * Created by nates on 12/20/2017.
 */

public class ScheduleDetail {
    private String pPayrNm;
    private Address client;
    private Integer carId;
    public String getPayerName() {
        return pPayrNm;
    }

    public void setPayerName(String pPayrNm) {
        this.pPayrNm = pPayrNm;
    }

    public Address getClient() {
        return client;
    }

    public void setClient(Address client) {
        this.client = client;
    }

    public Integer getCaregiverId() {
        return carId;
    }

    public void setCaregiverId(Integer carId) {
        this.carId = carId;
    }
}
