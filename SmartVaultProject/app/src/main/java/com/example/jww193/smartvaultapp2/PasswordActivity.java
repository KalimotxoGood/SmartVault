package com.example.jww193.smartvaultapp2;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.os.Bundle;
import android.os.AsyncTask;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import android.util.Log;


public class PasswordActivity extends AppCompatActivity {
    Button openVaultButton;
    Button closeVaultButton;
    String valFromMain;
    EditText vaultPassField;
    TextView userText;
    String strURL = "";
    String result = "";

    private static final String TAG = "PasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_main);

        userText = (TextView)findViewById(R.id.welcomeUser);
        valFromMain = getIntent().getExtras().getString("value");
        userText.setText("welcome " + valFromMain + "!");
        vaultPassField = (EditText) findViewById(R.id.vaultPassword);
        VaultOpen();
    }
    public void GoogleButton(View view)
    {
        strURL = "http://172.168.0.106:5000/23/on";
        new ControlGPIO().execute();
    }


    // function that operates the on and off button on-clicks
    public void VaultOpen() {
        openVaultButton = (Button) findViewById(R.id.openVault);
        closeVaultButton = (Button) findViewById(R.id.closeVault);
        userText = (TextView) findViewById(R.id.welcomeUser);
        valFromMain = getIntent().getExtras().getString("value");
        userText.setText("Welcome " + valFromMain + "!");
        vaultPassField = (EditText) findViewById(R.id.vaultPassword);


        // listens for the open button to be clicked, provided that the correct password has been inputted
        // Once pressed, will display that the Vault has been opened, else if password was entered incorrectly
        // will display that access was denied
        openVaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });


        // listens for the close button to be clicked
        // Once pressed, will display that the Vault has been closed
        closeVaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ControlGPIO().execute();
                EditText theNet = findViewById(R.id.myNet);
                String netAddress = theNet.getText().toString();
                EditText mDevice = findViewById(R.id.dName);
                String chosenDevice = mDevice.getText().toString();
                strURL = netAddress + "/" + chosenDevice + "/off";
                Toast.makeText(getApplicationContext(), "Vault has been closed!", Toast.LENGTH_SHORT).show();

                new ControlGPIO().execute();
            }
        });
    }

    // controls the GPIO pins
    public class ControlGPIO extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


        @Override
        protected String doInBackground(String...params){
            try
            {
                //Toast.makeText(getApplicationContext(), "hello url?", Toast.LENGTH_SHORT).show();
                URL url = new URL(strURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                //Toast.makeText(getApplicationContext(), "hello after URL", Toast.LENGTH_SHORT).show();
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

        }
    }
}

