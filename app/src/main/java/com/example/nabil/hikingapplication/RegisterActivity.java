package com.example.nabil.hikingapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText tEmail, tPassword, tName, tIC;
    private TextView tvSignIn;
    private Button btnRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Spinner userType;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new Session(this);

        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("mountainGuide");
        spinnerArray.add("hiker");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);


        //create instance
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // link to xml
        tEmail = findViewById(R.id.etEmail);
        tPassword = findViewById(R.id.etPassword);
        tName = findViewById(R.id.etName);
        tIC = findViewById(R.id.etIC);
        tvSignIn = findViewById(R.id.tvSignin);
        btnRegister = findViewById(R.id.btnRegister);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        userType = findViewById(R.id.userType);
        userType.setAdapter(adapter);

        //add listener
        tvSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void register(){

        String email = tEmail.getText().toString().trim();
        String password = tPassword.getText().toString().trim();
        String name = tName.getText().toString().trim();
        String ic = tIC.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ic)){
            Toast.makeText(this, "Please enter your IC", Toast.LENGTH_SHORT).show();
            return;
        }

        //method to create use using firebaseauth
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    registerUserInfo();
                    }
            }
        });
    }

    public void getUserType(){

        mDatabase.child("Admin").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    Admin admin = dataSnapshot.getValue(Admin.class);
                    if (admin.user.equalsIgnoreCase("hiker")){
                        session.setusename("hiker");
                    }else {
                        session.setusename("mountainguide");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void registerUserInfo(){
        String uid = mAuth.getCurrentUser().getUid();
        String email = tEmail.getText().toString().trim();
        String password = tPassword.getText().toString().trim();
        String name = tName.getText().toString().trim();
        String ic = tIC.getText().toString().trim();
        String user = userType.getSelectedItem().toString();


        Admin admin = new Admin(uid, email, name, ic, user);
        mDatabase.child("Admin").child(uid).setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Register Success", Toast.LENGTH_SHORT).show();
                    getUserType();
                    finish();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                }else{
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v){
        if(v == btnRegister){
            register();
        }else if( v == tvSignIn){
            finish();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
}
}
