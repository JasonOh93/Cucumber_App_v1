package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewBoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<FragmentBoardMember> members;

    public RecyclerViewBoardAdapter() {
    }//constructor

    public RecyclerViewBoardAdapter(Context context, ArrayList<FragmentBoardMember> members) {
        this.context = context;
        this.members = members;
    }//constructor

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.frag_board_recycler_item, parent, false));
    }//onCreateViewHolder method

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setItem();
    }//onBindViewHolder method

    @Override
    public int getItemCount() {
        return members.size();
    }//getItemCount method

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civ;
        TextView tvTitle, tvWeight, tvMessage, tvDate, tvEmail, tvPersonName;
        TextView tvLocation;
        ImageView ivChat;
        ToggleButton tgFavor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.recycler_item_frag_board_title);
            tvWeight = itemView.findViewById(R.id.recycler_item_frag_board_weight);
            tvMessage = itemView.findViewById(R.id.recycler_item_frag_board_msg);
            tvDate = itemView.findViewById(R.id.recycler_item_frag_board_date);
            tvEmail = itemView.findViewById(R.id.recycler_item_frag_board_email);
            civ = itemView.findViewById(R.id.recycler_item_frag_board_civ);
            tgFavor = itemView.findViewById(R.id.recycler_item_frag_board_favorite);
            ivChat = itemView.findViewById(R.id.recycler_item_frag_board_chat);

            tvLocation = itemView.findViewById(R.id.recycler_item_frag_board_location);
            tvPersonName = itemView.findViewById(R.id.recycler_item_frag_board_person_name);

        }//constructor

        public void setItem(){
            tvTitle.setText( members.get(getLayoutPosition()).title );
            tvWeight.setText( members.get(getLayoutPosition()).weight );
            tvMessage.setText( members.get(getLayoutPosition()).message );
            tvDate.setText( members.get(getLayoutPosition()).date );
            tvEmail.setText( members.get(getLayoutPosition()).email );
            tvLocation.setText( members.get(getLayoutPosition()).location );
            tvPersonName.setText( members.get(getLayoutPosition()).personName );
            Glide.with(context).load( "http://jasonoh93.dothome.co.kr/CucumberRetrofit/" + members.get(getLayoutPosition()).imgUri ).into( civ );
            tgFavor.setChecked( members.get(getLayoutPosition()).favor == 1 ? true : false );

            //채팅 버튼 클릭시 반응
            ivChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //채팅방으로 이동할때 채팅방에서 채팅창을 선택하려고 했으나 시간상의 문제로
                    // 바로 채팅창으로 이동 해보자!!
                    Intent intent = new Intent(context, ChattingActivity.class);
                    //todo : 채팅창으로 넘어갈때 해당 사용자 정보 받아오기 (이메일 필수)
                    intent.putExtra( "ChattingPersonName", members.get(getLayoutPosition()).personName );
                    context.startActivity( intent );

                }//onClick method
            });//ivChat.setOnClickListener
        }//setItem method

    }// inner class ViewHolder

}//RecyclerViewMyHealthAdapter class
