package com.example.trackyourpackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.paperdb.Paper;


public class HomePage extends AppCompatActivity {
    Button savebtn,cancelbtn;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        savebtn=findViewById(R.id.buttonregistersave);
        cancelbtn=findViewById(R.id.buttonregistercancel);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this,"Current Location- Hinoo Ranchi 834002",Toast.LENGTH_LONG).show();

            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this,"Your Order has been successfully cancelled!",Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.homepagemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.log_out)
        {
            //Paper.book().destroy();
            //Intent intent=new Intent(HomePage.this,SelectLoginPage.class);
            //startActivity(intent);
            //oast.makeText(this, "You have successfully logged out!", Toast.LENGTH_SHORT).show();
        }
        return true;
    };

    public void onBackPressed(){

        //startActivity(new Intent(getApplicationContext(),LoginPage.class));
        finish();
                // I'm here
        return;
    }

}