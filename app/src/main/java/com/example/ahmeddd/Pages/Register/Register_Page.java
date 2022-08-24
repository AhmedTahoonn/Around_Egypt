package com.example.ahmeddd.Pages.Register;

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

import com.example.ahmeddd.Pages.Login.Login_Page;
import com.example.ahmeddd.R;
import com.example.ahmeddd.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register_Page extends AppCompatActivity {
    TextView go_to_signIn ;
    EditText emailEditText;
    EditText usernameEditText;
    EditText phoneEditText;
    EditText passwordEditText;
    ProgressBar progressBar;
    Button registerButton;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Map<String, Object> user = new HashMap<>();

    // Map<String, Object> user = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().hide();
        emailEditText = findViewById(R.id.Register_email);
        usernameEditText = findViewById(R.id.Register_username);
        passwordEditText = findViewById(R.id.Register_password);
        phoneEditText = findViewById(R.id.Register_phone);
        registerButton= findViewById(R.id.Register_button);
        progressBar=findViewById(R.id.Register_progressBar);
        go_to_signIn=findViewById(R.id.Register_signIn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_Email = emailEditText.getText().toString();
                String get_Username = usernameEditText.getText().toString();
                String get_Phone = phoneEditText.getText().toString();
                String get_password = passwordEditText.getText().toString();

                if(get_Email.isEmpty())
                {
                    emailEditText.setError("Please Enter Your Email");

                }
                if(get_Username.isEmpty())
                {
                    usernameEditText.setError("Please Enter Your Username");

                }
                if(get_Phone.isEmpty())
                {
                    phoneEditText.setError("Please Enter Your Phone");

                }
                if ( get_password.isEmpty()) {
                    passwordEditText.setError("Please Enter Your Password");

                }

                else {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(get_Email, get_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                               // UserModel userModel=new UserModel(firebaseAuth.getUid(),get_Email,get_Username,get_Phone);
                                user.put("Email_Address", get_Email);
                                user.put("Username", get_Username);
                                user.put("Phone", get_Phone);
                                user.put("uId",firebaseAuth.getUid());
                                user.put("profileImage","https://fetview.com/wp-content/themes/fetview/img/no-photo.png");
                                firebaseFirestore.collection("users").document(firebaseAuth.getUid().toString()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent( Register_Page.this, Login_Page.class);
                                            startActivity(intent);
                                            progressBar.setVisibility(View.GONE);

                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                });
                            }
                        }
                    });

                }
            }
        });
        go_to_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Register_Page.this,Login_Page.class);
                startActivity(intent);
                finish();
            }
        });

    }
}