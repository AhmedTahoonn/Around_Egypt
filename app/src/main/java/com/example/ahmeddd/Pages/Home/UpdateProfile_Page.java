package com.example.ahmeddd.Pages.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahmeddd.Drawer.Drawer_Page;
import com.example.ahmeddd.Models.UserModel;
import com.example.ahmeddd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile_Page extends AppCompatActivity {

    EditText username,phone,email;
    TextView changePhoto;
    ImageView myImage;
    ProgressBar updateProgressBar,uploadProgressBar;
    Button cancelButton,saveButton;
    FirebaseFirestore firebaseFirestore;
    Uri imageUri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseAuth auth;
    Map<String, Object> user = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_page);

        username=findViewById( R.id.update_username);
        email=findViewById( R.id.update_email);
        phone=findViewById(R.id.update_profile_phone);
        myImage=findViewById(R.id.myImage);
        cancelButton=findViewById(R.id.update_cancel_button);
        saveButton=findViewById(R.id.update_save_button);
        changePhoto=findViewById(R.id.changephoto);
        updateProgressBar=findViewById(R.id.update_progressBar);
        uploadProgressBar=findViewById(R.id.upload_Image_progressBar);
        firebaseFirestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        firebaseFirestore.collection("users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                { System.out.println(task.getResult().getData());
                    username.setText(task.getResult().getData().get("Username").toString());
                    email.setText(task.getResult().getData().get("Email_Address").toString());
                    phone.setText(task.getResult().getData().get("Phone").toString());
                    Glide.with(getApplicationContext()).load(task.getResult().getData().get("profileImage")).into(myImage);


                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_Email = email.getText().toString();
                String get_Username = username.getText().toString();
                String get_Phone = phone.getText().toString();
                if(get_Email.isEmpty())
                {
                    email.setError("Please Enter Your Email");
                }
                if(get_Username.isEmpty())
                {
                    username.setError("Please Enter Your Username");

                }
                if(get_Phone.isEmpty())
                {
                    phone.setError("Please Enter Your Phone Number");

                }
                else
                {
                    updateProgressBar.setVisibility(View.VISIBLE);
                   // UserModel userModel=new UserModel(auth.getUid(),get_Email,get_Username,get_Phone);
                    user.put("Email_Address", get_Email);
                    user.put("Username", get_Username);
                    user.put("Phone", get_Phone);
                    firebaseFirestore.collection("users").document(auth.getCurrentUser().getUid()).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                updateProgressBar.setVisibility(View.GONE);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                updateProgressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UpdateProfile_Page.this, Drawer_Page.class);
                startActivity(intent);
                finish();
            }
        });
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                startActivityForResult(intent, 100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            if (requestCode == 100 && data != null && resultCode == RESULT_OK) {
                imageUri = data.getData();
                uploadProgressBar.setVisibility(View.VISIBLE);
                storageRef.child("profile_image").child(auth.getUid()).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       // Toast.makeText(getApplicationContext(), "Profile Image Upload successfully!", Toast.LENGTH_LONG).show();
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        //createNewPost(imageUrl);
                                        System.out.println(imageUrl);
                                        user.put("profileImage", imageUrl);
                                        firebaseFirestore.collection("users").document(auth.getUid().toString()).update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(getApplicationContext(), "Profile Image Upload successfully!", Toast.LENGTH_LONG).show();
                                                    uploadProgressBar.setVisibility(View.GONE);
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                                                    uploadProgressBar.setVisibility(View.GONE);
                                                }


                                            }
                                        });
                                    }
                                });
                            }

                        }
                    }


                });

            }
        }
    }
}