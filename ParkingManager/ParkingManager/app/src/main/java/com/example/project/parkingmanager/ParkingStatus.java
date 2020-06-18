package com.example.project.parkingmanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class ParkingStatus extends AppCompatActivity {

    ParkingDbHelper mDbHelper;

    Cursor data;

    Button btns[] = new Button[55];
    int isEmpty[] = new int[55];

    public void park(View view) {
        Button tmp = (Button)view;
        String str = tmp.getText().toString();
        int flg = 0;
        for(int i=0;i<str.length();++i)
        {
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                flg *= 10;
                flg += str.charAt(i) - '0';
            }
        }
        Intent park_intent = new Intent(this,ScanCarNumber.class);
        //Toast.makeText(ParkingStatus.this, tmp.getText().toString(), Toast.LENGTH_LONG).show();
        park_intent.setFlags(flg);
        startActivity(park_intent);
    }

    public void open_details(View view){

        Button tmp = (Button) view;
        String str = tmp.getText().toString();
        int flg = 0;
        for(int i=0;i<str.length();++i)
        {
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                flg *= 10;
                flg += str.charAt(i) - '0';
            }
        }

        data = mDbHelper.fetchCarInLot(flg);

        int id = 1;
        int carId = 0;
        if(data.getCount() != 0) {
            carId = data.getColumnIndex("ID");
        }
        if(data.getCount() != 0) {
            data.moveToFirst();
            do{
                id = Integer.parseInt(data.getString(carId));
            }
            while(data.moveToNext());
        }

        Intent details_intent = new Intent(this,CarDetails.class);
        details_intent.setFlags(id);
        startActivity(details_intent);
    }

    public void intBtns()
    {
        btns[1] = (Button)findViewById(R.id.lot1);
        btns[2] = (Button)findViewById(R.id.lot2);
        btns[3] = (Button)findViewById(R.id.lot3);
        btns[4] = (Button)findViewById(R.id.lot4);
        btns[5] = (Button)findViewById(R.id.lot5);
        btns[6] = (Button)findViewById(R.id.lot6);
        btns[7] = (Button)findViewById(R.id.lot7);
        btns[8] = (Button)findViewById(R.id.lot8);
        btns[9] = (Button)findViewById(R.id.lot9);
        btns[10] = (Button)findViewById(R.id.lot10);
        btns[11] = (Button)findViewById(R.id.lot11);
        btns[12] = (Button)findViewById(R.id.lot12);
        btns[13] = (Button)findViewById(R.id.lot13);
        btns[14] = (Button)findViewById(R.id.lot14);
        btns[15] = (Button)findViewById(R.id.lot15);
        btns[16] = (Button)findViewById(R.id.lot16);
        btns[17] = (Button)findViewById(R.id.lot17);
        btns[18] = (Button)findViewById(R.id.lot18);
        btns[19] = (Button)findViewById(R.id.lot19);
        btns[20] = (Button)findViewById(R.id.lot20);
        btns[21] = (Button)findViewById(R.id.lot21);
        btns[22] = (Button)findViewById(R.id.lot22);
        btns[23] = (Button)findViewById(R.id.lot23);
        btns[24] = (Button)findViewById(R.id.lot24);
        btns[25] = (Button)findViewById(R.id.lot25);
        btns[26] = (Button)findViewById(R.id.lot26);
        btns[27] = (Button)findViewById(R.id.lot27);
        btns[28] = (Button)findViewById(R.id.lot28);
        btns[29] = (Button)findViewById(R.id.lot29);
        btns[30] = (Button)findViewById(R.id.lot30);
        btns[31] = (Button)findViewById(R.id.lot31);
        btns[32] = (Button)findViewById(R.id.lot32);
        btns[33] = (Button)findViewById(R.id.lot33);
        btns[34] = (Button)findViewById(R.id.lot34);
        btns[35] = (Button)findViewById(R.id.lot35);
        btns[36] = (Button)findViewById(R.id.lot36);
        btns[37] = (Button)findViewById(R.id.lot37);
        btns[38] = (Button)findViewById(R.id.lot38);
        btns[39] = (Button)findViewById(R.id.lot39);
        btns[40] = (Button)findViewById(R.id.lot40);
        btns[41] = (Button)findViewById(R.id.lot41);
        btns[42] = (Button)findViewById(R.id.lot42);
        btns[43] = (Button)findViewById(R.id.lot43);
        btns[44] = (Button)findViewById(R.id.lot44);
        btns[45] = (Button)findViewById(R.id.lot45);
        btns[46] = (Button)findViewById(R.id.lot46);
        btns[47] = (Button)findViewById(R.id.lot47);
        btns[48] = (Button)findViewById(R.id.lot48);
        btns[49] = (Button)findViewById(R.id.lot49);
        btns[50] = (Button)findViewById(R.id.lot50);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_status);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Parking Status");

        intBtns();

        mDbHelper = new ParkingDbHelper(this);
        mDbHelper.open();

        fillData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillData();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public void fillData()
    {
        data = mDbHelper.fetchAllCars();
        int lotNumber = 0;
        for(int i=1;i<=50;++i)
            isEmpty[i] = 1;
        if(data.getCount() != 0) {
            lotNumber = data.getColumnIndex("parkingLot");
        }
        if(data.getCount() != 0){
            data.moveToFirst();
            do{
                isEmpty[Integer.parseInt(data.getString(lotNumber))] = 0;
            }
            while(data.moveToNext());
        }
        for(int i=1;i<=50;++i) {
            if (isEmpty[i] == 1)
            {
                btns[i].setBackgroundResource(R.drawable.empty_lot);
            }
        }
        for(int i=1;i<=50;++i)
        {
            if(isEmpty[i] == 1)
                btns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        park(v);
                    }
                });
            else
                btns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        open_details(v);
                    }
                });
        }
    }
}
