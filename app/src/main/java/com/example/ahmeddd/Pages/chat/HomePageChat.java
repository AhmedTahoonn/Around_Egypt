package com.example.ahmeddd.Pages.chat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahmeddd.Adapter.UserAdapter;
import com.example.ahmeddd.Models.UserModel;
import com.example.ahmeddd.R;
import com.example.ahmeddd.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomePageChat extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    ArrayList<UserModel> userModelArrayList=new ArrayList<>();
    UserAdapter myAdapter;
    FirebaseFirestore db;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_chat);
        recyclerView = findViewById(R.id.recycler4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        userModelArrayList=new ArrayList<UserModel>();
        myAdapter=new UserAdapter(this,HomePageChat.this,userModelArrayList);
        recyclerView.setAdapter(myAdapter);
        getData();
    }

    private void getData() {

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    for (UserModel document : task.getResult().toObjects(UserModel.class)) {
                        if(!document.getuId().equals(auth.getCurrentUser().getUid()))
                        {
                            System.out.println( document.getuId() + " => " + document.getEmail_Address()+ " => " + document.getUsername());
                            userModelArrayList.add(document);
                        }

                    }
                    myAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public void onItemClick(int position)
    {
        //Toast.makeText(this,userModelArrayList.get(position).getEmail(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(HomePageChat.this, ChatActivity.class);
        intent.putExtra("reciverId",userModelArrayList.get(position).getuId());
        startActivity(intent);

    }
}
