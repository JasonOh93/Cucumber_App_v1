package com.jasonoh.cucumber_app_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    EditText etLocationSearch;

    ListView listViewLocation;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListLocation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        setSupportActionBar( findViewById(R.id.location_toolbar) );
        getSupportActionBar().setTitle("");

        etLocationSearch = findViewById(R.id.et_location_search);

        getLocation();

        setLocationListView();

    }//onCreate method

    GoogleApiClient googleApiClient; //위치정보 관리 객체( LocationManager 역할 )
    FusedLocationProviderClient providerClient;
    final int LOCATION_ACCESS_REQUEST_CODE = 600;

    public void getLocation(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_ACCESS_REQUEST_CODE );
        }
    }//getLocation method

    GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(5000); // 위치 정보 탐식 주기

            providerClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    //연결 시도에 실패했을때를 듣는 리스너 객체
    GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Toast.makeText(LocationActivity.this, "위치 정보 탐색을 시작할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    Location location;

    //위치정보가 갱신 되는 것을 듣는 리스너
    LocationCallback locationCallback = new LocationCallback(){

        //위치정보 결과를 받았을때 호출되는 메소드
        @Override
        public void onLocationResult(LocationResult locationResult) {

            location = locationResult.getLastLocation();
            //todo : 위도 경도 저장
            Global.locationLatitude = location.getLatitude();
            Global.locationLongitude = location.getLongitude();

            getSharedPreferences(Global.MY_LOCATION_KEY_NAME, MODE_PRIVATE)
                    .edit()
                    .putString(Global.MY_LOCATION_LAT_KEY_NAME, Global.locationLatitude + "")
                    .putString(Global.MY_LOCATION_LON_KEY_NAME, Global.locationLongitude + "")
                    .commit();

            //todo : 위도 경도를 한글로 저장
            changeAddressFromLatLon();
            //Toast.makeText(LocationActivity.this, Global.locationLatitude + ", " + Global.locationLongitude, Toast.LENGTH_SHORT).show();

            super.onLocationResult(locationResult);
        }
    };

    //todo : 위도 경도를 한글로 저장 메소드
    public void changeAddressFromLatLon(){

        final Geocoder geocoder = new Geocoder(LocationActivity.this);

        List<Address> addressList = null;
        try {
            Log.w("TAG", "지오코딩 테스트");
            addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
        } catch (IOException e) { e.printStackTrace(); }

        if(addressList != null) {
            if(addressList.size() == 0) Toast.makeText(LocationActivity.this, "해당되는 주소가 없습니다.", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(LocationActivity.this, addressList.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                Global.locationAddressFromLatLong = addressList.get(0).getAddressLine(0);
            }// if else
        }//if(addressList != null)

    }//changeAddressFromLatLon method

    //액티비티가 화면에 보이지 않으면 위치정보를 더이상 갱신하지 않도록
    @Override
    protected void onPause() {
        super.onPause();

        if(providerClient != null) providerClient.removeLocationUpdates( locationCallback );

    }//onPause method


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_ACCESS_REQUEST_CODE :
                if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "위치 정보 사용을 거부하셨습니다.\n사용자의 위치 탐색이 불가합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }

    }//onRequestPermissionsResult method

    public void setLocationListView(){

        addLocationListView();

        listViewLocation = findViewById(R.id.list_view_location_list);

        listViewLocation.setEmptyView( findViewById(R.id.tv_location_empty) );

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListLocation);
        listViewLocation.setAdapter( adapter );

        listViewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //확인용
                Toast.makeText(LocationActivity.this, ""+position + " : " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }//onItemClick method
        });//setOnItemClickListener

    }//setLocationListView method

    public void addLocationListView(){
//        arrayListLocation.add( "aaa" );
//        arrayListLocation.add( "aaa" );
//        arrayListLocation.add( "aaa" );
//        arrayListLocation.add( "aaa" );
//        arrayListLocation.add( "aaa" );
    }//addLocationListView method

    public void clickBackBtn(View view) {
        finish();
    }//clickBackBtn method

    public void clickBtn(View view) {

        switch (view.getId()) {
            case R.id.btn_location_search :
                Toast.makeText(this, "검색", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_location_now_location_setting :
                Toast.makeText(this, "현재 위치로 설정", Toast.LENGTH_SHORT).show();

                GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
                builder.addApi(LocationServices.API);
                builder.addConnectionCallbacks(connectionCallbacks);
                builder.addOnConnectionFailedListener(connectionFailedListener);

                googleApiClient = builder.build();
                googleApiClient.connect();
                providerClient = LocationServices.getFusedLocationProviderClient(this);

                finish();

                break;
        }//switch case

    }//clickBtn method
}//LocationActivity class
