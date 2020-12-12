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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginPage extends AppCompatActivity
{

    Button btn_loginNow, btn_registerNow;

    TextInputLayout textphone, textPassword;

    private FirebaseAuth firebaseAuth;
    private String parentDB="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btn_loginNow = findViewById(R.id.btnLoginNow);
        btn_registerNow = findViewById(R.id.btnRegisterNow);

        textphone = findViewById(R.id.emailInLogin);
        textPassword = findViewById(R.id.passwordInLogin);
        Paper.init(LoginPage.this);

        //firebaseAuth = FirebaseAuth.getInstance();

        btn_registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterPage.class));
                finish();
            }
        });


        btn_loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = textphone.getEditText().getText().toString().trim();
                String password = textPassword.getEditText().getText().toString().trim();


                if((TextUtils.isEmpty(phone)||phone.length()<10)){
                    Toast.makeText(LoginPage.this, "Enter valid phone no", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password) || password.length()<6){
                    Toast.makeText(LoginPage.this, "Enter correct password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    LoginUser(phone, password);
                    Toast.makeText(LoginPage.this, "Please wait while we check login credentials.", Toast.LENGTH_SHORT).show();

                 }




//                firebaseAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//
//                                    startActivity(new Intent(getApplicationContext(), HomePage.class));
//
//
//                                } else {
//                                    Toast.makeText(LoginPage.this, "Login failed. User not found or wrong credentials entered", Toast.LENGTH_SHORT).show();
//
//
//                                }
//
//                                // ...
//                            }
//                        });





            }
        });








    }



    public void onBackPressed(){

        //startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));
        finish();

        return;
    }
    private void LoginUser(final String phoneL, final String passwordL)
    { Paper.book().write(PrevalentCustomer.CustomerPhoneKey,phoneL);
    Paper.book().write(PrevalentCustomer.CustomerPasswordKey,passwordL);
        final DatabaseReference RootRef;
    RootRef= FirebaseDatabase.getInstance().getReference();
    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            if(dataSnapshot.child("Users").child(phoneL).exists())
            {
                Customer customerData=dataSnapshot.child("Users").child(phoneL).getValue(Customer.class);
                if(customerData.getPassword().equals(passwordL))
                {
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    PrevalentCustomer.currentonlineUser = customerData;
                    finish();


                    Intent intent=new Intent(LoginPage.this,Display.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(LoginPage.this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(LoginPage.this, "Login failed. User not found or wrong credentials entered", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LoginPage.this,RegisterPage.class);
                startActivity(intent);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }


}