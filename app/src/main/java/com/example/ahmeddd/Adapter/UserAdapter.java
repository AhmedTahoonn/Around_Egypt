package com.example.ahmeddd.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ahmeddd.Pages.chat.HomePageChat;
import com.example.ahmeddd.Models.UserModel;
import com.example.ahmeddd.R;
import com.example.ahmeddd.RecyclerViewInterface;

import java.util.ArrayList;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.MyViewHolder>{
    private ArrayList<UserModel> userModelArrayList;
    private HomePageChat context;
    private final RecyclerViewInterface recyclerViewInterface;


    public UserAdapter(HomePageChat recyclerViewInterface, HomePageChat context, ArrayList<UserModel> userModelArrayList) {
        this.userModelArrayList = userModelArrayList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModel userModel=userModelArrayList.get(position);
        holder.name.setText(userModel.getUsername());
        Glide.with(context).load(userModel.getProfileImage()).into(holder.imageView);

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")

    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name=itemView.findViewById(R.id.username);
            imageView=itemView.findViewById(R.id.chat_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null)
                    {
                        int pos=getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
