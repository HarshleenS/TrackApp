package com.example.trackyourpackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import io.paperdb.Paper;


public class HomePage extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            Paper.book().destroy();
            Intent intent=new Intent(HomePage.this,SelectLoginPage.class);
            startActivity(intent);
            Toast.makeText(this, "You have successfully logged out!", Toast.LENGTH_SHORT).show();
        }
        return true;
    };

    public void onBackPressed(){

        startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));
        finish();
                // I'm here
        return;
    }

}