package com.example.teamproject1;

import static com.example.teamproject1.LoginActivity.exit_state;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton menuButton1;
    private ImageButton menuButton2;
    private ImageButton menuButton3;
    private ImageButton menuButton4;
    private ImageButton menuButton5;
    private Button searchButton;

    private final long finishTime= 1000;
    private long pressTime = 0;

    private boolean btn_state = true;

    public static String userSid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        menuButton1 = findViewById(R.id.menuButton1);
        menuButton2 = findViewById(R.id.menuButton2);
        menuButton3 = findViewById(R.id.menuButton3);
        menuButton4 = findViewById(R.id.menuButton4);
        menuButton5 = findViewById(R.id.menuButton5);
        searchButton = findViewById(R.id.search_bar_btn);

        menuButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_state){
                    changeState();
                    menuButton2.setVisibility(View.VISIBLE);
                    menuButton3.setVisibility(View.VISIBLE);
                    menuButton4.setVisibility(View.VISIBLE);
                    menuButton5.setVisibility(View.VISIBLE);
                } else {
                    changeState();
                    menuButton2.setVisibility(View.INVISIBLE);
                    menuButton3.setVisibility(View.INVISIBLE);
                    menuButton4.setVisibility(View.INVISIBLE);
                    menuButton5.setVisibility(View.INVISIBLE);
                }
            }
        });

        //즐겨찾기
        menuButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });

        //최근 이용
        menuButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecentlyUsedActivity.class);
                startActivity(intent);
            }
        });

        //길찾기
        menuButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(intent);
            }
        });

        menuButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo 테마색 바꾸기 구현
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - pressTime;

        if(0 <= intervalTime && finishTime >= intervalTime) {
            finish();
        } else {
            pressTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로가기를 한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeState() {
        if(btn_state){
            btn_state = false;
        } else {
            btn_state = true;
        }
    }
}