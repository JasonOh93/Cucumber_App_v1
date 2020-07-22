package com.jasonoh.cucumber_app_v1;

import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Global {
    //전체에서 사용 할수 있도록 하는 것

    //home fragment 에서 설정 한 것
    final static int CALENDAR_REQUEST = 100;
    final static int LOCATION_REQUEST = 101;
    final static int MY_MENU_REQUEST = 102;
    final static String GET_START_HEALTH_FEED_FROM_MY_MENU_ACTIVITY = "StartHealthFeed";

    //마이 메뉴에서 앱 내의 비밀번호 설정 창으로 이동하는 것
    final static int APP_INNER_PASSWORD_SETTING_PASSWORD_REQUEST = 1000;
    //마이 메뉴에서 로그인 하는 창으로 이동
    final static int SOCIAL_LOGIN_REQUEST = 900;

    // SocialLoginActivity 에서 구글 로그인
    static final int RC_SIGN_IN = 800;

    // SocialLoginActivity 에서 구글 로그인 한 것을 가지고 MyMenu Activity 로 이동하는 REQUEST CODE
    static final String GOOGLE_ACCOUNT = "google_account";

    // 로그인 시 전체적으로 사용하기 위해서 인트로 액티비티에서 설정 해주고 나머지는 SocialLoginActivity, MyMenuActivity에서 실행함
    static SharedPreferences loginPreferences = null;
    static final String LOGIN_PREFERENCES_KEY = "Login";
    static final String LOGIN_NAME_KEY = "Name";
    static final String LOGIN_EMAIL_KEY = "Email";
    static final String LOGIN_IMG_URI_KEY = "ImageUri";
    static final String LOGIN_APP_PASSWORD_KEY = "AppPassword";
    static final String LOGIN_KAKAO_SUCCESS_BOOLEAN_KEY = "KakaoLoginSuccessBoolean";
    static final String LOGIN_GOOGLE_SUCCESS_BOOLEAN_KEY = "GoogleLoginSuccessBoolean";
    static boolean kakaoLoginSuccessBoolean = false;
    static boolean googleLoginSuccessBoolean = false;

    //Hospital Pharmacy Fragment
    final static int FAVORITES_HOSPITAL_PHARMACY_REQUEST_FROM_FRAG_HOSPITAL_PHARMACY = 200;
    final static int SEARCH_HOSPITAL_PHARMACY_REQUEST_FROM_FRAG_HOSPITAL_PHARMACY = 201;
    final static int LOCATION_REQUEST_FROM_FRAG_HOSPITAL_PHARMACY = 202;
    static boolean hospitalPharmacyBooleanFromHomeFrag = false;

    //Favorites Hospital Pharmacy Activity
    static boolean favoritesHospitalPharmacyBooleanFromMyMenuActivity = false;

    // Health Feed Fragment
    static boolean healthFeedBooleanFromMyMenuActivity = false;

    //Search Hospital Pharmacy Activity
//    static boolean searchHospitalPharmacyBooleanFromMyMenuActivity = false;

    //App Inner Password Activity
    static String appPassword;

    //Location Activity
    static double locationLatitude;
    static double locationLongitude;
    final static String MY_LOCATION_KEY_NAME = "myLocation";
    final static String MY_LOCATION_LAT_KEY_NAME = "myLocationLat";
    final static String MY_LOCATION_LON_KEY_NAME = "myLocationLon";
    static String locationAddressFromLatLong;

    //Calendar Activity
    static String getDateFromCalendar = "";


}//Global class
