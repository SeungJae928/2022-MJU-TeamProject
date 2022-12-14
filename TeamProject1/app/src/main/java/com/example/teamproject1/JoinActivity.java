package com.example.teamproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class JoinActivity extends AppCompatActivity {

    private Button verification_button, join_button2;
    private EditText joinID, joinPassword, joinName;
    private UserDBHelper db;
    private List<User> userList;
    private boolean isChecked;
    private String verificated_ID = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_page_layout);

        db = new UserDBHelper(JoinActivity.this);
        userList = db.getUserData();
        isChecked = false;

        verification_button = findViewById(R.id.verification_button);
        join_button2 = findViewById(R.id.join_button2);
        joinID = findViewById(R.id.joinID);
        joinPassword = findViewById(R.id.joinPassword);
        joinName = findViewById(R.id.joinName);

        verification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String uID = joinID.getText().toString();

                if(verification(uID)){
                    verificated_ID = uID;
                    isChecked = true;
                    Toast.makeText(getApplicationContext(), "사용가능한 ID입니다.", Toast.LENGTH_LONG).show();
                } else {
                    isChecked = false;
                    Toast.makeText(getApplicationContext(), "사용할 수 없는 ID입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        join_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String uID = joinID.getText().toString();
                final String uPW = joinPassword.getText().toString();
                final String uName = joinName.getText().toString();

                if(!uID.equals("") && !uPW.equals("") && !uName.equals("")){
                    if(verificated_ID.equals(uID) && isChecked && Join(uID, uPW, uName)){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "ID 중복 확인을 해주세요.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "회원 정보를 입력해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //회원가입 - DB 입력 정상적으로 되는거 체크 완료
    public boolean Join(String id, String pw, String name) {
        if(!verification(id)){
             return false;
        } else {
            db.insertDatatoUser(id, pw, name);
            return true;
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
