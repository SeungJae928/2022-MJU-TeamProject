package com.example.teamproject1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class ColorsetActivity extends AppCompatActivity {

    private ImageButton back;
    private Button blue;
    private Button pink;
    private Button green;
    private UserDBHelper db;
    private GradientDrawable drawableAddButton;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_page);

        back = findViewById(R.id.backBtn_col);
        blue = findViewById(R.id.blue_btn);
        pink = findViewById(R.id.pink_btn);
        green = findViewById(R.id.green_btn);
        drawableAddButton = (GradientDrawable) getResources().getDrawable(R.drawable.add_button);


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
                drawableAddButton.setColor(Color.parseColor(String.valueOf(R.color.blue_B3D5F2)));
            }
        });

        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pink
                drawableAddButton.setColor(Color.parseColor(String.valueOf(R.color.pink_F2B3DD)));
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //green
                drawableAddButton.setColor(Color.parseColor(String.valueOf(R.color.green_B3F2B9)));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}