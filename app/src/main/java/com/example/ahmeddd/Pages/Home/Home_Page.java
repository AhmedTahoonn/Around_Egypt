package com.example.ahmeddd.Pages.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ahmeddd.Models.CityModel;
import com.example.ahmeddd.Adapter.HomePageAdapter;
import com.example.ahmeddd.R;
import com.example.ahmeddd.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Home_Page extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    ArrayList<CityModel> cityModelArrayList =new ArrayList<>();
    HomePageAdapter homePageAdapter;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        cityModelArrayList =new ArrayList<CityModel>();
       // homePageAdapter =new HomePageAdapter(this,Home_Page.this, cityModelArrayList);
        recyclerView.setAdapter(homePageAdapter);
        getData();
    }

    private void getData() {

        db.collection("City").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    for (CityModel document : task.getResult().toObjects(CityModel.class)) {
                        System.out.println( document.getuId() + " => " + document.getCityName()+ " => " + document.getUrl());
                        cityModelArrayList.add(document);
                    }
                    homePageAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public void onItemClick(int position)
    {
        Intent intent=new Intent(Home_Page.this, Places_Page.class);
        intent.putExtra("cityUid", cityModelArrayList.get(position).getuId());
        startActivity(intent);
    }
}
