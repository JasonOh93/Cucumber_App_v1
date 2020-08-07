package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewFragmentHospitalAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<FragmentHospitalItem> items;

    public RecyclerViewFragmentHospitalAdapter() {
    }

    public RecyclerViewFragmentHospitalAdapter(Context context, ArrayList<FragmentHospitalItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.frag_hospital_pharmacy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        vh.setHospitalItem();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView name, address, telNum;
        ToggleButton toggleButton;

        public VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recycler_item_frag_hospital_pharmacy_name_tv);
            address = itemView.findViewById(R.id.recycler_item_frag_hospital_pharmacy_address_tv);
            telNum = itemView.findViewById(R.id.recycler_item_frag_hospital_pharmacy_tel_tv);
            toggleButton = itemView.findViewById(R.id.recycler_item_frag_hospital_pharmacy_favorite_tg);
        }

        public void setHospitalItem(){
            name.setText( items.get(getLayoutPosition()).hospitalName );
            address.setText( items.get(getLayoutPosition()).hospitalAddress );
            telNum.setText( items.get(getLayoutPosition()).hospitalTelNum );
        }
    }
}
