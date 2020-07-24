package com.jasonoh.cucumber_app_v1;

//todo : 앞으로 해야 할 것들!!
//  myPage Activity : 최근 본 정보 나타나는 것
//                     알림 설정
//                      개인정보처리방침
//  Location Activity : 검색시 리스트로 검색된 결과물들 이름 보여주기
//                      현재 위치로 하면 병원 프레그먼트에서 바로바로 지도에 표시하기
//  Hospital Pharmacy Fragment : 진료과목별로 나타내기
//                              즐겨찾기 전부 하기 (Favorite Activity)
//                              검색 화면 설정하기 (Search Activity)
//                              위치 불러오기
//                              근처 병원및 등등 알아오기
//  Health Feed Fragment : 업로드 한 데이터 불러오기   = 일부 함 불러오기만 OK
//                          나의건강기록에서 차트 설정하기
//                          게시판 작업시 favor 설정 하기 (우선 boolean으로 하고 다음에 점점 올라가는 것으로 변경하기)
//                          게시판 더보기 클릭시 각각 분류별로 나오도록 해보기
// Chatting Activity : 채팅창 대화상대 이름 나오도록 툴바 설정
//                      Firebase 실시간 DB사용해서 해보기
// Health Information Fragment : 검색어 누를시 웹으로 검색 시키기
//                               유투브 검색시 해당 유투브 나오도록 설정

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
