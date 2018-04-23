package com.example.cwt59.smartvaulthttp;

import android.content.Intent;
import android.icu.text.StringSearch;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText userField, passField;
    TextView theAttempts, theLeftovers;
    int counter = 3;
    String the_user;
    static String strURL="http://192.168.0.106:5000/23/on";
    static String result ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userField = (EditText) findViewById(R.id.theUsername);
        passField = (EditText) findViewById(R.id.thePassword);
        theAttempts = (TextView) findViewById(R.id.attempts);
        theLeftovers = (TextView) findViewById(R.id.leftovers);

        //new ControlGPIO().execute();
    }

    public void LoginButton(View view){
        if (userField.getText().toString().equals("admin") &&
                passField.getText().toString().equals("password")) {
            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
            the_user = userField.getText().toString();
            Intent i = new Intent(this, PasswordActivity.class);
            i.putExtra("value", the_user);
            startActivity(i);
        }

    };

    public void GoogleButton(View view){

        strURL = "http://192.168.0.106:5000/24/on";
        new ControlGPIO().execute();
    }





    public class ControlGPIO extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
           // Toast.makeText(MainActivity.this,"The output is   " + result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String...params){
            try {
                URL url = new URL(strURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String value = bf.readLine(); //this fetches the response from the server
                System.out.println("result is " + value);
                result = value;


            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            return result;
        }

        public class UserInput
        {

        }
    }
}

