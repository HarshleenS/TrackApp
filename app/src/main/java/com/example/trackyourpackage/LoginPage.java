package com.example.trackyourpackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    Button btn_loginNow, btn_registerNow;

    TextInputLayout textEmail, textPassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btn_loginNow = findViewById(R.id.btnLoginNow);
        btn_registerNow = findViewById(R.id.btnRegisterNow);

        textEmail = findViewById(R.id.emailInLogin);
        textPassword = findViewById(R.id.passwordInLogin);

        firebaseAuth = FirebaseAuth.getInstance();

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

                String email = textEmail.getEditText().getText().toString().trim();
                String password = textPassword.getEditText().getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginPage.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password) || password.length()<6){
                    Toast.makeText(LoginPage.this, "Enter correct password", Toast.LENGTH_SHORT).show();
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    startActivity(new Intent(getApplicationContext(), HomePage.class));


                                } else {
                                    Toast.makeText(LoginPage.this, "Login failed. User not found or wrong credentials entered", Toast.LENGTH_SHORT).show();


                                }

                                // ...
                            }
                        });





            }
        });








    }



    public void onBackPressed(){

        startActivity(new Intent(getApplicationContext(), SelectLoginPage.class));

        return;
    }


}