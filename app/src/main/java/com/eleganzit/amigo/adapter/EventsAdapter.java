package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.EventProfileActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.databinding.EventsMainLayoutBinding;
import com.eleganzit.amigo.databinding.OpportunityRowBinding;
import com.eleganzit.amigo.model.EventsData;
import com.eleganzit.amigo.model.GetEvents;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder>
{

   List<GetEvents> events;
    Context context;
    Activity activity;

    public EventsAdapter (List<GetEvents> events, Context context) {
        this.events = events;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        EventsMainLayoutBinding eventsMainLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.events_main_layout,viewGroup,false);



        return new MyViewHolder(eventsMainLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final GetEvents getEvents=events.get(i);

        if (getEvents.getEventTime()!=null && (!getEvents.getEventTime().isEmpty()))
        {

                holder.eventsMainLayoutBinding.eventsTime.setText(""+getEvents.getEventTime());

        }
        if (getEvents.getEventName()!=null && (!getEvents.getEventName().isEmpty()))
        {
            holder.eventsMainLayoutBinding.name.setText(getEvents.getEventName());
        }
        if (getEvents.getEventAddress()!=null && (!getEvents.getEventAddress().isEmpty()))
        {
            if (!getEvents.getEventAddress().equalsIgnoreCase("null"))
            {
                holder.eventsMainLayoutBinding.evLoc.setText(""+getEvents.getEventAddress());
            }

        }

        if (getEvents.getEventPhoto()!=null && (!getEvents.getEventPhoto().isEmpty()))
        {

            Glide.with(context).load(getEvents.getEventPhoto()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)).into(holder.eventsMainLayoutBinding.eventBanner);

        }


        if (getEvents.getEventDate()!=null && (!getEvents.getEventDate().isEmpty()))
        {
            if (!getEvents.getEventDate().equalsIgnoreCase("0000-00-00"))
            {
                String input_date=""+getEvents.getEventDate();
                SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                Date dt1= null;
                try {
                    dt1 = format1.parse(input_date);
                    DateFormat format2=new SimpleDateFormat("MMM");
                    DateFormat format=new SimpleDateFormat("dd");
                    String month=format2.format(dt1);
                    String day=format.format(dt1);

                    Log.d("aadapter",""+month);
                    Log.d("aadapter",""+getEvents.getEventAddress());
                    Log.d("aadapter",""+day+" "+getEvents.getEventDate());
                    holder.eventsMainLayoutBinding.evMonth.setText(""+month);
                    holder.eventsMainLayoutBinding.evDay.setText(""+day);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }



        holder.eventsMainLayoutBinding.eventMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EventProfileActivity.class).putExtra("event",getEvents));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                activity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        EventsMainLayoutBinding eventsMainLayoutBinding;

        public MyViewHolder(@NonNull  EventsMainLayoutBinding eventsMainLayoutBinding) {
            super(eventsMainLayoutBinding.getRoot());
            this.eventsMainLayoutBinding=eventsMainLayoutBinding  ;

        }
    }
}