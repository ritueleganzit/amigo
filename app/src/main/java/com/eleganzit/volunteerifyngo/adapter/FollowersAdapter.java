package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.FollowersData;
import com.eleganzit.volunteerifyngo.model.PagesData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.MyViewHolder>
{

    ArrayList<FollowersData> followers;
    Context context;
    Activity activity;

    public FollowersAdapter(ArrayList<FollowersData> followers, Context context) {
        this.followers = followers;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.followers_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        FollowersData followersData=followers.get(i);

        final String[] isFollowing = {followersData.getIsFollowing()};

        holder.rel_follow_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFollowing[0].equalsIgnoreCase("true"))
                {
                    isFollowing[0] ="false";
                    holder.rel_follow_bg.setBackgroundResource(R.drawable.rounded_green_bg);
                    holder.txt_follow.setText("Follow");
                }
                else
                {
                    isFollowing[0] ="true";
                    holder.rel_follow_bg.setBackgroundResource(R.drawable.rounded_yellow_bg);
                    holder.txt_follow.setText("Following");
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel_follow_bg,rel_remove_bg;
        TextView txt_follow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rel_follow_bg=itemView.findViewById(R.id.rel_follow_bg);
            rel_remove_bg=itemView.findViewById(R.id.rel_remove_bg);
            txt_follow=itemView.findViewById(R.id.txt_follow);


        }
    }
}
