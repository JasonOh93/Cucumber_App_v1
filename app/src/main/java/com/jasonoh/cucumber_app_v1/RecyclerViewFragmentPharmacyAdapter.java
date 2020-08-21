package com.jasonoh.cucumber_app_v1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class RecyclerViewFragmentPharmacyAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<FragmentPharmacyItem> items;

    GoogleMap googleMap;

    public RecyclerViewFragmentPharmacyAdapter() {
    }

    public RecyclerViewFragmentPharmacyAdapter(Context context, ArrayList<FragmentPharmacyItem> items, GoogleMap googleMap) {
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
                }
            });
        }

        public void setHospitalItem(){
            name.setText( items.get(getLayoutPosition()).name );
            address.setText( items.get(getLayoutPosition()).address );
            telNum.setText( items.get(getLayoutPosition()).telNum );

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position( new LatLng(Double.parseDouble(items.get(getLayoutPosition()).latitude),
                    Double.parseDouble(items.get(getLayoutPosition()).longitude) ));
            markerOptions.title(name.getText().toString());
            googleMap.addMarker(markerOptions).showInfoWindow();
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent();
                    intent.setAction( Intent.ACTION_VIEW );
                    intent.setData( Uri.parse("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=" + name.getText() + " " + address.getText()) );
                    context.startActivity( intent );
                }
            });
        }
    }
}
