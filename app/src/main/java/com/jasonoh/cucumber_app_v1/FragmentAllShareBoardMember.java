package com.jasonoh.cucumber_app_v1;

public class FragmentAllShareBoardMember {

    int no;

    String date;

    String file;
    String title, weight, message, saveDate, personEmail;
    //boolean favor;  //좋아요 여부 [ mysql 에 true, false 를 1, 0으로 대체하여 저장 ]
    int favor;          //좋아요 여부

    String location;

    String allShare, titleShare, pictureShare, locationShare, messageShare, dateShare, weightShare;

    String personName;

    public FragmentAllShareBoardMember() {
    }//constructor

    public FragmentAllShareBoardMember(int no, String date, String file, String title, String weight, String message, String saveDate, String personEmail, int favor, String location, String allShare, String titleShare, String pictureShare, String locationShare, String messageShare, String dateShare, String weightShare, String personName) {
        this.no = no;
        this.date = date;
        this.file = file;
        this.title = title;
        this.weight = weight;
        this.message = message;
        this.saveDate = saveDate;
        this.personEmail = personEmail;
        this.favor = favor;
        this.location = location;
        this.allShare = allShare;
        this.titleShare = titleShare;
        this.pictureShare = pictureShare;
        this.locationShare = locationShare;
        this.messageShare = messageShare;
        this.dateShare = dateShare;
        this.weightShare = weightShare;
        this.personName = personName;
    }
}//FragmentMyHealthMember class
