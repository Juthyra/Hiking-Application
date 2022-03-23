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

public class CheckpointAdapter extends RecyclerView.Adapter<CheckpointAdapter.LocationViewHolder>{

    private Context mCtx;
    private List<mCheckpoint> checkpointList;

    public CheckpointAdapter(Context mCtx, List<mCheckpoint> checkpointList) {
        this.mCtx = mCtx;
        this.checkpointList = checkpointList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.checkpoint_layout, viewGroup, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder locationViewHolder, int i) {
        final mCheckpoint checkpoint = checkpointList.get(i);
        locationViewHolder.mCpoint.setText(checkpoint.getMcheckpoint());
        locationViewHolder.cpDate.setText(checkpoint.getDate());

        locationViewHolder.cPointLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, MapsActivity.class);
                i.putExtra("ID", checkpoint.getUid());
                i.putExtra("name", checkpoint.getMcheckpoint());
                i.putExtra("latitude", checkpoint.getLatitude());
                i.putExtra("longitude", checkpoint.getLongitude());
                i.putExtra("routeName",checkpoint.getRoute());
                i.putExtra("date",checkpoint.getDate());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkpointList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder{

        TextView mCpoint, cpDate;
        CardView cPointLayout;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            mCpoint = itemView.findViewById(R.id.tvCpoint);
            cPointLayout = itemView.findViewById(R.id.cvCheckpoint);
            cpDate = itemView.findViewById(R.id.tv_cp_date);
        }
    }
}
