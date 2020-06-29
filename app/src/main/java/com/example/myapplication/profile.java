package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore mfirestore = FirebaseFirestore.getInstance();
    private CollectionReference ref = mfirestore.collection("profile");
    private EditText e1, e2, e3;
    private ImageView iv;
    private TextView headtv;
    private Button btn;
    private long backpressedtime;
    private Toast backToast;
    private ProgressDialog progressDialog;
    private Boolean check = false;
    private String id;
    private static final int PICK_IMAGE_REQUESTT=2;
    private Uri imageuri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_profile);
        }
        firebaseAuth=FirebaseAuth.getInstance();
        updateheader();
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        e3 = (EditText) findViewById(R.id.e3);
        btn = (Button) findViewById(R.id.btn);
        iv=(ImageView)findViewById(R.id.iv);
        progressDialog = new ProgressDialog(this);
        storageReference= FirebaseStorage.getInstance().getReference().child("userpics");
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        Picasso.with(this).load(user.getPhotoUrl()).fit().placeholder(R.drawable.ww).into(iv);
        ref.whereEqualTo("uid", user.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    prof f = documentSnapshot.toObject(prof.class);
                    e1.setText(f.getName());
                    e2.setText(f.getContact());
                    e3.setText(f.getAge());
                    id = documentSnapshot.getId();
                    check=true;
                }
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE_REQUESTT);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check) {
                    if (e1.getText().toString().trim().isEmpty() || e2.getText().toString().trim().isEmpty() || e3.getText().toString().trim().isEmpty()) {
                        Toast.makeText(profile.this, "PLEASE ENTER ALL DETAILS", Toast.LENGTH_SHORT).show();
                    } else if (e2.length() != 10) {
                        Toast.makeText(profile.this, "CONTACT MUST BE 10 DIGITS", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.setMessage("PLEASE WAIT");
                        progressDialog.show();
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCancelable(false);
                        prof p = new prof(e1.getText().toString(), e3.getText().toString(), e2.getText().toString(), user.getUid());
                        mfirestore.collection("profile").add(p).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                if(imageuri!=null){
                                    final StorageReference imagefilepath=storageReference.child(imageuri.getLastPathSegment());
                                imagefilepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        imagefilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                UserProfileChangeRequest profileupdate=new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
                                                user.updateProfile(profileupdate);
                                            }
                                        });
                                    }
                                });}
                                UserProfileChangeRequest profileupdate=new UserProfileChangeRequest.Builder().setDisplayName(e1.getText().toString()).build();
                                user.updateProfile(profileupdate);
                                progressDialog.dismiss();
                                Toast.makeText(profile.this, "PROFILE RECORDED", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(profile.this,nav_home.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(profile.this, "ERROR, TRY AGAIN", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    if (e1.getText().toString().trim().isEmpty() || e2.getText().toString().trim().isEmpty() || e3.getText().toString().trim().isEmpty()) {
                        Toast.makeText(profile.this, "PLEASE ENTER ALL DETAILS", Toast.LENGTH_SHORT).show();
                    } else if (e2.length() != 10) {
                        Toast.makeText(profile.this, "CONTACT MUST BE 10 DIGITS", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.setMessage("PLEASE WAIT");
                        progressDialog.show();
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCancelable(false);
                        ref.document(id).update("name", e1.getText().toString());
                        ref.document(id).update("age", e3.getText().toString());
                        ref.document(id).update("contact", e2.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if(imageuri!=null){
                                    final StorageReference imagefilepath=storageReference.child(imageuri.getLastPathSegment());
                                imagefilepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        imagefilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                UserProfileChangeRequest profileupdate=new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
                                                user.updateProfile(profileupdate);
                                            }
                                        });
                                    }
                                });}
                                UserProfileChangeRequest profileupdate=new UserProfileChangeRequest.Builder().setDisplayName(e1.getText().toString()).build();
                                user.updateProfile(profileupdate);
                                progressDialog.dismiss();
                                Toast.makeText(profile.this, "PROFILE UPDATED", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(profile.this,nav_home.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(profile.this, "ERROR, TRY AGAIN", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }
        });
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
        getMenuInflater().inflate(R.menu.backmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backmenu: {
                finish();
                startActivity(new Intent(profile.this, nav_home.class));

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
            startActivity(new Intent(profile.this, nav_home.class));
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_contact) {
            finish();
            startActivity(new Intent(profile.this, contactus.class));
        }
        else if (id == R.id.nav_logout) {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(profile.this, MainActivity.class));

        }else if (id == R.id.nav_refresh) {
            updateheader();
        }else if (id==R.id.nav_about){
            finish();
            startActivity(new Intent(profile.this,Aboutus.class));
        }
        else if (id == R.id.nav_hos) {
            finish();
            startActivity(new Intent(profile.this,hospital_view.class));
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUESTT && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageuri=data.getData();
            Picasso.with(this).load(imageuri).fit().placeholder(R.drawable.ww).into(iv);
        }
    }
}
