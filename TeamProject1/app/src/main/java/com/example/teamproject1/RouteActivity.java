package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.userSid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RouteActivity extends AppCompatActivity {

    private Button back;
    private String type;
    private UserDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_map);

        back = findViewById(R.id.backBtn_map);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        db = new UserDBHelper(RouteActivity.this);
        type = db.getUserDatabySId(userSid).getColor();

        androidx.appcompat.widget.Toolbar tb = (Toolbar)findViewById(R.id.toolbar_map);
        if (type.equals("blue")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tb.setBackgroundColor(getColor(R.color.blue_3F9CF1));
            }
        } else if (type.equals("pink")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tb.setBackgroundColor(getColor(R.color.pink_F13FCA));
            }
        } else if (type.equals("green")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tb.setBackgroundColor(getColor(R.color.green_64AE70));
                System.out.println("hi");
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        startActivity(intent);
        finish();
    }
}
