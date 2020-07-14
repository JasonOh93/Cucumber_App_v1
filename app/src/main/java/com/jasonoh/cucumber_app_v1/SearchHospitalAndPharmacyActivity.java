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

public class SearchHospitalAndPharmacyActivity extends AppCompatActivity {

    EditText etSearchHospitalPharmacy;

    ListView listViewSearchHospitalPharmacy;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListSearchHospitalPharmacy = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hospital_and_pharmacy);

        setSupportActionBar( findViewById(R.id.search_hospital_pharmacy_toolbar) );
        getSupportActionBar().setTitle("");

        etSearchHospitalPharmacy = findViewById(R.id.et_search_hospital_pharmacy_search);

        setSearchHospitalPharmacyListView();

    }//onCreate method

    public void setSearchHospitalPharmacyListView(){

        addSearchHospitalPharmacyListView();

        listViewSearchHospitalPharmacy = findViewById(R.id.list_search_hospital_pharmacy_location_list);

        listViewSearchHospitalPharmacy.setEmptyView( findViewById(R.id.tv_search_hospital_pharmacy_empty) );

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListSearchHospitalPharmacy);
        listViewSearchHospitalPharmacy.setAdapter( adapter );

        listViewSearchHospitalPharmacy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //확인용
                Toast.makeText(SearchHospitalAndPharmacyActivity.this,
                        ""+position + " : " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }//onItemClick method
        });//setOnItemClickListener

    }//setSearchHospitalPharmacyListView method

    public void addSearchHospitalPharmacyListView(){
//        arrayListSearchHospitalPharmacy.add( "aaa" );
//        arrayListSearchHospitalPharmacy.add( "aaa" );
    }//addSearchHospitalPharmacyListView method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method
}//SearchHospitalAndPharmacyActivity class
