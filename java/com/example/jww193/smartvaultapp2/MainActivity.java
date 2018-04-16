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

public class MainActivity extends AppCompatActivity {
    Button loginButton, cancelButton;
    EditText userField, passField;
    TextView theAttempts, theLeftovers;
    int counter = 3;
    String the_user;

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
                    theLeftovers.setBackgroundColor(Color.RED);
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
}

