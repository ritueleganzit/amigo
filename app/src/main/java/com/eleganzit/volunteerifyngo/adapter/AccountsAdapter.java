package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.LoginSelectedActivity;
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.Accounts;
import com.hzn.lib.EasyTransition;
import com.hzn.lib.EasyTransitionOptions;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder>
{

    ArrayList<Accounts> accounts;
    Context context;
    Activity activity;

    public AccountsAdapter(ArrayList<Accounts> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.accounts_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginSelectedActivity.class);
                intent.putExtra("name", accounts.get(i).getUsername());

                // ready for transition options
                EasyTransitionOptions options =
                        EasyTransitionOptions.makeTransitionOptions(
                                activity,
                                holder.userdata);

                // start transition
                EasyTransition.startActivity(intent, options);
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout main;
        LinearLayout userdata;
        TextView username;
        ImageView profile_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            main=itemView.findViewById(R.id.main);
            userdata=itemView.findViewById(R.id.userdata);
            profile_photo=itemView.findViewById(R.id.profile_photo);
            username=itemView.findViewById(R.id.username);
        }
    }
}
