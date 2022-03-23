package com.example.nabil.hikingapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListRoute extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ListLocation";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<mRoute> mRouteList;
    private Button addRoute;
    private Session session;

    String locationID, date, locationName;
    TextView lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_route);

        session = new Session(this);

        addRoute = findViewById(R.id.btnAddRoute);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        mRecyclerView = findViewById(R.id.rvRoute);
        lName = findViewById(R.id.tvLocationName);

        String type = session.getusename();
        if(type.equalsIgnoreCase("hiker")){
            addRoute.setVisibility(View.INVISIBLE);
        }else if (type.equalsIgnoreCase("mountainguide")){

        }

        Bundle extra = getIntent().getExtras();
        if(extra != null){
           locationID = extra.getString("locationID");
           date = extra.getString("locationDate");
           locationName = extra.getString("locationName");
            Log.d(TAG, "dataTest: "+locationID);
        }

        lName.setText(locationName);

        mDatabase.child("Route").orderByChild("uidLocation").equalTo(locationID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRouteList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    mRoute mr = ds.getValue(mRoute.class);
                    Log.d(TAG, "onDataChange: "+ mr);
                    mRouteList.add(mr);
                }

                mAdapter = new RouteAdapter(getApplicationContext(), mRouteList);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addRoute.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addRoute){
            Intent i = new Intent(ListRoute.this,updateLocation.class);
            i.putExtra("locationID",locationID);
            i.putExtra("lDate",date);
            i.putExtra("lName",locationName);
            startActivity(i);

        }
    }
}
