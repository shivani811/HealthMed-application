package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class mybook extends AppCompatActivity {
    public static final String EXTRA_NAME="com.example.myapplication.EXTRA_NAME";
    public static final String EXTRA_AGE="com.example.myapplication.EXTRA_AGE";
    public static final String EXTRA_CONTACT="com.example.myapplication.EXTRA_CONTACT";
    public static final String EXTRA_APPOINTEMENT="com.example.myapplication.EXTRA_APPOINTEMENT";
    public static final String EXTRA_TIMESLOT="com.example.myapplication.EXTRA_TIMESLOT";
    public static final String EXTRA_DIESEASE="com.example.myapplication.EXTRA_DIESEASE";
    public static final String EXTRA_REASON="com.example.myapplication.EXTRA_REASON";
    public static final String EXTRA_PATH="com.example.myapplication.EXTRA_PATH";
    public static final String EXTRA_DDNAME="com.example.myapplication.EXTRA_DNAME";
    public static final String EXTRA_HOSPITAL="com.example.myapplication.EXTRA_HOSPITAL";
    private FirebaseFirestore mfirestore=FirebaseFirestore.getInstance();
    private CollectionReference ref=mfirestore.collection("booking");
    private mybookadapter adapter;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybook);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        Query query=ref.whereEqualTo("uid",user.getUid());
        FirestoreRecyclerOptions<bookss> options=new FirestoreRecyclerOptions.Builder<bookss>().setQuery(query,bookss.class).build();
        adapter=new mybookadapter(options);
        RecyclerView recyclerView=findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setonitemclicklistner(new mybookadapter.onitemclicklistner() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                bookss b=documentSnapshot.toObject(bookss.class);
                String path=documentSnapshot.getReference().getPath();
                Intent intent=new Intent(mybook.this,look.class);
                intent.putExtra(EXTRA_NAME,b.getName());
                intent.putExtra(EXTRA_AGE,b.getAge());
                intent.putExtra(EXTRA_CONTACT,b.getContact());
                intent.putExtra(EXTRA_DIESEASE,b.getDiesease());
                intent.putExtra(EXTRA_APPOINTEMENT,b.getDate());
                intent.putExtra(EXTRA_TIMESLOT,b.getSlot());
                intent.putExtra(EXTRA_REASON,b.getReason());
                intent.putExtra(EXTRA_PATH,path);
                intent.putExtra(EXTRA_DDNAME,b.getDocname());
                intent.putExtra(EXTRA_HOSPITAL,b.getHospital());
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
                startActivity(new Intent(mybook.this,nav_home.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(mybook.this,nav_home.class));
    }
}
