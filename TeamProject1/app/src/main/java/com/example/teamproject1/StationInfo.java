package com.example.teamproject1;

public class StationInfo {

    private String st_name;
    private int congestion;
    private boolean toilet;
    private boolean store;
    private boolean vending;
    private int departTime;


    public StationInfo(String st_name, int congestion, boolean toilet, boolean store, boolean vending){
        this.st_name = st_name;
        this.congestion = congestion;
        this.toilet = toilet;
        this.store = store;
        this.vending = vending;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public int getCongestion() {
        return congestion;
    }

    public void setCongestion(int congestion) {
        this.congestion = congestion;
    }

    public boolean isToilet() {
        return toilet;
    }

    public void setToilet(boolean toilet) {
        this.toilet = toilet;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    public boolean isVending() {
        return vending;
    }

    public void setVending(boolean vending) {
        this.vending = vending;
    }

    public int getDepartTime() {
        return departTime;
    }

    public void setDepartTime(int departTime) {
        this.departTime = departTime;
    }
}
