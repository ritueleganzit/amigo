package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.GUsersdata;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddedGroupUsersAdapter extends RecyclerView.Adapter<AddedGroupUsersAdapter.MyViewHolder>
{

    ArrayList<GUsersdata> users;
    Context context;
    Activity activity;

    public AddedGroupUsersAdapter(ArrayList<GUsersdata> users, Context context) {
        this.users = users;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.added_group_user_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}