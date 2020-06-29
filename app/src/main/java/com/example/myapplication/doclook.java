package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class doclook extends AppCompatActivity {
    private TextView e1,e2,e3,e4,e5,e6;
    private Button btn;
    public static final String XTRA_NAME="com.example.myapplication.EXTRA_NAME";
    public static final String XTRA_SPECIALIZATION="com.example.myapplication.EXTRA_SPECIALIZATION";
    public static final String XTRA_TIME="com.example.myapplication.EXTRA_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doclook);
        e1 = (TextView) findViewById(R.id.e1);
        e2 = (TextView) findViewById(R.id.e2);
        e3 = (TextView) findViewById(R.id.e3);
        e4 = (TextView) findViewById(R.id.e4);
        e5 = (TextView) findViewById(R.id.e5);
        e6 = (TextView) findViewById(R.id.e6);
        btn = (Button) findViewById(R.id.btn);
        final Intent intent = getIntent();
        e1.setText(intent.getStringExtra(doctors.EEEXTRA_NAME));
        e2.setText(intent.getStringExtra(doctors.EEEXTRA_CONTACT));
        e3.setText(intent.getStringExtra(doctors.EEEXTRA_AGE));
        e4.setText(intent.getStringExtra(doctors.EEEXTRA_SPECIALIZATION));
        e5.setText(intent.getStringExtra(doctors.EEEXTRA_EXP) + " yrs");
        e6.setText(intent.getStringExtra(doctors.EEEXTRA_TIME));
        btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(doclook.this, book.class);
                    intent1.putExtra(XTRA_NAME, intent.getStringExtra(doctors.EEEXTRA_NAME));
                    intent1.putExtra(XTRA_SPECIALIZATION, intent.getStringExtra(doctors.EEEXTRA_SPECIALIZATION));
                    intent1.putExtra(XTRA_TIME,intent.getStringExtra(doctors.EEEXTRA_TIME));
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
                startActivity(new Intent(doclook.this,doctors.class));

            }

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(doclook.this,doctors.class));
    }
}
