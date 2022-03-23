package com.example.nabil.hikingapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import static android.app.PendingIntent.getActivity;

public class StartlocationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int DATEPICKER_FRAGMENT = 1;

    private Button btn_start_activity, btn_cancel;
    private EditText txt_location, txt_date, txt_route;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startlocation);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_start_activity = findViewById(R.id.btnStart_update);
        btn_cancel = findViewById(R.id.btnCancel);

        txt_date = findViewById(R.id.etDate_update);
        txt_location = findViewById(R.id.etLocation_update);
        txt_route = findViewById(R.id.etRoute_update);

        txt_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Calendar calendar = Calendar.getInstance();
                Dialog mDialog = new DatePickerDialog(StartlocationActivity.this, mDatesetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                mDialog.show();

            }
        });


        btn_start_activity.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


    }


    private void addLocationActivity() {
        String userUid = mAuth.getCurrentUser().getUid();
        final String locationUid = mDatabase.child("posts").push().getKey();
        final String mlocation = txt_location.getText().toString().trim();
        final String date = txt_date.getText().toString().trim();

        final String routeUid = mDatabase.child("post").push().getKey();
        final String mroute = txt_route.getText().toString().trim();

        if (TextUtils.isEmpty(mlocation)) {
            Toast.makeText(this, "Please fill the location", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mroute)) {

            Toast.makeText(this,"Please fill the route", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Please fill the date", Toast.LENGTH_SHORT).show();
            return;
        }



        mLocation location = new mLocation(locationUid, mlocation, date, userUid);
        mDatabase.child("Location").child(locationUid).setValue(location).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Adding Success", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mRoute route = new mRoute(routeUid,mroute,locationUid,date);
        mDatabase.child("Route").child(routeUid).setValue(route).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Adding Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = new Intent(StartlocationActivity.this, LocationActivity.class);
        intent.putExtra("idLocation", locationUid);
        intent.putExtra("idRoute",routeUid);
        intent.putExtra("date",date);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if (v == btn_start_activity) {
            addLocationActivity();
        }else if(v == btn_cancel){
            Intent i = new Intent(StartlocationActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month + 1;

            String date = year + "/" + month + "/" + dayOfMonth;

            txt_date.setText(date);
        }
    };
}

