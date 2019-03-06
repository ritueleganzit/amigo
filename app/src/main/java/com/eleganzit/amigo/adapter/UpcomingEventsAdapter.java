package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eleganzit.amigo.ChatActivity;
import com.eleganzit.amigo.EventProfileActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.ViewEventActivity;
import com.eleganzit.amigo.model.EventsData;

import java.util.ArrayList;

public class UpcomingEventsAdapter extends RecyclerView.Adapter<UpcomingEventsAdapter.MyViewHolder>
{

    ArrayList<EventsData> events;
    Context context;
    Activity activity;

    public UpcomingEventsAdapter(ArrayList<EventsData> events, Context context) {
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