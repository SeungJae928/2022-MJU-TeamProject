package com.example.teamproject1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.teamproject1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FindActivity extends AppCompatActivity {
    TextView mtextView;
    private TextView timeTv;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button mButton;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_page);

        timeTv = (TextView) findViewById(R.id.clockView);
        MainTimerTask timerTask = new MainTimerTask();
        mTimer = new Timer();
        mTimer.schedule(timerTask, 500, 1000);

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
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd일 hh시 mm분 출발");
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
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // 각 메뉴별 아이디를 조사한 후 할일을 적어줌
                switch (menuItem.getItemId()) {
                    case R.id.best:
                        //
                        break;
                    case R.id.fast:
                        //
                        break;
                    case R.id.minimum:
                        break;
                }
                return false;
            }
        });

        popup.show();
    }

}

