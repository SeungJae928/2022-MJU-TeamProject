package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.getStList;
import static com.example.teamproject1.MainActivity.userSid;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private TextView textView, conges, msg1, msg2;
    public static String station_name = "";
    private CheckBox fav_checkBox;
    private UserDBHelper db;
    private List<Favorites> fav_list;
    public static boolean state;
    private TextView line;
    private List<StationInfo> stList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        String str = "";

        db = new UserDBHelper(InfoActivity.this);
        fav_list = db.getFavoriteDatabyUserSid(userSid);
        stList = getStList();

        textView = findViewById(R.id.st_name_info);
        textView.setText(station_name);

        msg1 = findViewById(R.id.textView14);
        msg2 = findViewById(R.id.textView15);
        conges = findViewById(R.id.textView10);
        for(StationInfo item : stList){
            if(item.getSt_name().equals(station_name)){
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


}
