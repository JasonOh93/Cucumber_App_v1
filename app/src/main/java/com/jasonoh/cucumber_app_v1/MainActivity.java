package com.jasonoh.cucumber_app_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //request code = 10 부터 99까지
    //Global class에서 설정

    Fragment[] bottomNavFrags = new Fragment[4];

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        setBottomNav(); //bottomNav setting

    }//onCreate Method

    public void setBottomNav(){

        bottomNavFrags[0] = new HomeFragment(this);
        bottomNavFrags[1] = new Fragment();
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

}//MainActivity class
