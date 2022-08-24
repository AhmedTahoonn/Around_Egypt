package com.example.ahmeddd.Pages.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahmeddd.Adapter.MessageAdapter;
import com.example.ahmeddd.Models.MessageModel;
import com.example.ahmeddd.R;
import com.example.ahmeddd.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    ArrayList<MessageModel> messageModelArrayList=new ArrayList<>();
    MessageAdapter myAdapter;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String reciverid;
    MessageModel model;
    private FirebaseAuth mAuth;
    String senderid;
    EditText text;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recycler5);
        text=findViewById(R.id.message);
        imageView=findViewById(R.id.imagee);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        reciverid= getIntent().getStringExtra("reciverId");
        System.out.println("aaaaaaaaaaaaaaaaaaa"+reciverid);
        firebaseAuth=FirebaseAuth.getInstance();
        messageModelArrayList=new ArrayList<MessageModel>();
        myAdapter= new MessageAdapter(this,ChatActivity.this,messageModelArrayList);
        recyclerView.setAdapter(myAdapter);
        FirebaseAuth currentUser = FirebaseAuth.getInstance();
        senderid=currentUser.getCurrentUser().getUid();
         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String message=text.getText().toString();
                 MessageModel model=new MessageModel(message,senderid,reciverid, Calendar.getInstance().getTime().toString());


                 db.collection("users").document(senderid).collection("chats").document(reciverid).collection("message").add(model).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentReference> task) {
                         if(task.isSuccessful())
                         {
                           //  Toast.makeText(getApplicationContext(),"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",Toast.LENGTH_SHORT).show();

                         }
                     }
                 });
                 db.collection("users").document(reciverid).collection("chats").document(senderid).collection("message").add(model).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentReference> task) {
                         if(task.isSuccessful())
                         {
                            // Toast.makeText(getApplicationContext(),"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",Toast.LENGTH_SHORT).show();

                         }
                     }
                 });
                 text.setText("");
             }
         });
        getData();
    }

    private void getData() {
        messageModelArrayList.clear();
        db.collection("users").document(senderid).collection("chats").document(reciverid).collection("message").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null)
                {
                    Toast.makeText(getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    return;

                }
                for(DocumentChange dc:value.getDocumentChanges())
                {
                    if (dc.getType()== DocumentChange.Type.ADDED)
                    {
                        messageModelArrayList.add(dc.getDocument().toObject(MessageModel.class));
                    }
                    myAdapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public void onItemClick(int position)
    {


    }
}
