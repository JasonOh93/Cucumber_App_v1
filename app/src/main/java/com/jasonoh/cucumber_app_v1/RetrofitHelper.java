package com.jasonoh.cucumber_app_v1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    public static Retrofit getInstanceGson(){
        return new Retrofit.Builder()
                .baseUrl("http://jasonoh93.dothome.co.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }//getInstanceGson

    public static Retrofit getInstanceScalars(){
        return new Retrofit.Builder()
                .baseUrl("http://jasonoh93.dothome.co.kr/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

}//RetrofitHelper class
