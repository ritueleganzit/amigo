package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.ViewEventActivity;
import com.eleganzit.amigo.model.DonationsData;
import com.eleganzit.amigo.model.EventsData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.MyViewHolder>
{

    ArrayList<DonationsData> donations;
    Context context;
    Activity activity;

    public DonationsAdapter(ArrayList<DonationsData> donations, Context context) {
        this.donations = donations;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.donations_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.donation_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout donation_main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            donation_main=itemView.findViewById(R.id.donation_main);

        }
    }
}