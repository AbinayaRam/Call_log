package com.example.abi.sharedpref5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    EditText phneno;
    Button signup;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";

    public SharedPreferences sharedpreferences;
    public SharedPreferences.Editor editor1;
    public static String myphoneno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Boolean isFirstTime;
        SharedPreferences app_preferences;
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        editor1 = app_preferences.edit();
        isFirstTime = app_preferences.getBoolean("isFirstTime", true);

        if (isFirstTime) {

            //implement your first time logic
            phneno = (EditText) findViewById(R.id.editText);
            signup = (Button) findViewById(R.id.button);
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myphoneno = phneno.getText().toString();
                    if(phneno.length() < 10) {
                        phneno.requestFocus();
                        phneno.setError("Enter a valid ten digit number");
                    } else {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Name, myphoneno);
                        editor.apply();
                        editor1.putBoolean("isFirstTime", false);
                        editor1.apply();
                        startActivity(new Intent(MainActivity.this, calllog.class));
                        finish();
                    }
                }
            });
        } else {
            startActivity(new Intent(MainActivity.this, calllog.class));
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}






