package com.example.teamproject1;

import android.widget.Button;

public class MyButton {

    public Button btn;
    public String station;

    public MyButton(Button btn, String station){
        this.btn = btn;
        this.station = station;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
