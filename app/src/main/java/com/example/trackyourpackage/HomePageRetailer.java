package com.example.trackyourpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class HomePageRetailer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_retailer);
    }


    public void onBackPressed(){

        startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));

        return;
    }





}