package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class FragmentHospitalPharmacy extends Fragment {

    Context context;

    Toolbar toolbar;
    Button btnHospital, btnPharmacy;

    ImageView favoritesHospitalPharmacy, searchHospitalPharmacy;

    Button btnMedicalSubject, btnLocation, btnSeeMore;
    RelativeLayout relativeGoogleMap;
    boolean btnSeeMoreBoolean = false;

    LinearLayout linearHospitalPharmacyMedicalAndLocation;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    GoogleMap GoogleMap;
    SupportMapFragment supportMapFragment;


    public FragmentHospitalPharmacy() {
    }//HomeFragment Constructor (null)

    public FragmentHospitalPharmacy(Context context) {
        this.context = context;
    }//HomeFragment Constructor

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        arrayList.add(0, "aaa");

    }//onCreate method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.frag_bottom_nav_hospital_pharmacy, container, false );

        toolbar = view.findViewById(R.id.frag_hospital_pharmacy_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar( toolbar );
        getActivity().setTitle("");

        btnHospital = view.findViewById(R.id.btn_frag_hospital_top);
        btnPharmacy = view.findViewById(R.id.btn_frag_pharmacy_top);

        favoritesHospitalPharmacy = view.findViewById(R.id.frag_hospital_pharmacy_star);
        searchHospitalPharmacy = view.findViewById(R.id.frag_hospital_pharmacy_search);

        btnMedicalSubject = view.findViewById(R.id.frag_btn_hospital_medical_subjects);
        btnLocation = view.findViewById(R.id.frag_btn_hospital_pharmacy_location);
        btnSeeMore = view.findViewById(R.id.frag_btn_hospital_pharmacy_see_more);
        relativeGoogleMap = view.findViewById(R.id.frag_hospital_pharmacy_map_google);
        linearHospitalPharmacyMedicalAndLocation =
                view.findViewById(R.id.frag_hospital_pharmacy_medical_location_view);

        listView = view.findViewById(R.id.frag_hospital_pharmacy_list);

        return view;
    }//onCreateView method

    // View 가 Create 되고 난 다음에 나타나는 것
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickItem();

        if(!Global.hospitalPharmacyBooleanFromHomeFrag) setHospital();
        else setPharmacy();

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map_google);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
//                GoogleMap = googleMap;
                setGoogleMapLocation(googleMap);
            }
        });

    }//onViewCreated method

    //지도가 생성되고 난후 내부 에서 정할때 사용
    public void setGoogleMapLocation(GoogleMap googleMap){

        //원하는 좌표 객체 생성
        LatLng seoul = new LatLng(37.56, 126.97);

        //마크 옵션 객체 생성(marker 의 설정)
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position( seoul );
        markerOptions.title( "Seoul" );
        markerOptions.snippet( "대한민국의 수도" );

        //지도에 마크를 추가
        googleMap.addMarker( markerOptions );

        //카메라 이동을 스무스하게 효과를 주면서 줌까지 적용
        googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( seoul, 16 ) );

    }//setGoogleMapLocation method

    public void clickItem(){

        btnHospital.setSelected( true );

        //제목줄 버튼 클릭시 행동

        //병원 버튼 클릭시 변화
        btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHospital();
            }//onClick method
        });//btnHospital.setOnClickListener

        //약국 버튼 클릭시 변화
        btnPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setPharmacy();
            }//onClick method
        });//btnHospital.setOnClickListener

        //즐겨찾기 한 곳으로 이동
        favoritesHospitalPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult( new Intent(context, FavoritesHospitalAndPharmacyActivity.class)
                        , Global.FAVORITES_HOSPITAL_PHARMACY_REQUEST_FROM_FRAG_HOSPITAL_PHARMACY);
            }//onClick method
        });//favoritesHospitalPharmacy.setOnClickListener

        //검색 한 창으로 이동
        searchHospitalPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult( new Intent(context, SearchHospitalAndPharmacyActivity.class)
                        , Global.SEARCH_HOSPITAL_PHARMACY_REQUEST_FROM_FRAG_HOSPITAL_PHARMACY);
            }//onClick method
        });//searchHospitalPharmacy.setOnClickListener

        // 전체 진료 과목 누르면 행동하는 것
        btnMedicalSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "전체 진료 과목 선택", Toast.LENGTH_SHORT).show();
            }//onClick method
        });//btnMedicalSubject.setOnClickListener

        // 위치 선택 누르면 이동
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult( new Intent(context, LocationActivity.class)
                        , Global.LOCATION_REQUEST_FROM_FRAG_HOSPITAL_PHARMACY);
            }//onClick method
        });//btnLocation.setOnClickListener

        // 하단에 병원보기 및 약국 보기 이용시
        btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!btnSeeMoreBoolean) {
                    seeMoreBooleanTrue();

                    //병원 보기 및 약국 보기 에 따라서 보여주는 값을 다르게
                    if(btnSeeMore.getText().toString().equals("병원 보기")) {
                        listView.setVisibility(View.VISIBLE);
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
                        listView.setAdapter( adapter );
                    } else if(btnSeeMore.getText().toString().equals("약국 보기")) {
                    }// if else if

                } else if(btnSeeMoreBoolean) {
                    seeMoreBooleanFalse();
                }// if else if

            }//onClick method
        });//btnSeeMore.setOnClickListener

    }//clickItem

    public void setHospital(){
        //병원 탭 누를 시
        btnHospital.setSelected(true);
        btnPharmacy.setSelected(false);

        btnMedicalSubject.setVisibility(View.VISIBLE);
        btnSeeMore.setText( R.string.frag_hospital_see_more_btn );

        seeMoreBooleanFalse();

    }//setHospital method
    public void setPharmacy(){
        //약국 팁 누를 시
        btnHospital.setSelected(false);
        btnPharmacy.setSelected(true);

        btnMedicalSubject.setVisibility(View.GONE);
        btnSeeMore.setText( R.string.frag_pharmacy_see_more_btn );

        seeMoreBooleanFalse();
    }//setPharmacy method

    //병원 보기 및 약국 보기가 참일경우
    public void seeMoreBooleanTrue(){

        // 병원 보기및 약국 보기를 누를때 발동
        if(!btnSeeMoreBoolean) {
            relativeGoogleMap.setVisibility(View.GONE);
            btnSeeMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_down_black_24dp, 0);
            linearHospitalPharmacyMedicalAndLocation.setVisibility(View.GONE);
            btnSeeMoreBoolean = true;
        }// if

    }//seeMoreBooleanTrue method

    //병원 보기 및 약국 보기가 거짓일 경우
    public void seeMoreBooleanFalse(){

        // 병원 보기및 약국 보기를 누를때 발동
        if(btnSeeMoreBoolean) {
            relativeGoogleMap.setVisibility(View.VISIBLE);
            btnSeeMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_up_black_24dp, 0);
            linearHospitalPharmacyMedicalAndLocation.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            btnSeeMoreBoolean = false;
        }// if

    }//seeMoreBooleanFalse method

}//FragmentHospitalPharmacy Class
