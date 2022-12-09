package com.example.teamproject1;

import static com.example.teamproject1.InfoActivity.station_name;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton menuButton1;
    private ImageButton menuButton2;
    private ImageButton menuButton3;
    private ImageButton menuButton4;
    private ImageButton menuButton5;
    private List<MyButton> btnlist;
    private Button searchButton;

    private final long finishTime= 1000;
    private long pressTime = 0;

    private boolean btn_state = true;

    public static String userSid;

    private String start = "";
    private String way = "";
    private String end = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        btnlist = this.getButtonList();

        for(MyButton btn : btnlist){
            btn.getBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intent);
                    station_name = btn.getStation();
                }
            });
            btn.getBtn().setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    if (start.equals(btn.getStation())) {
                        // 출발역과 지금 길게 누른 역이 같으면
                        start = "";
                        if (btn.getBtn().getBackground().equals(getDrawable(R.drawable.station_button2))) {
                            btn.getBtn().setBackground(getDrawable(R.drawable.station_button));
                        } else {
                            btn.getBtn().setBackground(getDrawable(R.drawable.transfer_station_button));
                        }
                    } else if (end.equals(btn.getStation())) {
                        // 도착역과 지금 길게 누른 역이 같으면
                        end = "";
                        if (btn.getBtn().getBackground().equals(getDrawable(R.drawable.station_button3))) {
                            btn.getBtn().setBackground(getDrawable(R.drawable.station_button));
                        } else {
                            btn.getBtn().setBackground(getDrawable(R.drawable.transfer_station_button));
                        }
                    } else if (start.equals("")) {
                        start = btn.getStation();
                        if (btn.getBtn().getBackground().equals(getDrawable(R.drawable.station_button))) {
                            btn.getBtn().setBackground(getDrawable(R.drawable.station_button2));
                        } else {
                            btn.getBtn().setBackground(getDrawable(R.drawable.transfer_station_button2));
                        }
                    } else if (end.equals("")) {
                        end = btn.getStation();
                        if (btn.getBtn().getBackground().equals(getDrawable(R.drawable.station_button))) {
                            btn.getBtn().setBackground(getDrawable(R.drawable.station_button3));
                        } else {
                            btn.getBtn().setBackground(getDrawable(R.drawable.transfer_station_button3));
                        }
                    } else {
                        return false;
                    }
                    return true;
                }
            });
        }

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
                if (!start.equals("")) {
                    intent.putExtra("start", start);
                }
                if (!end.equals("")) {
                    intent.putExtra("end", end);
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void changeState() {
        if(btn_state){
            btn_state = false;
        } else {
            btn_state = true;
        }
    }

    public List<MyButton> getButtonList() {
        List<MyButton> list = new ArrayList<>();
        list.add(new MyButton(findViewById(R.id.station_101), "101"));
        list.add(new MyButton(findViewById(R.id.station_102), "102"));
        list.add(new MyButton(findViewById(R.id.station_103), "103"));
        list.add(new MyButton(findViewById(R.id.station_104), "104"));
        list.add(new MyButton(findViewById(R.id.station_105), "105"));
        list.add(new MyButton(findViewById(R.id.station_106), "106"));
        list.add(new MyButton(findViewById(R.id.station_107), "107"));
        list.add(new MyButton(findViewById(R.id.station_108), "108"));
        list.add(new MyButton(findViewById(R.id.station_109), "109"));
        list.add(new MyButton(findViewById(R.id.station_110), "110"));
        list.add(new MyButton(findViewById(R.id.station_111), "111"));
        list.add(new MyButton(findViewById(R.id.station_112), "112"));
        list.add(new MyButton(findViewById(R.id.station_113), "113"));
        list.add(new MyButton(findViewById(R.id.station_114), "114"));
        list.add(new MyButton(findViewById(R.id.station_115), "115"));
        list.add(new MyButton(findViewById(R.id.station_116), "116"));
        list.add(new MyButton(findViewById(R.id.station_117), "117"));
        list.add(new MyButton(findViewById(R.id.station_118), "118"));
        list.add(new MyButton(findViewById(R.id.station_119), "119"));
        list.add(new MyButton(findViewById(R.id.station_120), "120"));
        list.add(new MyButton(findViewById(R.id.station_121), "121"));
        list.add(new MyButton(findViewById(R.id.station_122), "122"));
        list.add(new MyButton(findViewById(R.id.station_123), "123"));

        list.add(new MyButton(findViewById(R.id.station_201), "201"));
        list.add(new MyButton(findViewById(R.id.station_202), "202"));
        list.add(new MyButton(findViewById(R.id.station_203), "203"));
        list.add(new MyButton(findViewById(R.id.station_204), "204"));
        list.add(new MyButton(findViewById(R.id.station_205), "205"));
        list.add(new MyButton(findViewById(R.id.station_206), "206"));
        list.add(new MyButton(findViewById(R.id.station_207), "207"));
        list.add(new MyButton(findViewById(R.id.station_208), "208"));
        list.add(new MyButton(findViewById(R.id.station_209), "209"));
        list.add(new MyButton(findViewById(R.id.station_210), "210"));
        list.add(new MyButton(findViewById(R.id.station_211), "211"));
        list.add(new MyButton(findViewById(R.id.station_212), "212"));
        list.add(new MyButton(findViewById(R.id.station_213), "213"));
        list.add(new MyButton(findViewById(R.id.station_214), "214"));
        list.add(new MyButton(findViewById(R.id.station_215), "215"));
        list.add(new MyButton(findViewById(R.id.station_216), "216"));
        list.add(new MyButton(findViewById(R.id.station_217), "217"));

        list.add(new MyButton(findViewById(R.id.station_301), "301"));
        list.add(new MyButton(findViewById(R.id.station_302), "302"));
        list.add(new MyButton(findViewById(R.id.station_303), "303"));
        list.add(new MyButton(findViewById(R.id.station_304), "304"));
        list.add(new MyButton(findViewById(R.id.station_305), "305"));
        list.add(new MyButton(findViewById(R.id.station_306), "306"));
        list.add(new MyButton(findViewById(R.id.station_307), "307"));
        list.add(new MyButton(findViewById(R.id.station_308), "308"));

        list.add(new MyButton(findViewById(R.id.station_401), "401"));
        list.add(new MyButton(findViewById(R.id.station_402), "402"));
        list.add(new MyButton(findViewById(R.id.station_403), "403"));
        list.add(new MyButton(findViewById(R.id.station_404), "404"));
        list.add(new MyButton(findViewById(R.id.station_405), "405"));
        list.add(new MyButton(findViewById(R.id.station_406), "406"));
        list.add(new MyButton(findViewById(R.id.station_407), "407"));
        list.add(new MyButton(findViewById(R.id.station_408), "408"));
        list.add(new MyButton(findViewById(R.id.station_409), "409"));
        list.add(new MyButton(findViewById(R.id.station_410), "410"));
        list.add(new MyButton(findViewById(R.id.station_411), "411"));
        list.add(new MyButton(findViewById(R.id.station_412), "412"));
        list.add(new MyButton(findViewById(R.id.station_413), "413"));
        list.add(new MyButton(findViewById(R.id.station_414), "414"));
        list.add(new MyButton(findViewById(R.id.station_415), "415"));
        list.add(new MyButton(findViewById(R.id.station_416), "416"));
        list.add(new MyButton(findViewById(R.id.station_417), "417"));

        list.add(new MyButton(findViewById(R.id.station_501), "501"));
        list.add(new MyButton(findViewById(R.id.station_502), "502"));
        list.add(new MyButton(findViewById(R.id.station_503), "503"));
        list.add(new MyButton(findViewById(R.id.station_504), "504"));
        list.add(new MyButton(findViewById(R.id.station_505), "505"));
        list.add(new MyButton(findViewById(R.id.station_506), "506"));
        list.add(new MyButton(findViewById(R.id.station_507), "507"));

        list.add(new MyButton(findViewById(R.id.station_601), "601"));
        list.add(new MyButton(findViewById(R.id.station_602), "602"));
        list.add(new MyButton(findViewById(R.id.station_603), "603"));
        list.add(new MyButton(findViewById(R.id.station_604), "604"));
        list.add(new MyButton(findViewById(R.id.station_605), "605"));
        list.add(new MyButton(findViewById(R.id.station_606), "606"));
        list.add(new MyButton(findViewById(R.id.station_607), "607"));
        list.add(new MyButton(findViewById(R.id.station_608), "608"));
        list.add(new MyButton(findViewById(R.id.station_609), "609"));
        list.add(new MyButton(findViewById(R.id.station_610), "610"));
        list.add(new MyButton(findViewById(R.id.station_611), "611"));
        list.add(new MyButton(findViewById(R.id.station_612), "612"));
        list.add(new MyButton(findViewById(R.id.station_613), "613"));
        list.add(new MyButton(findViewById(R.id.station_614), "614"));
        list.add(new MyButton(findViewById(R.id.station_615), "615"));
        list.add(new MyButton(findViewById(R.id.station_616), "616"));
        list.add(new MyButton(findViewById(R.id.station_617), "617"));
        list.add(new MyButton(findViewById(R.id.station_618), "618"));
        list.add(new MyButton(findViewById(R.id.station_619), "619"));
        list.add(new MyButton(findViewById(R.id.station_620), "620"));
        list.add(new MyButton(findViewById(R.id.station_621), "621"));
        list.add(new MyButton(findViewById(R.id.station_622), "622"));

        list.add(new MyButton(findViewById(R.id.station_701), "701"));
        list.add(new MyButton(findViewById(R.id.station_702), "702"));
        list.add(new MyButton(findViewById(R.id.station_703), "703"));
        list.add(new MyButton(findViewById(R.id.station_704), "704"));
        list.add(new MyButton(findViewById(R.id.station_705), "705"));
        list.add(new MyButton(findViewById(R.id.station_706), "706"));
        list.add(new MyButton(findViewById(R.id.station_707), "707"));

        list.add(new MyButton(findViewById(R.id.station_801), "801"));
        list.add(new MyButton(findViewById(R.id.station_802), "802"));
        list.add(new MyButton(findViewById(R.id.station_803), "803"));
        list.add(new MyButton(findViewById(R.id.station_804), "804"));
        list.add(new MyButton(findViewById(R.id.station_805), "805"));
        list.add(new MyButton(findViewById(R.id.station_806), "806"));

        list.add(new MyButton(findViewById(R.id.station_901), "901"));
        list.add(new MyButton(findViewById(R.id.station_902), "902"));
        list.add(new MyButton(findViewById(R.id.station_903), "903"));
        list.add(new MyButton(findViewById(R.id.station_904), "904"));

        return list;
    }
}