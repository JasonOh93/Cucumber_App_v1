package com.jasonoh.cucumber_app_v1;

import android.content.Context;
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

public class HomeFragment extends Fragment {

    Context context;

    Toolbar toolbar;
    CircleImageView civHospital;
    CircleImageView civPharmacy;
    CircleImageView civHealthFeed;
    CircleImageView civHealthInfo;

    public HomeFragment() {
    }//HomeFragment Constructor (null)

    public HomeFragment(Context context) {
        this.context = context;
    }//HomeFragment Constructor

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//onCreate method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.frag_bottom_nav_home, container, false );

        toolbar = view.findViewById(R.id.home_frag_toolbar);
        setHasOptionsMenu(true);

        ((MainActivity) getActivity()).setSupportActionBar( toolbar );
        ((MainActivity) getActivity()).setTitle("");

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
                Toast.makeText(context, "feed", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_health_feed );

            }//onClick method
        });//setOnClickListener

        civHealthInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "info", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_health_info );
            }//onClick method
        });//setOnClickListener

        civHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "hospital", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_hospital_pharmacy );
                //((MainActivity)getActivity()).bottomNavFrags[1]. //여기서 병원 탭또는 약국 탭으로 이동
            }//onClick method
        });//setOnClickListener

        civPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "pharmacy", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId( R.id.menu_hospital_pharmacy );
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
                Toast.makeText(context, "calendar", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_frag_home_location :
                Toast.makeText(context, "location", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_frag_home_my_menu :
                Toast.makeText(context, "mymenu", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}//HomeFragment Class
