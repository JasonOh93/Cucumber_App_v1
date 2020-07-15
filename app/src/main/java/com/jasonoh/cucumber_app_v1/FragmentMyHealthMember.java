package com.jasonoh.cucumber_app_v1;

public class FragmentMyHealthMember {

    String imgUri;
    String title, weight, message, date;

    public FragmentMyHealthMember() {
    }//constructor

    public FragmentMyHealthMember(String imgUri, String title, String weight, String message, String date) {
        this.imgUri = imgUri;
        this.title = title;
        this.weight = weight;
        this.message = message;
        this.date = date;
    }//constructor

}//FragmentMyHealthMember class
