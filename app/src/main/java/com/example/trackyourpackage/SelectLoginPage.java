package com.example.trackyourpackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.trackyourpackage.PrevalentCustomer.PrevalentCustomer;
import com.example.trackyourpackage.model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SelectLoginPage extends AppCompatActivity {

    Button btnLoginUser, btnLoginRetailer, btnLoginWarehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_page);

        btnLoginUser = findViewById(R.id.btnToLoginUser);
        btnLoginRetailer = findViewById(R.id.btnToLoginRetailer);
        btnLoginWarehouse = findViewById(R.id.btnToLoginWarehouse);
        Paper.init(this);







        btnLoginUser.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String CustomerPhoneKey=Paper.book().read(PrevalentCustomer.CustomerPhoneKey);
                final String CustomerPasswordKey=Paper.book().read(PrevalentCustomer.CustomerPasswordKey);
               if(CustomerPasswordKey!=""&&CustomerPasswordKey!="")
                {
                    if(!TextUtils.isEmpty(CustomerPasswordKey)&&!TextUtils.isEmpty(CustomerPhoneKey))
                    {Toast.makeText(SelectLoginPage.this, "Already logged in as customer!", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(SelectLoginPage.this, CustomerPasswordKey+" "+CustomerPhoneKey, Toast.LENGTH_SHORT).show();
                       AllowAccessCustomer(CustomerPhoneKey,CustomerPasswordKey);
//
                   }
               }
                //else
                {
                    startActivity(new Intent(getApplicationContext(), LoginPage.class));
                    finish();
                }
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

    private void AllowAccessCustomer(final String customerPhoneKey, final String customerPasswordKey)
    { final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child("Users").child(customerPhoneKey).exists())
                {
                    Customer customerData=dataSnapshot.child("Users").child(customerPhoneKey).getValue(Customer.class);
                    if(customerData.getPassword().equals(customerPasswordKey))
                    {
                        Toast.makeText(SelectLoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();


                        Intent intent=new Intent(SelectLoginPage.this,Display.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SelectLoginPage.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        Paper.book().destroy();
                    }
                }
                else
                {
                    Toast.makeText(SelectLoginPage.this, "Login failed. User not found or wrong credentials entered", Toast.LENGTH_SHORT).show();
                    Paper.book().destroy();
                    Intent intent=new Intent(SelectLoginPage.this,RegisterPage.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onBackPressed()
    {

        //startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));
        // I'm here
        finish();
        //System.exit(0);
        return;
    }
}