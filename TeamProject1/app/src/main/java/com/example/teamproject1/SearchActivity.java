package com.example.teamproject1;

import static android.content.ContentValues.TAG;

import static com.example.teamproject1.InfoActivity.station_name;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private String[] mStrs = { "aaa", "bbb", "ccc", "ddd" };
    private SearchView msearchView;
    private ListView mListView;
    private ListViewAdapter listViewAdapter;
    private UserDBHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

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
        listViewAdapter = new ListViewAdapter();
        mListView.setAdapter(listViewAdapter);

        listViewAdapter.addItem("str");
        listViewAdapter.addItem("str2");

        // 검색바 구현
        msearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){
                    mListView.setFilterText(newText);
                }else{
                    mListView.clearTextFilter();
                }
                return false;
            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<String> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(String item) {
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
            final String stItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.simple_list_item , viewGroup, false);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            TextView text = convertView.findViewById(R.id.st_name_search);
            text.setText(stItem);
            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(stItem);
                    station_name = stItem;
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
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
