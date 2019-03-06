package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.FollowersData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VolunteersAdapter extends RecyclerView.Adapter<VolunteersAdapter.MyViewHolder>
{

    ArrayList<FollowersData> followers;
    Context context;
    Activity activity;

    public VolunteersAdapter(ArrayList<FollowersData> followers, Context context) {
        this.followers = followers;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.volunteers_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {


    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
