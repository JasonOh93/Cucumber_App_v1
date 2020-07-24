package com.jasonoh.cucumber_app_v1;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RetrofitService {

    @Multipart
    @POST("/CucumberRetrofit/insertDB.php")
    Call<String> postDataToMyBoard(@PartMap Map<String, String> dataPart, @Part MultipartBody.Part filePart);

//    @GET("/CucumberRetrofit/loadMyHealthInfoDB.php")
//    Call<ArrayList<FragmentMyHealthMember>> loadDataFromMyHealthInfoCucumberBoard();

    @GET("/CucumberRetrofit/loadMyHealthInfoDB.php")
    Call<Map<String, Object>> loadDataFromMyHealthInfoCucumberBoard();

//    @GET("/CucumberRetrofit/loadBoardDB.php")
//    Call<ArrayList<FragmentBoardMember>> loadDataFromCucumberBoard();

    @GET("/CucumberRetrofit/loadMyHealthInfoDB.php")
    Call<ArrayList<FragmentAllShareBoardMember>> loadDataFromCucumberBoard();

}//RetrofitService interface
