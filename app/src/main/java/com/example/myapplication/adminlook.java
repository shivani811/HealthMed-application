package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class adminlook extends AppCompatActivity {
    private TextView e1,e2,e3,e4,e5,e6,e7,e8,e9;
    private Button btn1,btn2;
    private FirebaseFirestore mfirestore=FirebaseFirestore.getInstance();
    public static final String EXTRA_POS="com.example.myapplication.EXTRA_POS";
    public static final String EXTRA_EMAIL="com.example.myapplication.EXTRA_EMAIL";
    public static final String EXTRA_NAME="com.example.myapplication.EXTRA_NAME";
    public static final String EXTRA_APPOINTEMENT="com.example.myapplication.EXTRA_APPOINTEMENT";
    public static final String EXTRA_TIMESLOT="com.example.myapplication.EXTRA_TIMESLOT";
    public static final String EXTRA_DNAME="com.example.myapplication.EXTRA_DNAME";
    public static final String EXTRA_DSPECS="com.example.myapplication.EXTRA_DSPECS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlook);
        e1=(TextView)findViewById(R.id.e1);
        e2=(TextView)findViewById(R.id.e2);
        e3=(TextView)findViewById(R.id.e3);
        e4=(TextView)findViewById(R.id.e4);
        e5=(TextView)findViewById(R.id.e5);
        e6=(TextView)findViewById(R.id.e6);
        e7=(TextView)findViewById(R.id.e7);
        e8=(TextView)findViewById(R.id.e8);
        e9=(TextView)findViewById(R.id.e9);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        final Intent intent=getIntent();
        e1.setText(intent.getStringExtra(checkbook.EEXTRA_NAME));
        e2.setText(intent.getStringExtra(checkbook.EEXTRA_CONTACT));
        e3.setText(intent.getStringExtra(checkbook.EEXTRA_AGE));
        e4.setText(intent.getStringExtra(checkbook.EEXTRA_DIESEASE));
        e5.setText(intent.getStringExtra(checkbook.EEXTRA_APPOINTEMENT));
        e6.setText(intent.getStringExtra(checkbook.EEXTRA_TIMESLOT));
        e7.setText(intent.getStringExtra(checkbook.EEXTRA_DNAME));
        e8.setText(intent.getStringExtra(checkbook.EEXTRA_DSPECS));
        String a=intent.getStringExtra(checkbook.EEXTRA_STAT);
        if(a.equals("2131165305"))
        {
            e9.setText("STATUS:- ACCEPTED");
        }
        if(a.equals("2131165306"))
        {
            e9.setText("STATUS:- REJECTED");
        }
        if(a.equals("2131165312"))
        {
            e9.setText("STATUS:- PENDING");
        }
        final String x=intent.getStringExtra(checkbook.EEXTRA_POS);
        final String y=intent.getStringExtra(checkbook.EEXTRA_EMAIL);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference=mfirestore.document(x);
                documentReference.update("accept",R.drawable.ic_check);
                documentReference.update("reason","STATUS :-  ACCEPTED");
                sendmail s=new sendmail(adminlook.this,y,"SMS HOSPITAL MANAGEMENT: APPOINTMENT ACCEPTED",intent.getStringExtra(checkbook.EEXTRA_NAME)+" your appointment has been accepted\nAPPOINTMENT DATE:- "+intent.getStringExtra(checkbook.EEXTRA_APPOINTEMENT)+"\nTIME SLOT:- "+intent.getStringExtra(checkbook.EEXTRA_TIMESLOT)+"\nDOCTOR'S NAME:- "+intent.getStringExtra(checkbook.EEXTRA_DNAME)+"\nSPECIALIZATION:- "+intent.getStringExtra(checkbook.EEXTRA_DSPECS)+"\nTHANK YOU");
                s.execute();
                finish();
                startActivity(new Intent(adminlook.this,acc.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(adminlook.this,reject.class);
                intent1.putExtra(EXTRA_POS,x);
                intent1.putExtra(EXTRA_EMAIL,y);
                intent1.putExtra(EXTRA_NAME,intent.getStringExtra(checkbook.EEXTRA_NAME));
                intent1.putExtra(EXTRA_APPOINTEMENT,intent.getStringExtra(checkbook.EEXTRA_APPOINTEMENT));
                intent1.putExtra(EXTRA_TIMESLOT,intent.getStringExtra(checkbook.EEXTRA_TIMESLOT));
                intent1.putExtra(EXTRA_DNAME,intent.getStringExtra(checkbook.EEXTRA_DNAME));
                intent1.putExtra(EXTRA_DSPECS,intent.getStringExtra(checkbook.EEXTRA_DSPECS));
                finish();
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(adminlook.this,checkbook.class));
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
                startActivity(new Intent(adminlook.this,checkbook.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
}
