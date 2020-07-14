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

public class LocationActivity extends AppCompatActivity {

    EditText etLocationSearch;

    ListView listViewLocation;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListLocation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        setSupportActionBar( findViewById(R.id.location_toolbar) );
        getSupportActionBar().setTitle("");

        etLocationSearch = findViewById(R.id.et_location_search);

        setLocationListView();

    }//onCreate method

    public void setLocationListView(){

        addLocationListView();

        listViewLocation = findViewById(R.id.list_view_location_list);

        listViewLocation.setEmptyView( findViewById(R.id.tv_location_empty) );

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListLocation);
        listViewLocation.setAdapter( adapter );

        listViewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //확인용
                Toast.makeText(LocationActivity.this, ""+position + " : " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }//onItemClick method
        });//setOnItemClickListener

    }//setLocationListView method

    public void addLocationListView(){
        arrayListLocation.add( "aaa" );
        arrayListLocation.add( "aaa" );
        arrayListLocation.add( "aaa" );
        arrayListLocation.add( "aaa" );
        arrayListLocation.add( "aaa" );
    }//addLocationListView method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickBtn(View view) {

        switch (view.getId()) {
            case R.id.btn_location_search :
                Toast.makeText(this, "검색", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_location_now_location_setting :
                Toast.makeText(this, "현재 위치로 설정", Toast.LENGTH_SHORT).show();
                break;
        }//switch case

    }//clickBtn method
}//LocationActivity class
