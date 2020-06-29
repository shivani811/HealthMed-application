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
import com.google.firebase.firestore.FirebaseFirestore;

public class look extends AppCompatActivity {
    private TextView e1,e2,e3,e4,e5,e6,e7,e8,e9;
    private Button btn;
    private FirebaseFirestore mfirestore=FirebaseFirestore.getInstance();
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        e1=(TextView)findViewById(R.id.e1);
        e2=(TextView)findViewById(R.id.e2);
        e3=(TextView)findViewById(R.id.e3);
        e4=(TextView)findViewById(R.id.e4);
        e5=(TextView)findViewById(R.id.e5);
        e6=(TextView)findViewById(R.id.e6);
        e7=(TextView)findViewById(R.id.e7);
        e8=(TextView)findViewById(R.id.e8);
        e9=(TextView)findViewById(R.id.e9);
        btn=(Button)findViewById(R.id.btn);
        final Intent intent=getIntent();
        e1.setText(intent.getStringExtra(mybook.EXTRA_NAME));
        e2.setText(intent.getStringExtra(mybook.EXTRA_CONTACT));
        e3.setText(intent.getStringExtra(mybook.EXTRA_AGE));
        e4.setText(intent.getStringExtra(mybook.EXTRA_DIESEASE));
        e5.setText(intent.getStringExtra(mybook.EXTRA_APPOINTEMENT));
        e6.setText(intent.getStringExtra(mybook.EXTRA_TIMESLOT));
        e7.setText(intent.getStringExtra(mybook.EXTRA_REASON));
        e8.setText(intent.getStringExtra(mybook.EXTRA_DDNAME));
        e9.setText(intent.getStringExtra(mybook.EXTRA_HOSPITAL));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference=mfirestore.document(intent.getStringExtra(mybook.EXTRA_PATH));
                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(look.this,"APPOINTMENT CANCELLED",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(look.this,mybook.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(look.this,"ERROR, TRY AGAIN",Toast.LENGTH_SHORT).show();
                    }
                });
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
                startActivity(new Intent(look.this,mybook.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(look.this,mybook.class));
    }
}
