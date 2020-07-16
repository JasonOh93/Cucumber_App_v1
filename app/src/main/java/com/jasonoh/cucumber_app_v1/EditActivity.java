package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setSupportActionBar( findViewById(R.id.edit_toolbar) );
        getSupportActionBar().setTitle("");

    }//onCreate method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickIv(View view) {

        switch (view.getId()) {

            //위치 클릭시
            case R.id.edit_location_iv :
                startActivity( new Intent( this, LocationActivity.class ));
                break;

            //달력 클릭시
            case R.id.edit_calendar_iv :
                startActivity( new Intent(this, CalendarActivity.class) );
                break;

        }// switch case

    }//clickIv method

    public void clickBtn(View view) {

        switch (view.getId()) {

            //취소 버튼 클릭시
            case R.id.edit_cancel_btn :
                new AlertDialog.Builder(this).setMessage(R.string.edit_cancel_alert_msg).setNegativeButton(R.string.edit_cancel_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                }).setPositiveButton(R.string.edit_ok_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
                break;

            //확인 버튼 클릭시
            case R.id.edit_ok_btn :
                new AlertDialog.Builder(this).setMessage(R.string.edit_ok_alert_msg).setNegativeButton(R.string.edit_cancel_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                }).setPositiveButton(R.string.edit_ok_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
                break;

            //사진 찾아오기 버튼 클릭시
            case R.id.edit_select_image_fa_btn :
                Toast.makeText(this, "사진을 찾아오기", Toast.LENGTH_SHORT).show();
                break;
        }//switch case

    }//clickBtn method
}//EditActivity class
