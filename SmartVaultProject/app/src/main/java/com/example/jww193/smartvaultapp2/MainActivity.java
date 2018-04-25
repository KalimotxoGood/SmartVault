package com.example.jww193.smartvaultapp2;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.icu.text.StringSearch;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    Button loginButton, cancelButton;
    EditText userField, passField;
    TextView theAttempts, theLeftovers;
    int counter = 3;
    String the_user;
    String strURL="http://192.168.0.106:5000/23/on";
    String result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginButton();
    }
    public void LoginButton(){

        loginButton = (Button) findViewById(R.id.loginButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        userField = (EditText) findViewById(R.id.theUsername);
        passField = (EditText) findViewById(R.id.thePassword);
        theAttempts = (TextView) findViewById(R.id.attempts);
        theLeftovers = (TextView) findViewById(R.id.leftovers);

        // Login listener, when clicked, checks for username and password
        // if correct, displays a toast showing redirection to the next activity
        // storing the user value with it
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userField.getText().toString().equals("admin") &&
                        passField.getText().toString().equals("password")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    the_user = userField.getText().toString();
                    Intent i = new Intent(v.getContext(), PasswordActivity.class);
                    i.putExtra("value", the_user);
                    startActivity(i);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                    theLeftovers.setVisibility(View.VISIBLE);
                    theLeftovers.setBackgroundColor(Color.WHITE);
                    counter--;
                    theLeftovers.setText(Integer.toString(counter));

                    if (counter == 0) {
                        loginButton.setEnabled(false);
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class ControlGPIO extends AsyncTask<String,String,String>
    {
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
        }

        @Override protected  void onPostExecute(String s)
        {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String...params) {
            try {
                URL url = new URL(strURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String value = bf.readLine();
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
            // nothing in here (yet?)
        }
    }
}

