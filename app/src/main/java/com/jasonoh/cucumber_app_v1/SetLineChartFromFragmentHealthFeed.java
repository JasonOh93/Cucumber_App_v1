package com.jasonoh.cucumber_app_v1;

import android.app.Activity;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class SetLineChartFromFragmentHealthFeed {

    private Activity activity;
    private LineChart lineChart;

    private Thread thread;

    public SetLineChartFromFragmentHealthFeed() {
    }//constructor

    public SetLineChartFromFragmentHealthFeed(Activity activity, LineChart lineChart) {
        this.activity = activity;
        this.lineChart = lineChart;
    }// constructor (activity, lineChart)

    public void setLineChart(){

        XAxis xAxis = lineChart.getXAxis(); //x축 가져오기

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 데이터의 위치를 아래로
        xAxis.setTextSize( 10f ); // 텍스트 크기 지정 (float 형으로 해주어야 함!)
        xAxis.setDrawGridLines(false); //배경 그리드 라인 세팅
        xAxis.setGranularity( 1f ); // x축 데이터 표시 간격
        xAxis.setAxisMinimum( 2f ); // x축 데이터의 최소 표시 값
        xAxis.setGranularityEnabled( true ); // x축 간격을 제한하는 세분화 기능

        //라인 차트 세팅
        lineChart.getAxisRight().setEnabled( false ); //y축의 오른쪽 데이터 비활성화
        lineChart.getAxisLeft().setAxisMaximum( 100f ); //y축의 왼쪽 데이터 최대값은 50으로 // 본인 몸무게 + 30으로 지정
        lineChart.getAxisLeft().setAxisMinimum( 0f ); // y축의 왼쪽 데이터 최대값은 0으로 // 목표 체중으로 설정
        //범례 세팅
        lineChart.getLegend().setTextSize( 15f ); //글자 크기 지정
        lineChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); // 수직 조정 -> 위로
        lineChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); // 수평 조정 -> 가운데
        lineChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL); // 범례와  차트 정렬 -> 수평
        lineChart.getLegend().setDrawInside( false ); // 차트 안에 그릴 것인가?

        LineData lineData = new LineData();
        lineChart.setData( lineData ); //라인 차트 데이터 지정
        feedMultiple();

    }//setLineChart method

    protected void feedMultiple(){

        if(thread != null) {
            thread.interrupt(); // 쓰레드 종료
        }// if thread != null

        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                addEntry();
            }//run method
        };//runnable

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    activity.runOnUiThread( runnable );
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }// while
            }//run method
        });// thread
        thread.start();

    }//feedMultiple method

    protected void addEntry(){
        LineData lineData = lineChart.getLineData();
        //라인 차트
        if(lineData != null) { // 데이터가 null이 아닐때 실행
            ILineDataSet lineDataSet = lineData.getDataSetByIndex( 0 );
            if(lineDataSet == null) {// 임의의 데이터 셋 (0번 부터 시작)
                lineDataSet = createSet();
                lineData.addDataSet( lineDataSet );
            }// if lineDataSet == null
            //데이터 엔트리 추가 Entry(x값 y값) y값은 내가 원하는 값으로 지정
            lineData.addEntry( new Entry( lineDataSet.getEntryCount(), 65f ), 0);
            lineData.notifyDataChanged(); //데이터 변경 알림
            lineChart.notifyDataSetChanged(); //라인 차트 변경 알림
            lineChart.moveViewToX( lineData.getEntryCount() ); //계속 x축을 데이터의 오른쪽 끝으로 옮기기
            lineChart.setVisibleXRangeMaximum( 4f ); // x축 데이터 최대 표현 개수
            lineChart.setPinchZoom( false ); //확대 설정
            lineChart.setDoubleTapToZoomEnabled( false ); //더블 탭 확대 설정
            lineChart.getDescription().setText( "시간" ); // 라인 차트 안의 텍스트 설정
            // 배경색 (미니멈 버전 확인 해야함)
            lineChart.setBackgroundColor( lineChart.getResources().getColor( R.color.colorPrimary_translucent_my) );
            lineChart.getDescription().setTextSize( 15f ); // 텍스트 사이즈
            lineChart.setExtraOffsets( 8f, 8f, 8f, 8f ); // 차트 padding 설정
        }// if lineDate != null
    }//addEntry method

    protected LineDataSet createSet(){
        LineDataSet lineDataSet = new LineDataSet( null, "몸무게" ); // 범례, yVals 설정 (라인차트 데이터셋)
        lineDataSet.setAxisDependency( YAxis.AxisDependency.LEFT ); // Y값 데이터를 왼쪽으로
        lineDataSet.setColor( lineDataSet.getColor( android.R.color.black ) );
        lineDataSet.setValueTextSize( 10f ); // 값 글자 크기
        lineDataSet.setLineWidth( 2f ); // 라인 두께
        lineDataSet.setCircleRadius( 3f ); // 원 크기
        lineDataSet.setFillAlpha( 0 ); // 라인 색 투명도
        lineDataSet.setFillColor( lineDataSet.getColor( android.R.color.black ) );
        lineDataSet.setHighLightColor(Color.BLACK); // 하이라이트 컬러 지정
        lineDataSet.setDrawValues( true ); // 값을 그리기

        return lineDataSet;
    }// createSet method

}// SetLineChartFromFragmentHealthFeed class
