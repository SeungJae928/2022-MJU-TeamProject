package com.example.teamproject1;

import static com.example.teamproject1.InfoActivity.station_name;
import static com.example.teamproject1.LoginActivity.exit_state;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageButton menuButton1;
    private ImageButton menuButton2;
    private ImageButton menuButton3;
    private ImageButton menuButton4;
    private ImageButton menuButton5;
    private List<MyButton> btnlist;
    private Button searchButton;
    private List<StationInfo> stList;

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

        btnlist = this.getButtonList();
        stList = this.getStList();

        for(MyButton btn : btnlist){
            btn.getBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intent);
                    station_name = btn.getStation();
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
                startActivity(intent);
            }
        });

        menuButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ColorsetActivity.class);
                startActivity(intent);
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

    public static List<StationInfo> getStList() {
        List<StationInfo> list = new ArrayList<>();
        long seed = 845489;
        Random rand = new Random(seed);
        Random rand2 = new Random(System.currentTimeMillis());
        list.add(new StationInfo("101", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("102", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("103", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("104", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("105", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("106", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("107", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("108", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("109", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("110", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("111", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("112", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("113", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("114", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("115", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("116", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("117", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("118", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("119", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("120", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("121", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("122", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("123", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        list.add(new StationInfo("201", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("202", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("203", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("204", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("205", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("206", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("207", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("208", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("209", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("210", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("211", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("212", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("213", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("214", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("215", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("216", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("217", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        list.add(new StationInfo("301", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("302", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("303", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("304", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("305", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("306", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("307", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("308", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        list.add(new StationInfo("401", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("402", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("403", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("404", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("405", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("406", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("407", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("408", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("409", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("410", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("411", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("412", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("413", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("414", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("415", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("416", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("417", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        list.add(new StationInfo("501", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("502", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("503", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("504", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("505", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("506", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("507", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        list.add(new StationInfo("601", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("602", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("603", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("604", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("605", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("606", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("607", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("608", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("609", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("610", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("611", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("612", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("613", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("614", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("615", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("616", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("617", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("618", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("619", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("620", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("621", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("622", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        list.add(new StationInfo("701", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("702", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("703", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("704", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("705", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("706", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("707", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        list.add(new StationInfo("801", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("802", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("803", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("804", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("805", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("806", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        list.add(new StationInfo("901", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("902", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("903", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        list.add(new StationInfo("904", rand2.nextInt(3), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));

        return list;
    }
}