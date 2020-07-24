package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouTubeDetailListActivity extends AppCompatActivity {

    EditText etSearchYouTube;

    RecyclerView recyclerView;
    YouTubeDetailListAdapter adapter;
    ArrayList<YouTubeDetailListRecyclerItem> items = new ArrayList<>();

    //돌아온 인텐트로 부터 값을 확인해서 값을 대입하기 위해서 사용하는 쿼리값
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_detail_list);

        setSupportActionBar( findViewById(R.id.youtube_detail_list_toolbar) );
        getSupportActionBar().setTitle("");

        etSearchYouTube = findViewById(R.id.youtube_detail_list_search_et);
        recyclerView = findViewById(R.id.youtube_detail_list_detail_youtube_info_recycler);

        adapter = new YouTubeDetailListAdapter(this, items);
        recyclerView.setAdapter(adapter);

        initYouTubeSearch();

    }//onCreate method

    public void initYouTubeSearch(){
        if(getIntent().getStringExtra("SearchText") != null && !getIntent().getStringExtra("SearchText").equals("")) {
            //여기서 온 인텐트로부터 값 가져와서 검색
            query = getIntent().getStringExtra("SearchText");
            loadDataFromYouTube();
        }
    }//initYouTubeSearch method

    public void loadDataFromYouTube(){

        String key = "AIzaSyCC2I2baJQ_5x5jKM7ysLmbUYYyDWlK1jY";//구글 키 값
        String part = "snippet";

        int maxResult = 10;

        RetrofitService retrofitService = RetrofitHelper.getInstanceGsonYouTube().create(RetrofitService.class);
        Call<Map<String, Object>> call = retrofitService.loadDataFromYouTube( key, part, query, maxResult );
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if(response.isSuccessful()) {

                    items.clear();
                    adapter.notifyDataSetChanged();

                    Map<String, Object> allYouTubeInfo = response.body();
                    ArrayList<Map<String, Object>> itemsYouTubeInfo =  (ArrayList<Map<String, Object>>) allYouTubeInfo.get("items");

                    for(int i = 0; i < itemsYouTubeInfo.size(); i++) {

                        Map<String, Object> idYouTubeInfo =  ( Map<String, Object> ) itemsYouTubeInfo.get(i).get("id");
                        Map<String, Object> snippetYouTubeInfo =  ( Map<String, Object> ) itemsYouTubeInfo.get(i).get("snippet");

                        //이미지 얻어오기
                        Map<String, Object> thumbnailsYouTubeInfo =  (Map<String, Object>) snippetYouTubeInfo.get("thumbnails");
                        Map<String, Object> defaultYouTubeInfo =  (Map<String, Object>) thumbnailsYouTubeInfo.get("default");

                        if(idYouTubeInfo.get("videoId") != null) {
                            items.add( items.size(),
                                    new YouTubeDetailListRecyclerItem(
                                            idYouTubeInfo.get("videoId").toString(),
                                            snippetYouTubeInfo.get("title").toString(),
                                            snippetYouTubeInfo.get("description").toString(),
                                            snippetYouTubeInfo.get("channelTitle").toString(),
                                            snippetYouTubeInfo.get("publishTime").toString(),
                                            defaultYouTubeInfo.get("url").toString()
                                    ) // new YouTubeDetailListRecyclerItem
                            );// items.add
                            adapter.notifyItemInserted(items.size());
                        }//if(idYouTubeInfo.get("videoId") != null)
                    }//for

                }// if(response.isSuccessful())
            }//onResponse method

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.w("TAG", "유투브로 부터 실패한 이유 : " + t.getMessage());
            }
        });


        //==========================================================================================================

    }//loadDataFromYouTube method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickIv(View view) {
        // 리스트로 항목 보여주기
        if(!etSearchYouTube.getText().toString().equals("")){
            query = etSearchYouTube.getText().toString();
            loadDataFromYouTube();

            //소프트 키패드를 안보이도록
            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //flags 는 즉시하려면 0
            //imm.showSoftInput() //화면에 보일때는 show

        } else Toast.makeText(this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();;

    }//clickIv method

    public void clickBtn(View view) {
        //유투브 검색으로 보내버리기
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/results?search_query=" + etSearchYouTube.getText().toString()));
        startActivity( intent );
    }//clickBtn method
}//YouTubeDetailListActivity class
