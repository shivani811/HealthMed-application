package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button bttn;
    private EditText et1;
    private EditText et2;
    private TextView tv1, tvf;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressdialog;
    private long backpressedtime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bttn = (Button) findViewById(R.id.bt);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        tv1 = (TextView) findViewById(R.id.tv1);
        tvf = (TextView) findViewById(R.id.tvf);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        progressdialog = new ProgressDialog(this);
        if (user != null) {
            if (user.getDisplayName().equals("A")) {
                finish();
                startActivity(new Intent(MainActivity.this, adminhome.class));
            } else {
                finish();
                startActivity(new Intent(MainActivity.this, nav_home.class));
            }
        }
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    progressdialog.setMessage("PLEASE WAIT");
                    progressdialog.show();
                    progressdialog.setCanceledOnTouchOutside(false);
                    progressdialog.setCancelable(false);

                    firebaseAuth.signInWithEmailAndPassword(et1.getText().toString().trim(), et2.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            checkemailverification();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressdialog.dismiss();
                            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, signupp.class));
            }
        });
        tvf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, password.class));
            }
        });
    }

    private void checkemailverification() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Boolean emailflag = user.isEmailVerified();
        if (emailflag) {
            if (user.getDisplayName().equals("A")) {
                progressdialog.dismiss();
                Toast.makeText(MainActivity.this, "WELCOME ADMIN", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(MainActivity.this, adminhome.class));
            } else {
                progressdialog.dismiss();
                Toast.makeText(MainActivity.this, "SUCCESSFULLY LOGGED IN", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(MainActivity.this,hospital_view.class));
            }
        } else {
            progressdialog.dismiss();
            Toast.makeText(MainActivity.this, "VERIFY YOUR EMAIL ID", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    private Boolean validate() {
        Boolean result = false;
        if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "PLEASE ENTER ALL DETAILS", Toast.LENGTH_SHORT).show();
        } else
            result = true;

        return result;
    }

    @Override
    public void onBackPressed() {

        if (backpressedtime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "PRESS BACK AGAIN TO EXIT", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpressedtime = System.currentTimeMillis();
    }
}
