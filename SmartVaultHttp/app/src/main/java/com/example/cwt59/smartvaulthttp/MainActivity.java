package com.example.cwt59.smartvaulthttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    Button onBtn, offBtn;
    String strURL="http://www.Google.com/meowman";
    String result ="";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btn = (Button) findViewById(R.id.btnAdd);


        new ControlGPIO().execute();
    }


// I could have used onClick listener but the code looks cleaner with designated function buttons rather than leaving multiple things in the onCreate
    // which don't need to be
    public void OnButton(View view){

        EditText editText = findViewById(R.id.myNet);
        String myNet = editText.getText().toString();
        EditText editText1 = findViewById(R.id.dName);
        String chosenDevice = editText1.getText().toString();
        strURL = myNet + "/" + chosenDevice +"/on";

        new ControlGPIO().execute();
    }

    public void OffButton(View view){
        new ControlGPIO().execute();
        EditText editText = findViewById(R.id.myNet);
        String myNet = editText.getText().toString();
        EditText editText1 = findViewById(R.id.dName);
        String chosenDevice = editText1.getText().toString();
        strURL = myNet + "/" + chosenDevice +"/off"; //make sure the address begins with "http://"

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


