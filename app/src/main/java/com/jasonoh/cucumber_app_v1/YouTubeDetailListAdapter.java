package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.prnd.YouTubePlayerView;

public class YouTubeDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<YouTubeDetailListRecyclerItem> items;

    public YouTubeDetailListAdapter() {
    }

    public YouTubeDetailListAdapter(Context context, ArrayList<YouTubeDetailListRecyclerItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH( LayoutInflater.from(context).inflate(R.layout.youtube_detail_list_recycler_item, parent, false) );
    }//onCreateViewHolder method

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        vh.initYouTubeDetailSetting();
    }//onBindViewHolder method

    @Override
    public int getItemCount() {
        return items.size();
    }//getItemCount method

    class VH extends RecyclerView.ViewHolder {

        YouTubePlayerView youTubePlayerView;
        TextView tvTitle, tvDescription, tvChannelTitle, tvPublishTime;

        public VH(@NonNull View itemView) {
            super(itemView);

            youTubePlayerView = itemView.findViewById(R.id.recycler_item_youtube_detail_youtube_view);
            tvTitle = itemView.findViewById(R.id.recycler_item_youtube_detail_title);
            tvDescription = itemView.findViewById(R.id.recycler_item_youtube_detail_description);
            tvChannelTitle = itemView.findViewById(R.id.recycler_item_youtube_detail_channel_title);
            tvPublishTime = itemView.findViewById(R.id.recycler_item_youtube_detail_publish_time);

        }//constructor

        public void initYouTubeDetailSetting(){
            youTubePlayerView.play( items.get(getLayoutPosition()).videoId, null );
            tvTitle.setText( items.get(getLayoutPosition()).title );
            tvDescription.setText( items.get(getLayoutPosition()).description );
            tvChannelTitle.setText( items.get(getLayoutPosition()).channelTitle );
            tvPublishTime.setText( items.get(getLayoutPosition()).publishTime );
        }//initYouTubeDetailSetting method
    }//inner class VH

}//YouTubeDetailListAdapter class
