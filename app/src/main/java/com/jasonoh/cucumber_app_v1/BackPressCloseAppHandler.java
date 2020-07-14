package com.jasonoh.cucumber_app_v1;

import android.app.Activity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BackPressCloseAppHandler {

    // 하단 뒤로가기 버튼 두번 클릭시 종료 되도록 하는 것
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    private Activity activity;

    public BackPressCloseAppHandler() {
    }//BackPressCloseAppHandler constructor

    public BackPressCloseAppHandler(Activity activity) {
        this.activity = activity;
    }//BackPressCloseAppHandler constructor (activity)

    public void onBackPressedApp(){
        if(System.currentTimeMillis() > backPressedTime + FINISH_INTERVAL_TIME) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(activity, "뒤로 가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(System.currentTimeMillis() <= backPressedTime + FINISH_INTERVAL_TIME) activity.finish();
    }

}
