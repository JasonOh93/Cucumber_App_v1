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

                if(Global.loginPreferences.getString( Global.LOGIN_APP_PASSWORD_KEY, null ) != null)
                    startActivity(new Intent(IntroActivity.this, AppIntroPasswordActivity.class));
                else startActivity( new Intent(IntroActivity.this, MainActivity.class) );
                finish();

            }
        }.start();

        Global.loginPreferences = getSharedPreferences(Global.LOGIN_PREFERENCES_KEY, MODE_PRIVATE);
//        Global.loginPreferences.getString( "Name", "" );
//        Global.loginPreferences.getString( "Email", "" );
//        Global.loginPreferences.getString( "ImageUri", "" );
        Global.kakaoLoginSuccessBoolean = Global.loginPreferences
                .getBoolean(Global.LOGIN_KAKAO_SUCCESS_BOOLEAN_KEY, false);
        Global.googleLoginSuccessBoolean = Global.loginPreferences
                .getBoolean(Global.LOGIN_GOOGLE_SUCCESS_BOOLEAN_KEY, false);

        Global.appPassword = Global.loginPreferences.getString( Global.LOGIN_APP_PASSWORD_KEY, null );

        Global.locationLatitude = Double.parseDouble(getSharedPreferences(Global.MY_LOCATION_KEY_NAME, MODE_PRIVATE)
                .getString(Global.MY_LOCATION_LAT_KEY_NAME, "37.5553"));

        Global.locationLongitude = Double.parseDouble(getSharedPreferences(Global.MY_LOCATION_KEY_NAME, MODE_PRIVATE)
                .getString(Global.MY_LOCATION_LON_KEY_NAME, "127.0422"));

    }//onCreate method
}//IntroActivity class
