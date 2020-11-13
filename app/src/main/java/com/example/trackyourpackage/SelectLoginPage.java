package com.example.trackyourpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectLoginPage extends AppCompatActivity {

    Button btnLoginUser, btnLoginRetailer, btnLoginWarehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_page);

        btnLoginUser = findViewById(R.id.btnToLoginUser);
        btnLoginRetailer = findViewById(R.id.btnToLoginRetailer);
        btnLoginWarehouse = findViewById(R.id.btnToLoginWarehouse);

        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });

        btnLoginRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginPageRetailer.class));
                finish();
            }
        });

        btnLoginWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginPageWarehouse.class));
                finish();
            }
        });


    }







}