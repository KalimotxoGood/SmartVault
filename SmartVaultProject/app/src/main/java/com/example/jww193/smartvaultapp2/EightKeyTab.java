package com.example.jww193.smartvaultapp2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class EightKeyTab extends Fragment {
    private static final String TAG = "EightKeyFragment";
    private Button btnTest;

    Button openVaultButton;
    Button closeVaultButton;
    String valFromMain;
    EditText vaultPassField;
    TextView userText;
    String strURL = "";
    String result = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.eightkeytab_frag, container, false);
        //btnTest = (Button) view.findViewById(R.id.testButton);

        userText = (TextView) view.findViewById(R.id.welcomeUser);
        valFromMain = getActivity().getIntent().getExtras().getString("value");
        userText.setText("welcome " + valFromMain + "!");
        vaultPassField = (EditText) view.findViewById(R.id.vaultPassword);
        openVaultButton = (Button) view.findViewById(R.id.openVault);
        closeVaultButton = (Button) view.findViewById(R.id.closeVault);
        userText = (TextView) view.findViewById(R.id.welcomeUser);
        valFromMain = getActivity().getIntent().getExtras().getString("value");
        userText.setText("Welcome " + valFromMain + "!");
        vaultPassField = (EditText) view.findViewById(R.id.vaultPassword);

        // listens for the open button to be clicked, provided that the correct password has been inputted
        // Once pressed, will display that the Vault has been opened, else if password was entered incorrectly
        // will display that access was denied
        openVaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText theNet = getView().findViewById(R.id.myNet);
                String netAddress = theNet.getText().toString();
                EditText mDevice = getView().findViewById(R.id.dName);
                String chosenDevice = mDevice.getText().toString();
                strURL = netAddress + "/" + chosenDevice + "/on";

                if (vaultPassField.getText().toString().equals("88888888")) {
                    Toast.makeText(getActivity().getApplicationContext(), "The Vault has been opened!", Toast.LENGTH_SHORT).show();
                    //new MainTab.ControlGPIO().execute();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Access denied", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // listens for the close button to be clicked
        // Once pressed, will display that the Vault has been closed
        closeVaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new MainTab.ControlGPIO().execute();
                EditText theNet = getView().findViewById(R.id.myNet);
                String netAddress = theNet.getText().toString();
                EditText mDevice = getView().findViewById(R.id.dName);
                String chosenDevice = mDevice.getText().toString();
                strURL = netAddress + "/" + chosenDevice + "/off";
                Toast.makeText(getActivity().getApplicationContext(), "Vault has been closed!", Toast.LENGTH_SHORT).show();

                 new EightKeyTab.ControlGPIO().execute();
            }
        });

        return view;
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
                Toast.makeText(getActivity().getApplicationContext(), "hello url?", Toast.LENGTH_SHORT).show();
                URL url = new URL(strURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                Toast.makeText(getActivity().getApplicationContext(), "hello after URL", Toast.LENGTH_SHORT).show();
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