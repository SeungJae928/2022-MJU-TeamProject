package com.example.teamproject1;

import static com.example.teamproject1.MainActivity.userSid;

import android.content.Context;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecentlyUsedActivity extends AppCompatActivity {

    private List<Recent> stList;
    private UserDBHelper db;
    private Button back_btn;
    private ListViewAdapter listViewAdapter;
    private ListView listView;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recently_used_page);

        db = new UserDBHelper(RecentlyUsedActivity.this);
        stList = db.getRecentlyUsedData(userSid);

        listView = findViewById(R.id.rec_listview);
        listViewAdapter = new ListViewAdapter();
        listView.setAdapter(listViewAdapter);

        type = db.getUserDatabySId(userSid).getColor();

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
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
            }
        }

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
            TextView st_name_way = (TextView) convertView.findViewById(R.id.st_name_rce_way);
            TextView st_name_end = (TextView) convertView.findViewById(R.id.st_name_rce_end);
            TextView searchtype = (TextView) convertView.findViewById(R.id.search_type);
            View arrow = (View) convertView.findViewById(R.id.arrow);

            st_name_start.setText(stItem.getStart());
            if (stItem.getWay().equals("0")) {
                st_name_way.setText(stItem.getEnd());
                st_name_end.setText("0");
                arrow.setVisibility(View.GONE);
                st_name_end.setVisibility(View.GONE);
            } else {
                arrow.setVisibility(View.VISIBLE);
                st_name_end.setVisibility(View.VISIBLE);
                st_name_way.setText(stItem.getWay());
                st_name_end.setText(stItem.getEnd());
            }

            switch (stItem.getType()) {
                case 0 :
                    searchtype.setText("최단 시간");
                    break;
                case 1 :
                    searchtype.setText("최적 경로");
                    break;
                case 2 :
                    searchtype.setText("최소 금액");
                    break;
            }

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), FindActivity.class);
                    intent.putExtra("start", st_name_start.getText());
                    if (st_name_end.getText().equals("0")) {
                        intent.putExtra("end", st_name_way.getText());
                    } else {
                        intent.putExtra("way", st_name_way.getText());
                        intent.putExtra("end", st_name_end.getText());
                    }
                    intent.putExtra("type", searchtype.getText());
                    startActivity(intent);
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
