package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

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

        TextView tvTitle, tvDescription, tvChannelTitle, tvPublishTime;
        ImageView ivYouTubeThumbnails;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.recycler_item_youtube_detail_title);
            tvDescription = itemView.findViewById(R.id.recycler_item_youtube_detail_description);
            tvChannelTitle = itemView.findViewById(R.id.recycler_item_youtube_detail_channel_title);
            tvPublishTime = itemView.findViewById(R.id.recycler_item_youtube_detail_publish_time);
            ivYouTubeThumbnails = itemView.findViewById(R.id.recycler_item_youtube_detail_youtube_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.youTubeVideoId = items.get(getLayoutPosition()).videoId;
                    context.startActivity( new Intent(context, YouTubeDetailActivity.class));
//                    ((AppCompatActivity)context).finish();
                }
            });

        }//constructor

        public void initYouTubeDetailSetting(){

            Glide.with(context).load(items.get(getLayoutPosition()).thumbnailsUrl).into(ivYouTubeThumbnails);

            tvTitle.setText( items.get(getLayoutPosition()).title );
            tvDescription.setText( items.get(getLayoutPosition()).description );
            tvChannelTitle.setText( items.get(getLayoutPosition()).channelTitle );
            tvPublishTime.setText( items.get(getLayoutPosition()).publishTime );
        }//initYouTubeDetailSetting method
    }//inner class VH

}//YouTubeDetailListAdapter class
