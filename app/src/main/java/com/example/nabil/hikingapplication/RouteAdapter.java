package com.example.nabil.hikingapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.LocationViewHolder>{

    private Context mCtx;
    private List<mRoute> routeList;

    public RouteAdapter(Context mCtx, List<mRoute> routeList) {
        this.mCtx = mCtx;
        this.routeList = routeList;

    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.route_layout, viewGroup, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder locationViewHolder, int i) {
        final mRoute route = routeList.get(i);
        locationViewHolder.mRoute.setText(route.getRoute());
        locationViewHolder.date.setText(route.getDate());

        locationViewHolder.routeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ListCheckpoint.class);
                intent.putExtra("routeID", route.getUid());
                intent.putExtra("route", route.getRoute());
                intent.putExtra("locationID", route.getUidLocation());
                intent.putExtra("routeDate", route.getDate());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder{

        TextView mRoute, date;
        CardView routeLayout;


        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            mRoute = itemView.findViewById(R.id.tvRoute);
            routeLayout = itemView.findViewById(R.id.cvRoute);
            date = itemView.findViewById(R.id.tv_route_date);
        }
    }
}
