package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.FollowersData;
import com.eleganzit.amigo.model.PagesData;
import com.eleganzit.amigo.model.SendRequestDataResponse;
import com.eleganzit.amigo.model.friendlist.FriendList;
import com.eleganzit.amigo.model.friendlist.FriendListResponse;
import com.eleganzit.amigo.model.searchDataClasses.VolunteersData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.MyViewHolder>
{

   List<FriendList> followers;
    Context context;
    Activity activity;

    public FollowersAdapter(List<FriendList> followers, Context context) {
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

        final FriendList followersData=followers.get(i);

holder.txt_username.setText(followersData.getFullname());
holder.txt_followers.setText(followersData.getCountFriend()+" Followers");
        Glide.with(context).load(followersData.getPhoto()).into(holder.pagePhoto);
        Toast.makeText(context, ""+followersData.getStatus(), Toast.LENGTH_SHORT).show();
        if(followersData.getStatus().equalsIgnoreCase("accept"))
        {


            holder.rel_follow_bg.setBackgroundResource(R.drawable.rounded_yellow_bg);
            holder.txt_follow.setText("Following");

        }
        else
        {
            holder.rel_follow_bg.setBackgroundResource(R.drawable.rounded_green_bg);
            holder.txt_follow.setText("Follow");
        }
        holder.rel_follow_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.txt_follow.getText().toString().equalsIgnoreCase("Follow"))
                {


                    holder.rel_follow_bg.setBackgroundResource(R.drawable.rounded_yellow_bg);
                    holder.txt_follow.setText("Following");

                }
                else
                {

                    holder.rel_follow_bg.setBackgroundResource(R.drawable.rounded_green_bg);
                    holder.txt_follow.setText("Follow");
                }

            }
        });
        holder.rel_remove_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followRequest(holder,followersData.getRequestId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel_follow_bg,rel_remove_bg;
        TextView txt_follow,txt_username,txt_followers;
ImageView pagePhoto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rel_follow_bg=itemView.findViewById(R.id.rel_follow_bg);
            rel_remove_bg=itemView.findViewById(R.id.rel_remove_bg);
            pagePhoto=itemView.findViewById(R.id.pagePhoto);
            txt_follow=itemView.findViewById(R.id.txt_follow);
            txt_username=itemView.findViewById(R.id.txt_username);
            txt_followers=itemView.findViewById(R.id.txt_followers);


        }
    }



    public void followRequest(final MyViewHolder holder, String request_id)
    {


        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendRequestDataResponse> call = myInterface.cancelFollowRequest(request_id,"0");
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {

                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {



                        Toast.makeText(context, "Request cancelled successfully", Toast.LENGTH_SHORT).show();

                    } else {



                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();

                      //  Toast.makeText(context, "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {


                    Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {

                Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();


            }
        });
    }
}
