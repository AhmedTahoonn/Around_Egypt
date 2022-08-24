package com.example.ahmeddd.Drawer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahmeddd.Adapter.HomePageAdapter;
import com.example.ahmeddd.Pages.Home.Change_Password;
import com.example.ahmeddd.Pages.Login.Login_Page;
import com.example.ahmeddd.Pages.Welcome.Welcome_Page;
import com.example.ahmeddd.Pages.chat.HomePageChat;
import com.example.ahmeddd.Models.CityModel;
import com.example.ahmeddd.Pages.Home.Places_Page;
import com.example.ahmeddd.Pages.Home.UpdateProfile_Page;
import com.example.ahmeddd.R;
import com.example.ahmeddd.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Drawer_Page extends AppCompatActivity implements RecyclerViewInterface {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;
    ArrayList<CityModel> cityModelArrayList = new ArrayList<>();
    HomePageAdapter homePageAdapter;
    FirebaseFirestore db;
    FirebaseAuth auth;
    ImageView drawerImage;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_page);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recycler1);
        drawerImage=findViewById(R.id.drawerImage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        cityModelArrayList = new ArrayList<CityModel>();
        homePageAdapter = new HomePageAdapter(this, Drawer_Page.this, cityModelArrayList);
        recyclerView.setAdapter(homePageAdapter);
        getData();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_editProfile:
                        Intent intent1= new Intent(Drawer_Page.this, UpdateProfile_Page.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_ChangePassword:
                        Intent intent2 = new Intent(Drawer_Page.this, Change_Password.class);
                        startActivity(intent2);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_message:
                        Intent intent3= new Intent(Drawer_Page.this, HomePageChat.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent4=new Intent(Drawer_Page.this, Welcome_Page.class);
                        startActivity(intent4);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }
                return true;
            }
        });


    }

    private void getData() {

        db.collection("City").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (CityModel document : task.getResult().toObjects(CityModel.class)) {
                        System.out.println(document.getuId() + " => " + document.getCityName() + " => " + document.getUrl());
                        cityModelArrayList.add(document);
                    }
                    homePageAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Drawer_Page.this, Places_Page.class);
        intent.putExtra("cityUid", cityModelArrayList.get(position).getuId());
        startActivity(intent);
    }
}
