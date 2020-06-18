package com.example.project.parkingmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rufaaaaa on 3/19/2017.
 */

public class ParkingDbHelper {


    public static final String ID_KEY = "ID";
    public static final String CAR_NUMBER = "carNumber";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String TICKET_NUMBER = "ticketNumber";
    public static final String PARKING_LOT = "parkingLot";
    public static final String START_DATE = "startDate";
    public static final String START_TIME = "startTime";

    private static final String TAG = "ParkingDbHelper";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "parkedCars";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + "(ID INTEGER PRIMARY KEY, carNumber TEXT, phoneNumber TEXT," +
            "ticketNumber TEXT, parkingLot TEXT, startDate Text, startTime TEXT);";

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    public ParkingDbHelper(Context ctx) {
        this.mCtx = ctx;
    }

    public ParkingDbHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public long parkCar (String carNumber, String phoneNumber, String ticketNumber, String parkingLot, String startDate, String startTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAR_NUMBER,carNumber);
        contentValues.put(PHONE_NUMBER,phoneNumber);
        contentValues.put(TICKET_NUMBER,ticketNumber);
        contentValues.put(PARKING_LOT,parkingLot);
        contentValues.put(START_DATE,startDate);
        contentValues.put(START_TIME,startTime);

        return mDb.insert(DATABASE_TABLE, null, contentValues);
    }

    public boolean releaseCar (long rowId) {
        return mDb.delete(DATABASE_TABLE, ID_KEY + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllCars() {
        return mDb.rawQuery("Select * FROM "+DATABASE_TABLE+";",null);
    }

    public Cursor fetchCar(long rowId) throws SQLException {
        return mDb.rawQuery("Select * FROM "+DATABASE_TABLE+" Where "+ID_KEY +"="+rowId+";",null);
    }

    public Cursor fetchCarInLot(long rowId) throws SQLException {
        return mDb.rawQuery("Select * FROM "+DATABASE_TABLE+" Where "+PARKING_LOT +"="+rowId+";",null);
    }

}
