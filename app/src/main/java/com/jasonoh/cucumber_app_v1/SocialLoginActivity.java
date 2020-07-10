package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

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

        //google Login을 위한
        googleLogin();

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

    private void updateUI(GoogleSignInAccount account) {
        //로그인 시 발동 하도록 하는 메소드
        //https://medium.com/@siisee111/android-google-sign-in%ED%95%98%EC%97%AC-profile-%ED%91%9C%EC%8B%9C%ED%95%98%EA%B8%B0-12e76899ea47

        //todo : 구글 로그인을 위해서 소셜 로그인 액티비티로 간 것을 다시 돌아 온 것을 확인 한것
//        if(account != null) {
//            if(account instanceof Parcelable) {
//                Log.w("TAG", "parcelable");
//            }
//            Log.w("TAG", "name : " + account.getDisplayName());
//            Intent intent = getIntent();
//            intent.putExtra(Global.GOOGLE_ACCOUNT, account);
//            setResult(RESULT_OK, intent);
//            finish();
//        } //if

        //==============================================================================================

        if(account != null) {
            if(account instanceof Parcelable) {
                Log.w("TAG", "parcelable");
            }
            Log.w("TAG", "name : " + account.getDisplayName());
            Intent intent = new Intent(this, MyMenuActivity.class);
            intent.putExtra(Global.GOOGLE_ACCOUNT, account);
            setResult(RESULT_OK, intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity( intent );
            finish();
        } //if

    }//updateUi method

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Global.RC_SIGN_IN);
    }//signIn method

    @Override
    protected void onStart() {
        super.onStart();

        //구글 로그인 위해서
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }//onStart method

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
