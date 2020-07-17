package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesHospitalAndPharmacyActivity extends AppCompatActivity {

    EditText etFavoritesHospitalSearch;

    ListView listViewFavoritesHospitalPharmacy;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListFavoritesHospitalPharmacy = new ArrayList<>();

    ImageButton btnSearch;
    Button btnHospital, btnPharmacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_hospital_and_pharmacy);

        setSupportActionBar( findViewById(R.id.favorites_hospital_pharmacy_toolbar) );
        getSupportActionBar().setTitle("");

        etFavoritesHospitalSearch = findViewById(R.id.et_favorites_hospital_pharmacy_search);

        setFavoritesHospitalPharmacyListView();

        btnSearch = findViewById(R.id.btn_favorites_hospital_pharmacy_search);
        btnHospital = findViewById(R.id.btn_favorites_hospital);
        btnPharmacy = findViewById(R.id.btn_favorites_Pharmacy);

        //초기 화면 설정
        setFavoritesHospital();

        //외부에서 오는 값 확인후 해당으로 탭으로
        if(!Global.favoritesHospitalPharmacyBooleanFromMyMenuActivity) setFavoritesHospital();
        else setFavoritesPharmacy();

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

    public void setFavoritesHospital(){
        btnHospital.setSelected(true);
        btnPharmacy.setSelected(false);
    }//setFavoritesHospital method

    public void setFavoritesPharmacy(){
        btnHospital.setSelected(false);
        btnPharmacy.setSelected(true);

    }//setFavoritesPharmacy method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickBtn(View view) {

        switch (view.getId()) {

            //검색창 누를시
            case R.id.btn_favorites_hospital_pharmacy_search :
                Toast.makeText(this, "검색", Toast.LENGTH_SHORT).show();
                break;

            //병원 탭 클릭시
            case R.id.btn_favorites_hospital :
                Toast.makeText(this, "병원", Toast.LENGTH_SHORT).show();
                setFavoritesHospital();
                break;

            //약국 탭 클릭시
            case R.id.btn_favorites_Pharmacy :
                Toast.makeText(this, "약국", Toast.LENGTH_SHORT).show();
                setFavoritesPharmacy();
                break;

        }//switch case

    }//clickBtn method
}//FavoritesHospitalAndPharmacyActivity class
