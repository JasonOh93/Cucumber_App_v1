package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentHealthFeed extends Fragment {

    private Context context;

    private ImageView myMenu;

    private Button btnSeeLineChart;
    private LineChart lineChart;
    private boolean btnSeeLineChartBoolean = false;

    private FloatingActionButton btnMyHealthEdit;
    private FloatingActionButton btnBoardSelectMore;

    private Button btnMyHealthInfo, btnBoard;
    private RelativeLayout myHealthInfoView, boardView;
    private RecyclerView recyclerViewMyHealth, recyclerViewBoard;
    private ArrayList<FragmentMyHealthMember> myHealthMembers = new ArrayList<>();
    private RecyclerViewMyHealthAdapter adapterMyHealth;

    private CircleImageView civProfile;
    private TextView tvName, tvEmail;

    private ArrayList<FragmentBoardMember> boardMembers = new ArrayList<>();
    private RecyclerViewBoardAdapter adapterBoard;


    public FragmentHealthFeed() {
    }//HomeFragment Constructor (null)

    public FragmentHealthFeed(Context context) {
        this.context = context;
    }//HomeFragment Constructor

    //시작시 로그인 확인
    public void confirmLoginInfo(){
        if(!Global.kakaoLoginSuccessBoolean && !Global.googleLoginSuccessBoolean)
            new AlertDialog.Builder(context)
                    .setMessage("로그인이 필요합니다.\n로그인을 하시겠습니까?")
                    .setNegativeButton(R.string.edit_cancel_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_home );
                        }
                    })
                    .setPositiveButton(R.string.edit_ok_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity( new Intent(context, SocialLoginActivity.class) );
                        }
                    })
                    .setCancelable(false)
                    .show();
    }//confirmLoginInfo method

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        confirmLoginInfo();

        myHealthMembers.add( new FragmentMyHealthMember( "https://i.pinimg.com/originals/c6/f4/4a/c6f44a7aba8db7ae72bfd08e0160c752.png",
                "title",
                "weight",
                "message",
                "date" ) );

        boardMembers.add( new FragmentBoardMember( "https://i.pinimg.com/originals/c6/f4/4a/c6f44a7aba8db7ae72bfd08e0160c752.png",
                "title",
                "weight",
                "message",
                "date",
                "sample@email.com",
                1) );

        boardMembers.add( new FragmentBoardMember( "https://i.pinimg.com/originals/c6/f4/4a/c6f44a7aba8db7ae72bfd08e0160c752.png",
                "title",
                "weight",
                "message",
                "date",
                "sample@email.com",
                0) );

    }//onCreate method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.frag_bottom_nav_health_feed, container, false );

        ((MainActivity) getActivity()).setSupportActionBar( view.findViewById(R.id.frag_health_feed_toolbar) );
        getActivity().setTitle("");

        myMenu = view.findViewById(R.id.frag_health_feed_my_page);

        btnMyHealthInfo = view.findViewById(R.id.frag_btn_health_feed_my_health);
        btnBoard = view.findViewById(R.id.frag_btn_health_feed_board);
        myHealthInfoView = view.findViewById(R.id.frag_health_feed_my_health_view);
        boardView = view.findViewById(R.id.frag_health_feed_board_view);

        btnMyHealthEdit = view.findViewById(R.id.frag_health_feed_my_health_float_btn);
        btnBoardSelectMore = view.findViewById(R.id.frag_health_feed_board_float_btn);

        recyclerViewMyHealth = view.findViewById(R.id.frag_health_feed_my_recycler);
        recyclerViewBoard = view.findViewById(R.id.frag_health_feed_board_recycler);

        lineChart = view.findViewById(R.id.frag_health_feed_line_chart);
        btnSeeLineChart = view.findViewById(R.id.frag_health_feed_btn_see_line_chart);

        civProfile = view.findViewById(R.id.frag_health_feed_civ_profile);
        tvName = view.findViewById(R.id.frag_health_feed_tv_profile_title);
        tvEmail = view.findViewById(R.id.frag_health_feed_tv_profile_email);

        return view;
    }//onCreateView method

    @Override
    public void onResume() {
        super.onResume();

        confirmLoginInfo();

        // 로그인 정보가 없다면 기본정보로 -> 임시 방편
        if(!Global.loginPreferences.getString("ImageUri", "").equals(""))
            Glide.with(context).load(Global.loginPreferences.getString("ImageUri", "")).into(civProfile);
        else Glide.with(context).load(R.mipmap.ic_launcher_round).into(civProfile);
        tvName.setText( Global.loginPreferences.getString("Name", "No Name") );
        tvEmail.setText( Global.loginPreferences.getString( "Email", "No Email" ) );

        //마이 메뉴에서 나의 건강 정보 또는 건강 게시판 누를시에 해당 탭으로 이동
        if(!Global.healthFeedBooleanFromMyMenuActivity) setMyHealthInfo();
        else setBoard();

    }//onResume method

    // View 가 Create 되고 난 다음에 나타나는 것
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickItem();

        clickMyHealthFeed();

        clickBoard();

        if(!Global.healthFeedBooleanFromMyMenuActivity) setMyHealthInfo();
        else setBoard();

        setMyHealthRecyclerItem();

        setBoardRecyclerItem();

    }//onViewCreated method

    public void clickItem(){

        //나의 메뉴 페이지로 이동
        myMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( context, MyMenuActivity.class ) );
            }//onClick
        });//myPage.setOnClickListener

    }//clickItem

    public void setMyHealthRecyclerItem(){
        adapterMyHealth = new RecyclerViewMyHealthAdapter(context, myHealthMembers);
        recyclerViewMyHealth.setAdapter( adapterMyHealth );
    }//setMyHealthRecyclerItem method

    public void setBoardRecyclerItem(){
        adapterBoard = new RecyclerViewBoardAdapter(context, boardMembers);
        recyclerViewBoard.setAdapter( adapterBoard );
    }//setBoardRecyclerItem method

    public void clickMyHealthFeed(){

        btnMyHealthInfo.setSelected(true); // 기본 처음 설정

        if(!Global.loginPreferences.getString("ImageUri", "").equals(""))
            Glide.with(context).load(Global.loginPreferences.getString("ImageUri", "")).into(civProfile);
        else Glide.with(context).load(R.mipmap.ic_launcher_round).into(civProfile);
        tvName.setText( Global.loginPreferences.getString("Name", "No Name") );
        tvEmail.setText( Global.loginPreferences.getString( "Email", "No Email" ) );

        // 나의 건강 정보 나오도록 설정
        btnMyHealthInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyHealthInfo();
            }//onClick method
        });//btnMyHealthInfo.setOnClickListener

        // 차트 보기 클릭
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

        //정보 입력 창으로
        btnMyHealthEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( context, EditActivity.class ) );
            }//onClick method
        });//btnMyHealthEdit.setOnClickListener

    }//clickMyHealthFeed method

    public void clickBoard(){

        //게시판 정보 보여주기
        //기본은 전체 보기로 설정되어 있음
        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBoard();
            }//onClick method
        });//btnBoard.setOnClickListener

        //더보기 클릭시 다이얼 로그 띄뭐서 정보 정하기
        btnBoardSelectMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setItems(R.array.alert_board_see_more, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //가져온 글자들
                        String[] items = getResources().getStringArray(R.array.alert_board_see_more);

                        //가져온 글자 우선 토스트 띄우기
                        Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }//onClick method
        });//btnBoardSelectMore.setOnClickListener

    }//clickBoard method

    public void setMyHealthInfo(){
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
