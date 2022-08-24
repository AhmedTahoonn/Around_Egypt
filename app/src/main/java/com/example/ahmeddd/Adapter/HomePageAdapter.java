package com.example.ahmeddd.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ahmeddd.Drawer.Drawer_Page;
import com.example.ahmeddd.Models.CityModel;
import com.example.ahmeddd.Pages.Home.Home_Page;
import com.example.ahmeddd.R;
import com.example.ahmeddd.RecyclerViewInterface;

import java.util.ArrayList;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

Drawer_Page context;
ArrayList<CityModel> cityModelArrayList;

    public HomePageAdapter(RecyclerViewInterface recyclerViewInterface, Drawer_Page context, ArrayList<CityModel> cityModelArrayList) {
        this.context = context;
        this.cityModelArrayList = cityModelArrayList;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item  , parent, false);
        return new MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
     CityModel cityModel = cityModelArrayList.get(position);
        holder.CityName.setText(cityModel.getCityName());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        //holder.uId.setText(cities.getuId());

        Glide.with(context).load(cityModel.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cityModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView uId, CityName;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            //uId = itemView.findViewById(R.id.cityuid);
            CityName = itemView.findViewById(R.id.cityname);
            imageView=itemView.findViewById(R.id.myImage);
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
