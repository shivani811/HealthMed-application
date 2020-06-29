package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class reject extends AppCompatActivity {
    private EditText et;
    private Button btn,btn2;
    private FirebaseFirestore mfirestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject);
        et=(EditText)findViewById(R.id.et);
        btn=(Button)findViewById(R.id.btn);
        btn2=(Button)findViewById(R.id.btn2);
        final Intent intent=getIntent();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et.getText().toString().trim().isEmpty()){
                String x=intent.getStringExtra(adminlook.EXTRA_POS);
                String y=intent.getStringExtra(adminlook.EXTRA_EMAIL);
                String name=intent.getStringExtra(adminlook.EXTRA_NAME);
                String appointment=intent.getStringExtra(adminlook.EXTRA_APPOINTEMENT);
                String time=intent.getStringExtra(adminlook.EXTRA_TIMESLOT);
                DocumentReference documentReference=mfirestore.document(x);
                documentReference.update("accept",R.drawable.ic_clear);
                documentReference.update("reason","STATUS :-  REJECTED\nREASON :-"+et.getText().toString());
                sendmail s=new sendmail(reject.this,y,"SMS HOSPITAL MANAGEMENT: APPOINTMENT REJECTED","Sorry "+name+" but your appointment has been rejected \nAPPOINTMENT DATE:- "+appointment+"\nTIME SLOT:- "+time+"\nDOCTOR'S NAME:- "+intent.getStringExtra(adminlook.EXTRA_DNAME)+"\nSPECIALIZATION:- "+intent.getStringExtra(adminlook.EXTRA_DSPECS)+"\nREASON:- "+et.getText().toString());
                s.execute();
                finish();
                startActivity(new Intent(reject.this,rej.class));
                }
                else
                {
                    Toast.makeText(reject.this,"PLEASE ENTER SOMETHING",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(reject.this,checkbook.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(reject.this,checkbook.class));
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
                startActivity(new Intent(reject.this,checkbook.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
}
