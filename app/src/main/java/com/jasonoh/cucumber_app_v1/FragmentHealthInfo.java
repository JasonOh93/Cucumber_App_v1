package com.jasonoh.cucumber_app_v1;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHealthInfo extends Fragment {

    private Context context;

    private EditText etWebSearch, etYouTubeSearch;
    private ImageView ivWebSearch, ivYouTubeSearch;

    RecyclerView recyclerView;
    YouTubeDetailListAdapter adapter;
    ArrayList<YouTubeDetailListRecyclerItem> items = new ArrayList<>();

    public FragmentHealthInfo() {
    }//HomeFragment Constructor (null)

    public FragmentHealthInfo(Context context) {
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

        View view = inflater.inflate( R.layout.frag_bottom_nav_health_info, container, false );

        ((MainActivity) getActivity()).setSupportActionBar( view.findViewById(R.id.frag_health_info_toolbar) );
        getActivity().setTitle("");

        etWebSearch = view.findViewById(R.id.frag_health_info_web_search_et);
        etYouTubeSearch = view.findViewById(R.id.frag_health_info_youtube_search_et);
        ivWebSearch = view.findViewById(R.id.frag_health_info_web_search_iv);
        ivYouTubeSearch = view.findViewById(R.id.frag_health_info_youtube_search_iv);

        recyclerView = view.findViewById(R.id.frag_health_info_youtube_info_recycler);

        return view;
    }//onCreateView method

    // View 가 Create 되고 난 다음에 나타나는 것
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickItem();

        adapter = new YouTubeDetailListAdapter(context, items);
        recyclerView.setAdapter(adapter);

    }//onViewCreated method

    public void clickItem(){

        //웹 검색 클릭시
        ivWebSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse( "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query="
                        + etWebSearch.getText().toString() ));
                startActivity( intent );
                etWebSearch.setText("");
            }
        });// ivWebSearch.setOnClickListener

        //유투브 검색 클릭시
        ivYouTubeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etYouTubeSearch.getText().toString().equals("")){
                    loadDataFromYouTube();

                    //소프트 키패드를 안보이도록
                    InputMethodManager imm =  (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0); //flags 는 즉시하려면 0
                    //imm.showSoftInput() //화면에 보일때는 show

                } else Toast.makeText(context, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();;

                etYouTubeSearch.setText("");
            }
        });// ivYouTubeSearch.setOnClickListener
    }//clickItem

    public void loadDataFromYouTube(){

        String key = getResources().getString(R.string.google_api_key);//구글 키 값
        String part = "snippet";
        String query = etYouTubeSearch.getText().toString();

        int maxResult = 10;

        RetrofitService retrofitService = RetrofitHelper.getInstanceGsonYouTube().create(RetrofitService.class);
        Call<Map<String, Object>> call = retrofitService.loadDataFromYouTube( key, part, query, maxResult );
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if(response.isSuccessful()) {

                    items.clear();
                    adapter.notifyDataSetChanged();

                    Map<String, Object> allYouTubeInfo = response.body();
                    ArrayList<Map<String, Object>> itemsYouTubeInfo =  (ArrayList<Map<String, Object>>) allYouTubeInfo.get("items");

                    for(int i = 0; i < itemsYouTubeInfo.size(); i++) {

                        Map<String, Object> idYouTubeInfo =  ( Map<String, Object> ) itemsYouTubeInfo.get(i).get("id");
                        Map<String, Object> snippetYouTubeInfo =  ( Map<String, Object> ) itemsYouTubeInfo.get(i).get("snippet");

                        //이미지 얻어오기
                        Map<String, Object> thumbnailsYouTubeInfo =  (Map<String, Object>) snippetYouTubeInfo.get("thumbnails");
                        Map<String, Object> defaultYouTubeInfo =  (Map<String, Object>) thumbnailsYouTubeInfo.get("default");

                        if(idYouTubeInfo.get("videoId") != null) {
                            items.add( items.size(),
                                    new YouTubeDetailListRecyclerItem(
                                            idYouTubeInfo.get("videoId").toString(),
                                            snippetYouTubeInfo.get("title").toString(),
                                            snippetYouTubeInfo.get("description").toString(),
                                            snippetYouTubeInfo.get("channelTitle").toString(),
                                            snippetYouTubeInfo.get("publishTime").toString(),
                                            defaultYouTubeInfo.get("url").toString()
                                    ) // new YouTubeDetailListRecyclerItem
                            );// items.add
                            adapter.notifyItemInserted(items.size());
                        }//if(idYouTubeInfo.get("videoId") != null)
                    }//for

                }// if(response.isSuccessful())
            }//onResponse method

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.w("TAG", "유투브로 부터 실패한 이유 : " + t.getMessage());
            }
        });


        //==========================================================================================================

    }//loadDataFromYouTube method

}//FragmentHealthFeed fragment class
