package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class admindoclook extends AppCompatActivity {
    private TextView e1,e2,e3,e4,e5,e6;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindoclook);
        e1 = (TextView) findViewById(R.id.e1);
        e2 = (TextView) findViewById(R.id.e2);
        e3 = (TextView) findViewById(R.id.e3);
        e4 = (TextView) findViewById(R.id.e4);
        e5 = (TextView) findViewById(R.id.e5);
        e6 = (TextView) findViewById(R.id.e6);
        btn = (Button) findViewById(R.id.btn);
        final Intent intent = getIntent();
        e1.setText(intent.getStringExtra(admindoctors.EEEXTRA_NAME));
        e2.setText(intent.getStringExtra(admindoctors.EEEXTRA_CONTACT));
        e3.setText(intent.getStringExtra(admindoctors.EEEXTRA_AGE));
        e4.setText(intent.getStringExtra(admindoctors.EEEXTRA_SPECIALIZATION));
        e5.setText(intent.getStringExtra(admindoctors.EEEXTRA_EXP) + " yrs");
        e6.setText(intent.getStringExtra(admindoctors.EXTRA_TIME));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(admindoclook.this,admindoctors.class);
                finish();
                startActivity(intent1);
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
                startActivity(new Intent(admindoclook.this,admindoctors.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(admindoclook.this,admindoctors.class));
    }
}
