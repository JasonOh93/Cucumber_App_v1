package com.jasonoh.cucumber_app_v1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    final int PICTURE_SELECT_REQUEST_CODE = 650;

    ImageView iv;
    EditText etTitle, etLocation, etMessage, etDate, etWeight;

    CheckBox cbAllShare, cbTitleShare, cbPictureShare, cbLocationShare, cbMessageShare, cbDateShare, cbWeightShare;

    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setSupportActionBar( findViewById(R.id.edit_toolbar) );
        getSupportActionBar().setTitle("");

        initFindViewById();

        allShareSelect();

        //외부 저장소의 접근 동적 퍼미션
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Global.REQUEST_STORAGE_FROM_EDIT_ACTIVITY );
        }

    }//onCreate method

    public void initFindViewById(){
        iv = findViewById(R.id.edit_iv);
        etTitle = findViewById(R.id.edit_title_et);
        etLocation = findViewById(R.id.edit_location_et);
        etMessage = findViewById(R.id.edit_msg_et);
        etDate = findViewById(R.id.edit_date_et);
        etWeight = findViewById(R.id.edit_weight_et);

        cbAllShare = findViewById(R.id.edit_share_all_check_box);
        cbTitleShare = findViewById(R.id.edit_share_title_check_box);
        cbPictureShare = findViewById(R.id.edit_share_picture_check_box);
        cbLocationShare = findViewById(R.id.edit_share_location_check_box);
        cbMessageShare = findViewById(R.id.edit_share_msg_check_box);
        cbDateShare = findViewById(R.id.edit_share_date_check_box);
        cbWeightShare = findViewById(R.id.edit_share_weight_check_box);
    }//initFindViewById

    public void allShareSelect(){

        cbAllShare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cbAllShare.isChecked()) {
                    cbTitleShare.setChecked(true);
                    cbPictureShare.setChecked(true);
                    cbLocationShare.setChecked(true);
                    cbMessageShare.setChecked(true);
                    cbDateShare.setChecked(true);
                    cbWeightShare.setChecked(true);
                } else{
                    cbTitleShare.setChecked(false);
                    cbPictureShare.setChecked(false);
                    cbLocationShare.setChecked(false);
                    cbMessageShare.setChecked(false);
                    cbDateShare.setChecked(false);
                    cbWeightShare.setChecked(false);
                }
            }
        });


    }//allShareSelect method

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == Global.REQUEST_STORAGE_FROM_EDIT_ACTIVITY && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "사진 파일 전송이 불가합니다!!", Toast.LENGTH_SHORT).show();
        }
    }//onRequestPermissionsResult method

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

                        imgPath = getRealPathFromUri(uri);
                    }
                }
                break;
            //위치로 이동 한 것
            case Global.REQUEST_LOCATION_FROM_EDIT_ACTIVITY :
                if(resultCode == RESULT_OK) etLocation.setText(data.getStringExtra("myLocation"));
                break;

            //날짜정보 창으로 이동 한것
            case Global.REQUEST_DATE_FROM_EDIT_ACTIVITY :
                if(resultCode == RESULT_OK) etDate.setText(Global.getDateFromCalendar);
                break;
        }

    }//onActivityResult method

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

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
                        shareData();
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

    //todo : Edit Activity 확인 버튼 클릭시 서버 전송
    public void shareData(){

        RetrofitService retrofitService = RetrofitHelper.getInstanceScalars().create( RetrofitService.class );

        //데이터들
        MultipartBody.Part filePart = null;
        if(imgPath != null) {
            File file = new File(imgPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
        }

        Map<String, String> dataPart = new HashMap<>();
        dataPart.put("personEmail", Global.loginPreferences.getString(Global.LOGIN_EMAIL_KEY, "이메일 없음"));

        Log.w("TAG", "아이디!!" + Global.loginPreferences.getString(Global.LOGIN_EMAIL_KEY, "이메일 없음"));

        dataPart.put("title", etTitle.getText().toString());
        dataPart.put("location", etLocation.getText().toString());
        dataPart.put("message", etMessage.getText().toString());
        dataPart.put("date", etDate.getText().toString());
        dataPart.put("weight", etWeight.getText().toString());

        dataPart.put("allShare", cbAllShare.isChecked() + "");
        dataPart.put("titleShare", cbTitleShare.isChecked() + "");
        dataPart.put("locationShare", cbLocationShare.isChecked() + "");
        dataPart.put("pictureShare", cbPictureShare.isChecked() + "");
        dataPart.put("messageShare", cbMessageShare.isChecked() + "");
        dataPart.put("dateShare", cbDateShare.isChecked() + "");
        dataPart.put("weightShare", cbWeightShare.isChecked() + "");

        Call<String> call = retrofitService.postDataToMyBoard(dataPart, filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    Log.w("TAG", "Response");
                    Toast.makeText(EditActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.w("TAG", "FAILED " + t.getMessage());
            }
        });//call.enqueue(new Callback<String>()

    }//shareData method
}//EditActivity class
