package com.example.ahmeddd.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahmeddd.Models.MessageModel;
import com.example.ahmeddd.Pages.chat.ChatActivity;
import com.example.ahmeddd.R;
import com.example.ahmeddd.RecyclerViewInterface;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    private ArrayList<MessageModel> messageModelArrayList;
    private ChatActivity context;
    private final RecyclerViewInterface recyclerViewInterface;



    public MessageAdapter(ChatActivity recyclerViewInterface, ChatActivity context, ArrayList<MessageModel> messageModelArrayList) {
        this.messageModelArrayList = messageModelArrayList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        return new MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageModel messageModel=messageModelArrayList.get(position);
        if(messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid()))
        {
            holder.name.setText(messageModel.getMessage());
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));

        }
        else
        {
            holder.name.setText(messageModel.getMessage());
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.teal_200));

        }

    }

    @Override
    public int getItemCount() {
        return messageModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        CardView cardView;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name=itemView.findViewById(R.id.message1);
            cardView=itemView.findViewById(R.id.card);
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
