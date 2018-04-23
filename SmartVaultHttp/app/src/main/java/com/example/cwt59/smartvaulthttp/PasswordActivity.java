package com.example.cwt59.smartvaulthttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PasswordActivity extends AppCompatActivity {

    static String result ="";
    static String strURL = "";
    EditText vaultPassField;
    TextView userText;
    String valFromMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        userText = (TextView) findViewById(R.id.welcomeUser);
        valFromMain = getIntent().getExtras().getString("value");
        userText.setText("Welcome " + valFromMain + "!");
        vaultPassField = (EditText) findViewById(R.id.vaultPassword);
    }

    public void GoogleButton(View view){

        strURL = "http://192.168.0.106:5000/23/on";
        new ControlGPIO().execute();
    }

    public void VaultOpen(View view){
        EditText theNet = findViewById(R.id.myNet);
        String netAddress = theNet.getText().toString();
        EditText mDevice = findViewById(R.id.dName);
        String chosenDevice = mDevice.getText().toString();
        strURL = netAddress + "/" + chosenDevice + "/on";

        if (vaultPassField.getText().toString().equals("OPEN"))
        {
            Toast.makeText(getApplicationContext(), "The Vault has been opened!", Toast.LENGTH_SHORT).show();
            new ControlGPIO().execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Access denied", Toast.LENGTH_SHORT).show();
        }
    }

    public void VaultClose(View view){
        EditText theNet = findViewById(R.id.myNet);
        String netAddress = theNet.getText().toString();
        EditText mDevice = findViewById(R.id.dName);
        String chosenDevice = mDevice.getText().toString();
        strURL = netAddress + "/" + chosenDevice + "/off";
        Toast.makeText(getApplicationContext(), strURL, Toast.LENGTH_SHORT).show();

        new ControlGPIO().execute();
    }























    //try private static if fails
    public class ControlGPIO extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
           //Toast.makeText(MainActivity.this,"The output is   " + result, Toast.LENGTH_LONG).show();
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
