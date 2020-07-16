package com.jasonoh.cucumber_app_v1;

public class ChattingMessageItem {

    String name;            // 닉네임
    String message;         // 메세지
    String time;            //작성시간
    String profileUrl;      //프로필 이미지의 https://... URL

    public ChattingMessageItem(String name, String message, String time, String profileUrl) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.profileUrl = profileUrl;
    }

    public ChattingMessageItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}//ChattingMessageItem class
