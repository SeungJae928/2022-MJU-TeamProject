package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.userSid;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private TextView textView;
    public static String station_name = "";
    private CheckBox fav_checkBox;
    private UserDBHelper db;
    private List<Favorites> fav_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        db = new UserDBHelper(InfoActivity.this);
        fav_list = db.getFavoriteDatabyUserSid(userSid);

        textView = findViewById(R.id.st_name_info);
        textView.setText(station_name);

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
        finish();
    }


}
