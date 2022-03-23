package com.example.nabil.hikingapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class updateLocation extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText location, route, mdate;
    private Button start, cancel;
    String locId, locDate, locName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        location = findViewById(R.id.etLocation_update);
        route = findViewById(R.id.etRoute_update);
        mdate = findViewById(R.id.etDate_update);

        start = findViewById(R.id.btnStart_update);
        cancel = findViewById(R.id.btnCancel_update);

        mdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Calendar calendar = Calendar.getInstance();
                Dialog mDialog = new DatePickerDialog(updateLocation.this, mDatesetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                mDialog.show();

            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            locId = extra.getString("locationID");
            locDate = extra.getString("lDate");
            locName = extra.getString("lName");
        }

        location.setText(locName);
        mdate.setText(locDate);
        location.setEnabled(false);


        start.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    public void addLocationActivity(){
        String userUid = mAuth.getCurrentUser().getUid();
        final String locationUid = locId;
        final String mlocation = location.getText().toString().trim();
        final String mLdate = mdate.getText().toString().trim();
        final String routeUid = mDatabase.child("post").push().getKey();
        final String mroute = route.getText().toString().trim();

        if (TextUtils.isEmpty(mlocation)) {
            Toast.makeText(this, "Please fill the location", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mLdate)) {
            Toast.makeText(this, "Please fill the date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mroute)) {

            Toast.makeText(this,"Please fill the route", Toast.LENGTH_SHORT).show();
        }

        mRoute route = new mRoute(routeUid,mroute,locationUid,mLdate);
        mDatabase.child("Route").child(routeUid).setValue(route).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Adding Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = new Intent(updateLocation.this, LocationActivity.class);
        intent.putExtra("idLocation", locationUid);
        intent.putExtra("idRoute",routeUid);
        startActivity(intent);
    }

    DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month + 1;

            String date = year + "/" + month + "/" + dayOfMonth;

            mdate.setText(date);
        }
    };


    @Override
    public void onClick(View v) {
        if (v == start) {
            addLocationActivity();
        } else if (v == cancel) {
            Intent i = new Intent(updateLocation.this,MainActivity.class);
            startActivity(i);
        }
    }
}
