package com.example.trackyourpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class HomePageWarehouse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_warehouse);
    }




    public void onBackPressed(){

        startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));

        return;
    }




}