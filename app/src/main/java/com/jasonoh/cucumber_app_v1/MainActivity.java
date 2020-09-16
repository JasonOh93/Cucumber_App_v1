package com.jasonoh.cucumber_app_v1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

    //하단 뒤로가기 버튼 두번 클릭시 종료되도록
    BackPressCloseAppHandler backPressCloseAppHandler;

    final int LOCATION_ACCESS_REQUEST_CODE = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        setBottomNav(); //bottomNav setting

        //카카오 키해시 얻어오기
        kakaoKeyHash = getKeyHash(this);
        Log.w("TAG", kakaoKeyHash);

        backPressCloseAppHandler = new BackPressCloseAppHandler(this);

        getLocation();

//        switch (bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId()).getItemId()) {
//            case R.id.menu_home :
//                Toast.makeText(this, bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId()).getTitle(), Toast.LENGTH_SHORT).show();
//                break;
//        }

    }//onCreate Method

    public void getLocation(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_ACCESS_REQUEST_CODE );
        }
    }//getLocation method

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_ACCESS_REQUEST_CODE :
                if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "위치 정보 사용을 거부하셨습니다.\n사용자의 위치 탐색이 불가합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }

    }//onRequestPermissionsResult method

    public void setBottomNav(){

        bottomNavFrags[0] = new FragmentHome(this);
        bottomNavFrags[1] = new FragmentHospitalPharmacy(this);
        bottomNavFrags[2] = new FragmentHealthFeed( this );
        bottomNavFrags[3] = new FragmentHealthInfo(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add( R.id.main_frag, bottomNavFrags[0] );
        transaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.addToBackStack( null ); // Activity 처럼 스텍영역에 저장 되는 것 FILO 방식

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

                Log.w("KEYHASH", Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w("TAG", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }//getKeyHash method

    // 하단 뒤로가기 버튼 클릭시
    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        backPressCloseAppHandler.onBackPressedApp();

    }//onBackPressed method
}//MainActivity class
