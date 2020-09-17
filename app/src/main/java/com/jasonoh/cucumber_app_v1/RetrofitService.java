package com.jasonoh.cucumber_app_v1;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @Multipart
    @POST("/CucumberRetrofit/insertDB.php")
    Call<String> postDataToMyBoard(@PartMap Map<String, String> dataPart, @Part MultipartBody.Part filePart);

    @GET("/CucumberRetrofit/loadMyHealthInfoDB.php")
    Call<ArrayList<FragmentAllShareBoardMember>> loadDataFromCucumberBoard();

    // 요청 파라미터 : key(필수), part(필수), q(검색어), maxResults(결과 개수 0~50개) default로 5개
    @GET("/youtube/v3/search")
    Call<Map<String, Object>> loadDataFromYouTube(@Query("key") String key,
                                                  @Query("part") String part,
                                                  @Query("q") String q,
                                                  @Query("maxResult") int maxResult);

    //좋아요 버튼 클릭시 사용되는 변수
    @PUT("/CucumberRetrofit/{filename}")
    Call<FragmentBoardMember> updateBoardData(@Path("filename") String filename, @Body FragmentBoardMember boardMember);

    //todo : FCM Push Service Post 방식
    @Multipart
    @POST("/CucumberRetrofit/fcmPushPost.php")
    Call<String> postFCMPushService(@PartMap Map<String, String> dataPartFCMPush);

}//RetrofitService interface
