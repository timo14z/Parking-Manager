package com.example.project.parkingmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public void open_status() {
        Intent status_intent = new Intent(this,ParkingStatus.class);
        startActivity(status_intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Button status_btn = (Button)findViewById(R.id.status_btn);

        status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_status();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
        super.onBackPressed();
    }
}
