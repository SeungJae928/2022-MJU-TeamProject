package com.example.teamproject1;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    CheckBox fav_checkbox;
    UserDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        db = new UserDBHelper(TestActivity.this);
//        db.insertDatatoRecentlyUsed("test", "1", "1");
//        db.insertDatatoRecentlyUsed("test", "2", "1");
//        db.insertDatatoRecentlyUsed("test", "3", "1");
//        db.insertDatatoRecentlyUsed("test", "4", "1");
//        db.insertDatatoRecentlyUsed("test", "5", "1");
//        db.insertDatatoRecentlyUsed("test", "6", "1");
//        db.insertDatatoRecentlyUsed("test", "7", "1");
//        db.insertDatatoRecentlyUsed("test", "8", "1");
//        db.insertDatatoRecentlyUsed("test", "9", "1");
//        db.insertDatatoRecentlyUsed("test", "10", "1");
//        db.insertDatatoRecentlyUsed("test", "11", "1");
//        db.insertDatatoRecentlyUsed("test", "12", "1");
//        db.insertDatatoRecentlyUsed("test", "13", "1");


//        db = new UserDBHelper(TestActivity.this);
//
//        fav_checkbox = findViewById(R.id.fav_checkbox);
//        fav_checkbox.setButtonDrawable(R.drawable.btn_selector);
//        fav_checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(fav_checkbox.isChecked()){
//                    db.insertDatatoFavorite("test", "test");
//                } else {
//                    db.deleteFavData("test");
//                }
//            }
//        });
    }
}