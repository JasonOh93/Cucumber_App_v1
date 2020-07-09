package com.jasonoh.cucumber_app_v1;

import android.app.Application;
import android.content.Context;

import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

public class MyApplication extends Application {

    //카카오 로그인위한 것
    @Override
    public void onCreate() {
        super.onCreate();

        // SDK 초기화
        KakaoSDK.init(new KakaoAdapter() {

            @Override
            public IApplicationConfig getApplicationConfig() {
                return new IApplicationConfig() {
                    @Override
                    public Context getApplicationContext() {
                        return MyApplication.this;
                    }
                };
            }
        });//KakaoSDK
    }//onCreate method
}//MyApplication class
