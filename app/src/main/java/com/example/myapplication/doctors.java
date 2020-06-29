package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class doctors extends AppCompatActivity {
    private FirebaseFirestore mfirestore=FirebaseFirestore.getInstance();
    private CollectionReference ref=mfirestore.collection("doctors");
    private doctoradapter adapter;
    public static final String EEEXTRA_NAME="com.example.myapplication.EXTRA_NAME";
    public static final String EEEXTRA_AGE="com.example.myapplication.EXTRA_AGE";
    public static final String EEEXTRA_CONTACT="com.example.myapplication.EXTRA_CONTACT";
    public static final String EEEXTRA_SPECIALIZATION="com.example.myapplication.EXTRA_SPECIALIZATION";
    public static final String EEEXTRA_EXP="com.example.myapplication.EXTRA_EXP";
    public static final String EEEXTRA_TIME="com.example.myapplication.EXTRA_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        Query query=ref.whereEqualTo("hospital",hospital_view.HOSPITAL);
        FirestoreRecyclerOptions<doc> options=new FirestoreRecyclerOptions.Builder<doc>().setQuery(query,doc.class).build();
        adapter=new doctoradapter(options);
        RecyclerView recyclerView=findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setonitemclicklistner(new doctoradapter.ondocclicklistner() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                doc d=documentSnapshot.toObject(doc.class);
                Intent intent=new Intent(doctors.this,doclook.class);
                intent.putExtra(EEEXTRA_NAME,d.getDname());
                intent.putExtra(EEEXTRA_AGE,d.getDage());
                intent.putExtra(EEEXTRA_CONTACT,d.getDcontact());
                intent.putExtra(EEEXTRA_SPECIALIZATION,d.getDspec());
                intent.putExtra(EEEXTRA_EXP,d.getDexp());
                intent.putExtra(EEEXTRA_TIME,d.getTime());
                finish();
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
                startActivity(new Intent(doctors.this,nav_home.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(doctors.this,nav_home.class));
    }
}
