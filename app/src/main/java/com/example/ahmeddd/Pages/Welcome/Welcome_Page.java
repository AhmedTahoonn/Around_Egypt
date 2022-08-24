package com.example.ahmeddd.Pages.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ahmeddd.Pages.Login.Login_Page;
import com.example.ahmeddd.Pages.Register.Register_Page;
import com.example.ahmeddd.R;

public class Welcome_Page extends AppCompatActivity {
       Button SignInButton;
       Button SignUpButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        getSupportActionBar().hide();
        SignInButton=findViewById(R.id.Welcome_SignIn);
        SignUpButton=findViewById(R.id.Welcome_Signup);

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome_Page.this, Login_Page.class);
                startActivity(intent);
                finish();
            }
        });
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome_Page.this, Register_Page.class);
                startActivity(intent);
                finish();
            }
        });


    }


}