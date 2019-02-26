package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eleganzit.volunteerifyngo.EventProfileActivity;
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.ViewCampaignActivity;
import com.eleganzit.volunteerifyngo.model.CampaignsData;
import com.eleganzit.volunteerifyngo.model.EventsData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CampaignsAdapter extends RecyclerView.Adapter<CampaignsAdapter.MyViewHolder>
{

    ArrayList<CampaignsData> campaigns;
    Context context;
    Activity activity;

    public CampaignsAdapter(ArrayList<CampaignsData> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.campaigns_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.campaign_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ViewCampaignActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout campaign_main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            campaign_main=itemView.findViewById(R.id.campaign_main);

        }
    }
}