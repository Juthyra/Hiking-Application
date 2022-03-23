package com.example.nabil.hikingapplication;

public class mCheckpoint {

    String uid;
    String mcheckpoint;
    String latitude;
    String longitude;
    String route;
    String date;

    public  mCheckpoint(){}

    public mCheckpoint(String uid, String mcheckpoint, String latitude, String longitude, String route, String date) {
        this.uid = uid;
        this.mcheckpoint = mcheckpoint;
        this.latitude = latitude;
        this.longitude = longitude;
        this.route = route;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMcheckpoint() {
        return mcheckpoint;
    }

    public void setMcheckpoint(String mcheckpoint) {
        this.mcheckpoint = mcheckpoint;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
