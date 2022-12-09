package com.example.teamproject1;

import static android.content.ContentValues.TAG;

import static com.example.teamproject1.InfoActivity.station_name;
import static com.example.teamproject1.MainActivity.getStList;
import static com.example.teamproject1.MainActivity.userSid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView msearchView;
    private ListView mListView;
    private ListViewAdapter listViewAdapter;
    private stListViewAdapter stlistViewAdapter;
    private UserDBHelper db;
    private List<Searched> searchedList_init;
    private List<StationInfo> stList;
    private String type;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        db = new UserDBHelper(SearchActivity.this);
        searchedList_init = db.getRecentlySearchedData(userSid);

        stList = getStList();

        type = db.getUserDatabySId(userSid).getColor();

        FrameLayout tb = (FrameLayout)findViewById(R.id.Fl);
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

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        // 화면 전환
        ImageButton mButton = (ImageButton) findViewById(R.id.backBtn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        //
        msearchView = (SearchView) findViewById(R.id.searchView);
        mListView = (ListView) findViewById(R.id.listView_search);
        TextView text = (TextView) findViewById(R.id.textView_search);

        listViewAdapter = new ListViewAdapter();
        for(Searched item : searchedList_init){
            listViewAdapter.addItem(item);
        }
        mListView.setAdapter(listViewAdapter);

        // 검색바 구현
        msearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("hi");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //아무것도 입력하지 않았을때 최근 검색 기록 노출
                if(newText.equals("")){
                    List<Searched> list = db.getRecentlySearchedData(userSid);
                    System.out.println(list);
                    listViewAdapter = new ListViewAdapter();
                    for(Searched item : list){
                        listViewAdapter.addItem(item);
                    }
                    mListView.setAdapter(listViewAdapter);
                    text.setText("최근 검색 기록");
                } else {
                    List<StationInfo> list = stList;
                    stlistViewAdapter = new stListViewAdapter();
                    for(StationInfo item : stList) {
                        if(item.getSt_name().contains(newText)){
                            stlistViewAdapter.addItem(item);
                        }
                    }
                    mListView.setAdapter(stlistViewAdapter);
                    text.setText("검색어를 포함하는 결과");
                }
                return false;
            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<Searched> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Searched item) {
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
            final Searched stItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.simple_list_item , viewGroup, false);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            TextView text = convertView.findViewById(R.id.st_name_search);
            text.setText(stItem.getStation());
            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    station_name = stItem.getStation();
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intent);
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

    public class stListViewAdapter extends BaseAdapter {
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

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.simple_list_item, viewGroup, false);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            TextView text = convertView.findViewById(R.id.st_name_search);
            text.setText(stItem.getSt_name());
            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    station_name = stItem.getSt_name();
                    List<Searched> list = db.getRecentlySearchedData(userSid);
                    try {
                        for(Searched item : list){
                            if(item.getStation().equals(station_name)){
                                throw new reduplicationEx("중복 입력 방지");
                            }
                        }
                        db.insertDatatoRecentlySearched(userSid, station_name);
                    } catch (reduplicationEx e) {
                        System.out.println(e.getMessage());
                    } finally {
                        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                        startActivity(intent);
                    }
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

