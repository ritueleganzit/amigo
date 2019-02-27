package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.FollowersData;
import com.eleganzit.volunteerifyngo.model.PaymentsData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.MyViewHolder>
{

    ArrayList<PaymentsData> payments;
    Context context;
    Activity activity;

    public PaymentsAdapter(ArrayList<PaymentsData> payments, Context context) {
        this.payments = payments;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payments_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}