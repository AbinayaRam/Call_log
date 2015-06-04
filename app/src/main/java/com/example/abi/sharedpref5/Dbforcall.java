package com.example.abi.sharedpref5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Dbforcall extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "logs.db";
    // private static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "calllogs";
    public static final String ID="IDNO";
    public static final String pnam="Type";
    public static final String phno="PhoneNo";
    public static final String pdur="Duration";
    public static final String pdate="Date";
    SQLiteDatabase db;
    // public static final StringBuilder sb=null;


    public static final String create_db=" CREATE TABLE " + Dbforcall.TABLE_NAME +" ( "+ID+ " INTEGER PRIMARY KEY, "+pnam+" TEXT , "+
            phno+ " TEXT , "+pdur+ " TEXT  , "+pdate+ " DATE)";

    //  public static final String create_indx="CREATE UNIQUE INDEX idx_something ON  (id_nick, name_nick, date_creation);"

    public Dbforcall(Context context){
        super(context, DATABASE_NAME, null, 2);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_db);
        //Toast.makeText(, "Data not retrived...!!!", Toast.LENGTH_LONG).show();
        Log.d("Table creation", "table created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+Dbforcall.TABLE_NAME);
        onCreate(db);
    }

 /*   public void getlog() {
     //   dbhelper = new Dbforcall(this);
        int idno;
        String type = null;
        String phn = null;
        String dur = null;
        String dates = null;
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM calllogs WHERE TYPE=\"OUTGOING CALLS\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                idno = cursor.getInt(0);
                type = cursor.getString(1);
                phn = cursor.getString(2);
                dur = cursor.getString(3);
                dates = cursor.getString(4);
                sb.append(idno).append("\n");
                sb.append(type).append("\n");
                sb.append("Phone number").append(phn).append("\n");
                sb.append("call duration").append(dur).append("\n").append("call date").append(dates).append("\n").append("\n").append("\n").append(System.getProperty("line.separator"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("data retrived", "data retrived");
        dbhelper.close();

    }*/






    // Toast.makeText(getBaseContext(), "Data retrieved...!!!", Toast.LENGTH_LONG).show();
    //cursor.close();
}



