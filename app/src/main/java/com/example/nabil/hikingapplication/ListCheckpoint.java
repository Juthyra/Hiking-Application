package com.example.nabil.hikingapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListCheckpoint extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ListLocation";
    private EditText routeName, routeDate;
    private Button btn_cancel, btn_update, btn_delete;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<mCheckpoint> mCheckpointList;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Session session;

    String idRoute, nameRoute, idLocation, mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_checkpoint);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        session = new Session(this);

        mRecyclerView = findViewById(R.id.rvCheckpoint);
        routeName = findViewById(R.id.etRouteName);
        routeDate = findViewById(R.id.et_RouteDate);

        btn_cancel = findViewById(R.id.btnCancelRoute);
        btn_update = findViewById(R.id.btnUpdateRoute);
        btn_delete = findViewById(R.id.btnDeleteRoute);


        btn_cancel.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);


        String type = session.getusename();
        if (type.equalsIgnoreCase("hiker")){
            btn_update.setVisibility(View.INVISIBLE);
            btn_delete.setVisibility(View.INVISIBLE);
        } else if (type.equalsIgnoreCase("mountainguide")){

        }


        Bundle extra = getIntent().getExtras();
        if (extra != null){
            idLocation = extra.getString("locationID");
            idRoute = extra.getString("routeID");
            nameRoute = extra.getString("route");
            mDate = extra.getString("routeDate");
        }

        routeDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Calendar calendar = Calendar.getInstance();
                Dialog mDialog = new DatePickerDialog(ListCheckpoint.this, mDatesetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                mDialog.show();

            }
        });

        routeName.setText(nameRoute);
        routeDate.setText(mDate);

        mDatabase.child("CheckPoint").orderByChild("route").equalTo(idRoute).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCheckpointList = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    mCheckpoint mc = ds.getValue(mCheckpoint.class);
                    Log.d(TAG, "onDataChange: "+ mc);
                    mCheckpointList.add(mc);
                }

                mAdapter = new CheckpointAdapter(getApplicationContext(), mCheckpointList);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void deleteCheckpoint(){

        mDatabase.child("CheckPoint").child(idRoute).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ListCheckpoint.this, "Delete Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteRoute(){

        mDatabase.child("Route").child(idRoute).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ListCheckpoint.this, "Delete Successful", Toast.LENGTH_SHORT).show();


            }
        });

        startActivity(new Intent(this,MainActivity.class));
    }

    public void updateRoute(){

        String name = routeName.getText().toString().trim();
        String idR = idRoute;
        String date = routeDate.getText().toString().trim();

        mRoute route = new mRoute(idR,name,idLocation,date);
        mDatabase.child("Route").child(idRoute).setValue(route).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ListCheckpoint.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ListCheckpoint.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btn_cancel){
            Intent i = new Intent(ListCheckpoint.this,MainActivity.class);
            startActivity(i);
        }else if (v == btn_delete){
            deleteRoute();
            deleteCheckpoint();
        }else if (v == btn_update){
            updateRoute();
        }
    }

    DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month + 1;

            String date = year + "/" + month + "/" + dayOfMonth;

            routeDate.setText(date);
        }
    };
}
