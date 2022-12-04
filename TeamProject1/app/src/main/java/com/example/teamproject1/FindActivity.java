package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.userSid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
    TextView mtextView;
    private TextView timeTv;
    private Timer mTimer;
    private boolean way = false;
    Dijkstra d;
    private UserDBHelper db;
    private List<Recent> recentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button mButton, addButton, alarmButton;
        d = new Dijkstra(this);

        db = new UserDBHelper(FindActivity.this);
        recentList = db.getRecentlyUsedData(userSid);

        androidx.appcompat.widget.Toolbar toolbar;
        EditText waySearchView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_page);

        addButton = (Button)findViewById(R.id.addButton);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        waySearchView = (EditText)findViewById(R.id.searchView12);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!way) {
                    toolbar.setLayoutParams(new LinearLayout.LayoutParams(toolbar.getLayoutParams().width, (int)(toolbar.getLayoutParams().height * 1.4)));
                    addButton.setBackground(getDrawable(R.drawable.delete_button));
                    waySearchView.setVisibility(SearchView.VISIBLE);

                } else {
                    toolbar.setLayoutParams(new LinearLayout.LayoutParams(toolbar.getLayoutParams().width, (int)(toolbar.getLayoutParams().height / 1.4)));
                    addButton.setBackground(getDrawable(R.drawable.add_button));
                    waySearchView.setVisibility(SearchView.GONE);
                }
                way = !way;
            }
        });

        timeTv = (TextView) findViewById(R.id.clockView);
        MainTimerTask timerTask = new MainTimerTask();
        mTimer = new Timer();
        mTimer.schedule(timerTask, 500, 1000);

        alarmButton = findViewById(R.id.alarm_btn);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "하차 알림을 시작합니다.", Toast.LENGTH_LONG).show();
            }
        });

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        // 버튼 등록
        mButton = (Button) findViewById(R.id.sort_button);

        //  텍스트뷰 받아오기
        mtextView = findViewById(R.id.clockView);

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
        mTimer.schedule(timerTask, 500, 3000);
        super.onResume();
    }

    // 버튼이 눌렸을 때
    public void mOnClick(View view) {
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
                if (!way.getText().toString().equals("")){
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
                            break;
                        case R.id.fast:
                            if (w == 0) {
                                fr1 = d.dijkstra(s.intValue(), e.intValue(),0);
                            } else {
                                fr1 = d.dijkstra(s.intValue(), w.intValue(), 0);
                                fr2 = d.dijkstra(w.intValue(), e.intValue(), 0);
                            }
                            break;
                        case R.id.min_amount:
                            if (w == 0) {
                                fr1 = d.dijkstra(s.intValue(), e.intValue(),2);
                            } else {
                                fr1 = d.dijkstra(s.intValue(), w.intValue(), 2);
                                fr2 = d.dijkstra(w.intValue(), e.intValue(), 2);
                            }
                            break;
                        case R.id.minimum:
                            //
                            break;
                    }

                } catch (IOException e) {
                    System.out.println(e);
                }
                if (w == 0) {
                    l1.setVisibility(View.GONE);
                    s2.setVisibility(View.GONE);
                    t3.setVisibility(View.GONE);
                    t1.setText(s.toString());
                    t2.setText(e.toString());
                    s1.setText(fr1.getCost().toString());
                } else {
                    l1.setVisibility(View.VISIBLE);
                    s2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    t1.setText(s.toString());
                    t2.setText(w.toString());
                    t3.setText(e.toString());
                    s1.setText(fr1.getCost().toString());
                    s2.setText(fr2.getCost().toString());
                }

                //최근 이용 db에 역 이름 등록
                try {
                    for(Recent item : recentList){
                        if(item.getStart().equals(s.toString()) && item.getEnd().equals(e.toString()))
                            throw new reduplicationEx("중복 데이터 입력 방지");
                    }
                    db.insertDatatoRecentlyUsed(userSid, s.toString(), e.toString());
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

