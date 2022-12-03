package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.userSid;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

}
