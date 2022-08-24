package com.example.ahmeddd.Pages.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ahmeddd.Models.CityModel;
import com.example.ahmeddd.Adapter.MyPlacesAdapter;
import com.example.ahmeddd.R;
import com.example.ahmeddd.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Places_Page extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    ArrayList<CityModel> cityModelArrayList =new ArrayList<>();
    MyPlacesAdapter myAdapter;
    FirebaseFirestore db;
    String cityUId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_page);
        recyclerView = findViewById(R.id.recycler2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        cityModelArrayList =new ArrayList<CityModel>();
        cityUId=getIntent().getStringExtra("cityUid");
        myAdapter=new MyPlacesAdapter(this, Places_Page.this, cityModelArrayList);
        recyclerView.setAdapter(myAdapter);
        getData();
    }

    private void getData() {

        db.collection("City").document(cityUId).collection("Places").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    for (CityModel document : task.getResult().toObjects(CityModel.class)) {
                        System.out.println( document.getuId() + " => " + document.getCityName()+ " => " + document.getUrl());
                        cityModelArrayList.add(document);
                    }
                    myAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public void onItemClick(int position)
    {
        Toast.makeText(this, cityModelArrayList.get(position).getCityName(),Toast.LENGTH_SHORT).show();
         Intent intent=new Intent(Places_Page.this, DisplayData_Page.class);
         intent.putExtra("placeUId", cityModelArrayList.get(position).getuId());
         intent.putExtra("cityUid",cityUId);
         startActivity(intent);
    }
}
