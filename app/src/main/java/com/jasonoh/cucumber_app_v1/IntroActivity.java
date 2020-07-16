package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Thread(){
            @Override
            public void run() {
                super.run();

                SystemClock.sleep(2000);

                startActivity( new Intent(IntroActivity.this, MainActivity.class) );
                finish();

            }
        }.start();

        Global.loginPreferences = getSharedPreferences("Login", MODE_PRIVATE);
//        Global.loginPreferences.getString( "Name", "" );
//        Global.loginPreferences.getString( "Email", "" );
//        Global.loginPreferences.getString( "ImageUri", "" );
        Global.kakaoLoginSuccessBoolean = Global.loginPreferences
                .getBoolean("KakaoLoginSuccessBoolean", false);
        Global.googleLoginSuccessBoolean = Global.loginPreferences
                .getBoolean("GoogleLoginSuccessBoolean", false);

    }//onCreate method
}//IntroActivity class
