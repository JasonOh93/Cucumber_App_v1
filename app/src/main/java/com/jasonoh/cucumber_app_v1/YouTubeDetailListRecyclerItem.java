package com.jasonoh.cucumber_app_v1;

public class YouTubeDetailListRecyclerItem {

    String videoId, title, description, channelTitle, publishTime, thumbnailsUrl;

    public YouTubeDetailListRecyclerItem() {
    }

    public YouTubeDetailListRecyclerItem(String videoId, String title, String description, String channelTitle, String publishTime, String thumbnailsUrl) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.channelTitle = channelTitle;
        this.publishTime = publishTime;
        this.thumbnailsUrl = thumbnailsUrl;
    }
}//YouTubeDetailListRecyclerItem class
