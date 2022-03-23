package com.example.nabil.hikingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private Button btnLogout, btn_activity, btn_viewActivity, btnAccount;
    private FirebaseAuth mAuth;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(this);

        mAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.btnLogout);
        btn_activity = findViewById(R.id.btnCreateActivity);
        btn_viewActivity = findViewById(R.id.btnViewActivity);
        btnAccount = findViewById(R.id.btnAccount);

        btnLogout.setOnClickListener(this);
        btn_activity.setOnClickListener(this);
        btn_viewActivity.setOnClickListener(this);
        btnAccount.setOnClickListener(this);

        String type = session.getusename();
        if(type.equalsIgnoreCase("hiker")){
            btn_activity.setVisibility(View.INVISIBLE);
        }else if (type.equalsIgnoreCase("mountainguide")){

        }
    }



    @Override
    public void onClick(View v){
        if ( v == btn_activity){
            startActivity(new Intent(MainActivity.this, StartlocationActivity.class));

        }else if (v == btn_viewActivity){
            startActivity(new Intent(MainActivity.this, ListLocation.class));
        }else if (v == btnAccount){
            startActivity(new Intent(MainActivity.this, UserAccount.class));
        }
        else if( v == btnLogout)
        {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}
