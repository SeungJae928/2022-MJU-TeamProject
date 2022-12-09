package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.userSid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject1.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FindActivity extends AppCompatActivity {
    private TextView mtextView;
    private TextView timeTv;
    private Timer mTimer;
    private boolean way = false;
    private Dijkstra d;
    private int timeSecond = -1;
    private UserDBHelper db;
    private List<Recent> recentList;
    private int tp;
    private Thread thread = null;
    private ImageButton button2;
    private String type;

    public String sharing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button mButton, addButton, alarmButton, share_btn;
        d = new Dijkstra(this);

        db = new UserDBHelper(FindActivity.this);
        recentList = db.getRecentlyUsedData(userSid);

        type = db.getUserDatabySId(userSid).getColor();

        androidx.appcompat.widget.Toolbar toolbar;
        EditText waySearchView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_page);

        addButton = (Button)findViewById(R.id.addButton);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        button2 = (ImageButton)findViewById(R.id.imageButton2);
        waySearchView = (EditText)findViewById(R.id.searchView12);

        if (type.equals("blue")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                toolbar.setBackgroundColor(getColor(R.color.blue_3F9CF1));
            }
        } else if (type.equals("pink")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                toolbar.setBackgroundColor(getColor(R.color.pink_F13FCA));
            }
        } else if (type.equals("green")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                toolbar.setBackgroundColor(getColor(R.color.green_64AE70));
            }
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!way) {
                    toolbar.setLayoutParams(new LinearLayout.LayoutParams(toolbar.getLayoutParams().width, (int)(toolbar.getLayoutParams().height * 1.4)));
                    addButton.setText("-");
                    waySearchView.setVisibility(SearchView.VISIBLE);

                } else {
                    toolbar.setLayoutParams(new LinearLayout.LayoutParams(toolbar.getLayoutParams().width, (int)(toolbar.getLayoutParams().height / 1.4)));
                    addButton.setText("+");
                    waySearchView.setVisibility(SearchView.GONE);
                }
                way = !way;
            }
        });

        Intent intent = getIntent();

        EditText start = (EditText) findViewById(R.id.searchView11);
        EditText end = (EditText) findViewById(R.id.searchView13);
        start.setText(intent.getStringExtra("start"));
        waySearchView.setText(intent.getStringExtra("way"));
        end.setText(intent.getStringExtra("end"));

        String searchType = intent.getStringExtra("type");

        if (intent.getStringExtra("way") != null && intent.getStringExtra("end") != null) {
            way = true;
            toolbar.setLayoutParams(new LinearLayout.LayoutParams(toolbar.getLayoutParams().width, (int)(toolbar.getLayoutParams().height * 1.4)));
            addButton.setText("-");
            waySearchView.setVisibility(SearchView.VISIBLE);
        } else {
            way = false;
            addButton.setText("+");
            waySearchView.setVisibility(SearchView.GONE);
        }

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText start = (EditText) findViewById(R.id.searchView11);
                EditText end = (EditText) findViewById(R.id.searchView13);

                String changeStart;
                changeStart = end.getText().toString();
                String changeEnd;
                changeEnd = start.getText().toString();

                start.setText(changeStart);

                end.setText(changeEnd);
            }
        });


        timeTv = (TextView) findViewById(R.id.clockView);

        mTimer = new Timer();
        alarmButton = findViewById(R.id.alarm_btn);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (thread != null){
                    if (thread.isAlive()){
                        Toast.makeText(getApplicationContext(), "하차 알림이 실행중입니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if (timeSecond == -1) {
                    return;
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(FindActivity.this, "default");

                builder.setSmallIcon(R.drawable.i_icon);
                builder.setContentTitle("목적지 도착 알림");
                builder.setContentText("목적지로 가는 중입니다.");

                builder.setColor(Color.RED);
                builder.setAutoCancel(false);
                PendingIntent intent =
                        PendingIntent.getActivity(FindActivity.this, 0, new Intent(getApplicationContext(), LoginActivity.class), PendingIntent.FLAG_ONE_SHOT);
                builder.setContentIntent(intent);

                NotificationManager notificationManager = (NotificationManager) FindActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_UNSPECIFIED));
                }
                notificationManager.notify(1, builder.build());
                Toast.makeText(getApplicationContext(), "하차 알림을 실행합니다.", Toast.LENGTH_LONG).show();

                thread = new Thread() {
                    int t = 0;
                    @Override
                    public void run() {
                        while (t <= timeSecond) {
                            System.out.println(t + " " + timeSecond);
                            builder.setProgress(timeSecond, t, false);
                            notificationManager.notify(1, builder.build());
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            t++;
                        }
                        builder.setContentText("목적지에 도착하였습니다.");
                        builder.setAutoCancel(true);
                        notificationManager.notify(1, builder.build());
                    }
                };

                thread.start();
            }
        });

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        // 버튼 등록
        mButton = (Button) findViewById(R.id.sort_button);

        //  텍스트뷰 받아오기
        mtextView = findViewById(R.id.clockView);

        share_btn = findViewById(R.id.share_btn);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
                Sharing_intent.setType("text/plain");

                String sharing  = FindActivity.this.sharing;

                Sharing_intent.putExtra(Intent.EXTRA_TEXT, sharing );

                Intent Sharing = Intent.createChooser(Sharing_intent, "공유하기");
                startActivity(Sharing);
            }
        });

        // 시작하자마자
        // 경로 계산

        TextView t1 = (TextView) findViewById(R.id.station1);
        TextView t2 = (TextView) findViewById(R.id.station2);
        TextView t3 = (TextView) findViewById(R.id.station3);

        View l1 = (View) findViewById(R.id.line2);
        TextView s1 = (TextView) findViewById(R.id.spend_time1);
        TextView s2 = (TextView) findViewById(R.id.spend_time2);

        View b1 = (View) findViewById(R.id.bar1);
        View b2 = (View) findViewById(R.id.bar2);
        TextView d1 = (TextView) findViewById(R.id.detail);
        TextView d2 = (TextView) findViewById(R.id.detail2);

        Integer s = 0;
        Integer w = 0;
        Integer e = 0;

        if (!start.getText().toString().equals("") && !end.getText().toString().equals("")){
            s = Integer.parseInt(start.getText().toString());
            e = Integer.parseInt(end.getText().toString());
            System.out.println(s + " " + e);
        } else {
            return;
        }
        if (!waySearchView.getText().toString().equals("") && FindActivity.this.way){
            w = Integer.parseInt(waySearchView.getText().toString());
        }
        // 각 메뉴별 아이디를 조사한 후 할일을 적어줌
        FindRoute fr1 = new FindRoute(-1, new ArrayList<>());
        FindRoute fr2 = new FindRoute(-1, new ArrayList<>());
        try {
            if (searchType.equals("최적 경로")) {
                if (w == 0) {
                    fr1 = d.dijkstra(s.intValue(), e.intValue(), 1);
                } else {
                    fr1 = d.dijkstra(s.intValue(), w.intValue(), 1);
                    fr2 = d.dijkstra(w.intValue(), e.intValue(), 1);
                }
                tp = 1;
            } else if (searchType.equals("최단 시간")) {
                if (w == 0) {
                    fr1 = d.dijkstra(s.intValue(), e.intValue(),0);
                } else {
                    fr1 = d.dijkstra(s.intValue(), w.intValue(), 0);
                    fr2 = d.dijkstra(w.intValue(), e.intValue(), 0);
                }
                tp = 0;
            } else if (searchType.equals("최소 금액")) {
                if (w == 0) {
                    fr1 = d.dijkstra(s.intValue(), e.intValue(),2);
                } else {
                    fr1 = d.dijkstra(s.intValue(), w.intValue(), 2);
                    fr2 = d.dijkstra(w.intValue(), e.intValue(), 2);
                }
                tp = 2;
            }
        } catch (IOException e1) {
            System.out.println(e1);
            return;
        } catch (Exception e1) {
            System.out.println(e1);
            Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        String cost1 = "";
        String cost2 = "";

        if (searchType.equals("최적 경로")) {
            if (w == 0) {
                cost1 = fr1.getCost() + "미터";
            } else {
                cost1 = fr1.getCost() + "미터";
                cost2 = fr2.getCost() + "미터";
            }
        } else if (searchType.equals("최단 시간")) {
            if (w == 0) {
                cost1 = fr1.getCost() + "초";
            } else {
                cost1 = fr1.getCost() + "초";
                cost2 = fr2.getCost() + "초";
            }
        } else if (searchType.equals("최소 금액")) {
            if (w == 0) {
                cost1 = fr1.getCost() + "원";
            } else {
                cost1 = fr1.getCost() + "원";
                cost2 = fr2.getCost() + "원";
            }
        }

        String[] details = new String[0];

        if (w == 0) {
            l1.setVisibility(View.GONE);
            s2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            d2.setVisibility(View.GONE);
            t1.setText(s.toString());
            t2.setText(e.toString());
            s1.setText(cost1);
            d1.setText(fr1.getRoute().toString());
            details = d.getDetails(fr1.getRoute());
            timeSecond = d.getTotal(fr1.getRoute(), "time");
        } else {
            l1.setVisibility(View.VISIBLE);
            s2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            d2.setVisibility(View.VISIBLE);
            t1.setText(s.toString());
            t2.setText(w.toString());
            t3.setText(e.toString());
            s1.setText(cost1);
            s2.setText(cost2);
            d1.setText(fr1.getRoute().toString());
            d2.setText(fr2.getRoute().toString());
            details = d.getDetails(fr1.getRoute(), fr2.getRoute());
            timeSecond = d.getTotal(fr1.getRoute(), "time")
                    + d.getTotal(fr2.getRoute(), "time");
        }

        TextView st = (TextView) findViewById(R.id.startTime);
        TextView info = (TextView) findViewById(R.id.information);

        st.setText(details[0]);
        info.setText(details[1] + ", " + details[2]);

        sharing = "경로 공유\n\n"
                + "출발역: " + start.getText();
        if (FindActivity.this.way) {
            sharing += "\n경유역: " + w;
        }
        sharing += "\n도착역: " + end.getText() + "\n\n이동 경로: ";
        for (int i = 0; i < fr1.getRoute().size(); i++) {
            sharing += fr1.getRoute().get(i);
            if (i != fr1.getRoute().size()-1) {
                sharing += " -> ";
            }
        }
        for (int i = 0; i < fr2.getRoute().size(); i++) {
            sharing += fr2.getRoute().get(i);
            if (i != fr2.getRoute().size()-1) {
                sharing += " -> ";
            }
        }
        sharing += "\n\n소요 시간: " + details[0];
        sharing += "\n필요 금액: " + (d.getTotal(fr1.getRoute(), "cost")
                + d.getTotal(fr2.getRoute(), "cost"));

        System.out.println(sharing);

        //최근 이용 db에 역 이름 등록
        try {
            for(Recent item : recentList){
                if(item.getStart().equals(s.toString()) && item.getEnd().equals(e.toString()))
                    throw new reduplicationEx("중복 데이터 입력 방지");
            }
            db.insertDatatoRecentlyUsed(userSid, s.toString(), w.toString(), e.toString(), tp);
        } catch (reduplicationEx e1) {
            System.out.println(e1.getMessage());
        }

    } // onCreate

    private Handler mHandler = new Handler();

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd일 hh시 mm분 출발");
            String dateString = formatter.format(now);
            timeTv.setText(dateString);

        }
    };

    class MainTimerTask extends TimerTask {
        public void run() {
            mHandler.post(mUpdateTimeTask);
        }
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mTimer.cancel();
        super.onPause();
    }

    @Override
    protected void onResume() {
        MainTimerTask timerTask = new MainTimerTask();
        mTimer = new Timer();
        mTimer.schedule(timerTask, 500, 3000);
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        finish();
    }

    // 버튼이 눌렸을 때
    public void mOnClick(View view) {

        View v = FindActivity.this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        // 팝업 메뉴 객체 생성
        PopupMenu popup = new PopupMenu(this, view);

        // xml 파일에 정의한 메뉴를 가져오기 위함
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();

        // 실제 메뉴 정의한 것을 가져온느 부분, menu 객체에 넣어줌
        inflater.inflate(R.menu.sort_menu, menu);


        // 메뉴를 클릭했을 때 처리하는 부분
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            EditText start = (EditText) findViewById(R.id.searchView11);
            EditText way = (EditText) findViewById(R.id.searchView12);
            EditText end = (EditText) findViewById(R.id.searchView13);

            TextView t1 = (TextView) findViewById(R.id.station1);
            TextView t2 = (TextView) findViewById(R.id.station2);
            TextView t3 = (TextView) findViewById(R.id.station3);

            View l1 = (View) findViewById(R.id.line2);
            TextView s1 = (TextView) findViewById(R.id.spend_time1);
            TextView s2 = (TextView) findViewById(R.id.spend_time2);

            View b1 = (View) findViewById(R.id.bar1);
            View b2 = (View) findViewById(R.id.bar2);
            TextView d1 = (TextView) findViewById(R.id.detail);
            TextView d2 = (TextView) findViewById(R.id.detail2);

            Integer s = 0;
            Integer w = 0;
            Integer e = 0;
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (!start.getText().toString().equals("") && !end.getText().toString().equals("")){
                    s = Integer.parseInt(start.getText().toString());
                    e = Integer.parseInt(end.getText().toString());
                    System.out.println(s + " " + e);
                } else {
                    return false;
                }
                if (!way.getText().toString().equals("") && FindActivity.this.way){
                    w = Integer.parseInt(way.getText().toString());
                }
                // 각 메뉴별 아이디를 조사한 후 할일을 적어줌
                FindRoute fr1 = new FindRoute(-1, new ArrayList<>());
                FindRoute fr2 = new FindRoute(-1, new ArrayList<>());
                try {
                    switch (menuItem.getItemId()) {
                        case R.id.best:
                            if (w == 0) {
                                fr1 = d.dijkstra(s.intValue(), e.intValue(),1);
                            } else {
                                fr1 = d.dijkstra(s.intValue(), w.intValue(), 1);
                                fr2 = d.dijkstra(w.intValue(), e.intValue(), 1);
                            }
                            tp = 1;
                            break;
                        case R.id.fast:
                            if (w == 0) {
                                fr1 = d.dijkstra(s.intValue(), e.intValue(),0);
                            } else {
                                fr1 = d.dijkstra(s.intValue(), w.intValue(), 0);
                                fr2 = d.dijkstra(w.intValue(), e.intValue(), 0);
                            }
                            tp = 0;
                            break;
                        case R.id.min_amount:
                            if (w == 0) {
                                fr1 = d.dijkstra(s.intValue(), e.intValue(),2);
                            } else {
                                fr1 = d.dijkstra(s.intValue(), w.intValue(), 2);
                                fr2 = d.dijkstra(w.intValue(), e.intValue(), 2);
                            }
                            tp = 2;
                            break;
                    }

                } catch (IOException e) {
                    System.out.println(e);
                    return false;
                } catch (Exception e) {
                    System.out.println(e);
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return false;
                }

                String cost1 = "";
                String cost2 = "";

                switch (menuItem.getItemId()) {
                    case R.id.best:
                        if (w == 0) {
                            cost1 = fr1.getCost() + "미터";
                        } else {
                            cost1 = fr1.getCost() + "미터";
                            cost2 = fr2.getCost() + "미터";
                        }
                        break;
                    case R.id.fast:
                        if (w == 0) {
                            cost1 = fr1.getCost() + "초";
                        } else {
                            cost1 = fr1.getCost() + "초";
                            cost2 = fr2.getCost() + "초";
                        }
                        break;
                    case R.id.min_amount:
                        if (w == 0) {
                            cost1 = fr1.getCost() + "원";
                        } else {
                            cost1 = fr1.getCost() + "원";
                            cost2 = fr2.getCost() + "원";
                        }
                        break;
                }

                String[] details = new String[0];

                if (w == 0) {
                    l1.setVisibility(View.GONE);
                    s2.setVisibility(View.GONE);
                    t3.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    d2.setVisibility(View.GONE);
                    t1.setText(s.toString());
                    t2.setText(e.toString());
                    s1.setText(cost1);
                    d1.setText(fr1.getRoute().toString());
                    details = d.getDetails(fr1.getRoute());
                    timeSecond = d.getTotal(fr1.getRoute(), "time");
                } else {
                    l1.setVisibility(View.VISIBLE);
                    s2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    d2.setVisibility(View.VISIBLE);
                    t1.setText(s.toString());
                    t2.setText(w.toString());
                    t3.setText(e.toString());
                    s1.setText(cost1);
                    s2.setText(cost2);
                    d1.setText(fr1.getRoute().toString());
                    d2.setText(fr2.getRoute().toString());
                    details = d.getDetails(fr1.getRoute(), fr2.getRoute());
                    timeSecond = d.getTotal(fr1.getRoute(), "time")
                            + d.getTotal(fr2.getRoute(), "time");
                }

                TextView st = (TextView) findViewById(R.id.startTime);
                TextView info = (TextView) findViewById(R.id.information);

                st.setText(details[0]);
                info.setText(details[1] + ", " + details[2]);

                sharing = "경로 공유\n\n"
                        + "출발역: " + start.getText();
                if (FindActivity.this.way) {
                    sharing += "\n경유역: " + way;
                }
                sharing += "\n도착역: " + end.getText() + "\n\n이동 경로: ";
                for (int i = 0; i < fr1.getRoute().size(); i++) {
                    sharing += fr1.getRoute().get(i);
                    if (i != fr1.getRoute().size()-1) {
                        sharing += " -> ";
                    }
                }
                for (int i = 0; i < fr2.getRoute().size(); i++) {
                    sharing += fr2.getRoute().get(i);
                    if (i != fr2.getRoute().size()-1) {
                        sharing += " -> ";
                    }
                }
                sharing += "\n\n소요 시간: " + details[0];
                sharing += "\n필요 금액: " + (d.getTotal(fr1.getRoute(), "cost")
                        + d.getTotal(fr2.getRoute(), "cost"));

                System.out.println(sharing);

                //최근 이용 db에 역 이름 등록
                try {
                    for(Recent item : recentList){
                        if(item.getStart().equals(s.toString()) && item.getWay().equals(w.toString()) && item.getEnd().equals(e.toString()))
                            throw new reduplicationEx("중복 데이터 입력 방지");
                    }
                    db.insertDatatoRecentlyUsed(userSid, s.toString(), w.toString(), e.toString(), tp);
                } catch (reduplicationEx e) {
                    System.out.println(e.getMessage());
                }

                return false;
            }
        });

        popup.show();
    }

}

class reduplicationEx extends Exception {
    reduplicationEx(){}

    reduplicationEx(String message){
        super(message);
    }
}

