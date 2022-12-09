package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.userSid;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static boolean exit_state;
    private UserDBHelper db;
    private List<User> userList;
    private EditText userID, userPassword;
    private Button login_button, join_button;

    private final long finishTime= 1000;
    private long pressTime = 0;

    private GradientDrawable drawableButOval;
    private GradientDrawable drawableElipseWhiteblue;
    private GradientDrawable drawableRadiusLayout;
    private GradientDrawable drawableRadiusMainblue;
    private GradientDrawable drawableRadiusSearchLayout;
    private GradientDrawable drawableRadiusWhiteblue;
    private GradientDrawable drawableFavItem;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_layout);

        db = new UserDBHelper(LoginActivity.this);
        userList = db.getUserData();

        userID = findViewById(R.id.userID);
        userPassword = findViewById(R.id.userPassword);
        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uID = null;
                String uPW = null;
                if (userID.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "ID를 입력해주세요.", Toast.LENGTH_LONG).show();
                } else if (userPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "PassWord를 입력해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    uID = userID.getText().toString();
                    uPW = userPassword.getText().toString();
                }

                if(uID != null && uPW != null) {
                    if (SignIn(uID, uPW)) {
                        //ToDo 로그인 이후 화면 전환 및 현재 유저 정보(SID 정도만?) 저장
                        System.out.println("로그인 성공");
                        userSid = db.getUserDatabyId(uID).getSid();
                        setColor(userSid);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "잘못된 회원정보입니다.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        join_button = findViewById(R.id.join_button);
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
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



    //로그인 - 로그인 작동 체크 완료
    //Id, Pw 예외 따로 처리하여 오류 메세지 구분해야함
    public boolean SignIn(String id, String pw) {
        User user = new User();

        for(User u: userList){
            if(u.getId().equals(id)){
                user = u;
                break;
            }
        }

        if(user.getPw() == null){
            return false;
        }

        if(user.getPw().equals(pw)){
            return true;
        } else {
            return false;
        }
    }

    public void setColor(String sid) {
        String type = db.getUserDatabySId(sid).getColor();
        drawableButOval = (GradientDrawable) getResources().getDrawable(R.drawable.but_oval);
        drawableElipseWhiteblue = (GradientDrawable) getResources().getDrawable(R.drawable.elipse_whiteblue);
        drawableRadiusLayout = (GradientDrawable) getResources().getDrawable(R.drawable.radius_layout);
        drawableRadiusMainblue = (GradientDrawable) getResources().getDrawable(R.drawable.radius_mainblue);
        drawableRadiusSearchLayout = (GradientDrawable) getResources().getDrawable(R.drawable.radius_search_layout);
        drawableRadiusWhiteblue = (GradientDrawable) getResources().getDrawable(R.drawable.radius_whiteblue);
        drawableFavItem = (GradientDrawable) getResources().getDrawable(R.drawable.fav_item);

        if(type.equals("blue")){
            setBlue();
        } else if(type.equals("pink")){
            setPink();
        } else if(type.equals("green")){
            setGreen();
        }
    }

    public void setBlue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableButOval.setColor(getColor(R.color.blue_B3D5F2));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableElipseWhiteblue.setColor(getColor(R.color.blue_E1E9F6));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusLayout.setColor(getColor(R.color.blue_B3D5F2));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusMainblue.setColor(getColor(R.color.blue_3F9CF1));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusSearchLayout.setColor(getColor(R.color.blue_E1E9F6));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusWhiteblue.setColor(getColor(R.color.blue_E1E9F6));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableFavItem.setColor(getColor(R.color.blue_E1E9F6));
        }
    }

    public void setPink() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableButOval.setColor(getColor(R.color.pink_F2B3DD));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableElipseWhiteblue.setColor(getColor(R.color.pink_F6E1F1));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusLayout.setColor(getColor(R.color.pink_F2B3DD));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusMainblue.setColor(getColor(R.color.pink_F13FCA));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusSearchLayout.setColor(getColor(R.color.pink_F6E1F1));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusWhiteblue.setColor(getColor(R.color.pink_F6E1F1));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableFavItem.setColor(getColor(R.color.pink_F6E1F1));
        }
    }

    public void setGreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableButOval.setColor(getColor(R.color.green_B3F2B9));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableElipseWhiteblue.setColor(getColor(R.color.green_E1F6E2));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusLayout.setColor(getColor(R.color.green_B3F2B9));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusMainblue.setColor(getColor(R.color.green_64AE70));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusSearchLayout.setColor(getColor(R.color.green_E1F6E2));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableRadiusWhiteblue.setColor(getColor(R.color.green_E1F6E2));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableFavItem.setColor(getColor(R.color.green_E1F6E2));
        }
    }

}
