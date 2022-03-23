package com.example.nabil.hikingapplication;

public class mRoute {

    String uid;
    String route;
    String uidLocation;
    String date;

    public mRoute(){}

    public mRoute(String uid, String route, String uidLocation, String date) {
        this.uid = uid;
        this.route = route;
        this.uidLocation = uidLocation;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getUidLocation() {
        return uidLocation;
    }

    public void setUidLocation(String uidLocation) {
        this.uidLocation = uidLocation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

