package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

public class contactus extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore mfirestore = FirebaseFirestore.getInstance();
    private CollectionReference ref = mfirestore.collection("profile");
    private long backpressedtime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("HealthMed");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null){
            navigationView.setCheckedItem(R.id.nav_contact);
        }
        firebaseAuth=FirebaseAuth.getInstance();
        updateheader();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
                startActivity(new Intent(contactus.this,nav_home.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
            startActivity(new Intent(contactus.this,nav_home.class));
        } else if (id == R.id.nav_profile) {
            finish();
            startActivity(new Intent(contactus.this,profile.class));
        }
        else if (id == R.id.nav_logout) {
            firebaseAuth= FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(contactus.this, MainActivity.class));

        } else if (id == R.id.nav_contact) {

        }else if (id == R.id.nav_refresh) {
            updateheader();
        }else if (id==R.id.nav_about){
            finish();
            startActivity(new Intent(contactus.this,Aboutus.class));
        }
        else if (id == R.id.nav_hos) {
            finish();
            startActivity(new Intent(contactus.this,hospital_view.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void updateheader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview=navigationView.getHeaderView(0);
        final TextView headtv=headerview.findViewById(R.id.headtv);
        TextView heademail=headerview.findViewById(R.id.heademail);
        ImageView iv=headerview.findViewById(R.id.imageView);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        headtv.setText(user.getDisplayName());
        Picasso.with(this).load(user.getPhotoUrl()).fit().placeholder(R.drawable.ww).into(iv);
        heademail.setText(user.getEmail());
    }
}
