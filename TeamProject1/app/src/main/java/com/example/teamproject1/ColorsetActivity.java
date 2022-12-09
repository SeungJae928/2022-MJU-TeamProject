package com.example.teamproject1;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ColorsetActivity extends AppCompatActivity {

    private ImageButton back;
    private Button blue;
    private Button pink;
    private Button green;
    private UserDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_page);

        back = findViewById(R.id.backBtn_col);
        blue = findViewById(R.id.blue_btn);
        pink = findViewById(R.id.pink_btn);
        green = findViewById(R.id.green_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파란테마 변경

            }
        });

        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pink
                Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tb.setBackgroundColor(getColor(R.color.pink_FAEFF8));
                }
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //green
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
