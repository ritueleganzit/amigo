package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.NotificationData;
import com.eleganzit.volunteerifyngo.model.PagesData;

import java.util.ArrayList;

public class AllNotificationsAdapter extends RecyclerView.Adapter<AllNotificationsAdapter.MyViewHolder>
{

    ArrayList<NotificationData> notifications;
    Context context;
    Activity activity;


    public AllNotificationsAdapter(ArrayList<NotificationData> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_notification_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}