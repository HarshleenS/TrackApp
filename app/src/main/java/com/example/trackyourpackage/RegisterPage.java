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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

    Button btn_registerNewUser, btn_goBackToLogin;
    TextInputLayout textPhoneNumber, textEmail, textPassword, textPassword2;
    ProgressBar progressBarRegister;

    private FirebaseAuth firebaseAuth;

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

        btn_goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        btn_registerNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = textEmail.getEditText().getText().toString().trim();
                String password = textPassword.getEditText().getText().toString().trim();
                String password2 = textPassword2.getEditText().getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterPage.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password) || password.length()<6){
                    Toast.makeText(RegisterPage.this, "Enter password of length 6 or more", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password2)){
                    Toast.makeText(RegisterPage.this, "Confirm correct password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(password2)){
                    Toast.makeText(RegisterPage.this, "Enter correct password again", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressBarRegister.setVisibility(View.VISIBLE);

                if (password.equals(password2)){

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBarRegister.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                                        Toast.makeText(RegisterPage.this, "Registration successful", Toast.LENGTH_SHORT).show();


                                    } else {

                                        Toast.makeText(RegisterPage.this, "Authentication failed. Check your internet connection.", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });


                }



            }
        });






    }
}