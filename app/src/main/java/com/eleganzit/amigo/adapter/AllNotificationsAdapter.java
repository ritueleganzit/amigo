package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.UserProfileActivity;
import com.eleganzit.amigo.model.NotificationData;
import com.eleganzit.amigo.model.PagesData;
import com.eleganzit.amigo.model.getfriendrequest.RequestData;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.eleganzit.amigo.utils.TextViewRobotoRegular;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class AllNotificationsAdapter extends RecyclerView.Adapter<AllNotificationsAdapter.MyViewHolder>
{


   List<RequestData> notifications;
    Context context;
    Activity activity;


    public AllNotificationsAdapter(List<RequestData> notifications, Context context) {
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
final RequestData requestData=notifications.get(i);
holder.txt_name.setText(""+requestData.getFullname());
holder.rel_request_row.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        context.startActivity(new Intent(context, UserProfileActivity.class)
        .putExtra("userid",""+requestData.getRequestUserId()));
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
});

        Glide.with(context).load(""+requestData.getPhoto()).into(holder.userPhoto);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            long time = sdf.parse(requestData.getCreatedDate()).getTime();
            long now = System.currentTimeMillis();

            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);

            if (ago.toString().equalsIgnoreCase("0 minutes ago"))
            {
                holder.txt_time.setText("Just Now");
            }
            else
            {
                holder.txt_time.setText(ago+"");

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
ImageView userPhoto;
RelativeLayout rel_request_row;
TextViewRobotoBold txt_name;
TextViewRobotoRegular txt_time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userPhoto=itemView.findViewById(R.id.userPhoto);
            txt_name=itemView.findViewById(R.id.txt_name);
            rel_request_row=itemView.findViewById(R.id.rel_request_row);
            txt_time=itemView.findViewById(R.id.txt_time);

        }
    }
}