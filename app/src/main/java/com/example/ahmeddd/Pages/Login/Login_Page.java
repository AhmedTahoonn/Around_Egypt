package com.example.ahmeddd.Pages.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmeddd.Drawer.Drawer_Page;
import com.example.ahmeddd.Pages.Register.Register_Page;
import com.example.ahmeddd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login_Page extends AppCompatActivity {
    EditText emailEditText;
    EditText passwordEditText;
    TextView forgetPassword;
    TextView Go_to_signUp;
    Button loginButton;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();
        emailEditText=findViewById(R.id.Login_email);
        passwordEditText=findViewById(R.id.Login_password);
        loginButton=findViewById(R.id.Login_button);
        progressBar=findViewById(R.id.Login_progressBar);
        forgetPassword=findViewById(R.id.Login_questionForget);
        Go_to_signUp=findViewById(R.id.Login_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_email=emailEditText.getText().toString();
                String get_password=passwordEditText.getText().toString();
                if(get_email.isEmpty())
                {
                    emailEditText.setError("Please Enter Your Email");

                }
                if(get_password.isEmpty())
                {
                    passwordEditText.setError("Please Enter Your Password");

                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(get_email,get_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                String UId=firebaseAuth.getUid();

                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                Intent intent=new Intent(Login_Page.this, Drawer_Page.class);
                                intent.putExtra("UId",UId);
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                }
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login_Page.this, ForgetPassword_Page.class);
                startActivity(intent);
            }
        });
        Go_to_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login_Page.this, Register_Page.class);
                startActivity(intent);
                finish();
            }
        });

    }
}