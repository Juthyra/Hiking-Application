package com.example.nabil.hikingapplication;

class mLocation {

    String uid;
    String location;
    String mdate;
    String userUid;

    public mLocation(){

    }

    public mLocation(String uid, String location, String mdate, String userUid) {
        this.uid = uid;
        this.location = location;
        this.mdate = mdate;
        this.userUid = userUid;
    }



    public String getUid() {
        return uid;
    }

    public String getLocation() {
        return location;
    }

    public String getMdate() {
        return mdate;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
