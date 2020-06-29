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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class admin_signupc extends AppCompatActivity {
    private EditText e1,e2,e3;
    private Button btn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore mfirestore= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signupc);
        e1=(EditText)findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        e3=(EditText)findViewById(R.id.e3);
        btn=(Button)findViewById(R.id.btn);
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
                    firebaseAuth.createUserWithEmailAndPassword(e2.getText().toString().trim(), e3.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendemailverification();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(admin_signupc.this, "ENTER CORRECT EMAIL ID", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private Boolean validate(){
        Boolean result=false;
        if(e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty() ||  e3.getText().toString().isEmpty() ) {
            Toast.makeText(admin_signupc.this, "PLEASE ENTER ALL DETAILS", Toast.LENGTH_SHORT).show();
        }
        else {
            if (e3.length() < 6) {
                Toast.makeText(admin_signupc.this, "PASSWORD SIZE MUST BE GREATER THAN 5 CHARACTERS ", Toast.LENGTH_SHORT).show();
            } else {
                result = true;
            }
        }

        return result;
    }
    private void sendemailverification(){
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            hosp h=new hosp(e1.getText().toString(),user.getUid(),R.drawable.cc);
            UserProfileChangeRequest profileupdate=new UserProfileChangeRequest.Builder().setDisplayName("A").build();
            user.updateProfile(profileupdate);
            mfirestore.collection("hospital").add(h).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(admin_signupc.this,"SUCCESSFULLY REGISTERED , VERIFICATION MAIL SENT",Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(admin_signupc.this,MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(admin_signupc.this,"VERIFICATION MAIL NOT SENT, TRY AGAIN",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(admin_signupc.this,"ERROR, TRY AGAIN",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(admin_signupc.this,adminsign.class));
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
                startActivity(new Intent(admin_signupc.this,adminsign.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
}
