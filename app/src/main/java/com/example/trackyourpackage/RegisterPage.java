package com.example.trackyourpackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterPage extends AppCompatActivity {

    Button btn_registerNewUser, btn_goBackToLogin;
    TextInputLayout textPhoneNumber, textEmail, textPassword, textPassword2,textName;
    ProgressBar progressBarRegister;

    //private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        btn_registerNewUser = findViewById(R.id.btnRegisterNewUser);
        btn_goBackToLogin = findViewById(R.id.btnBackToLogin);

        progressBarRegister = findViewById(R.id.progressBarRegister);

        textPhoneNumber = findViewById(R.id.phoneNumberInRegister);
        textEmail = findViewById(R.id.emailInRegister);
        textPassword = findViewById(R.id.passwordInRegister);
        textPassword2 = findViewById(R.id.passwordInRegister2);
        textName=findViewById(R.id.nameInRegister);

        btn_goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });

        //firebaseAuth = FirebaseAuth.getInstance();

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
                    Toast.makeText(RegisterPage.this, "Enter valid phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterPage.this, "Enter valid name", Toast.LENGTH_SHORT).show();
                    return;
                }


                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterPage.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password) || password.length()<6)
                {
                    Toast.makeText(RegisterPage.this, "Enter password of length 6 or more", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password2))
                {
                    Toast.makeText(RegisterPage.this, "Confirm correct password", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!password.equals(password2))
                {
                    Toast.makeText(RegisterPage.this, "Enter correct password again", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
//                    loadingBar.setTitle("Create Account");
////                    loadingBar.setMessage("Please wait while the credentials are verified!");
////                    loadingBar.setCanceledOnTouchOutside(false);
////                    loadingBar.show();
                    Toast.makeText(RegisterPage.this, "Please wait while we check the credentials!", Toast.LENGTH_SHORT).show();
                    Validate(phone,name,email,password);
                    return;
                }


               // progressBarRegister.setVisibility(View.VISIBLE);

                //if (password.equals(password2)){
//
//                    firebaseAuth.createUserWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                    progressBarRegister.setVisibility(View.GONE);
//
//                                    if (task.isSuccessful()) {
//
//                                        startActivity(new Intent(getApplicationContext(), HomePage.class));
//                                        Toast.makeText(RegisterPage.this, "Registration successful", Toast.LENGTH_SHORT).show();
//
//
//                                    } else {
//
//                                        Toast.makeText(RegisterPage.this, "Authentication failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                    // ...
//                                }
//                            });
//
//
//                }



            }
        });






    }
    public void onBackPressed()
    {

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
            if(!(dataSnapshot.child("Users").child(phonep).exists()))
            {
                HashMap<String,Object> userDataMap=new HashMap<>();
                userDataMap.put("name",namep);
                userDataMap.put("email",emailp);
                userDataMap.put("password",password);
                userDataMap.put("phone",phonep);
                Rootref.child("Users").child(phonep).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    { if(task.isSuccessful())
                    {
                        Toast.makeText(RegisterPage.this, "Your account is successfully created!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterPage.this, HomePage.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(RegisterPage.this,"Network error!Please try again.",Toast.LENGTH_SHORT).show();

                    }
                });


            }
            else
            {
                Toast.makeText(RegisterPage.this,"This account already exists.Use another email.",Toast.LENGTH_SHORT).show();
                Intent AgainLogin=new Intent(RegisterPage.this,SelectLoginPage.class);
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