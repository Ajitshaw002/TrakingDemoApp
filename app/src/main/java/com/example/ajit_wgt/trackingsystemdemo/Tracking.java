package com.example.ajit_wgt.trackingsystemdemo;

public class Tracking {
    String email,uId,lat,lng;

    public Tracking() {
    }

    public Tracking(String email, String uId, String lat, String lng) {
        this.email = email;
        this.uId = uId;
        this.lat = lat;
        this.lng = lng;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
