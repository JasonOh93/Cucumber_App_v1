package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class RecyclerViewFragmentHospitalAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<FragmentHospitalItem> items;

    GoogleMap googleMap;

    public RecyclerViewFragmentHospitalAdapter() {
    }

    public RecyclerViewFragmentHospitalAdapter(Context context, ArrayList<FragmentHospitalItem> items, GoogleMap googleMap) {
        this.context = context;
        this.items = items;
        this.googleMap = googleMap;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, items.get(getLayoutPosition()).hospitalLatitude, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setHospitalItem(){
            name.setText( items.get(getLayoutPosition()).hospitalName );
            address.setText( items.get(getLayoutPosition()).hospitalAddress );
            telNum.setText( items.get(getLayoutPosition()).hospitalTelNum );

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position( new LatLng(Double.parseDouble(items.get(getLayoutPosition()).hospitalLatitude),
                    Double.parseDouble(items.get(getLayoutPosition()).hospitalLongitude) ));
            markerOptions.title(name.getText().toString());
            googleMap.addMarker(markerOptions);
        }
    }
}
