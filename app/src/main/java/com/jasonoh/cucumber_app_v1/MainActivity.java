package com.jasonoh.cucumber_app_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class MainActivity extends AppCompatActivity {

    //request code = 10 부터 99까지
    //Global class에서 설정

    Fragment[] bottomNavFrags = new Fragment[4];

    BottomNavigationView bottomNavigationView;

    //카카오톡 키 해쉬
    String kakaoKeyHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        setBottomNav(); //bottomNav setting

        //카카오 키해시 얻어오기
        kakaoKeyHash = getKeyHash(this);
        Log.w("TAG", kakaoKeyHash);

    }//onCreate Method

    public void setBottomNav(){

        bottomNavFrags[0] = new HomeFragment(this);
        bottomNavFrags[1] = new HospitalPharmacyFragment(this);
        bottomNavFrags[2] = new Fragment();
        bottomNavFrags[3] = new Fragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add( R.id.main_frag, bottomNavFrags[0] );
        transaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();

                switch (menuItem.getItemId()) {
                    case R.id.menu_home :
                        transaction1.replace( R.id.main_frag, bottomNavFrags[0] );
                        break;

                    case R.id.menu_hospital_pharmacy :
                        transaction1.replace( R.id.main_frag, bottomNavFrags[1] );
                        break;

                    case R.id.menu_health_feed :
                        transaction1.replace( R.id.main_frag, bottomNavFrags[2] );
                        break;

                    case R.id.menu_health_info :
                        transaction1.replace( R.id.main_frag, bottomNavFrags[3] );
                        break;
                }//switch case 문

                transaction1.commit();

                return true;
            }//onNavigationItemSelected method
        });//OnNavigationItemSelectedListener 익명클래스

    }//setBottomNav method

    // Kakao KeyHash
    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w("TAG", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }//getKeyHash method

    //확인용 : 병원 약국 탭
    public void clickBtn(View view) {

        switch (view.getId()) {
            case R.id.btn_frag_hospital_top :
                findViewById(R.id.btn_frag_hospital_top).setSelected(true);
                findViewById(R.id.btn_frag_pharmacy_top).setSelected(false);
                break;
            case R.id.btn_frag_pharmacy_top :
                findViewById(R.id.btn_frag_pharmacy_top).setSelected(true);
                findViewById(R.id.btn_frag_hospital_top).setSelected(false);
                break;
        }// switch case

    }//clickBtn method
}//MainActivity class
