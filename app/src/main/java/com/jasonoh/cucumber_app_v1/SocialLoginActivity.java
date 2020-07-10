package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;

public class SocialLoginActivity extends AppCompatActivity {

    Toolbar toolbar;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        toolbar = findViewById(R.id.social_login_toolbar);
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle("");

        //todo : google Login을 위한 onCreate
        googleLogin();

        //todo : 카카오톡 로그인을 위한 onCreate
        //카카오로그인 버튼은 별도의 클릭 이벤트 처리 없이도 자동으로 웹뷰를 실행하여 로그인 웹 페이지를 보여줌
        //그 웹페이지의 로그인 응답 결과를 받기 위한 세션(카카오와 연결되는 통로 같은 느낌)을 연결해야함!!
        Session.getCurrentSession().addCallback( sessionCallback );

    }//onCreate method

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        switch (requestCode) {
            case Global.RC_SIGN_IN:
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
        }//switch case
    }

    //todo : 카카오 로그인 서버와 연결을 시도하는 세션 작업의 결과를 듣는 리스너 sessionCallback
    ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Toast.makeText(SocialLoginActivity.this, "로그인 세션연결 성공", Toast.LENGTH_SHORT).show();

            //로그인 된 사용자의 정보들 얻어오기
            requestUserInfo();

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(SocialLoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    };// ISessionCallback

    //todo : 카카오 로그인 사용자 정보 받기 requestUserInfo() method
    public void requestUserInfo(){
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                //사용자 계정 정보 객체
                UserAccount userAccount = result.getKakaoAccount();
                if(userAccount == null) return;

                // 기본 프로필 정보 (닉네임, 이미지, 섬네일 이미지)
                Profile profile = userAccount.getProfile();
                if(profile == null) return;

                Intent intent = getIntent();
                intent.putExtra("KakaoName", profile.getNickname());
                if(userAccount.getEmail() != null) intent.putExtra("KakaoEmail", userAccount.getEmail());
                if(profile.getProfileImageUrl() != null) intent.putExtra("KakaoImage", profile.getProfileImageUrl());
                setResult( RESULT_OK, intent );

                Global.kakaoLoginSuccessBoolean = true;

                SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Name", profile.getNickname());
                editor.putString("Email", userAccount.getEmail());
                editor.putString("ImageUri", profile.getProfileImageUrl());
                editor.putBoolean("KakaoLoginSuccessBoolean", Global.kakaoLoginSuccessBoolean);
                editor.commit();

                // todo : 나중에 확인
                Global.loginPreferences = pref;

                finish();

            }
        });//UserManagement
    }//requestUserInfo method

    //todo : onDestroy method
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //앱이 끝날때 카카오 세션 연결을 종료
        Session.getCurrentSession().removeCallback(sessionCallback);
    }//onDestroy method

    // todo : googleLogin() method
    public void googleLogin(){

        //google Login을 위한 메소드
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.social_login_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.social_login_google :
                        signIn();
                        break;
                }//switch case 문
            }//onClick method
        });//onClickListener (social_login_google)
    }//googleLogin method

    //todo : google Login을 위한 handleSignInResult method
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

                    // Signed in successfully, show authenticated UI.
            updateUI(account);
            //todo : 테스트 목적 로그인 성공을 알리는 토스트
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }//handleSignInResult method

    //todo : google login 을 위한 updateUI() method
    private void updateUI(GoogleSignInAccount account) {
        //로그인 시 발동 하도록 하는 메소드
        //https://medium.com/@siisee111/android-google-sign-in%ED%95%98%EC%97%AC-profile-%ED%91%9C%EC%8B%9C%ED%95%98%EA%B8%B0-12e76899ea47

        //todo : 구글 로그인을 위해서 소셜 로그인 액티비티로 간 것을 다시 돌아 온 것을 확인 한것
        if(account != null) {
            if(account instanceof Parcelable) {
                Log.w("TAG", "parcelable");
            }
            Log.w("TAG", "name : " + account.getDisplayName());
            Intent intent = getIntent();
            intent.putExtra(Global.GOOGLE_ACCOUNT, account);
            setResult(RESULT_OK, intent);

            SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Name", account.getDisplayName());
            editor.putString("Email", account.getEmail());
            editor.putString("ImageUri", account.getPhotoUrl().toString());
            editor.commit();

            // todo : 나중에 확인
            Global.loginPreferences = pref;

            finish();
        } //if

        //==============================================================================================

//        if(account != null) {
//            if(account instanceof Parcelable) {
//                Log.w("TAG", "parcelable");
//            }
//            Log.w("TAG", "name : " + account.getDisplayName());
//            Intent intent = new Intent(this, MyMenuActivity.class);
//            intent.putExtra(Global.GOOGLE_ACCOUNT, account);
//            setResult(RESULT_OK, intent);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity( intent );
//            finish();
//        } //if

    }//updateUi method

    // todo : google login 을 위한 signIn() method
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Global.RC_SIGN_IN);
    }//signIn method

    //todo : onStart() method
    @Override
    protected void onStart() {
        super.onStart();

        //구글 로그인 위해서
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }//onStart method

    //todo : clickBackBtn method
    public void clickBackBtn(View view){
        //이전 화면으로 돌아가기
        finish();
    }// clickBackBtn method

    //todo : 테스트용 구글 로그아웃
    public void clickLogoutBtn(View view) {
        mGoogleSignInClient.signOut();
        // todo : 로그아웃 되었는지 확인하는 테스트
        if(!mGoogleSignInClient.signOut().isSuccessful()) Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
    }//clickLogoutBtn method
}//SocialLoginActivity class
