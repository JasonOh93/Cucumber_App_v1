package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AppInnerPasswordActivity extends AppCompatActivity {

    //request code = 500 부터 599까지 Global class 에서 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_inner_password);
    }//onCreate method

    public void clickBackBtn(View view) {
        //이전화면으로 가기 위해
        finish();
    }//clickBackBtn method
}//AppInnerPasswordActivity class
