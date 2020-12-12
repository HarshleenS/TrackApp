package com.example.trackyourpackage;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

import io.paperdb.Paper;

public class RegisterPageRetailer extends AppCompatActivity {
    Button btn_registerNewUser, btn_goBackToLogin;
    TextInputLayout textPhoneNumber, textEmail, textPassword, textPassword2,textName;
    ProgressBar progressBarRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page_retailer);


        btn_registerNewUser = findViewById(R.id.btnRegisterNewUserRetailer);
        btn_goBackToLogin = findViewById(R.id.btnBackToLoginRetailer);

        progressBarRegister = findViewById(R.id.progressBarRegister);

        textPhoneNumber = findViewById(R.id.phoneNumberInRegisterRetailer);
        textEmail = findViewById(R.id.emailInRegisterRetailer);
        textPassword = findViewById(R.id.passwordInRegisterRetailer);
        textPassword2 = findViewById(R.id.passwordInRegisterRetailer2);
        textName=findViewById(R.id.nameInRegisterRetailer);
        btn_goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginPageRetailer.class));
                finish();
            }
        });
        btn_registerNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String email = textEmail.getEditText().getText().toString().trim();
                String phone = textPhoneNumber.getEditText().getText().toString().trim();
                String password = textPassword.getEditText().getText().toString().trim();
                String password2 = textPassword2.getEditText().getText().toString().trim();
                String name=textName.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(phone)||phone.length()<10){
                    Toast.makeText(RegisterPageRetailer.this, "Enter valid phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterPageRetailer.this, "Enter valid name", Toast.LENGTH_SHORT).show();
                    return;
                }


                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterPageRetailer.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password) || password.length()<6)
                {
                    Toast.makeText(RegisterPageRetailer.this, "Enter password of length 6 or more", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password2))
                {
                    Toast.makeText(RegisterPageRetailer.this, "Confirm correct password", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!password.equals(password2))
                {
                    Toast.makeText(RegisterPageRetailer.this, "Enter correct password again", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
//                    loadingBar.setTitle("Create Account");
////                    loadingBar.setMessage("Please wait while the credentials are verified!");
////                    loadingBar.setCanceledOnTouchOutside(false);
////                    loadingBar.show();
                    Toast.makeText(RegisterPageRetailer.this, "Please wait while we check the credentials!", Toast.LENGTH_SHORT).show();
                    Validate(phone,name,email,password);
                    return;
                }
            }
        });






    }



                public void onBackPressed()
                {

                    //Paper.book().destroy();
                    startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));
                    finish();

                    return;
                }

                private void Validate(final String phonep,final String namep, final String emailp, final String password)
                {final DatabaseReference Rootref;
                    Rootref= FirebaseDatabase.getInstance().getReference();
                    Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(!(dataSnapshot.child("Retailers").child(phonep).exists()))
                            {
                                HashMap<String,Object> retailerDataMap=new HashMap<>();
                                retailerDataMap.put("name",namep);
                                retailerDataMap.put("email",emailp);
                                retailerDataMap.put("password",password);
                                retailerDataMap.put("phone",phonep);
                                Rootref.child("Retailers").child(phonep).updateChildren(retailerDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    { if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterPageRetailer.this, "Your account is successfully created!-Retailer", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterPageRetailer.this, ProductsCategoryRetailer.class);
                                        startActivity(intent);
                                    }
                                    else
                                        Toast.makeText(RegisterPageRetailer.this,"Network error!Please try again-retailer.",Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }
                            else
                            {
                                Toast.makeText(RegisterPageRetailer.this,"This account already exists.Use another email.-Retailer",Toast.LENGTH_SHORT).show();
                                Intent AgainLogin=new Intent(RegisterPageRetailer.this,SelectLoginPage.class);
                                startActivity(AgainLogin);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });




                }
}