package com.example.ahmeddd.Pages.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahmeddd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DisplayData_Page extends AppCompatActivity {
String placeUid;
String cityUid;
ImageView displayImageView;
TextView descriptionText;
TextView placeNameText;
FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data_page);
        firebaseFirestore=FirebaseFirestore.getInstance();
        placeUid=getIntent().getStringExtra("placeUId");
        System.out.println(placeUid);
        cityUid=getIntent().getStringExtra("cityUid");
        displayImageView=findViewById(R.id.display_data_image);
        descriptionText=findViewById(R.id.display_data_description);
        placeNameText=findViewById(R.id.display_data_placeName);
        firebaseFirestore.collection("City").document(cityUid).collection("Places").document(placeUid).collection("description").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
             if (task.isSuccessful())
             {
                 System.out.println(task.getResult().getDocuments().get(0).getData().get("placeName"));
                 descriptionText.setText(task.getResult().getDocuments().get(0).getData().get("description").toString());
                 placeNameText.setText(task.getResult().getDocuments().get(0).getData().get("placeName").toString());

                 Glide.with(getApplicationContext()).load(task.getResult().getDocuments().get(0).getData().get("url")).into(displayImageView);

             }
            }
        });


    }
}