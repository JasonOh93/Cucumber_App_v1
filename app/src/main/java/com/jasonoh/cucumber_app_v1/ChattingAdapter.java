package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingAdapter extends BaseAdapter {

    Context context;
    ArrayList<ChattingMessageItem> items;

    public ChattingAdapter() {
    }//constructor

    public ChattingAdapter(Context context, ArrayList<ChattingMessageItem> items) {
        this.context = context;
        this.items = items;
    }//constructor

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //1. create view [ my_msgbox or other_msgbox ]
        // 우선 나의 정보를 이용해서 채팅방 만들기
        View view = null;
        if(Global.googleLoginSuccessBoolean || Global.kakaoLoginSuccessBoolean) {
            if(Global.loginPreferences.getString("Name", "").equals(items.get(position).name))
                view = LayoutInflater.from(context).inflate(R.layout.chatting_my_msgbox, parent, false);
            else view = LayoutInflater.from(context).inflate(R.layout.chatting_other_msgbox, parent, false);
        }//if 로그인이 되어있는지 아닌지

        //2. bind view
        CircleImageView iv = view.findViewById(R.id.civ_my_profile);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvMsg =view.findViewById(R.id.tv_msg);
        TextView tvTime = view.findViewById(R.id.tv_time);

        Glide.with(context).load(items.get(position).profileUrl).into(iv);
        tvName.setText( items.get(position).name );
        tvMsg.setText( items.get(position).message );
        tvTime.setText( items.get(position).time );


        return view;
    }
}
