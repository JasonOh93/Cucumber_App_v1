package com.jasonoh.cucumber_app_v1;

public class FragmentBoardMember {

    String imgUri;
    String title, weight, message, date, email;
    String location;
    String personName;
    //boolean favor;  //좋아요 여부 [ mysql 에 true, false 를 1, 0으로 대체하여 저장 ]
    int favor;          //좋아요 여부


    public FragmentBoardMember() {
    }//constructor

    public FragmentBoardMember(String imgUri, String title, String weight, String message, String date, String email, String location, String personName, int favor) {
        this.imgUri = imgUri;
        this.title = title;
        this.weight = weight;
        this.message = message;
        this.date = date;
        this.email = email;
        this.location = location;
        this.personName = personName;
        this.favor = favor;
    }
}//FragmentMyHealthMember class
