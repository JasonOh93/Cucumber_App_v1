package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ChattingActivity extends AppCompatActivity {

    //todo : firebase 실시간 DB로 보내는 것을 기준으로 시작하기!!

    TextView tvTopTitleName;

    EditText etMessage;

    ListView listView;
    ChattingAdapter adapter;
    ArrayList<ChattingMessageItem> items = new ArrayList<>();

    Button btnSend;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        setSupportActionBar( findViewById(R.id.chatting_toolbar) );
        getSupportActionBar().setTitle( "" );

//        items.add( new ChattingMessageItem("a", "a", "a", "a" , "a") );
//        items.add( new ChattingMessageItem("a", "a", "a", "a" , "a") );


        initChattingRoom();

    }//onCreate method

    public void initChattingRoom(){
        tvTopTitleName = findViewById(R.id.chatting_top_title);
        tvTopTitleName.setText( getIntent().getStringExtra("ChattingPersonName") != null
                ? getIntent().getStringExtra("ChattingPersonName")
                : "이름이 없음"
        );

        etMessage = findViewById(R.id.chatting_et_msg);
        btnSend = findViewById(R.id.chatting_btn_send);

        //초기 설정
        btnSend.setEnabled(false);
        btnSend.setTextColor( getResources().getColor( android.R.color.darker_gray ) );

        listView = findViewById(R.id.chatting_list_view);
        adapter = new ChattingAdapter(this, items, getIntent().getStringExtra("ChattingPersonEmail"));
        listView.setAdapter(adapter);

        setChattingRoom();

    }//initChattingRoom method

    public void setChattingRoom(){

//        if(getCurrentFocus() != null) {
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.w("TAG", "aaaaaaaa");
                    hideSoftKeyBoard();
                    return false;
                }
            });
//        }


        firebaseDatabase = FirebaseDatabase.getInstance();
        chatRef = firebaseDatabase.getReference( "chat_" + getIntent().getStringExtra("ChattingPersonName") );
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChattingMessageItem chattingMessageItem = snapshot.getValue( ChattingMessageItem.class );

                //todo : 이부분 부터 개별 채팅을 만들고 싶어서 하는 것이다!! 하지만 잘 안된다.
                if(!Global.loginPreferences.getString(Global.LOGIN_NAME_KEY, "").equals(chattingMessageItem.name) ||
                getIntent().getStringExtra("ChattingPersonName").equals(chattingMessageItem.name)) {

                    items.add(chattingMessageItem);
                    adapter.notifyDataSetChanged();
                    listView.setSelection( items.size() - 1 );
                    listView.requestFocus();

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etMessage.getText().toString().equals("")) {
                    btnSend.setEnabled( false );
                    btnSend.setTextColor( getResources().getColor( android.R.color.darker_gray ) );
                } else {
                    btnSend.setEnabled( true );
                    btnSend.setTextColor( getResources().getColor( android.R.color.black ) );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }//setChattingRoom method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickSend(View view) {
        String noImageUrl = "https://img.icons8.com/clouds/2x/no-image.png";

        String time = Calendar.getInstance().get( Calendar.HOUR_OF_DAY ) + " : " + Calendar.getInstance().get(Calendar.MINUTE);

        ChattingMessageItem chattingMessageItem =
                new ChattingMessageItem(
                        Global.loginPreferences.getString(Global.LOGIN_NAME_KEY, "noName"),
                        etMessage.getText().toString(),
                        time,
                        Global.loginPreferences.getString(Global.LOGIN_IMG_URI_KEY, noImageUrl),
                        Global.loginPreferences.getString(Global.LOGIN_EMAIL_KEY, "noEmail")
        );

        chatRef.push().setValue( chattingMessageItem );

        etMessage.setText("");

        hideSoftKeyBoard();

    }//clickSend

    public void hideSoftKeyBoard(){
        //소프트 키패드를 안보이도록
        InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //flags 는 즉시하려면 0

//        InputMethodManager imm2 =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm2.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //flags 는 즉시하려면 0
        //imm.showSoftInput() //화면에 보일때는 show
    }//hideSoftKeyBoard method

}//ChattingActivity class
