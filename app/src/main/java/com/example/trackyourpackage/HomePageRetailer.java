package com.example.trackyourpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import io.paperdb.Paper;

public class HomePageRetailer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_retailer);
    }


    public void onBackPressed(){
        Paper.book().destroy();

        startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));
        finish();

        return;
    }





}