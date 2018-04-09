package com.example.jww193.smartvaultapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;

import org.w3c.dom.Text;

public class PasswordActivity extends AppCompatActivity {
    Button openVaultButton;
    Button closeVaultButton;
    String valFromMain;
    EditText vaultPassField;
    TextView userText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_main);

        openVaultButton = (Button)findViewById(R.id.openVault);
        closeVaultButton = (Button)findViewById(R.id.closeVault);
        userText = (TextView)findViewById(R.id.welcomeUser);
        valFromMain = getIntent().getExtras().getString("value");
        userText.setText("Welcome {0}, valFromMain");
    }

    public void openVaultClick(View v)
    {
        if(vaultPassField.getText().toString().equals("12345678"))
        {
            Toast.makeText(this, "The Vault has been unlocked!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "You are not allowed to access this Vault", Toast.LENGTH_SHORT).show();
        }
    }

    public void closeVaultClick(View v)
    {
        Toast.makeText(this , "The Vault has been locked!", Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.swipe:
                Toast.makeText(this, "Dot-to-Dot was chosen", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.riddle:
                Toast.makeText(this, "Riddle me this was chosen", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.spoken:
                Toast.makeText(this, "Speak was chosen", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
