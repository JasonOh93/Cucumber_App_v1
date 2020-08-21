package com.jasonoh.cucumber_app_v1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchHospitalAndPharmacyActivity extends AppCompatActivity {

    EditText etSearchHospitalPharmacy;

    ListView listViewSearchHospitalPharmacy;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListSearchHospitalPharmacy = new ArrayList<>();

    ImageButton btnSearch;
    Button btnHospital, btnPharmacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hospital_and_pharmacy);

        setSupportActionBar( findViewById(R.id.search_hospital_pharmacy_toolbar) );
        getSupportActionBar().setTitle("");

        etSearchHospitalPharmacy = findViewById(R.id.et_search_hospital_pharmacy_search);

        setSearchHospitalPharmacyListView();

        btnSearch = findViewById(R.id.btn_search_hospital_pharmacy_search);
        btnHospital = findViewById(R.id.btn_search_hospital);
        btnPharmacy = findViewById(R.id.btn_search_Pharmacy);

        //초기 화면 설정
        setSearchHospital();

//        //외부에서 오는 값 확인후 해당으로 탭으로
//        if(!Global.searchHospitalPharmacyBooleanFromMyMenuActivity) setSearchHospital();
//        else setSearchPharmacy();

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

    public void setSearchHospital(){
        btnHospital.setSelected(true);
        btnPharmacy.setSelected(false);
    }//setFavoritesHospital method

    public void setSearchPharmacy(){
        btnHospital.setSelected(false);
        btnPharmacy.setSelected(true);

    }//setFavoritesPharmacy method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickBtn(View view) {

        switch (view.getId()) {

            //검색창 누를시
            case R.id.btn_search_hospital_pharmacy_search :
                Toast.makeText(this, "검색", Toast.LENGTH_SHORT).show();
                break;

            //병원 탭 클릭시
            case R.id.btn_search_hospital :
                Toast.makeText(this, "병원", Toast.LENGTH_SHORT).show();
                setSearchHospital();
                break;

            //약국 탭 클릭시
            case R.id.btn_search_Pharmacy :
                Toast.makeText(this, "약국", Toast.LENGTH_SHORT).show();
                setSearchPharmacy();
                break;

        }//switch case

    }//clickBtn method

}//SearchHospitalAndPharmacyActivity class
