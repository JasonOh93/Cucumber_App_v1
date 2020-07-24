package com.jasonoh.cucumber_app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class AppIntroPasswordActivity extends AppCompatActivity {

    EditText etPassword1, etPassword2, etPassword3, etPassword4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro_password);

        setSupportActionBar( findViewById(R.id.app_intro_password_toolbar) );
        getSupportActionBar().setTitle("");

        etPassword1 = findViewById(R.id.app_intro_password_et_input_1);
        etPassword2 = findViewById(R.id.app_intro_password_et_input_2);
        etPassword3 = findViewById(R.id.app_intro_password_et_input_3);
        etPassword4 = findViewById(R.id.app_intro_password_et_input_4);

        setPassword();

    }//onCreate method

    public void setInitialPassword(){

        etPassword1.setText("");
        etPassword2.setText("");
        etPassword3.setText("");
        etPassword4.setText("");

    }//setInitialPassword method

    public void setPassword(){

        //초기 설정 처음 클릭시 모든값 리셋
        etPassword1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setInitialPassword();
                return false;
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.w("TAG", "sssa");

                if(etPassword1.getText().toString().length() == 1) etPassword2.requestFocus();
                if(etPassword2.getText().toString().length() == 1) etPassword3.requestFocus();
                if(etPassword3.getText().toString().length() == 1) etPassword4.requestFocus();
                if(etPassword4.getText().toString().length() == 1) {

                    //소프트 키패드를 안보이도록
                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //flags 는 즉시하려면 0
                    //imm.showSoftInput() //화면에 보일때는 show

                    //비밀번호 대조 및 다시 보이도록
                    String password = etPassword1.getText().toString() + etPassword2.getText().toString()
                            + etPassword3.getText().toString() + etPassword4.getText().toString();

                    if(Global.appPassword != null && password.equals(Global.appPassword)) {
                        startActivity( new Intent(AppIntroPasswordActivity.this, MainActivity.class));
                    } else {
                        //여기서 에러 무한 루팅됨 etPassword1.setText("") 일때 무한 루팅
                        //https://www.androidpub.com/356114
                        if(!s.toString().equals("")) setInitialPassword();
                        etPassword1.requestFocus();
                        imm.showSoftInput( etPassword1, 0 );
                        Toast toast = Toast.makeText(AppIntroPasswordActivity.this, R.string.app_intro_password_different_password, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };//textWatcher

        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {

                    if(v.getId() == R.id.app_inner_password_et_input_2 &&
                            ((EditText)v).getText().toString().length() == 0) { etPassword1.requestFocus(); etPassword1.setText(null); }

                    if(v.getId() == R.id.app_inner_password_et_input_3 &&
                            ((EditText)v).getText().toString().length() == 0) { etPassword2.requestFocus(); etPassword2.setText(null); }

                    if(v.getId() == R.id.app_inner_password_et_input_4 &&
                            ((EditText)v).getText().toString().length() == 0) { etPassword3.requestFocus(); etPassword3.setText(null);}
                }// if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN)
                return false; // return 값이 true 일때 event.getAction() == KeyEvent.ACTION_DOWN 일 경우로 한번 나온다
            }//onKey method
        };//keyListener

        etPassword1.setOnKeyListener(keyListener);
        etPassword2.setOnKeyListener(keyListener);
        etPassword3.setOnKeyListener(keyListener);
        etPassword4.setOnKeyListener(keyListener);

        etPassword1.addTextChangedListener(textWatcher);
        etPassword2.addTextChangedListener(textWatcher);
        etPassword3.addTextChangedListener(textWatcher);
        etPassword4.addTextChangedListener(textWatcher);

    }//setPassword method

}//AppIntroPasswordActivity class
