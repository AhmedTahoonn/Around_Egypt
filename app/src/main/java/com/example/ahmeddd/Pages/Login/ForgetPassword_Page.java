package com.example.ahmeddd.Pages.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmeddd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword_Page extends AppCompatActivity {
  EditText EmailAddress;
  Button sendButton,cancelButton;
  FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword_page);
        getSupportActionBar().hide();

        EmailAddress=findViewById(R.id.Forgot_email);
        sendButton=findViewById(R.id.Forget_sendButton);
        cancelButton=findViewById(R.id.Forget_cancelButton);
        firebaseAuth = FirebaseAuth.getInstance();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_emailAddress=EmailAddress.getText().toString();
                firebaseAuth.sendPasswordResetEmail(get_emailAddress.trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful())
                      {
                          Toast.makeText(getApplicationContext(), "Check Your Email", Toast.LENGTH_SHORT).show();

                      }

                  }
              });
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetPassword_Page.this,Login_Page.class);
                startActivity(intent);
                finish();
            }
        });

    }
}