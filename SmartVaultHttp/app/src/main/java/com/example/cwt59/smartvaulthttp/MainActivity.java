package com.example.cwt59.smartvaulthttp;

import android.icu.text.StringSearch;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText num1, num2;
    Button btn;
    String strURL="http://www.Google.com/meowman";
    String result ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);

        btn = (Button) findViewById(R.id.btnAdd);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int i = Integer.parseInt(num1.getText().toString());
                int j = Integer.parseInt(num2.getText().toString());
                //int k = Integer.parseInt(num1.getText().toString());
                strURL="http://www.Google.com";
                new ControlGPIO().execute();


            }
        });


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
            Toast.makeText(MainActivity.this,"The output is   " + result, Toast.LENGTH_LONG).show();
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


