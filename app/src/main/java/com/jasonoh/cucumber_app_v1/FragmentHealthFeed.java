package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentHealthFeed extends Fragment {

    private Context context;

    private ImageView myMenu;

    private Button btnSeeLineChart;
    private LineChart lineChart;
    private boolean btnSeeLineChartBoolean = false;

    private FloatingActionButton btnMyHealthEdit;

    private Button btnMyHealthInfo, btnBoard;
    private RelativeLayout myHealthInfoView, boardView;
    private RecyclerView recyclerViewMyHealth, recyclerViewBoard;
    private ArrayList<FragmentMyHealthMember> members = new ArrayList<>();
    private RecyclerViewMyHealthAdapter adapterMyHealth;


    public FragmentHealthFeed() {
    }//HomeFragment Constructor (null)

    public FragmentHealthFeed(Context context) {
        this.context = context;
    }//HomeFragment Constructor

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        members.add( new FragmentMyHealthMember( "https://i.pinimg.com/originals/c6/f4/4a/c6f44a7aba8db7ae72bfd08e0160c752.png",
                "title",
                "weight",
                "message",
                "date" ) );

    }//onCreate method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.frag_bottom_nav_health_feed, container, false );

        ((MainActivity) getActivity()).setSupportActionBar( view.findViewById(R.id.frag_health_feed_toolbar) );
        ((MainActivity) getActivity()).setTitle("");

        myMenu = view.findViewById(R.id.frag_health_feed_my_page);

        btnMyHealthInfo = view.findViewById(R.id.frag_btn_health_feed_my_health);
        btnBoard = view.findViewById(R.id.frag_btn_health_feed_board);
        myHealthInfoView = view.findViewById(R.id.frag_health_feed_my_health_view);
        boardView = view.findViewById(R.id.frag_health_feed_board_view);

        btnMyHealthEdit = view.findViewById(R.id.frag_health_feed_my_health_float_btn);

        recyclerViewMyHealth = view.findViewById(R.id.frag_health_feed_my_recycler);

        lineChart = view.findViewById(R.id.frag_health_feed_line_chart);
        btnSeeLineChart = view.findViewById(R.id.frag_health_feed_btn_see_line_chart);

        return view;
    }//onCreateView method

    // View 가 Create 되고 난 다음에 나타나는 것
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickItem();

        setMyHealthRecyclerItem();

    }//onViewCreated method

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(context, "파괴 되었다!!!", Toast.LENGTH_SHORT).show();
    }

    public void setMyHealthRecyclerItem(){
        adapterMyHealth = new RecyclerViewMyHealthAdapter(context, members);
        recyclerViewMyHealth.setAdapter( adapterMyHealth );
    }//setMyHealthRecyclerItem method

    public void clickItem(){

        //나의 메뉴 페이지로 이동
        myMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( context, MyMenuActivity.class ) );
            }//onClick
        });//myPage.setOnClickListener

        btnMyHealthInfo.setSelected(true); // 기본 처음 설정

        // 나의 건강 정보 나오도록 설정
        btnMyHealthInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyMenu();
            }//onClick method
        });//btnMyHealthInfo.setOnClickListener

        //게시판 정보 보여주기
        //기본은 전체 보기로 설정되어 있음
        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBoard();
            }//onClick method
        });//btnBoard.setOnClickListener

        btnSeeLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!btnSeeLineChartBoolean) {
                    btnSeeLineChartBoolean = true;
                    new SetLineChartFromFragmentHealthFeed(getActivity(), lineChart).setLineChart();
                    lineChart.setVisibility(View.VISIBLE);
                    btnSeeLineChart.setText( R.string.frag_health_feed_close_line_chart );
                    btnMyHealthEdit.hide();
                    Toast.makeText(context, ""+ btnSeeLineChartBoolean, Toast.LENGTH_SHORT).show();
                } else {
                    btnSeeLineChartBoolean = false;
                    lineChart.setVisibility(View.GONE);
                    btnSeeLineChart.setText( R.string.frag_health_feed_see_line_chart );
                    btnMyHealthEdit.show();
                    Toast.makeText(context, ""+ btnSeeLineChartBoolean, Toast.LENGTH_SHORT).show();
                }//if else
            }//onClick method
        });//btnSeeLineChart.setOnClickListener

        btnMyHealthEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "aaa", Toast.LENGTH_SHORT).show();
            }//onClick method
        });//btnMyHealthEdit.setOnClickListener

    }//clickItem

    public void setMyMenu(){
        myHealthInfoView.setVisibility(View.VISIBLE);
        boardView.setVisibility(View.GONE);
        btnMyHealthInfo.setSelected(true);
        btnBoard.setSelected(false);
    }//setMyMenu method

    public void setBoard(){
        myHealthInfoView.setVisibility(View.GONE);
        boardView.setVisibility(View.VISIBLE);
        btnMyHealthInfo.setSelected(false);
        btnBoard.setSelected(true);
    }// setBoard method


}//FragmentHealthFeed fragment class
