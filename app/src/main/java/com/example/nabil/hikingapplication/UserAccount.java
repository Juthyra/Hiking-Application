package com.example.nabil.hikingapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccount extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "userAccount";
    private Button mUpdate, mDelete;
    private TextView mType;
    private EditText mName, mIc;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String nameUser, icUser, typeUser, emailUser, idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mUpdate = findViewById(R.id.btn_userUpdate);
        mDelete = findViewById(R.id.btn_userDelete);
        mType = findViewById(R.id.tv_userType);
        mName = findViewById(R.id.et_user_name);
        mIc = findViewById(R.id.et_user_ic);

        mDatabase.child("Admin").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Admin admin = dataSnapshot.getValue(Admin.class);
                    nameUser = admin.getName();
                    icUser = admin.getIc();
                    typeUser = admin.getUser();
                    emailUser = admin.getEmail();
                    idUser = admin.getUid();

                    mName.setText(nameUser);
                    mIc.setText(icUser);
                    mType.setText(typeUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUpdate.setOnClickListener(this);
        mDelete.setOnClickListener(this);
    }

    public void updateUser(){
        String name = mName.getText().toString().trim();
        String ic = mIc.getText().toString().trim();


        Admin admin = new Admin(idUser,emailUser,name,ic,typeUser);
        mDatabase.child("Admin").child(mAuth.getUid()).setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserAccount.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(UserAccount.this, MainActivity.class));
                }
            }
        });

    }

    public void deleteUser(){

        mDatabase.child("Admin").child(idUser).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserAccount.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(UserAccount.this, LoginActivity.class));
                }
            }
        });
    }

    public void deleteAuth() {
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
        // [END delete_user]
    }


    @Override
    public void onClick(View v) {
        if (v == mUpdate) {
            updateUser();
        } else if (v == mDelete) {
            deleteUser();
            deleteAuth();
        }
    }

}
