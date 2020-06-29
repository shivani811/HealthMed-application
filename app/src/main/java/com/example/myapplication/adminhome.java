package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class adminhome extends AppCompatActivity {
    private Button btn1,btn2,btn3,btn4,btn5;
    private TextView e2;
    private FirebaseAuth firebaseAuth;
    private long backpressedtime;
    private Toast backToast;
    private FirebaseFirestore mfirestore = FirebaseFirestore.getInstance();
    private CollectionReference ref = mfirestore.collection("hospital");
    public static String ADMIN_HOSPITAL= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        e2=(TextView)findViewById(R.id.e2);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);
        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        ref.whereEqualTo("uid",user.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    hosp f = documentSnapshot.toObject(hosp.class);
                    ADMIN_HOSPITAL=f.getName();
                    e2.setText(ADMIN_HOSPITAL);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(adminhome.this,"MAKE SURE U R CONNECTED TO THE INTERNET",Toast.LENGTH_SHORT).show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(adminhome.this,checkbook.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(adminhome.this,checkreports.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(adminhome.this,checkfeeds.class));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(adminhome.this,adddoc.class));
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminhome.this,admindoctors.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(adminhome.this,MainActivity.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        if(backpressedtime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else{
            backToast= Toast.makeText(getBaseContext(),"PRESS BACK AGAIN TO EXIT",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpressedtime=System.currentTimeMillis();
    }
}
