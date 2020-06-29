package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class hospital_view extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore mfirestore;
    private hospital_adapterr adapter;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progresscircle;
    private List<hosp> muploads;
    public static String HOSPITAL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_view);
        progresscircle=findViewById(R.id.progress_circle);
        recyclerView = findViewById(R.id.rv);
        recyclerView=findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        progresscircle=findViewById(R.id.progress_circle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        muploads=new ArrayList<>();
        mfirestore = FirebaseFirestore.getInstance();
        CollectionReference ref = mfirestore.collection("hospital");
        ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot d:queryDocumentSnapshots){
                    hosp h=d.toObject(hosp.class);
                    muploads.add(h);
                }
                adapter=new hospital_adapterr(recyclerView,hospital_view.this,muploads);
                recyclerView.setAdapter(adapter);
                progresscircle.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(hospital_view.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                progresscircle.setVisibility(View.INVISIBLE);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backmenu: {
                finish();
                startActivity(new Intent(hospital_view.this, nav_home.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(hospital_view.this, nav_home.class));
    }
}
