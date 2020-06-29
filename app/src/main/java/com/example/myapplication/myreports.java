package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myreports extends AppCompatActivity {
    private RecyclerView recyclerView;
    private image_adapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<upload> muploads;
    private ProgressBar progresscircle;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreports);
        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        recyclerView=findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        progresscircle=findViewById(R.id.progress_circle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        muploads=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("reports");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    upload up=post.getValue(upload.class);
                    if(user.getUid().equals(up.getUid()))
                    {
                        muploads.add(up);
                    }
                }
                imageAdapter=new image_adapter(recyclerView,myreports.this,muploads);
                recyclerView.setAdapter(imageAdapter);
                progresscircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(myreports.this,"ERROR, TRY AGAIN",Toast.LENGTH_SHORT).show();
                progresscircle.setVisibility(View.INVISIBLE);
            }
        });
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
                startActivity(new Intent(myreports.this,nav_home.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(myreports.this,nav_home.class));
    }
}
