package com.example.myapplication;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class adddoc extends AppCompatActivity{
    private EditText e1,e2,e3,e4,e5,e6,e7;
    private Button btn;
    private FirebaseFirestore mfirestore= FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddoc);
        e1=(EditText)findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        e3=(EditText)findViewById(R.id.e3);
        e4=(EditText)findViewById(R.id.e4);
        e5=(EditText)findViewById(R.id.e5);
        e6=(EditText)findViewById(R.id.e6);
        e7=(EditText)findViewById(R.id.e7);
        btn=(Button)findViewById(R.id.btn);
        e6.setFocusable(false);
        e6.setClickable(true);
        e7.setFocusable(false);
        e7.setClickable(true);
        progressDialog=new ProgressDialog(this);
        e6.setOnClickListener(new View.OnClickListener() {
            Calendar c=Calendar.getInstance();
            int hour=c.get(Calendar.HOUR_OF_DAY);
            int min=c.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(adddoc.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        e6.setText(hourOfDay+":"+minute);
                    }
                },hour,min, DateFormat.is24HourFormat(adddoc.this));
                timePickerDialog.show();
            }
        });
        e7.setOnClickListener(new View.OnClickListener() {
            Calendar c=Calendar.getInstance();
            int hour=c.get(Calendar.HOUR_OF_DAY);
            int min=c.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(adddoc.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        e7.setText(hourOfDay+":"+minute);
                    }
                },hour,min, DateFormat.is24HourFormat(adddoc.this));
                timePickerDialog.show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    progressDialog.setMessage("PLEASE WAIT");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    String t=e6.getText().toString()+" TO "+e7.getText().toString();
                    doc d=new doc(e1.getText().toString(),e3.getText().toString(),e2.getText().toString(),e4.getText().toString(),e5.getText().toString(),adminhome.ADMIN_HOSPITAL,t);
                    mfirestore.collection("doctors").add(d).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.dismiss();
                            Toast.makeText(adddoc.this, "ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(adddoc.this, "ERROR, TRY AGAIN", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
                startActivity(new Intent(adddoc.this,adminhome.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    private Boolean validate(){
        Boolean result=false;
        if(e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty() || e3.getText().toString().isEmpty() || e4.getText().toString().isEmpty() || e5.getText().toString().isEmpty() || e6.getText().toString().isEmpty() || e7.getText().toString().isEmpty())
        {
            Toast.makeText(adddoc.this,"PLEASE ENTER ALL DETAILS",Toast.LENGTH_SHORT).show();
        }
        else {
            if (e2.length() != 10) {
                Toast.makeText(adddoc.this, "CONTACT INFO MUST BE 10 DIGITS", Toast.LENGTH_SHORT).show();
            } else
                result = true;
        }

        return result;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(adddoc.this,adminhome.class));
    }

}
