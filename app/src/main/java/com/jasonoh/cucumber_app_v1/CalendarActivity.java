package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CalendarActivity extends AppCompatActivity {

    //REQUEST CODE = 300 부터 399 //Global class에서 설정

    //final int CALENDAR_REQUEST = 100; // 홈 프레그먼트에서 캘린더 액티비티로 연결되는 인탠트 연결 변호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }
}
