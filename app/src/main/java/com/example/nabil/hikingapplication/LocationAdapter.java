package com.example.nabil.hikingapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{

    private Context mCtx;
    private List<mLocation> locationList;

    public LocationAdapter(Context mCtx, List<mLocation> locationList) {
        this.mCtx = mCtx;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.location_layout, viewGroup, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder locationViewHolder, int i) {
        final mLocation location = locationList.get(i);
        locationViewHolder.mlocation.setText(location.getLocation());
        locationViewHolder.mdate.setText(location.getMdate());
        System.out.print("123213");
        Log.d(TAG, "onBindViewHolder: "+location.getLocation());
        locationViewHolder.locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ListRoute.class);
                intent.putExtra("locationID", location.getUid());
                intent.putExtra("locationName", location.getLocation());
                intent.putExtra("locationDate", location.getMdate());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder{

        TextView mlocation, mdate;
        CardView locationLayout;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            mlocation = itemView.findViewById(R.id.tvLocation);
            mdate = itemView.findViewById(R.id.tvDate);
            locationLayout = itemView.findViewById(R.id.cvLocation);
        }
    }
}
