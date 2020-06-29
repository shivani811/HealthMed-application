package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.Nullable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Aboutus extends AppCompatActivity{

        private long backpressedtime;
        private Toast backToast;


        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

    }
        @Override
        public void onBackPressed () {
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
        public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.backmenu, menu);
        return true;
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.backmenu: {
                finish();
                startActivity(new Intent(Aboutus.this, nav_home.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }




    }




