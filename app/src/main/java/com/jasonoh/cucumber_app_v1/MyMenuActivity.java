package com.jasonoh.cucumber_app_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MyMenuActivity extends AppCompatActivity {

    Toolbar toolbar;

    //스위치 버튼 클릭시 발동하기 위해
    Switch passwordSetting;
    Switch notificationSetting;

    //비밀번호 변경 하기 위한 것들을 우선 없애고 비밀번호 설정시에만 나오도록 설정
    TextView tvPasswordChange;
    Button passwordChangeBtn;

    //request code = 1000 부터 1999까지
    //final int APP_INNER_PASSWORD_SETTING_PASSWORD_REQUEST = 1000;

    //final int MY_MENU_REQUEST = 102; // 홈 프레그먼트에서 마이메뉴 액티비티로 연결되는 인탠트 연결 변호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_menu);

        toolbar = findViewById(R.id.my_menu_toolbar);
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle("");

        //스위치 버튼 클릭시 발동하기 위해
        passwordSetting = findViewById(R.id.switch_btn_my_menu_password_setting);
        notificationSetting = findViewById(R.id.switch_btn_my_menu_notification_setting);
        checkSwitchBtn();

        //비밀번호 변경 하기 위한 것들을 우선 없애고 비밀번호 설정시에만 나오도록 설정
        passwordChangeBtn = MyMenuActivity.this.findViewById(R.id.btn_my_menu_password_change);
        tvPasswordChange = MyMenuActivity.this.findViewById(R.id.tv_my_menu_password_change);
        passwordChangeBtn.setVisibility(View.GONE);
        tvPasswordChange.setVisibility(View.GONE);

    }//onCreate method

    public void checkSwitchBtn(){
        //스위치 버튼 클릭시 발동

        // notification setting switch button

        // password setting switch button
        passwordSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    startActivityForResult( new Intent(MyMenuActivity.this, AppInnerPasswordActivity.class), Global.APP_INNER_PASSWORD_SETTING_PASSWORD_REQUEST);
                    passwordChangeBtn.setVisibility(View.VISIBLE);
                    tvPasswordChange.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MyMenuActivity.this, "비밀번호 설정이 OFF 됩니다.", Toast.LENGTH_SHORT).show();
                    passwordChangeBtn.setVisibility(View.GONE);
                    tvPasswordChange.setVisibility(View.GONE);
                }// if else 문

            }//onCheckedChanged method
        });//setOnCheckedChangeListener

    }//checkSwitchBtn method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Global.APP_INNER_PASSWORD_SETTING_PASSWORD_REQUEST :
                if(resultCode == RESULT_OK) {
                } else {
                    passwordSetting.setChecked( false );
                }// if else 문
                break;
        } // switch case 문

    }//onActivityResult method

    public void clickBackBtn(View view) {
        //이전화면으로 돌아가기 위해서
        finish();
    }//clickBackBtn

    public void clickBtn(View view) {

        switch (view.getId()) {
            case R.id.btn_my_menu_play_login :
                startActivityForResult( new Intent(this, SocialLoginActivity.class), Global.SOCIAL_LOGIN_REQUEST );
                break;
            case R.id.btn_my_menu_password_change :
                startActivityForResult( new Intent(this, AppInnerPasswordActivity.class), Global.APP_INNER_PASSWORD_SETTING_PASSWORD_REQUEST);
                break;

            case R.id.btn_my_menu_personal_info :
                break;
        }// switch case 문

    }//clickBtn class
}//MyMenuActivity class
