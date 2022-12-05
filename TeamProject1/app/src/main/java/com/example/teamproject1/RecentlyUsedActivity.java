package com.example.teamproject1;

import static android.content.ContentValues.TAG;

import static com.example.teamproject1.MainActivity.userSid;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecentlyUsedActivity extends AppCompatActivity {

    private List<Recent> stList;
    private UserDBHelper db;
    private Button back_btn;
    private ListViewAdapter listViewAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recently_used_page);

        db = new UserDBHelper(RecentlyUsedActivity.this);
        stList = db.getRecentlyUsedData(userSid);

        listView = findViewById(R.id.rec_listview);
        listViewAdapter = new ListViewAdapter();
        listView.setAdapter(listViewAdapter);

        for(Recent item : stList){
            listViewAdapter.addItem(item);
        }

        back_btn = findViewById(R.id.backBtn_rec);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<Recent> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Recent item) {
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
            final Recent stItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_list_item_rec, viewGroup, false);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            TextView st_name_start = (TextView) convertView.findViewById(R.id.st_name_rce_start);
            TextView st_name_end = (TextView) convertView.findViewById(R.id.st_name_rce_end);

            st_name_start.setText(stItem.getStart());
            st_name_end.setText(stItem.getEnd());

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
