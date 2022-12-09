package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.getStList;
import static com.example.teamproject1.MainActivity.userSid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private TextView textView, conges, msg1, msg2, remainTime;
    private CheckBox fav_checkBox;
    private UserDBHelper db;
    private List<Favorites> fav_list;
    private TextView line;
    private List<StationInfo> stList;
    private BackgroundThread backgroundThread;
    private long t;
    private long time = System.currentTimeMillis();
    private long remain = 1200000 - time%1200000;
    private String type;
    private Button route_btn;

    public static boolean state;
    public static String station_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        String str = "";

        db = new UserDBHelper(InfoActivity.this);
        fav_list = db.getFavoriteDatabyUserSid(userSid);
        stList = getStList();

        type = db.getUserDatabySId(userSid).getColor();

        LinearLayout Ll = (LinearLayout) findViewById(R.id.linearLayout1);
        if (type.equals("blue")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Ll.setBackgroundColor(getColor(R.color.blue_3F9CF1));
            }
        } else if (type.equals("pink")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Ll.setBackgroundColor(getColor(R.color.pink_F13FCA));
            }
        } else if (type.equals("green")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Ll.setBackgroundColor(getColor(R.color.green_64AE70));
            }
        }

        textView = findViewById(R.id.st_name_info);
        textView.setText(station_name);

        remainTime = findViewById(R.id.textView11);
        msg1 = findViewById(R.id.textView14);
        msg2 = findViewById(R.id.textView15);
        conges = findViewById(R.id.textView10);
        for(StationInfo item : stList){
            if(item.getSt_name().equals(station_name)){
                t = item.getDepartTime() * 60000;
                remain -= t;
                System.out.println(remain);
                String message1 = "";
                String message2 = "";
                switch (item.getCongestion()){
                    case 0 :
                        conges.setText("여유");
                        conges.setTextColor(Color.GREEN);
                        break;
                    case 1 :
                        conges.setText("보통");
                        conges.setTextColor(Color.BLUE);
                        break;
                    case 2 :
                        conges.setText("혼잡");
                        conges.setTextColor(Color.RED);
                        break;
                }
                if(item.isStore()) {
                    message2 += "편의점 있음 ";
                } else {
                    message2 += "편의점 없음 ";
                }
                if(item.isVending()) {
                    message2 += "자판기 있음 ";
                } else {
                    message2 += "자판기 없음 ";
                }
                if(item.isToilet()) {
                    message1 += "개찰구 내 화장실 있음 ";
                } else {
                    message1 += "개찰구 내 화장실 없음 ";
                }
                msg1.setText(message1);
                msg2.setText(message2);
            }
        }

        line = findViewById(R.id.imageView4);
        str += station_name.charAt(0);
        line.setText(str);

        fav_checkBox = findViewById(R.id.fav_checkbox_info);

        if(!fav_list.isEmpty()){
            for(Favorites items : fav_list) {
                if(items.getStation().equals(station_name)){
                    fav_checkBox.setChecked(true);
                }
            }
        }

        fav_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fav_checkBox.isChecked()){
                    db.insertDatatoFavorite(userSid, station_name);
                } else {
                    db.deleteFavData(station_name);
                }
            }
        });
        try {
            handleMessage();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        route_btn = findViewById(R.id.route_btn);
        route_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RouteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(state){
            Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
            startActivity(intent);
            state = false;
        }
        finish();
    }

    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<InfoActivity> mActivity;
        public MyHandler(InfoActivity activity) {
            mActivity = new WeakReference<InfoActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg){
            InfoActivity activity = mActivity.get();
            if (activity != null){

                try {
                    activity.handleMessage();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void handleMessage() throws ParseException {
        if(remain < 0){
            remain = 1200000 + remain;
        }
        SimpleDateFormat a= new SimpleDateFormat("mm분 ss초");
        remainTime.setText(a.format(remain));
        System.out.println(a.format(1200000 - time%1200000));
        System.out.println(time%1200000);
        remain -= 1000;
    }

    @Override
    protected void onStart() {
        super.onStart();

        backgroundThread = new BackgroundThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        boolean retry = true;
        backgroundThread.setRunning(false);

        while(retry){
            try{
                backgroundThread.join();
                retry = false;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public class BackgroundThread extends Thread{
        boolean running = false;
        void setRunning(boolean b) {
            running = b;
        }

        @Override
        public void run(){
            while (running){
                try {
                    sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        }
    }

}
