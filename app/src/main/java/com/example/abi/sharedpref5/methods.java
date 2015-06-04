package com.example.abi.sharedpref5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.CallLog;
import android.util.Log;

import java.util.Date;

/**
 * Created by Abi on 6/2/2015.
 */
public class methods  {

    public Dbforcall dbhelper;
    private SQLiteDatabase db;
    private Context my_context;
    public  static final StringBuilder sb = new StringBuilder();

    public methods(Context context)
    {
        this.my_context=context;
        // Dbforcall dbcall=new Dbforcall();
    }

    public void addLog() {

        dbhelper = new Dbforcall(my_context);
        Cursor cursor = null;
      ////  ContentResolver cr = getContentResolver();
       // cursor = cr.query(
             //   android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
             //   android.provider.CallLog.Calls.DATE + " DESC ");
      //  cursor=managedQuery(CallLog.Calls.CONTENT_URI,null,null,null,null);
        db = dbhelper.getWritableDatabase();

        db.delete(Dbforcall.TABLE_NAME, null, null);
        //startManagingCursor(cursor);
        int idno=0;
        int calltype = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int numberColumnId = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int durationId = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        int dateId = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);
        // int date=mc.getColumnIndex(CallLog.Calls.DATE);
       /* Date dt = new Date();
        int hours = dt.getHours();
        int minutes = dt.getMinutes();
        int seconds = dt.getSeconds();
        String currTime = hours + ":" + minutes + ":" + seconds;*/


        //ArrayList<String> callList = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String cType = cursor.getString(calltype);
                String callogstr = "";
                switch (Integer.parseInt(cType)) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        callogstr = "OUTGOING CALLS";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        callogstr = "INCOMING CALLS";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        callogstr = "MISSED CALLS";
                        break;
                }
                String contactNumber = cursor.getString(numberColumnId);
                String duration = cursor.getString(durationId);
                String ddd = cursor.getString(dateId);
                Date dd = new Date(Long.valueOf(ddd));
                // String callDate = DateFormat.getDateInstance().format(dateId);
                ContentValues values = new ContentValues();
                values.put(Dbforcall.ID, idno);
                values.put(Dbforcall.pnam, callogstr);
                values.put(Dbforcall.phno, contactNumber);
                values.put(Dbforcall.pdur, duration);
                values.put(Dbforcall.pdate, String.valueOf(dd));

                db.insert(Dbforcall.TABLE_NAME, null, values);
                idno++;
            } while (cursor.moveToNext());

        }

       // Toast.makeText(getBaseContext(), "Inserted!", Toast.LENGTH_LONG).show();
        cursor.close();
        Log.d("data inserted", "data inserted");
        dbhelper.close();
    }


    public void getlog() {
        dbhelper = new com.example.abi.sharedpref5.Dbforcall(my_context);
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

    }



}
