package com.example.teamproject1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserDBHelper db;
    EditText userID, userPassword;
    SQLiteDatabase sqLiteDatabase;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_detail_page);

        db = new UserDBHelper(MainActivity.this);
        userList = db.getTableData();
        SignUp("Bang", "60212192", "BSJ");
        for(User user: userList){
            System.out.println(user.getId() + " " + user.getName() + " " + user.getPw() + " " + user.getSid());
        }
    }

    //로그인 - 로그인 작동 체크 완료
    public boolean SignIn(String id, String pw) {
        User user = new User();

        for(User u: userList){
            if(u.getId().equals(id)){
                user = u;
                break;
            }
        }
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