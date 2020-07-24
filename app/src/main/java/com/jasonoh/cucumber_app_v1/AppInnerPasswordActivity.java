package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AppInnerPasswordActivity extends AppCompatActivity {

    EditText etPassword1, etPassword2, etPassword3, etPassword4;

    int countPasswordDel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_inner_password);

        etPassword1 = findViewById(R.id.app_inner_password_et_input_1);
        etPassword2 = findViewById(R.id.app_inner_password_et_input_2);
        etPassword3 = findViewById(R.id.app_inner_password_et_input_3);
        etPassword4 = findViewById(R.id.app_inner_password_et_input_4);

        setPassword();

    }//onCreate method

    public void setPassword(){

        //초기 설정 처음 클릭시 모든값 리셋
        etPassword1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etPassword1.setText("");
                etPassword2.setText("");
                etPassword3.setText("");
                etPassword4.setText("");
                return false;
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(etPassword1.getText().toString().length() == 1) etPassword2.requestFocus();
                if(etPassword2.getText().toString().length() == 1) etPassword3.requestFocus();
                if(etPassword3.getText().toString().length() == 1) etPassword4.requestFocus();
                if(etPassword4.getText().toString().length() == 1) {
                    //소프트 키패드를 안보이도록
                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //flags 는 즉시하려면 0
                    //imm.showSoftInput() //화면에 보일때는 show
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };//textWatcher

        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.w("TAG", "aaaaa" + event.getAction());

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

    public void clickBackBtn(View view) {
        //이전화면으로 가기 위해
        finish();
    }//clickBackBtn method

    public void clickOK(View view) {
        Global.appPassword = etPassword1.getText().toString() + etPassword2.getText().toString()
                + etPassword3.getText().toString() + etPassword4.getText().toString();
        getSharedPreferences(Global.LOGIN_PREFERENCES_KEY, MODE_PRIVATE)
                .edit()
                .putString( Global.LOGIN_APP_PASSWORD_KEY, Global.appPassword )
                .commit();

        setResult(RESULT_OK, getIntent());

        finish();
    }//clickOK method
}//AppInnerPasswordActivity class
