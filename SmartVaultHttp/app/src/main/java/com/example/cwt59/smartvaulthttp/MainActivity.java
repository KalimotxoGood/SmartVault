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
    Button onBtn, offBtn; ///declare button objects for MainActivity. We link these buttons with the function below
    String strURL="http://www.Google.com/meowman"; // this http is never used.
    String result =""; // this is a global variable for setting the return string from the Flask Server. it is returned in the doInBackground() of 
                       // ControlGPIO class. if you want the return value, just use 'result'
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
        EditText editText = findViewById(R.id.myNet); //declare an EditText object and set it to whatever the user inputs for the ip address (
        String myNet = editText.getText().toString(); //stringify the EditText object and call it myNet (will be used for the final httpRequest address
        EditText editText1 = findViewById(R.id.dName); // this allows the user to choose which gpio to toggle (ie turn off). For example, input "24" would be for gpio 24.
        String chosenDevice = editText1.getText().toString(); // stringify editText1 (editTextONE!)
        strURL = myNet + "/" + chosenDevice +"/off"; //Puts all the parts together to make it neat RESTFUL request(make sure the address from myNet begins with "http://")

        new ControlGPIO().execute(); //Calls the ControlGPIO class below. *Note: strURL is a global variable.
                                    // So, strURL is used in ControlGPIO's HttpURLconnection. 
                                    // When http://myNet/24/off is connected (called), it turns the gpio 24 pin off and
                                    // returns whatever app.route("/24/off") returns (as shown in Web-server/app.py)
        

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
                URL url = new URL(strURL); // create a new URL object with the create string from OffButton() or OnButton()
                HttpURLConnection con = (HttpURLConnection)url.openConnection(); //opens the connection
                 con.setRequestMethod("GET"); //Get method rather than post
                 con.connect(); //Connect

                 BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));

                 String value = bf.readLine(); //this fetches the response from the server
                 System.out.println("result is " + value);
                 result = value; //sets the value of the server response to result (which is returned to the function outside this method)


            }
            catch(Exception e) //necessary catch exception for this AsyncTask to work
            {
                System.out.println(e);
            }
            return result; //returns 'result' as the server response! Get whatever you want as long as the server returns it. States? number of times opened?
        }

        public class UserInput //function stub for other user inputs if needed in main.
        {

        }
    }
}


