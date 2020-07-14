package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesHospitalAndPharmacyActivity extends AppCompatActivity {

    EditText etFavoritesHospitalSearch;

    ListView listViewFavoritesHospitalPharmacy;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListFavoritesHospitalPharmacy = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_hospital_and_pharmacy);

        setSupportActionBar( findViewById(R.id.favorites_hospital_pharmacy_toolbar) );
        getSupportActionBar().setTitle("");

        etFavoritesHospitalSearch = findViewById(R.id.et_favorites_hospital_pharmacy_search);

        setFavoritesHospitalPharmacyListView();

    }//onCreate method

    public void setFavoritesHospitalPharmacyListView(){

        addFavoritesHospitalPharmacyListView();

        listViewFavoritesHospitalPharmacy = findViewById(R.id.list_favorites_hospital_pharmacy_list);

        listViewFavoritesHospitalPharmacy.setEmptyView( findViewById(R.id.tv_favorites_hospital_pharmacy_empty) );

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListFavoritesHospitalPharmacy);
        listViewFavoritesHospitalPharmacy.setAdapter( adapter );

        listViewFavoritesHospitalPharmacy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //확인용
                Toast.makeText(FavoritesHospitalAndPharmacyActivity.this,
                        ""+position + " : " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }//onItemClick method
        });//setOnItemClickListener

    }//setFavoritesHospitalPharmacyListView method

    public void addFavoritesHospitalPharmacyListView(){
//        arrayListFavoritesHospitalPharmacy.add( "aaa" );
//        arrayListFavoritesHospitalPharmacy.add( "aaa" );
    }//addFavoritesHospitalPharmacyListView method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method
}//FavoritesHospitalAndPharmacyActivity class
