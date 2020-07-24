package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;

import kr.co.prnd.YouTubePlayerView;

public class FragmentHealthInfo extends Fragment {

    private Context context;

    private EditText etWebSearch, etYouTubeSearch;
    private ImageView ivWebSearch, ivYouTubeSearch;

    private YouTubePlayerView youTubePlayerView;


    public FragmentHealthInfo() {
    }//HomeFragment Constructor (null)

    public FragmentHealthInfo(Context context) {
        this.context = context;
    }//HomeFragment Constructor

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }//onCreate method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.frag_bottom_nav_health_info, container, false );

        ((MainActivity) getActivity()).setSupportActionBar( view.findViewById(R.id.frag_health_info_toolbar) );
        getActivity().setTitle("");

        etWebSearch = view.findViewById(R.id.frag_health_info_web_search_et);
        etYouTubeSearch = view.findViewById(R.id.frag_health_info_youtube_search_et);
        ivWebSearch = view.findViewById(R.id.frag_health_info_web_search_iv);
        ivYouTubeSearch = view.findViewById(R.id.frag_health_info_youtube_search_iv);

        youTubePlayerView = view.findViewById(R.id.frag_health_info_youtube_view);

        return view;
    }//onCreateView method

    // View 가 Create 되고 난 다음에 나타나는 것
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickItem();

        loadYouTube();

    }//onViewCreated method

    //todo : 유투브 알아보기
    //  https://github.com/PRNDcompany/YouTubePlayerView
    public void loadYouTube(){
        youTubePlayerView.play("fyyiEBit8F0", new YouTubePlayerView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }//loadYouTube method

    public void clickItem(){

        //웹 검색 클릭시
        ivWebSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse( "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query="
                        + etWebSearch.getText().toString() ));
                startActivity( intent );
                etWebSearch.setText("");
            }
        });// ivWebSearch.setOnClickListener

        //유투브 검색 클릭시
        ivYouTubeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YouTubeDetailListActivity.class);
                intent.putExtra("SearchText", etYouTubeSearch.getText().toString());
                startActivity( intent );
            }
        });// ivYouTubeSearch.setOnClickListener
    }//clickItem



}//FragmentHealthFeed fragment class
