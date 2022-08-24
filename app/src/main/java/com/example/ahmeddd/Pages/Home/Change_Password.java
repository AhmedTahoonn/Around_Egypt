package com.example.ahmeddd.Pages.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmeddd.Drawer.Drawer_Page;
import com.example.ahmeddd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Change_Password extends AppCompatActivity {

    EditText new_password,confirm_password;
    Button changePassword_button,cancel_button;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_new_password);
        changePassword_button = findViewById(R.id.change_password_button);
        cancel_button = findViewById(R.id.change_cancel_button);

        auth = FirebaseAuth.getInstance();
        changePassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword=new_password.getText().toString();
                String confirmPassword=confirm_password.getText().toString();
                if(newPassword.isEmpty())
                {
                    new_password.setError("Please Enter Your New Password");
                }
                if(confirmPassword.isEmpty())
                {
                    confirm_password.setError("Please Enter Your New Password Again");

                }
                else{
                    if(!newPassword.equals(confirmPassword))
                    {
                        Toast.makeText(getApplicationContext(),"please enter the same password",Toast.LENGTH_SHORT).show();

                    }else
                    {
                        auth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "password Changed Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();


                                }
                            }
                        });

                    }
                }
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Change_Password.this, Drawer_Page.class);
                startActivity(intent);
                finish();
            }
        });
    }
}