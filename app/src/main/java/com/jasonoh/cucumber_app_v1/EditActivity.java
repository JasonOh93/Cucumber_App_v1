package com.jasonoh.cucumber_app_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class EditActivity extends AppCompatActivity {

    final int PICTURE_SELECT_REQUEST_CODE = 650;

    ImageView iv;
    EditText etTitle, etLocation, etMessage, etDate, etWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setSupportActionBar( findViewById(R.id.edit_toolbar) );
        getSupportActionBar().setTitle("");

        initFindViewById();



    }//onCreate method

    public void initFindViewById(){
        iv = findViewById(R.id.edit_iv);
        etTitle = findViewById(R.id.edit_title_et);
        etLocation = findViewById(R.id.edit_location_et);
        etMessage = findViewById(R.id.edit_msg_et);
        etDate = findViewById(R.id.edit_date_et);
        etWeight = findViewById(R.id.edit_weight_et);
    }//initFindViewById

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //사진 앱으로 이동
            case PICTURE_SELECT_REQUEST_CODE :
                if(resultCode == RESULT_OK) {
                    //이미지를 선택했다면
                    Uri uri = data.getData();
                    if(uri != null) {
                        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
                        Glide.with(this).load(uri).into(iv);
                    }
                }
                break;
            //위치로 이동 한 것
            case Global.REQUEST_LOCATION_FROM_EDIT_ACTIVITY :
                Log.w("TAG", "aaaaaaa");
                if(resultCode == RESULT_OK) etLocation.setText(data.getStringExtra("myLocation"));
                break;

            //날짜정보 창으로 이동 한것
            case Global.REQUEST_DATE_FROM_EDIT_ACTIVITY :
                if(resultCode == RESULT_OK) etDate.setText(Global.getDateFromCalendar);
                break;
        }

    }//onActivityResult method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickIv(View view) {

        switch (view.getId()) {

            //위치 클릭시
            case R.id.edit_location_iv :
                startActivityForResult( new Intent( this, LocationActivity.class ), Global.REQUEST_LOCATION_FROM_EDIT_ACTIVITY);
                break;

            //달력 클릭시
            case R.id.edit_calendar_iv :
                startActivityForResult( new Intent(this, CalendarActivity.class), Global.REQUEST_DATE_FROM_EDIT_ACTIVITY );
                break;

        }// switch case

    }//clickIv method

    //todo : EditActivity 사진 찾아오기 버튼 클릭시 method
    public void getPicture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICTURE_SELECT_REQUEST_CODE);
    }//getPicture method

    public void clickBtn(View view) {

        switch (view.getId()) {

            //todo : EditActivity 취소 버튼 클릭시
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

            //todo : EditActivity 확인 버튼 클릭시
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

            //todo : EditActivity 사진 찾아오기 버튼 클릭시
            case R.id.edit_select_image_fa_btn :
                Toast.makeText(this, "사진을 찾아오기", Toast.LENGTH_SHORT).show();
                getPicture();
                break;
        }//switch case

    }//clickBtn method
}//EditActivity class
