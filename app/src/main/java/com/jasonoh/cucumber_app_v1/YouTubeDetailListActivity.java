package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class YouTubeDetailListActivity extends AppCompatActivity {

    EditText etSearchYouTube;

    RecyclerView recyclerView;
    YouTubeDetailListAdapter adapter;
    ArrayList<YouTubeDetailListRecyclerItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_detail_list);

        setSupportActionBar( findViewById(R.id.youtube_detail_list_toolbar) );
        getSupportActionBar().setTitle("");

        etSearchYouTube = findViewById(R.id.youtube_detail_list_search_et);

        initYouTubeSearch();

    }//onCreate method

    public void initYouTubeSearch(){
        if(getIntent().getStringExtra("SearchText") != null) {
            //여기서 온 인텐트로부터 값 가져와서 검색
        }
    }//initYouTubeSearch method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickIv(View view) {
        // 리스트로 항목 보여주기

    }//clickIv method

    public void clickBtn(View view) {
        //유투브 검색으로 보내버리기
    }//clickBtn method
}//YouTubeDetailListActivity class
