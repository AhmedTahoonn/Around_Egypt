package com.example.ahmeddd.Pages.Welcome;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ahmeddd.Drawer.Drawer_Page;
import com.example.ahmeddd.R;

public class Splash_Page extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash_Page.this, Drawer_Page.class);
                startActivity(intent);;
                finish();
            }
        },5000);
    }
}