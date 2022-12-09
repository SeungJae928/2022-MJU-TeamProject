package com.example.teamproject1;

import static android.content.ContentValues.TAG;

import static com.example.teamproject1.InfoActivity.state;
import static com.example.teamproject1.InfoActivity.station_name;
import static com.example.teamproject1.MainActivity.getStList;
import static com.example.teamproject1.MainActivity.userSid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private UserDBHelper db;
    private Button back_btn;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private List<Favorites> fav_list;
    private List<StationInfo> stList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_page);

        db = new UserDBHelper(FavoriteActivity.this);
        fav_list = db.getFavoriteDatabyUserSid(userSid);
        stList = getStList();

        listView = findViewById(R.id.fav_list);
        listViewAdapter = new ListViewAdapter();

        if(!fav_list.isEmpty()){
            for(Favorites item : fav_list){
                listViewAdapter.addItem(item);
            }
        }

        listView.setAdapter(listViewAdapter);

        back_btn = findViewById(R.id.backBtn_fav);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<Favorites> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Favorites item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        public List getItemlist() {
            return this.items;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final Favorites stItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_list_item_fav, viewGroup, false);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            TextView st_name_fav = (TextView) convertView.findViewById(R.id.st_name_fav);
            TextView st_conge = (TextView) convertView.findViewById(R.id.st_conge);
            TextView isStore = (TextView) convertView.findViewById(R.id.isStore);
            TextView isToilet = (TextView) convertView.findViewById(R.id.isToilet);
            TextView isVending = (TextView) convertView.findViewById(R.id.isVending);

            for(StationInfo item : stList){
                if(item.getSt_name().equals(stItem.getStation())){
                    switch (item.getCongestion()){
                        case 0 :
                            st_conge.setText("여유");
                            st_conge.setTextColor(Color.GREEN);
                            break;
                        case 1 :
                            st_conge.setText("보통");
                            st_conge.setTextColor(Color.BLUE);
                            break;
                        case 2 :
                            st_conge.setText("혼잡");
                            st_conge.setTextColor(Color.RED);
                            break;
                    }
                    if(item.isStore()){
                        isStore.setText("있음");
                    } else {
                        isStore.setText("없음");
                    }
                    if(item.isToilet()){
                        isToilet.setText("있음");
                    } else {
                        isToilet.setText("없음");
                    }
                    if(item.isVending()){
                        isVending.setText("있음");
                    } else {
                        isVending.setText("없음");
                    }
                }
            }
            st_name_fav.setText(stItem.getStation());
            Log.d(TAG, "getView() - [ "+position+" ] "+stItem.getStation());

            CheckBox fav_checkbox = convertView.findViewById(R.id.fav_checkbox);

            for(Favorites item : fav_list){
                if(item.getStation().equals(stItem.getStation())){
                    fav_checkbox.setChecked(true);
                }
            }

            fav_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fav_checkbox.isChecked()) {
                        db.insertDatatoFavorite(userSid, stItem.getStation());
                    } else {
                        db.deleteFavData(stItem.getStation());
                    }
                }
            });

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intent);
                    station_name = stItem.getStation();
                    state = true;
                    finish();
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
