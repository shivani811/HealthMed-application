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
import com.google.firebase.auth.FirebaseAuth;

public class password extends AppCompatActivity {
private Button btn;
private EditText etp;
private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog;
private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        btn=(Button)findViewById(R.id.btn);
        etp=(EditText)findViewById(R.id.etp);
        tv=(TextView)findViewById(R.id.tv);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etp.getText().toString().isEmpty())
                {
                    Toast.makeText(password.this,"PLEASE ENTER YOUR REGISTERED EMAIL ID",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.setMessage("PLEASE WAIT");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(etp.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(password.this,"PASSWORD RESET MAIL SENT",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(password.this,MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(password.this,"PASSWORD RESET MAIL NOT SENT, TRY AGAIN",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(password.this,MainActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(password.this,MainActivity.class));
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
                startActivity(new Intent(password.this,MainActivity.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
}
