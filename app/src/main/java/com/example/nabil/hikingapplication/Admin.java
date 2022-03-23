package com.example.nabil.hikingapplication;

public class Admin {
    String uid;
    String email;
    String name;
    String ic;
    String user;

    //empty constructor needed
    public Admin() {
    }

    public Admin(String uid, String email, String name, String ic, String user) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.ic = ic;
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}