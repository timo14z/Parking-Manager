package com.example.project.parkingmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Parking extends AppCompatActivity {

    ParkingDbHelper mDbHelper;

    Button parkCar_btn;
    Button scanAgain_btn;
    TextView setStartDate_txt;
    TextView setStartTime_txt;
    TextView carNumber_txt;
    TextView ticketNumber_txt;
    TextView parkLot;
    EditText phoneNumber_txt;

    Calendar calendar;

    ArrayAdapter<String> adapter;

    public void parkCar() {
            Intent parkCar_intent = new Intent(getApplicationContext(), ParkingStatus.class);
            startActivity(parkCar_intent);
    }

    public void scanAgain() {
        Intent park_intent = new Intent(this,ScanCarNumber.class);
        startActivity(park_intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Parking");
        mDbHelper = new ParkingDbHelper(this);
        mDbHelper.open();

        parkCar_btn = (Button)findViewById(R.id.park_btn);
        scanAgain_btn = (Button)findViewById(R.id.scan_btn);
        setStartDate_txt = (TextView)findViewById(R.id.start_date_set_txt);
        setStartTime_txt = (TextView)findViewById(R.id.start_time_set_txt);
        carNumber_txt = (TextView)findViewById(R.id.car_scn_num);
        ticketNumber_txt = (TextView)findViewById(R.id.ticket_Text);
        phoneNumber_txt = (EditText) findViewById(R.id.phone_editText);
        parkLot = (TextView) findViewById(R.id.lot_number);

        int t = getIntent().getFlags();
        parkLot.setText(Integer.toString(t));

        carNumber_txt.setText(ScanCarNumber.scannedNumber.getText());

        calendar = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = df.format(calendar.getTime());
        setStartDate_txt.setText(formattedDate);

        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        String formattedTime = tf.format(calendar.getTime());
        setStartTime_txt.setText(formattedTime);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE-ddMMyymmss");
        Date d = new Date();
        String ticketNum = sdf.format(d);

        ticketNumber_txt.setText(ticketNum);

        parkCar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((phoneNumber_txt.getText().toString().length() > 0) &&
                        (carNumber_txt.getText().toString().length() > 0)) {
                    parkCar();
                    setResult(RESULT_OK);

                    String carNumber = carNumber_txt.getText().toString();
                    String phoneNumber = phoneNumber_txt.getText().toString();
                    String ticketNumber = ticketNumber_txt.getText().toString();
                    String parkingLot = parkLot.getText().toString();
                    String startDate = setStartDate_txt.getText().toString();
                    String startTime = setStartTime_txt.getText().toString();

                    long id = mDbHelper.parkCar(carNumber, phoneNumber, ticketNumber, parkingLot, startDate, startTime);

                    if (id != -1)
                        Toast.makeText(Parking.this, "Car Successfully Parked!", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                }
                else
                {
                    Toast.makeText(Parking.this, "Insert all the details !!", Toast.LENGTH_LONG).show();
                }
            }
        });


        scanAgain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanAgain();
            }
        });

    }

    @Override
    protected void onResume() {
            super.onResume();
    }

}


