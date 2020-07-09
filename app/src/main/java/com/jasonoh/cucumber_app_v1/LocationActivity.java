package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LocationActivity extends AppCompatActivity {

    //request code = 200 부터 299까지
    //Global class에서 설정
    //final int LOCATION_REQUEST = 101; //홈 프레그먼트에서 로케이션 액티비티로 연결되는 인탠트 연결 변호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }
}
