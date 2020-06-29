package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class book extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private FirebaseFirestore mfirestore= FirebaseFirestore.getInstance();
    private Button sub;
    private EditText et1,et2,et3,et4,et5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private CollectionReference ref=mfirestore.collection("profile");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        progressDialog=new ProgressDialog(this);
        sub = (Button) findViewById(R.id.sub);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et4.setFocusable(false);
        et4.setClickable(true);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getInstance().getCurrentUser();
        ref.whereEqualTo("uid",user.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots ){
                    prof f=documentSnapshot.toObject(prof.class);
                    et1.setText(f.getName());
                    et5.setText(f.getContact());
                    et2.setText(f.getAge());
                }
            }
        });
        et4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker=new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"DATE PICKER");
            }
        });

sub.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (validate()){
            progressDialog.setMessage("PLEASE WAIT");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        String pat = et1.getText().toString();
        String ag = et2.getText().toString();
        String dis = et3.getText().toString();
        String dat = et4.getText().toString();
        String contact = et5.getText().toString();
        Intent intent=getIntent();
        FirebaseUser user=firebaseAuth.getInstance().getCurrentUser();
        String id=user.getUid();
        String slot=intent.getStringExtra(doclook.XTRA_TIME);
        bookss b=new bookss(ag,contact,pat,dat,slot,dis,System.currentTimeMillis(),id,R.drawable.ic_priority,"STATUS:-  YOUR REQUEST HAS NOT YET BEEN ATTENDED TO",user.getEmail(),intent.getStringExtra(doclook.XTRA_NAME),intent.getStringExtra(doclook.XTRA_SPECIALIZATION),hospital_view.HOSPITAL);
        final sendmail s=new sendmail(book.this,user.getEmail(),"SMS HOSPITAL MANAGEMENT: DETAILS OF YOUR APPOINTMENT","PATIENT NAME:- "+pat+"\nDOCTORS NAME:- "+intent.getStringExtra(doclook.XTRA_NAME)+"\nDOCTOR'S SPECIALIZATION:- "+intent.getStringExtra(doclook.XTRA_SPECIALIZATION)+"\nAPPOINTMENT DATE:- "+dat+"\nTIME SLOT:- "+slot);
        mfirestore.collection("booking").add(b).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                s.execute();
                progressDialog.dismiss();
                Toast.makeText(book.this, "SUCCESSFULLY UPADADTED", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(book.this, thanku.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(book.this, "ERROR, TRY AGAIN", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
});
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String curdate= DateFormat.getDateInstance().format(c.getTime());
        et4.setText(curdate);
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
                startActivity(new Intent(book.this,doctors.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    private Boolean validate(){
        Boolean result=false;
        if(et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty() || et4.getText().toString().isEmpty() || et5.getText().toString().isEmpty())
        {
            Toast.makeText(book.this,"PLEASE ENTER ALL DETAILS",Toast.LENGTH_SHORT).show();
        }
        else {
            if (et5.length() != 10) {
                Toast.makeText(book.this, "CONTACT INFO MUST BE 10 DIGITS", Toast.LENGTH_SHORT).show();
            } else
                result = true;
        }

        return result;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(book.this,doctors.class));
    }

}