package com.example.teamproject1;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecentlyUsedActivity extends AppCompatActivity {

    List<Recent> stList;
    UserDBHelper db;
    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recently_used_page);

        db = new UserDBHelper(RecentlyUsedActivity.this);
        stList = db.getRecentlyUsedData();

        back_btn = findViewById(R.id.backBtn_rec);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<StationInfo> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(StationInfo item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final StationInfo stItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_list_item_fav, viewGroup, false);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            TextView st_name_fav = (TextView) convertView.findViewById(R.id.st_name_fav);

            st_name_fav.setText(stItem.getSt_name());
            Log.d(TAG, "getView() - [ "+position+" ] "+stItem.getSt_name());

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("hello");
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
