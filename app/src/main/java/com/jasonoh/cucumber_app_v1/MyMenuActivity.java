package com.jasonoh.cucumber_app_v1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.exception.KakaoException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyMenuActivity extends AppCompatActivity {

    Toolbar toolbar;

    //스위치 버튼 클릭시 발동하기 위해
    Switch passwordSetting;
    Switch notificationSetting;

    //비밀번호 변경 하기 위한 것들을 우선 없애고 비밀번호 설정시에만 나오도록 설정
    TextView tvPasswordChange;
    Button passwordChangeBtn;

//    Intent googleLoginIntent;
    GoogleSignInClient mGoogleSignInClient;
//    GoogleSignInAccount mGoogleSignInAccount;

    //로그인 시 사용되는 프로필 정보
    RelativeLayout myMenuLogin;
    CircleImageView civMyMenuProfile;
    TextView tvMyMenuProfileTitle, tvMyMenuProfileEmail;

    //비로그인 시 나타나는 것
    RelativeLayout myMenuNoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_menu);

        toolbar = findViewById(R.id.my_menu_toolbar);
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle("");

        //스위치 버튼 클릭시 발동하기 위해
        checkSwitchBtn();

        //비밀번호 변경 하기 위한 것들을 우선 없애고 비밀번호 설정시에만 나오도록 설정
        passwordChangeBtn = findViewById(R.id.btn_my_menu_password_change);
        tvPasswordChange = findViewById(R.id.tv_my_menu_password_change);

        if(Global.appPassword != null) passwordSetting.setChecked(true);

        if(!passwordSetting.isChecked()) {
            passwordChangeBtn.setVisibility(View.GONE);
            tvPasswordChange.setVisibility(View.GONE);
        } // if 스위치가 체크 되어 있을때만 나타나도록 하기 위해서

        //로그인시 나타나도록 하기 위해서 찾아온는 것
        myMenuLogin = findViewById(R.id.my_menu_login);
        civMyMenuProfile = findViewById(R.id.my_menu_civ_profile);
        tvMyMenuProfileTitle = findViewById(R.id.my_menu_tv_profile_title);
        tvMyMenuProfileEmail = findViewById(R.id.my_menu_tv_profile_email);

        //비로그인시 나타나도록 하기 위해서 찾아 오는 것
        myMenuNoLogin = findViewById(R.id.my_menu_no_login);

        //==========================================================================
        //todo : 구글 로그인을 위해서 소셜 로그인 액티비티에서 마이 메뉴로 보낸 것을 확인
//        if(getIntent().getParcelableExtra(Global.GOOGLE_ACCOUNT) != null) {
//            myMenuLogin.setVisibility(View.VISIBLE);
//            myMenuNoLogin.setVisibility(View.GONE);
//            googleLogin(getIntent().getParcelableExtra(Global.GOOGLE_ACCOUNT));
//        }// if
        //===========================================================================

    }//onCreate method

    @Override
    protected void onStart() {
        super.onStart();
        // todo : google login 확인

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//
//        Log.w("TAG", "account : " + account);

        Log.w("TAG", "Global Google Preference : " + Global.loginPreferences);

//        if(account != null) {
//            googleLogin(account);
//            myMenuLogin.setVisibility(View.VISIBLE);
//            myMenuNoLogin.setVisibility(View.GONE);
//        }
        if (Global.googleLoginSuccessBoolean) {
            myMenuLogin.setVisibility(View.VISIBLE);
            myMenuNoLogin.setVisibility(View.GONE);
            googleLogin();
        }
        Log.w("TAG", "kakaoLoginBoolean : " + Global.kakaoLoginSuccessBoolean);
        if (Global.kakaoLoginSuccessBoolean) {
            myMenuLogin.setVisibility(View.VISIBLE);
            myMenuNoLogin.setVisibility(View.GONE);
            kakaoLoadData();
        }
    }//onStart method

    public void googleLogin(){

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        if(googleLoginIntent != null) mGoogleSignInAccount = account;
//        else mGoogleSignInAccount = account;
//
//        if(mGoogleSignInAccount.getPhotoUrl() != null) {
//            Glide.with(this).load(mGoogleSignInAccount.getPhotoUrl()).into(civMyMenuProfile);
//            tvMyMenuProfileTitle.setText( mGoogleSignInAccount.getDisplayName() );
//            tvMyMenuProfileEmail.setText( mGoogleSignInAccount.getEmail() );
//
//            //todo : Log 구글 돌아온 이미지 경로 확인 하는 것
//            Log.w("TAG", "Image Uri : " + mGoogleSignInAccount.getPhotoUrl().toString());
//
//        } else {
//            Glide.with(this).load(R.mipmap.ic_launcher_round).into(civMyMenuProfile);
//        } // if else (그림이 없을경우 대체 그림으로 하기)

        //=====================================================================================================

        // 데이터 안쓰고 사용하기 위해서
        tvMyMenuProfileTitle.setText( Global.loginPreferences.getString(Global.LOGIN_NAME_KEY, "") );
        tvMyMenuProfileEmail.setText( Global.loginPreferences.getString(Global.LOGIN_EMAIL_KEY, "") );
        if(!Global.loginPreferences.getString(Global.LOGIN_IMG_URI_KEY, "").equals(""))
            Glide.with(this).load(Global.loginPreferences.getString(Global.LOGIN_IMG_URI_KEY, "")).into(civMyMenuProfile);
        else Glide.with(this).load(R.mipmap.ic_launcher_round).into(civMyMenuProfile);

    }//googleLogin method

    public void checkSwitchBtn(){
        //스위치 버튼 클릭시 발동
        passwordSetting = findViewById(R.id.switch_btn_my_menu_password_setting);
        notificationSetting = findViewById(R.id.switch_btn_my_menu_notification_setting);

        // notification setting switch button

        // password setting switch button
        passwordSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //todo : 비밀번호 설정이 체크 되었는지 확인 한 것
                if(isChecked) {
                    if(Global.appPassword == null)
                        startActivityForResult( new Intent(MyMenuActivity.this, AppInnerPasswordActivity.class), Global.APP_INNER_PASSWORD_SETTING_PASSWORD_REQUEST);
                    passwordChangeBtn.setVisibility(View.VISIBLE);
                    tvPasswordChange.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MyMenuActivity.this, R.string.myMenu_menu_password_setting_off, Toast.LENGTH_SHORT).show();
                    passwordChangeBtn.setVisibility(View.GONE);
                    tvPasswordChange.setVisibility(View.GONE);
                    Global.appPassword = null;
                    getSharedPreferences(Global.LOGIN_PREFERENCES_KEY, MODE_PRIVATE).edit().putString(Global.LOGIN_APP_PASSWORD_KEY, null).commit();
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
                    // 설정 완료시 토스트 띄우기
                    Toast.makeText(MyMenuActivity.this, R.string.myMenu_menu_password_setting_on, Toast.LENGTH_SHORT).show();
                } else {
                    passwordSetting.setChecked( false );
                }// if else 문
                break;

                //todo : 구글 로그인을 위해서 소셜 로그인 액티비티로 간 것을 다시 돌아 온 것을 확인 한것
            case Global.SOCIAL_LOGIN_REQUEST :

                if(resultCode == RESULT_OK) {

                    //todo : 구글 로그인 확인용 로그 : 인텐트 돌아온 것 확인
                    //Log.w("TAG", "msg : " + data.getParcelableExtra(Global.GOOGLE_ACCOUNT));

                    if(data != null) {
                        myMenuLogin.setVisibility(View.VISIBLE);
                        myMenuNoLogin.setVisibility(View.GONE);

                        if(data.getParcelableExtra(Global.GOOGLE_ACCOUNT) != null) {
//                            googleLoginIntent = data;
//                            googleLogin(data.getParcelableExtra(Global.GOOGLE_ACCOUNT));
                            googleLogin();
                        } else kakaoLoadData();
                    }

                }// if result ok

                break;

        } // switch case 문

    }//onActivityResult method

    //todo : kakaoLoadData() method
    public void kakaoLoadData(){

        Session.getCurrentSession().addCallback( sessionCallback );

        tvMyMenuProfileTitle.setText( Global.loginPreferences.getString(Global.LOGIN_NAME_KEY, "") );
        tvMyMenuProfileEmail.setText( Global.loginPreferences.getString(Global.LOGIN_EMAIL_KEY, "") );
        if(!Global.loginPreferences.getString(Global.LOGIN_IMG_URI_KEY, "").equals(""))
            Glide.with(this).load(Global.loginPreferences.getString(Global.LOGIN_IMG_URI_KEY, "")).into(civMyMenuProfile);
        else Glide.with(this).load(R.mipmap.ic_launcher_round).into(civMyMenuProfile);
//        Global.kakaoLoginSuccessBoolean = true;
//        getSharedPreferences("Login", MODE_PRIVATE).edit()
//                .putBoolean("KakaoLoginSuccessBoolean", Global.kakaoLoginSuccessBoolean).commit();

    }//kakaoLoadData method

    //카카오 로그인 서버와 연결을 시도하는 세션 작업의 결과를 듣는 리스너
    ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Toast.makeText(MyMenuActivity.this, "로그인 세션연결 성공", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(MyMenuActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    };

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
                startActivity( new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("http://jasonoh93.dothome.co.kr/CucumberApp.html")));
                break;

            case R.id.btn_my_menu_logout :
                signOut();
                break;
        }// switch case 문

    }//clickBtn class

    private void signOut() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(MyMenuActivity.this, gso);

        if(Global.googleLoginSuccessBoolean) {
            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    myMenuLogin.setVisibility(View.GONE);
                    myMenuNoLogin.setVisibility(View.VISIBLE);

                    Global.googleLoginSuccessBoolean = false;
                    getSharedPreferences(Global.LOGIN_PREFERENCES_KEY, MODE_PRIVATE).edit()
                            .putBoolean(Global.LOGIN_GOOGLE_SUCCESS_BOOLEAN_KEY, Global.googleLoginSuccessBoolean)
                            .putString(Global.LOGIN_NAME_KEY, null)
                            .putString(Global.LOGIN_EMAIL_KEY, null)
                            .putString(Global.LOGIN_IMG_URI_KEY, null).commit();

                    mGoogleSignInClient = null;

                }
            });
        } else if(Global.kakaoLoginSuccessBoolean) {

            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {

                    // todo : 이미 카카오에서는 그래픽을 건드는 것을 할수 없게 되어 있는 것 같아서 Thread를 사용
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyMenuActivity.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
                            myMenuLogin.setVisibility(View.GONE);
                            myMenuNoLogin.setVisibility(View.VISIBLE);
                            Session.getCurrentSession().removeCallback( sessionCallback );
                            Global.kakaoLoginSuccessBoolean = false;
                            getSharedPreferences(Global.LOGIN_PREFERENCES_KEY, MODE_PRIVATE).edit()
                                    .putBoolean(Global.LOGIN_KAKAO_SUCCESS_BOOLEAN_KEY, Global.kakaoLoginSuccessBoolean)
                                    .putString(Global.LOGIN_NAME_KEY, null)
                                    .putString(Global.LOGIN_EMAIL_KEY, null)
                                    .putString(Global.LOGIN_IMG_URI_KEY, null).commit();
                        }
                    });// runOnUiThread
                }// onCompleteLogout() method
            }); // UserManagement.getInstance().requestLogout

        }// if else


    }//signOut method

    //todo : MyMenuActivity onDestroy method
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback( sessionCallback );
        mGoogleSignInClient = null;
    }//onDestroy method

    public void clickTextView(View view) {

        switch (view.getId()) {

            //관심병원 클릭시
            case R.id.my_menu_like_hospital :
                Global.favoritesHospitalPharmacyBooleanFromMyMenuActivity = false;
                startActivity( new Intent(this, FavoritesHospitalAndPharmacyActivity.class) );
                break;

            //관심약국 클릭시
            case R.id.my_menu_like_pharmacy :
                Global.favoritesHospitalPharmacyBooleanFromMyMenuActivity = true;
                startActivity( new Intent(this, FavoritesHospitalAndPharmacyActivity.class) );
                break;

            //나의 건강 기록 클릭시
            case R.id.my_menu_health_feed :
                Global.healthFeedBooleanFromMyMenuActivity = false;
                Global.healthFeedBoardBooleanFromMyMenuActivity = false;
                Intent intent = getIntent();
                intent.putExtra( "StartHealthFeed", "StartHealthFeed" );
                setResult( RESULT_OK, intent );
                finish();
                break;

            //건강게시판 클릭시
            case R.id.my_menu_health_board :
                Global.healthFeedBooleanFromMyMenuActivity = true;
                getIntent().putExtra( "StartHealthFeed", "StartHealthFeed" );
                setResult(RESULT_OK, getIntent());
                finish();
                break;

            //최근 본 정보 클릭시
            case R.id.my_menu_recently_info :
                break;

        }//switch case

    }//clickTextView method
}//MyMenuActivity class
