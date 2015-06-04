package com.example.abi.sharedpref5;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;


public class calllog extends Activity {
    TextView callDetails,phno;
    ImageButton outgoingbtn , missedbtn,incomingbtn;
    ImageButton allcallsbtn;
    String phnofrmmain;

    public Dbforcall dbhelper;
    public SQLiteDatabase db;
    public Cursor c;
    int idno;
    String type = null;
    String phn = null;
    String dur = null;
    String dates = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calllog);

        phno=(TextView)findViewById(R.id.calllog);
        allcallsbtn=(ImageButton)findViewById(R.id.allcallsbtn);
        outgoingbtn=(ImageButton) findViewById(R.id.outgoingbtn);
        missedbtn=(ImageButton)findViewById(R.id.missedbtn);
        incomingbtn=(ImageButton)findViewById(R.id.incomingbtn);
        callDetails = (TextView) findViewById(R.id.callog);

        phnofrmmain=MainActivity.myphoneno;
        phno.setText(phnofrmmain);
        addLog();
        c=getallcalls();
        display(c);
    }

    public Cursor getallcalls() {
        dbhelper = new Dbforcall(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM calllogs";
        Cursor mycursor = db.rawQuery(selectQuery, null);
        return mycursor;
    }


    public Cursor getoutgngcalls() {
        dbhelper = new Dbforcall(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM calllogs WHERE TYPE=\"OUTGOING CALLS\"";
        Cursor mycursor = db.rawQuery(selectQuery, null);
        return mycursor;
    }

    public Cursor getmissedcalls() {
        dbhelper = new Dbforcall(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM calllogs WHERE TYPE=\"MISSED CALLS\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return  cursor;
    }

    public Cursor getincomingcalls() {
        dbhelper = new Dbforcall(this);
        db = dbhelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM calllogs WHERE TYPE=\"INCOMING CALLS\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return  cursor;
    }


    public void addLog() {
        dbhelper = new Dbforcall(this);
        Cursor cursor;
        ContentResolver cr = getContentResolver();
        cursor = cr.query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
                android.provider.CallLog.Calls.DATE + " DESC ");
        db = dbhelper.getWritableDatabase();
        db.delete(Dbforcall.TABLE_NAME, null, null);
        int idno=0;
        int calltype = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int numberColumnId = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int durationId = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        int dateId = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);

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
        cursor.close();
        dbhelper.close();
    }

    public void display(Cursor cursor)
    {
        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                idno = cursor.getInt(0);
                type = cursor.getString(1);
                phn = cursor.getString(2);
                dur = cursor.getString(3);
                dates = cursor.getString(4);
              //  sb.append(idno).append("\n");
                sb.append(type).append("\n");
                sb.append("Phone number").append(phn).append("\n");
                sb.append("call duration").append(dur).append("\n").append("call date").append(dates).append("\n").append("\n").append("\n").append(System.getProperty("line.separator"));

            } while (cursor.moveToNext());
        }
        callDetails.setText(sb.toString());

    }
    public void help(View v)
    {
        ImageButton clicked=(ImageButton) v;
        switch (clicked.getId())
        {
            case R.id.allcallsbtn:
                c = getallcalls();
                display(c);
                break;
            case R.id.outgoingbtn:
                c = getoutgngcalls();
                display(c);
                break;
            case R.id.missedbtn:
                c = getmissedcalls();
                display(c);
                break;
            case R.id.incomingbtn:
                c = getincomingcalls();
                display(c);
                break;
        }
    }


}



