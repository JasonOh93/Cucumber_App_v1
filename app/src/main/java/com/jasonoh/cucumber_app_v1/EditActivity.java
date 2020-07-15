package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }//onCreate method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method
}//EditActivity class
