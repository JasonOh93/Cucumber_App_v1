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
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class HospitalPharmacyFragment extends Fragment {

    Context context;

    Toolbar toolbar;
    Button btnHospital, btnPharmacy;

    public HospitalPharmacyFragment() {
    }//HomeFragment Constructor (null)

    public HospitalPharmacyFragment(Context context) {
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

        View view = inflater.inflate( R.layout.frag_bottom_nav_hospital_pharmacy, container, false );

        toolbar = view.findViewById(R.id.frag_hospital_pharmacy_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar( toolbar );
        ((MainActivity) getActivity()).setTitle("");

        btnHospital = view.findViewById(R.id.btn_frag_hospital_top);
        btnPharmacy = view.findViewById(R.id.btn_frag_pharmacy_top);

        clickItem();


        return view;
    }//onCreateView method

    public void clickItem(){

        btnHospital.setSelected( true );

        //제목줄 버튼 클릭시 행동
        btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHospital.setSelected(true);
                btnPharmacy.setSelected(false);
            }//onClick method
        });//btnHospital.setOnClickListener

        btnPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHospital.setSelected(false);
                btnPharmacy.setSelected(true);
            }//onClick method
        });//btnHospital.setOnClickListener

    }//clickItem

}//HomeFragment Class
