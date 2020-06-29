package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class register extends AppCompatActivity {
private Button btn;
private EditText et1,et2;
private TextView tv1;
private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn=(Button)findViewById(R.id.bt);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        tv1=(TextView)findViewById(R.id.tv1);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (validate()) {
                        progressDialog.setMessage("PLEASE WAIT");
                        progressDialog.show();
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCancelable(false);
                    firebaseAuth.createUserWithEmailAndPassword(et1.getText().toString().trim(), et2.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendemailverification();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(register.this, "ENTER CORRECT EMAIL ID", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(register.this,MainActivity.class));
            }
        });
    }
private Boolean validate(){
        Boolean result=false;
        if(et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty()) {
            Toast.makeText(register.this, "PLEASE ENTER ALL DETAILS", Toast.LENGTH_SHORT).show();
        }
            else {
            if (et2.length() < 6) {
                Toast.makeText(register.this, "PASSWORD SIZE MUST BE GREATER THAN 5 CHARACTERS ", Toast.LENGTH_SHORT).show();
            } else {
                result = true;
            }
        }

        return result;
}
private void sendemailverification(){
    FirebaseUser user=firebaseAuth.getCurrentUser();
    if(user!=null)
    {
        UserProfileChangeRequest profileupdate=new UserProfileChangeRequest.Builder().setDisplayName(" ").build();
        user.updateProfile(profileupdate);
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(register.this,"SUCCESSFULLY REGISTERED , VERIFICATION MAIL SENT",Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(register.this,MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(register.this,"VERIFICATION MAIL NOT SENT, TRY AGAIN",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(register.this,signupp.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.backmenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backmenu:{
                finish();
                startActivity(new Intent(register.this,signupp.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
}
