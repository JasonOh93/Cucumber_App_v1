package com.jasonoh.cucumber_app_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyMenuActivity extends AppCompatActivity {

    Toolbar toolbar;

    //스위치 버튼 클릭시 발동하기 위해
    Switch passwordSetting;
    Switch notificationSetting;

    //비밀번호 변경 하기 위한 것들을 우선 없애고 비밀번호 설정시에만 나오도록 설정
    TextView tvPasswordChange;
    Button passwordChangeBtn;

    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount mGoogleSignInAccount;

    //로그인 시 사용되는 프로필 정보
    RelativeLayout myMenuLogin;
    CircleImageView civMyMenuProfile;
    TextView tvMyMenuProfileTitle, tvMyMenuProfileEmail;
    Button btnMyMenuLogOut;

    //비로그인 시 나타나는 것
    RelativeLayout myMenuNoLogin;

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

        //로그인시 나타나도록 하기 위해서 찾아온는 것
        myMenuLogin = findViewById(R.id.my_menu_login);
        civMyMenuProfile = findViewById(R.id.my_menu_civ_profile);
        tvMyMenuProfileTitle = findViewById(R.id.my_menu_tv_profile_title);
        tvMyMenuProfileEmail = findViewById(R.id.my_menu_tv_profile_email);

        //비로그인시 나타나도록 하기 위해서 찾아 오는 것
        myMenuNoLogin = findViewById(R.id.my_menu_no_login);


//        if(getIntent().getParcelableExtra(Global.GOOGLE_ACCOUNT).equals( Global.GOOGLE_ACCOUNT )) {
//            myMenuLogin.setVisibility(View.VISIBLE);
//            myMenuNoLogin.setVisibility(View.GONE);
//            googleLogin();
//        } else {
//
//        }// if else문

        // todo : nullPoint 예외 발생
        //googleLogin();


    }//onCreate method

    public void googleLogin(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInAccount = getIntent().getParcelableExtra(Global.GOOGLE_ACCOUNT);

        Glide.with(this).load(mGoogleSignInAccount.getPhotoUrl()).into(civMyMenuProfile);
        tvMyMenuProfileTitle.setText( mGoogleSignInAccount.getDisplayName() );
        tvMyMenuProfileEmail.setText( mGoogleSignInAccount.getEmail() );

        myMenuLogin.setVisibility(View.VISIBLE);
        myMenuNoLogin.setVisibility(View.GONE);


    }//googleLogin method

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

                // 이곳이 아닌 가능성이 있어서 우선 감추기
//            case Global.SOCIAL_LOGIN_GOOGLE_OK_REQUEST :
//                if(resultCode == RESULT_OK) {
//
//                } else {
//                    //todo : 이곳 부터 구글 로그인 되는 것을 확인 할 것
//                    Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
//                }
//                break;
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

            case R.id.btn_my_menu_logout :
                signOut();
                break;
        }// switch case 문

    }//clickBtn class

    private void signOut() {

        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                myMenuLogin.setVisibility(View.GONE);
                myMenuNoLogin.setVisibility(View.VISIBLE);

                //On Succesfull signout we navigate the user back to LoginActivity
                Intent intent=new Intent(MyMenuActivity.this, SocialLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }//signOut method

}//MyMenuActivity class
