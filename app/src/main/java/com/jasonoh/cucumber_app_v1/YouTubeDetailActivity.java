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

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouTubeDetailActivity extends AppCompatActivity {

    EditText etSearchYouTube;

    RecyclerView recyclerView;
    YouTubeDetailListAdapter adapter;
    ArrayList<YouTubeDetailListRecyclerItem> items = new ArrayList<>();

    //돌아온 인텐트로 부터 값을 확인해서 값을 대입하기 위해서 사용하는 쿼리값
    String query;

    YouTubePlayerFragment youTubePlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_detail);

        setSupportActionBar( findViewById(R.id.youtube_detail_toolbar) );
        getSupportActionBar().setTitle("");

        etSearchYouTube = findViewById(R.id.youtube_detail_search_et);

        initYouTubeSearch();

    }//onCreate method

    public void initYouTubeSearch(){

        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_detail_youtube_view_frag);

        if(Global.youTubeVideoId != null) {
            youTubePlayerFragment.initialize("first", new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    Log.w("TAG", "저장된 유투브 값" + Global.youTubeVideoId);
                    youTubePlayer.cueVideo( Global.youTubeVideoId );
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
        }

    }//initYouTubeSearch method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickBtn(View view) {
        //유투브 검색으로 보내버리기
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/results?search_query=" + etSearchYouTube.getText().toString()));
        startActivity( intent );
        etSearchYouTube.setText("");

        //소프트 키패드를 안보이도록
        InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //flags 는 즉시하려면 0
        //imm.showSoftInput() //화면에 보일때는 show
    }//clickBtn method
}//YouTubeDetailListActivity class
