package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.amigo.EventProfileActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.SearchResultsActivity;
import com.eleganzit.amigo.ViewEventActivity;
import com.eleganzit.amigo.model.searchDataClasses.EventsData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchEventsAdapter extends RecyclerView.Adapter<SearchEventsAdapter.MyViewHolder>
{

    ArrayList<EventsData> events;

    Context context;
    Activity activity;
    boolean liked=false;

    public SearchEventsAdapter(ArrayList<EventsData> events, Context context) {
        this.events = events;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_events_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final EventsData eventsData=events.get(i);

        holder.event_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EventProfileActivity.class).putExtra("eventid",eventsData.getEvent_id()));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        if(eventsData.getDate()!=null && !eventsData.getDate().isEmpty() && !eventsData.getDate().equalsIgnoreCase("0000-00-00"))
        {
            String[] date=parseDateToddMMyyyy2(eventsData.getDate()).split("-");

            holder.txt_month.setText(date[1]);
            holder.txt_date.setText(date[2]);
        }

        holder.txt_title.setText(eventsData.getTitle()+"");

        if(eventsData.getTime()!=null && !eventsData.getTime().isEmpty())
        {
            holder.txt_time.setText(parseTimeToAMPM(eventsData.getTime()));
        }

        holder.txt_address.setText(eventsData.getLocation());

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout event_main;
        TextView txt_month,txt_date,txt_title,txt_time,txt_address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event_main=itemView.findViewById(R.id.event_main);
            txt_month=itemView.findViewById(R.id.txt_month);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_time=itemView.findViewById(R.id.txt_time);
            txt_address=itemView.findViewById(R.id.txt_address);

        }
    }


    public String parseDateToddMMyyyy2(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "yyyy-MMM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String parseTimeToAMPM(String time) {
        String inputPattern = "HH:mm:ss";
        String outputPattern = "HH:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
