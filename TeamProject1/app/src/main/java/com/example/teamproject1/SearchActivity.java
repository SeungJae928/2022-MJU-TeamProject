package com.example.teamproject1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

public class SearchActivity extends AppCompatActivity {

    private String[] mStrs = { "aaa", "bbb", "ccc", "ddd" };
    private SearchView msearchView;
    private ListView mListView;

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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }

        });

        //
        msearchView = (SearchView) findViewById(R.id.searchView);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter((new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs)));
        mListView.setTextFilterEnabled(true);

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

}
