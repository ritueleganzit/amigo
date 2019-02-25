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
import com.eleganzit.volunteerifyngo.ViewEventActivity;
import com.eleganzit.volunteerifyngo.model.EventsData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OngoingEventsAdapter extends RecyclerView.Adapter<OngoingEventsAdapter.MyViewHolder>
{

    ArrayList<EventsData> events;
    Context context;
    Activity activity;

    public OngoingEventsAdapter(ArrayList<EventsData> events, Context context) {
        this.events = events;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.events_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.event_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ViewEventActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout event_main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event_main=itemView.findViewById(R.id.event_main);

        }
    }
}