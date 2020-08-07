package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FragmentHospitalPharmacy extends Fragment {

    Context context;

    Toolbar toolbar;
    Button btnHospital, btnPharmacy;

    ImageView favoritesHospitalPharmacy, searchHospitalPharmacy;

    Button btnMedicalSubject, btnLocation, btnSeeMore;
    RelativeLayout relativeGoogleMap;
    boolean btnSeeMoreBoolean = false;

    LinearLayout linearHospitalPharmacyMedicalAndLocation;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListNearHospital = new ArrayList<>();
    ArrayList<String> arrayListGetDataHospital = new ArrayList<>();
    ArrayList<String> arrayListGetDataSafetyHospital = new ArrayList<>();
    ArrayList<String> arrayListGetDataPharmacy = new ArrayList<>();

    RecyclerView recyclerView;
    ArrayList<FragmentHospitalItem> arrayListHospitalItems = new ArrayList<>();
    RecyclerViewFragmentHospitalAdapter hospitalAdapter;

    GoogleMap GoogleMap;
    SupportMapFragment supportMapFragment;


    public FragmentHospitalPharmacy() {
    }//HomeFragment Constructor (null)

    public FragmentHospitalPharmacy(Context context) {
        this.context = context;
    }//HomeFragment Constructor

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }//onCreate method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.frag_bottom_nav_hospital_pharmacy, container, false );

        toolbar = view.findViewById(R.id.frag_hospital_pharmacy_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar( toolbar );
        getActivity().setTitle("");

        btnHospital = view.findViewById(R.id.btn_frag_hospital_top);
        btnPharmacy = view.findViewById(R.id.btn_frag_pharmacy_top);

        favoritesHospitalPharmacy = view.findViewById(R.id.frag_hospital_pharmacy_star);
        searchHospitalPharmacy = view.findViewById(R.id.frag_hospital_pharmacy_search);

        btnMedicalSubject = view.findViewById(R.id.frag_btn_hospital_medical_subjects);
        btnLocation = view.findViewById(R.id.frag_btn_hospital_pharmacy_location);
        btnSeeMore = view.findViewById(R.id.frag_btn_hospital_pharmacy_see_more);
        relativeGoogleMap = view.findViewById(R.id.frag_hospital_pharmacy_map_google);
        linearHospitalPharmacyMedicalAndLocation =
                view.findViewById(R.id.frag_hospital_pharmacy_medical_location_view);

        //listView = view.findViewById(R.id.frag_hospital_pharmacy_list);
        recyclerView = view.findViewById(R.id.frag_hospital_pharmacy_recycle_list);

        return view;
    }//onCreateView method

    // View 가 Create 되고 난 다음에 나타나는 것
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickItem();

        if(!Global.hospitalPharmacyBooleanFromHomeFrag) setHospital();
        else setPharmacy();

    }//onViewCreated method

    @Override
    public void onResume() {
        super.onResume();

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map_google);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GoogleMap = googleMap;
                setGoogleMapLocation(googleMap);
            }
        });

//        getDataHospital();
    }//onResume method

    //todo : 위치정보 가져오기
    public void getNowMyLocation(){
    }//getNowMyLocation method

    //todo : 병원데이터 가져오기!!
//    public void getDataHospital(){
//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//                //네트워크 주소 저장
//                String dataHospitalAddress = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncFullDown"
//                        + "?serviceKey=" + "6dqyQeM6Z1N4y9BZCEBwdt00gqLY6XZhny6jJs3ljEWE2NypmtrGJHRNkfgA%2FvtgZlWdqYCjoFGnPu3oKSTi0g%3D%3D"
//                        + "&pageNo=" + "100" + "&numOfRows=" + "100";
//
//                try {
//
//                    URL url =new URL(dataHospitalAddress);
//                    InputStream is = url.openStream();
//                    InputStreamReader isr = new InputStreamReader(is);
//
//                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//                    XmlPullParser xpp = factory.newPullParser();
//                    xpp.setInput(isr);
//
//                    int eventType = xpp.getEventType();
//
//                    StringBuffer stringBuffer = new StringBuffer();
//
//                    String lat = null;
//                    String lon = null;
//
//                    while (eventType != XmlPullParser.END_DOCUMENT) {
//
//                        eventType = xpp.next();
//
//                        switch (eventType) {
//                            case XmlPullParser.START_DOCUMENT :
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(context, "검색시작", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                                break;
//                            case XmlPullParser.START_TAG :
//                                String tagName_start = xpp.getName();
//                                if(tagName_start.equals("items")) stringBuffer = new StringBuffer();
//                                else if(tagName_start.equals("item")) stringBuffer = new StringBuffer();
//                                else if(tagName_start.equals("dutyDivNam")) {
//                                    xpp.next();
////                                    stringBuffer.append(xpp.getText() + "\n");
//                                }
//                                else if(tagName_start.equals("dutyName")) {
//                                    xpp.next();
//                                    stringBuffer.append(xpp.getText() + "\n");
////                                    stringBuffer.append("병원 이름 : " + xpp.getText() + "\n");
//                                }
//                                else if(tagName_start.equals("wgs84Lat")) {
//                                    xpp.next();
////                                    stringBuffer.append("Lat : " + xpp.getText() + "\n");
//                                    lat = xpp.getText();
//                                }
//                                else if(tagName_start.equals("wgs84Lon")) {
//                                    xpp.next();
////                                    stringBuffer.append("Lon : " + xpp.getText() + "\n");
//                                    lon = xpp.getText();
//                                }
//                                break;
//                            case XmlPullParser.TEXT :
//                                break;
//                            case XmlPullParser.END_TAG :
//                                if(xpp.getName().equals("item")) {
//                                    Log.w("TAG", "Hospital : " + stringBuffer.toString());
//
////                                    MarkerOptions markerOptions = new MarkerOptions();
////                                    markerOptions.position( new LatLng( Double.parseDouble(lat), Double.parseDouble(lon) ) );
////                                    markerOptions.title(stringBuffer.toString());
////
////                                    GoogleMap.addMarker( markerOptions );
//
//                                    arrayListGetDataHospital.add( stringBuffer.toString() );
//                                    stringBuffer = new StringBuffer();
//                                }
//                                break;
//                        }//switch case
//
//                    }//while
//
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "검색종료", Toast.LENGTH_SHORT).show();
//                            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayListGetDataHospital);
//
//                            try {
//                                isr.close();
//                                is.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    });
//
//                } catch (Exception e) { }
//
//            }//run
//        }.start();
//
//    }//getDataHospital method

    //todo : 병원데이터 가져오기!!
    public void getDataHospital(){

        Log.w("TAG", "Lon : " + Global.locationLongitude + "Lat : " + Global.locationLatitude );

        new Thread() {
            @Override
            public void run() {
                super.run();

                //네트워크 주소 저장
                String dataHospitalAddress = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncLcinfoInqire"
                        + "?WGS84_LON=" + Global.locationLongitude + "&WGS84_LAT=" + Global.locationLatitude
                        + "&pageNo=" + "1" + "&numOfRows=" + "10"
                        + "&serviceKey=" + "6dqyQeM6Z1N4y9BZCEBwdt00gqLY6XZhny6jJs3ljEWE2NypmtrGJHRNkfgA%2FvtgZlWdqYCjoFGnPu3oKSTi0g%3D%3D";

                try {

                    //arrayListGetDataHospital.clear();

                    URL url =new URL(dataHospitalAddress);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(isr);

                    int eventType = xpp.getEventType();

                    StringBuffer stringBuffer = new StringBuffer();

                    String lat = null;
                    String lon = null;
                    String name = "";
                    String address = "";
                    String telNum = "";


                    while (eventType != XmlPullParser.END_DOCUMENT) {

                        eventType = xpp.next();

                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT :
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "검색시작", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            case XmlPullParser.START_TAG :
                                String tagName_start = xpp.getName();
                                if(tagName_start.equals("items")) stringBuffer = new StringBuffer();
                                else if(tagName_start.equals("item")) stringBuffer = new StringBuffer();
                                else if(tagName_start.equals("dutyAddr")) {
                                    xpp.next();
                                    address = xpp.getText();
//                                    stringBuffer.append(xpp.getText() + "\n");
                                }
                                else if(tagName_start.equals("dutyName")) {
                                    xpp.next();
                                    name = xpp.getText();
                                    stringBuffer.append(xpp.getText() + "\n");
//                                    stringBuffer.append("병원 이름 : " + xpp.getText() + "\n");
                                }
                                else if(tagName_start.equals("dutyTel1")) {
                                    xpp.next();
                                    telNum = xpp.getText();
//                                    stringBuffer.append(xpp.getText() + "\n");
                                }
                                else if(tagName_start.equals("latitude")) {
                                    xpp.next();
//                                    stringBuffer.append("Lat : " + xpp.getText() + "\n");
                                    lat = xpp.getText();
                                }
                                else if(tagName_start.equals("longitude")) {
                                    xpp.next();
//                                    stringBuffer.append("Lon : " + xpp.getText() + "\n");
                                    lon = xpp.getText();
                                }
                                break;
                            case XmlPullParser.TEXT :
                                break;
                            case XmlPullParser.END_TAG :
                                if(xpp.getName().equals("item")) {
                                    Log.w("TAG", "Hospital : " + stringBuffer.toString());

                                    arrayListHospitalItems.add( new FragmentHospitalItem(name, address, lat, lon, telNum) );

                                    //arrayListGetDataHospital.add( stringBuffer.toString() );
                                    stringBuffer = new StringBuffer();
                                }
                                break;
                        }//switch case

                    }//while

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "검색종료", Toast.LENGTH_SHORT).show();

                            //adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayListGetDataHospital);

                            // 여기까지 했음.. 이제 어뎁터를 바꿔줘야함!!
                            hospitalAdapter = new RecyclerViewFragmentHospitalAdapter(context, arrayListHospitalItems);

                            // 확인용 ! 리스트 항목에 내용이 있는지 확인용
                            new AlertDialog.Builder(context).setMessage(arrayListHospitalItems.get(0).hospitalAddress).show();

                            try {
                                isr.close();
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } catch (Exception e) { }

            }//run
        }.start();

    }//getDataHospital method

    //todo : 안심병원데이터 가져오기!!
    public void getDataSafetyHospital(){

        new Thread() {
            @Override
            public void run() {
                super.run();

                //네트워크 주소 저장
                String dataHospitalAddress = "http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList"
                        + "?serviceKey=" + "6dqyQeM6Z1N4y9BZCEBwdt00gqLY6XZhny6jJs3ljEWE2NypmtrGJHRNkfgA%2FvtgZlWdqYCjoFGnPu3oKSTi0g%3D%3D"
                        + "&pageNo=" + "1" + "&numOfRows=" + "100"
                        + "&spclAdmTyCd=" + "A0";

                try {

                    URL url =new URL(dataHospitalAddress);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(isr);

                    int eventType = xpp.getEventType();

                    StringBuffer stringBuffer = new StringBuffer();

                    while (eventType != XmlPullParser.END_DOCUMENT) {

                        eventType = xpp.next();

                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT :
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "검색시작", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            case XmlPullParser.START_TAG :
                                String tagName_start = xpp.getName();
                                if(tagName_start.equals("items")) stringBuffer = new StringBuffer();
                                else if(tagName_start.equals("item")) stringBuffer = new StringBuffer();
                                else if(tagName_start.equals("sgguNm")) {
                                    xpp.next();
//                                    stringBuffer.append(xpp.getText() + "\n");
                                }
                                else if(tagName_start.equals("yadmNm")) {
                                    xpp.next();
                                    stringBuffer.append(xpp.getText() + "\n");
//                                    stringBuffer.append("병원 이름 : " + xpp.getText() + "\n");
                                }
                                break;
                            case XmlPullParser.TEXT :
                                break;
                            case XmlPullParser.END_TAG :
                                if(xpp.getName().equals("item")) {
                                    Log.w("TAG", "SafetyHospital : " + stringBuffer.toString());
                                    arrayListGetDataSafetyHospital.add( stringBuffer.toString() );
                                    stringBuffer = new StringBuffer();
                                }
                                break;
                        }//switch case

                    }//while

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "검색종료", Toast.LENGTH_SHORT).show();
                            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayListGetDataSafetyHospital);
                            adapter.notifyDataSetChanged();

                            try {
                                isr.close();
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (Exception e) { }

            }//run
        }.start();

    }//getDataSafetyHospital method

    //todo : 약국 데이터 가져오기!!
    public void getDataPharmacy(){

        new Thread() {
            @Override
            public void run() {
                super.run();

                //네트워크 주소 저장
                String dataHospitalAddress = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown"
                        + "?serviceKey=" + "6dqyQeM6Z1N4y9BZCEBwdt00gqLY6XZhny6jJs3ljEWE2NypmtrGJHRNkfgA%2FvtgZlWdqYCjoFGnPu3oKSTi0g%3D%3D"
                        + "&pageNo=" + "1" + "&numOfRows=" + "100";

                String latPharmacy = null;
                String lonPharmacy = null;

                try {

                    URL url =new URL(dataHospitalAddress);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(isr);

                    int eventType = xpp.getEventType();

                    StringBuffer stringBuffer = new StringBuffer();

                    while (eventType != XmlPullParser.END_DOCUMENT) {

                        eventType = xpp.next();

                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT :
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "검색시작", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            case XmlPullParser.START_TAG :
                                String tagName_start = xpp.getName();
                                if(tagName_start.equals("items")) stringBuffer = new StringBuffer();
                                else if(tagName_start.equals("item")) stringBuffer = new StringBuffer();
                                else if(tagName_start.equals("dutyAddr")) {
                                    xpp.next();
//                                    stringBuffer.append(xpp.getText() + "\n");
                                }
                                else if(tagName_start.equals("dutyName")) {
                                    xpp.next();
                                    stringBuffer.append(xpp.getText() + "\n");
//                                    stringBuffer.append("병원 이름 : " + xpp.getText() + "\n");
                                }
                                else if(tagName_start.equals("wgs84Lat")) {
                                    xpp.next();
//                                    stringBuffer.append("Lat : " + xpp.getText() + "\n");
                                    latPharmacy = xpp.getText();
                                }
                                else if(tagName_start.equals("wgs84Lon")) {
                                    xpp.next();
//                                    stringBuffer.append("Lon : " + xpp.getText() + "\n");
                                    lonPharmacy = xpp.getText();
                                }
                                break;
                            case XmlPullParser.TEXT :
                                break;
                            case XmlPullParser.END_TAG :
                                if(xpp.getName().equals("item")) {
                                    Log.w("TAG", "Pharmacy : " + stringBuffer.toString());
                                    arrayListGetDataPharmacy.add( stringBuffer.toString() );
                                    stringBuffer = new StringBuffer();
                                }
                                break;
                        }//switch case

                    }//while

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "검색종료", Toast.LENGTH_SHORT).show();
                            adapter = null;
                            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayListGetDataPharmacy);
                            adapter.notifyDataSetChanged();

                            try {
                                isr.close();
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (Exception e) { }

            }//run
        }.start();

    }//getDataSafetyHospital method

    //지도가 생성되고 난후 내부 에서 정할때 사용
    public void setGoogleMapLocation(GoogleMap googleMap){

        //원하는 좌표 객체 생성
        LatLng myLatLng = new LatLng(Global.locationLatitude, Global.locationLongitude);

        //마크 옵션 객체 생성(marker 의 설정)
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position( myLatLng );
        markerOptions.title( "설정위치" );
        markerOptions.snippet( "설정위치" );

        //지도에 마크를 추가
        googleMap.addMarker( markerOptions );

        //카메라 이동을 스무스하게 효과를 주면서 줌까지 적용
        googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( myLatLng, 16 ) );

        googleMap.setMyLocationEnabled(true);

        getDataHospital();

        //todo : 내위치 주변에 병원 위치 나타나도록

    }//setGoogleMapLocation method

    public void clickItem(){

        btnHospital.setSelected( true );

        //제목줄 버튼 클릭시 행동

        //병원 버튼 클릭시 변화
        btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHospital();
            }//onClick method
        });//btnHospital.setOnClickListener

        //약국 버튼 클릭시 변화
        btnPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setPharmacy();
                getDataPharmacy();
            }//onClick method
        });//btnHospital.setOnClickListener

        //즐겨찾기 한 곳으로 이동
        favoritesHospitalPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult( new Intent(context, FavoritesHospitalAndPharmacyActivity.class)
                        , Global.FAVORITES_HOSPITAL_PHARMACY_REQUEST_FROM_FRAG_HOSPITAL_PHARMACY);
            }//onClick method
        });//favoritesHospitalPharmacy.setOnClickListener

        //검색 한 창으로 이동
        searchHospitalPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult( new Intent(context, SearchHospitalAndPharmacyActivity.class)
                        , Global.SEARCH_HOSPITAL_PHARMACY_REQUEST_FROM_FRAG_HOSPITAL_PHARMACY);
            }//onClick method
        });//searchHospitalPharmacy.setOnClickListener

        // 위치 선택 누르면 이동
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(context, LocationActivity.class));

            }//onClick method

        });//btnLocation.setOnClickListener

        // 하단에 병원보기 및 약국 보기 이용시
        btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!btnSeeMoreBoolean) {
                    seeMoreBooleanTrue();

                    //병원 보기 및 약국 보기 에 따라서 보여주는 값을 다르게
                    if(btnSeeMore.getText().toString().equals("병원 보기")) {
//                        listView.setVisibility(View.VISIBLE);
//                        listView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();

                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(hospitalAdapter);
                        hospitalAdapter.notifyDataSetChanged();
                    }
                    else if(btnSeeMore.getText().toString().equals("코로나 안심병원 보기")) {
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    else if(btnSeeMore.getText().toString().equals("약국 보기")) {
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }// if else if

                } else if(btnSeeMoreBoolean) {
                    seeMoreBooleanFalse();
                }// if else if

            }//onClick method
        });//btnSeeMore.setOnClickListener

        //전체 진료과목 누를시 나타나는 것
        btnMedicalSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //다이얼 로그로 행동해보기
                new AlertDialog.Builder(context).setItems(R.array.hospital_alert_dialog_medical_subject, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] s = getResources().getStringArray(R.array.hospital_alert_dialog_medical_subject);
                        btnMedicalSubject.setText(s[which]);

                        if(btnMedicalSubject.getText().equals("코로나 안심병원")) {
                            getDataSafetyHospital();
                            btnSeeMore.setText( btnMedicalSubject.getText() + " 보기" );
                        } else if(btnMedicalSubject.getText().equals("일반 진료 병원")) {
                            getDataHospital();
                            btnSeeMore.setText( "병원 보기" );
                        }

                    }// AlertDialog onClick
                }).show();

            }//onClick method
        });// btnMedicalSubject.setOnClickListener

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context, arrayListGetDataHospital.get(position), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                if(btnSeeMore.getText().equals("병원 보기")) {
//                    intent.setData(Uri.parse( "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=" + arrayListGetDataHospital.get(position) ));
//                } else if(btnSeeMore.getText().equals("코로나 안심병원 보기")) {
//                    intent.setData(Uri.parse( "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=" + arrayListGetDataSafetyHospital.get(position) ));
//                } else if(btnSeeMore.getText().equals("약국 보기")) {
//                    intent.setData(Uri.parse("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=" + arrayListGetDataPharmacy.get(position)));
//                }
//                getActivity().startActivity( intent );
//            }
//        });

    }//clickItem

    public void setHospital(){
        //병원 탭 누를 시
        btnHospital.setSelected(true);
        btnPharmacy.setSelected(false);

        btnMedicalSubject.setVisibility(View.VISIBLE);
        btnSeeMore.setText( R.string.frag_hospital_see_more_btn );

        seeMoreBooleanFalse();

    }//setHospital method
    public void setPharmacy(){
        //약국 팁 누를 시
        btnHospital.setSelected(false);
        btnPharmacy.setSelected(true);

        btnMedicalSubject.setVisibility(View.GONE);
        btnSeeMore.setText( R.string.frag_pharmacy_see_more_btn );

        seeMoreBooleanFalse();
    }//setPharmacy method

    //병원 보기 및 약국 보기가 참일경우
    public void seeMoreBooleanTrue(){

        // 병원 보기및 약국 보기를 누를때 발동
        if(!btnSeeMoreBoolean) {
            relativeGoogleMap.setVisibility(View.GONE);
            btnSeeMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_down_black_24dp, 0);
            linearHospitalPharmacyMedicalAndLocation.setVisibility(View.GONE);
            btnSeeMoreBoolean = true;
        }// if

    }//seeMoreBooleanTrue method

    //병원 보기 및 약국 보기가 거짓일 경우
    public void seeMoreBooleanFalse(){

        // 병원 보기및 약국 보기를 누를때 발동
        if(btnSeeMoreBoolean) {
            relativeGoogleMap.setVisibility(View.VISIBLE);
            btnSeeMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_up_black_24dp, 0);
            linearHospitalPharmacyMedicalAndLocation.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            //listView.setVisibility(View.GONE);
            btnSeeMoreBoolean = false;
        }// if

    }//seeMoreBooleanFalse method

}//FragmentHospitalPharmacy Class
