package com.jasonoh.cucumber_app_v1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChattingActivity extends AppCompatActivity {

    //todo : firebase 실시간 DB로 보내는 것을 기준으로 시작하기!!

    TextView tvTopTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        setSupportActionBar( findViewById(R.id.chatting_toolbar) );
        getSupportActionBar().setTitle( "" );

        tvTopTitleName = findViewById(R.id.chatting_top_title);
        tvTopTitleName.setText( getIntent().getStringExtra("ChattingPersonName") != null ? getIntent().getStringExtra("ChattingPersonName") : "이름이 없음" );

    }//onCreate method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickSend(View view) {
    }//clickSend
}//ChattingActivity class
