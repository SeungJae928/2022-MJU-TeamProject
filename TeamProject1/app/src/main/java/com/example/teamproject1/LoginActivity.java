package com.example.teamproject1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity{

    private UserDBHelper db;
    private EditText userID, userPassword;
    private Button login_button, signup_button;
    private SQLiteDatabase sqLiteDatabase;
    private List<User> userList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_layout);

        db = new UserDBHelper(LoginActivity.this);
        userList = db.getTableData();

        userID = findViewById(R.id.userID);
        userPassword = findViewById(R.id.userPassword);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String uID = userID.getText().toString();
                final String uPW = userPassword.getText().toString();

                if(SignIn(uID, uPW)) {
                    //ToDo 로그인 이후 화면 전환 및 현재 유저 정보 저장
                } else {
                    Toast.makeText(getApplicationContext(), "잘못된 회원정보입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

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

        System.out.println(user.getPw() + " " + pw);
        if(user.getPw().equals(pw)){
            return true;
        } else {
            return false;
        }
    }

    //회원가입 - DB 입력 정상적으로 되는거 체크 완료
    public boolean SignUp(String id, String pw, String name) {
        if(verification(id)){
            db.insertData(id, pw, name);
            return true;
        } else {
            return false;
        }
    }

    //아이디 중복 확인 - 중복데이터 안들어가는거 체크 완료
    public boolean verification(String id){
        for(User u: userList){
            if(u.getId().equals(id)){
                return false;
            }
        }
        return true;
    }

}
