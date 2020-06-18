package com.example.project.parkingmanager;

import android.app.AlertDialog;
import android.app.IntentService;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CarDetails extends AppCompatActivity {

    ParkingDbHelper mDbHelper;

    Cursor data;

    Calendar calendar;

    private Long mRowId;

    TextView carNum;
    TextView phoneNum;
    TextView ticketNum;
    TextView parkingLot;
    TextView startDate;
    TextView startTime;
    Button costBtn;
    Button releaseBtn;
    CheckBox paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Car Details");

        mDbHelper = new ParkingDbHelper(this);
        mDbHelper.open();

        carNum = (TextView)findViewById(R.id.car_num);
        phoneNum = (TextView)findViewById(R.id.phone_num);
        ticketNum =(TextView)findViewById(R.id.ticket_num);
        parkingLot = (TextView)findViewById(R.id.lot_num);
        startDate = (TextView)findViewById(R.id.start_date);
        startTime = (TextView)findViewById(R.id.start_time);
        costBtn = (Button)findViewById(R.id.cost_btn);
        releaseBtn = (Button)findViewById(R.id.release_btn);
        paid = (CheckBox) findViewById(R.id.paid_yes_cb);

        mRowId = (long) getIntent().getFlags();

        populateFields();

        releaseBtn.setEnabled(false);

        paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    releaseBtn.setEnabled(true);
                    return;
                }
                releaseBtn.setEnabled(false);
            }
        });

        releaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbHelper.releaseCar(mRowId);
                finish();
            }
        });

        long diffInHours = 0,diffInDays = 0,diffInMinutes = 0;
        long cost = 0;

        try{
        calendar = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        String fromDateTime = data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.START_DATE)) + " " + data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.START_TIME));
        String toDateTime = df.format(calendar.getTime());

            Date st = df.parse(fromDateTime);
            Date en = df.parse(toDateTime);

            Calendar c1 = Calendar.getInstance(),c2 = Calendar.getInstance();
            c1.setTime(st);
            c2.setTime(en);

            long diffDateTime = c2.getTimeInMillis() - c1.getTimeInMillis();
            long tmp = diffDateTime;
            diffInDays = tmp / (1000 * 60 * 60 * 24);
            tmp -= diffInDays * (1000 * 60 * 60 * 24);
            diffInHours = tmp / (1000 * 60 * 60);
            tmp -= diffInHours * (1000 * 60 * 60);
            diffInMinutes = tmp / (1000 * 60);
            diffDateTime /= (1000 * 60);
            cost = diffDateTime * 5;
        }
        catch (Exception e)
        {

        }
        final Long dID = diffInDays,dIH = diffInHours,dIM = diffInMinutes,cst = cost;

    costBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = mDbHelper.fetchCar(mRowId);
                if (data.getCount() == 0) {
                    display("Error","There is No details");
                   return;
                }

                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("Car Number: " + data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.CAR_NUMBER)) + "\n \n");
                    buffer.append("Duration: " + String.valueOf(dID) + " Days" + "\n \t \t \t \t \t \t \t" +
                            String.valueOf(dIH) + " Hours" + "\n" + "\t \t \t \t \t \t \t"+
                            String.valueOf(dIM) + " Minutes" + "\n \n");
                    buffer.append("Cost: " + String.valueOf(cst) + " SYP" + "\n");

                    display("Car Details", buffer.toString());
                }
            }
        });

    }

    public void display(String title, String message){
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setCancelable(true);
        dlg.setTitle(title);
        dlg.setMessage(message);
        dlg.setPositiveButton("OK",null);
        dlg.show();
    }


    private void populateFields() {
        if (mRowId != null) {
            data = mDbHelper.fetchCar(mRowId);

            try {
                data.moveToFirst();
                carNum.setText(data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.CAR_NUMBER)));
                phoneNum.setText(data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.PHONE_NUMBER)));
                ticketNum.setText(data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.TICKET_NUMBER)));
                parkingLot.setText(data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.PARKING_LOT)));
                startDate.setText(data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.START_DATE)));
                startTime.setText(data.getString(data.getColumnIndexOrThrow(ParkingDbHelper.START_TIME)));
            }
            catch(Exception e)
            {
                Toast.makeText(CarDetails.this, Long.toString(mRowId), Toast.LENGTH_LONG).show();
            }
        }
    }

}
