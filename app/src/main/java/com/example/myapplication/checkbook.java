package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class checkbook extends AppCompatActivity {
    public static final String EEXTRA_NAME="com.example.myapplication.EXTRA_NAME";
    public static final String EEXTRA_AGE="com.example.myapplication.EXTRA_AGE";
    public static final String EEXTRA_CONTACT="com.example.myapplication.EXTRA_CONTACT";
    public static final String EEXTRA_APPOINTEMENT="com.example.myapplication.EXTRA_APPOINTEMENT";
    public static final String EEXTRA_TIMESLOT="com.example.myapplication.EXTRA_TIMESLOT";
    public static final String EEXTRA_DIESEASE="com.example.myapplication.EXTRA_DIESEASE";
    public static final String EEXTRA_POS="com.example.myapplication.EXTRA_POS";
    public static final String EEXTRA_EMAIL="com.example.myapplication.EXTRA_EMAIL";
    public static final String EEXTRA_DNAME="com.example.myapplication.EXTRA_DNAME";
    public static final String EEXTRA_DSPECS="com.example.myapplication.EXTRA_DSPECS";
    public static final String EEXTRA_STAT="com.example.myapplication.EXTRA_STAT";
    private FirebaseFirestore mfirestore=FirebaseFirestore.getInstance();
    private CollectionReference ref=mfirestore.collection("booking");
    private bookadapter adapter;
    private FirebaseAuth firebaseAuth;
    private String r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbook);
        firebaseAuth=FirebaseAuth.getInstance();
        Query query=ref.whereEqualTo("hospital",adminhome.ADMIN_HOSPITAL).orderBy("priority",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<bookss> options=new FirestoreRecyclerOptions.Builder<bookss>().setQuery(query,bookss.class).build();
        adapter=new bookadapter(options);
        RecyclerView recyclerView=findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setonitemclicklistner(new bookadapter.onnameclicklistner() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                bookss b=documentSnapshot.toObject(bookss.class);
                String pos=documentSnapshot.getReference().getPath();
                Intent intent=new Intent(checkbook.this,adminlook.class);
                intent.putExtra(EEXTRA_NAME,b.getName());
                intent.putExtra(EEXTRA_AGE,b.getAge());
                intent.putExtra(EEXTRA_CONTACT,b.getContact());
                intent.putExtra(EEXTRA_DIESEASE,b.getDiesease());
                intent.putExtra(EEXTRA_APPOINTEMENT,b.getDate());
                intent.putExtra(EEXTRA_TIMESLOT,b.getSlot());
                intent.putExtra(EEXTRA_POS,pos);
                intent.putExtra(EEXTRA_EMAIL,b.getEmail());
                intent.putExtra(EEXTRA_DNAME,b.getDocname());
                intent.putExtra(EEXTRA_DSPECS,b.getDocspecs());
                intent.putExtra(EEXTRA_STAT,String.valueOf(b.getAccept()));
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
                startActivity(new Intent(checkbook.this,adminhome.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(checkbook.this,adminhome.class));
    }
}
