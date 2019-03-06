package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.PlacesData;

import java.util.ArrayList;

public class SearchPlacesAdapter extends RecyclerView.Adapter<SearchPlacesAdapter.MyViewHolder>
{

    ArrayList<PlacesData> places;
    Context context;
    Activity activity;
    boolean liked=false;

    public SearchPlacesAdapter(ArrayList<PlacesData> places, Context context) {
        this.places = places;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_pages_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(liked)
                {
                    liked=false;
                    holder.like.setImageResource(R.mipmap.like_gray);
                }
                else
                {
                    liked=true;
                    holder.like.setImageResource(R.mipmap.like_green);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView like;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            like=itemView.findViewById(R.id.like);


        }
    }
}