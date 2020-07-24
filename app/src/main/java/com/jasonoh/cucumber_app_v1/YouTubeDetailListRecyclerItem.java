package com.jasonoh.cucumber_app_v1;

public class YouTubeDetailListRecyclerItem {

    String videoId, title, description, channelTitle, publishTime;

    public YouTubeDetailListRecyclerItem() {
    }

    public YouTubeDetailListRecyclerItem(String videoId, String title, String description, String channelTitle, String publishTime) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.channelTitle = channelTitle;
        this.publishTime = publishTime;
    }
}//YouTubeDetailListRecyclerItem class
