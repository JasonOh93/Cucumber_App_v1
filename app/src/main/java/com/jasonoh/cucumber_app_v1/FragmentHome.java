package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentHome extends Fragment {

    Context context;

    Toolbar toolbar;
    CircleImageView civHospital;
    CircleImageView civPharmacy;
    CircleImageView civHealthFeed;
    CircleImageView civHealthInfo;

    //request code = 100 부터 199까지
    // Global class에서 설정
//    final int CALENDAR_REQUEST = 100;
//    final int LOCATION_REQUEST = 101;
//    final int MY_MENU_REQUEST = 102;

    public FragmentHome() {
    }//HomeFragment Constructor (null)

    public FragmentHome(Context context) {
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

        View view = inflater.inflate( R.layout.frag_bottom_nav_home, container, false );

        toolbar = view.findViewById(R.id.home_frag_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar( toolbar );
        getActivity().setTitle("");


        civHealthFeed = view.findViewById(R.id.home_frag_civ_health_feed);
        civHealthInfo = view.findViewById(R.id.home_frag_civ_health_info);
        civHospital = view.findViewById(R.id.home_frag_civ_hospital);
        civPharmacy = view.findViewById(R.id.home_frag_civ_pharmacy);


        clickItem();

        return view;
    }//onCreateView method

    public void clickItem(){
        civHealthFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_health_feed );

            }//onClick method
        });//setOnClickListener

        civHealthInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_health_info );
            }//onClick method
        });//setOnClickListener

        civHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_hospital_pharmacy );
                Global.hospitalPharmacyBooleanFromHomeFrag = false;
                //((MainActivity)getActivity()).bottomNavFrags[1]. //여기서 병원 탭또는 약국 탭으로 이동
            }//onClick method
        });//setOnClickListener

        civPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_hospital_pharmacy );
                Global.hospitalPharmacyBooleanFromHomeFrag = true;
            }//onClick method
        });//setOnClickListener
    }//

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate( R.menu.frag_home_menu, menu );

    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_frag_home_calendar :
                startActivityForResult( new Intent(context, CalendarActivity.class), Global.CALENDAR_REQUEST);
                break;

            case R.id.menu_frag_home_location :
                startActivityForResult( new Intent(context, LocationActivity.class), Global.LOCATION_REQUEST);
                break;

            case R.id.menu_frag_home_my_menu :
                startActivityForResult( new Intent(context, MyMenuActivity.class), Global.MY_MENU_REQUEST);
                break;
        }

        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected method

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Global.CALENDAR_REQUEST :
                break;

            case Global.LOCATION_REQUEST :
                break;

            case Global.MY_MENU_REQUEST :
                //돌아오는지 확인 용
                if(resultCode != getActivity().RESULT_OK)
                    Toast.makeText(context, "CANCEL" + resultCode, Toast.LENGTH_SHORT).show();

                if(resultCode == getActivity().RESULT_OK) {
                    Toast.makeText(context, "OK" + resultCode, Toast.LENGTH_SHORT).show();
                    //마이 메뉴에서 설정한 값으로 프레그먼트 제어
                    if(data.getStringExtra(Global.GET_START_HEALTH_FEED_FROM_MY_MENU_ACTIVITY)
                            .equals(Global.GET_START_HEALTH_FEED_FROM_MY_MENU_ACTIVITY))
                        ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_health_feed );
                }//if resultCode OK
                break;
        }//switch case

    }//onActivityResult method
}//HomeFragment Class
