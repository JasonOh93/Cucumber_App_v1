package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewMyHealthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<FragmentMyHealthMember> members;

    public RecyclerViewMyHealthAdapter() {
    }//constructor

    public RecyclerViewMyHealthAdapter(Context context, ArrayList<FragmentMyHealthMember> members) {
        this.context = context;
        this.members = members;
    }//constructor

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.frag_my_health_feed_recycler_item, parent, false));
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
        TextView tvTitle, tvWeight, tvMessage, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.recycler_item_frag_my_health_feed_title);
            tvWeight = itemView.findViewById(R.id.recycler_item_frag_my_health_feed_weight);
            tvMessage = itemView.findViewById(R.id.recycler_item_frag_my_health_feed_msg);
            tvDate = itemView.findViewById(R.id.recycler_item_frag_my_health_feed_date);
            civ = itemView.findViewById(R.id.recycler_item_frag_my_health_feed_civ);

        }//constructor

        public void setItem(){
            tvTitle.setText( members.get(getLayoutPosition()).title );
            tvWeight.setText( members.get(getLayoutPosition()).weight );
            tvMessage.setText( members.get(getLayoutPosition()).message );
            tvDate.setText( members.get(getLayoutPosition()).date );
            Glide.with(context).load( "http://jasonoh93.dothome.co.kr/CucumberRetrofit/" + members.get(getLayoutPosition()).imgUri ).into( civ );
        }//setItem method

    }// inner class ViewHolder

}//RecyclerViewMyHealthAdapter class
