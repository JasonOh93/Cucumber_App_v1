package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ChattingActivity extends AppCompatActivity {

    //todo : firebase 실시간 DB로 보내는 것을 기준으로 시작하기!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        setSupportActionBar( findViewById(R.id.chatting_toolbar) );
        getSupportActionBar().setTitle( "" );

    }//onCreate method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickSend(View view) {
    }//clickSend
}//ChattingActivity class
