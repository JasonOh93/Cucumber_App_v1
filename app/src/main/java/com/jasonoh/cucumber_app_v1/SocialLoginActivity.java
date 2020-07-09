package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class SocialLoginActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        toolbar = findViewById(R.id.social_login_toolbar);
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle("");

    }//onCreate method

    public void clickBackBtn(View view){
        //이전 화면으로 돌아가기
        finish();
    }// clickBackBtn method

}//SocialLoginActivity class
