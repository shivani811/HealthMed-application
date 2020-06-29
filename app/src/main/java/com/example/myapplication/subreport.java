package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class subreport extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST=1;
    private EditText et1,et2,et3;
    private Button btn1,btn2;
    private TextView tv;
    private ProgressBar pb;
    private Uri imageuri;
    private String fname;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private DatabaseReference mdatabaseref;
    private StorageTask uptask;
    public static final String TAG="TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subreport);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        tv=(TextView)findViewById(R.id.tv);
        pb=(ProgressBar)findViewById(R.id.pb);
        firebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("reports");
        mdatabaseref= FirebaseDatabase.getInstance().getReference("reports");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().trim().isEmpty() || et3.length()!=10)
                {
                    Toast.makeText(subreport.this,"PLEASE ENTER PROPER DETAILS",Toast.LENGTH_SHORT).show();
                }else {

                        uploadfile();
                }

            }
        });
    }
    private void openFileChooser(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageuri=data.getData();
            fname=data.getData().getLastPathSegment();
            tv.setText(fname);
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
                startActivity(new Intent(subreport.this,nav_home.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    private String getfileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadfile(){
        if(imageuri!=null){
            final StorageReference fileref=storageReference.child(System.currentTimeMillis()+"."+getfileExtension(imageuri));
           uptask=fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(0);
                        }
                    },500);
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(subreport.this,"UPLOAD SUCCESSFULL",Toast.LENGTH_SHORT).show();
                            String m;
                            if(et2.getText().toString().trim().isEmpty()){
                                m="NO MESSAGE";
                            }else{
                                m=et2.getText().toString();
                            }
                            firebaseAuth=FirebaseAuth.getInstance();
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            upload up=new upload(et1.getText().toString().trim(),uri.toString(),m,user.getUid(),fname,et3.getText().toString(),hospital_view.HOSPITAL);
                            String uploadid=mdatabaseref.push().getKey();
                            mdatabaseref.child(uploadid).setValue(up);
                            finish();
                            startActivity(new Intent(subreport.this,up.class));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(subreport.this,"ERROR UPLOADING, TRY AGAIN",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    pb.setProgress((int)progress);
                }
            });

        }else
        {
            Toast.makeText(subreport.this,"NO FILE SELECTED",Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(subreport.this,nav_home.class));
    }
}
