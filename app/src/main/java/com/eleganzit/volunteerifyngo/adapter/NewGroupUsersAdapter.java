package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.GUsersdata;
import com.eleganzit.volunteerifyngo.model.PagesData;

import java.util.ArrayList;

public class NewGroupUsersAdapter extends RecyclerView.Adapter<NewGroupUsersAdapter.MyViewHolder>
{

    ArrayList<GUsersdata> users;
    Context context;
    Activity activity;
    boolean liked=false;

    public NewGroupUsersAdapter(ArrayList<GUsersdata> users, Context context) {
        this.users = users;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_group_user_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout add_user;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            add_user=itemView.findViewById(R.id.add_user);

        }
    }
}
