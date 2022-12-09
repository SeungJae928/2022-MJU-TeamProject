package com.example.teamproject1;

import android.app.AlertDialog;

public class Favorites {

    private String sid;
    private String userSid;
    private String station;

    public Favorites(String station){
        this.station = station;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUserSid() {
        return userSid;
    }

    public void setUserSid(String userSid) {
        this.userSid = userSid;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
