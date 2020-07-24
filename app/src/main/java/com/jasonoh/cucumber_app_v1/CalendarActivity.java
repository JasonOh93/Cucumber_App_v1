package com.jasonoh.cucumber_app_v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setSupportActionBar( findViewById(R.id.calendar_tool_bar) );
        getSupportActionBar().setTitle("");

        calendarView = findViewById(R.id.calendar_calendar_view);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);

    }//onCreate method

    public void calendarSetting(){

        for (int i = 0; i < calendarView.getSelectedDates().size(); i++ ) {
            Calendar calendar = calendarView.getSelectedDates().get(i);
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            String week = new SimpleDateFormat("EE", Locale.KOREA).format(calendar.getTime());
            String day_full = year + "년 " + (month+1) + "월 " + day + "일 " + hour + "시 " + minute + "분 " + week + "요일";
            Log.w("TAG", "갤린터 시간" + day_full);
            result = (day_full + "\n");
        }

        Global.getDateFromCalendar = result;

        Intent intent = getIntent();
        intent.putExtra("myDate", result);
        setResult(RESULT_OK, intent);

        calendarView.setSelectionType(SelectionType.SINGLE);

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

    }//calendarSetting method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.calendar_select_btn :
                calendarSetting();
                finish();
                break;
        }
    }//clickBtn method
}//CalendarActivity class
