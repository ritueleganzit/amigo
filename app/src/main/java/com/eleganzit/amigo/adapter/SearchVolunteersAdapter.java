package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.SearchResultsActivity;
import com.eleganzit.amigo.UserProfileActivity;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.SendRequestDataResponse;
import com.eleganzit.amigo.model.searchDataClasses.VolunteersData;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SearchVolunteersAdapter extends RecyclerView.Adapter<SearchVolunteersAdapter.MyViewHolder>
{

    ArrayList<VolunteersData> volunteers;
    Context context;
    Activity activity;
    UserLoggedInSession userSessionManager;
    HashMap<String, String> userInfo;

    public SearchVolunteersAdapter(ArrayList<VolunteersData> volunteers, Context context) {
        this.volunteers = volunteers;
        this.context = context;
        activity = (Activity) context;
        userSessionManager=new UserLoggedInSession(context);
        userInfo=userSessionManager.getUserDetails();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_pages_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final VolunteersData volunteersData=volunteers.get(i);

        if(volunteersData.getFail_status().equalsIgnoreCase("0"))
        {
            if(volunteersData.getRequest_user_id().equalsIgnoreCase(userInfo.get(UserLoggedInSession.USER_ID)))
            {
                if(volunteersData.getRequest_status().equalsIgnoreCase("0") ||volunteersData.getRequest_status().equalsIgnoreCase(""))
                {
                    holder.follow.setImageResource(R.mipmap.icon_follow);

                }
                else if(volunteersData.getRequest_status().equalsIgnoreCase("pending"))
                {
                    holder.follow.setImageResource(R.mipmap.icon_requested);
                    volunteersData.setRequest_status("pending");

                }
                else if(volunteersData.getRequest_status().equalsIgnoreCase("accept"))
                {
                    holder.follow.setVisibility(View.GONE);
                    volunteersData.setRequest_status("accept");

                }

            }
            else
            {
                holder.follow.setVisibility(View.GONE);
            }

        }
        else
        {
            holder.follow.setImageResource(R.mipmap.icon_follow);
        }

        Glide
                .with(context)
                .asBitmap()
                .load(volunteersData.getPhoto())
                .apply(new RequestOptions().override(150, 150).transforms(new RoundedCorners(8)).centerCrop())
                .thumbnail(.1f)
                .into(holder.profilePhoto);

        holder.txt_fullname.setText(volunteersData.getFullname()+"");
        holder.txt_followers.setText(volunteersData.getFollowers()+" followers");


        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(volunteersData.getIsFollowed().equalsIgnoreCase("0"))
                {
                    sendFollowRequest(holder.follow,volunteersData);
                }
                else if(volunteersData.getIsFollowed().equalsIgnoreCase("pending"))
                {
                    cancelFollowRequest(holder.follow,volunteersData);
                }

            }
        });


        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences viewUserPref=context.getSharedPreferences("viewUserPref",MODE_PRIVATE);
                SharedPreferences.Editor viewUserEditor=viewUserPref.edit();

                viewUserEditor.putString("viewUserId",volunteersData.getVolunteer_id());
                viewUserEditor.commit();

                context.startActivity(new Intent(context,UserProfileActivity.class)
                .putExtra("userid",volunteersData.getVolunteer_id()));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });


    }

    @Override
    public int getItemCount() {
        return volunteers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView follow;
        RelativeLayout main;
        ImageView profilePhoto;
        TextView txt_fullname,txt_followers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            main=itemView.findViewById(R.id.main);
            follow=itemView.findViewById(R.id.follow);
            profilePhoto=itemView.findViewById(R.id.profilePhoto);
            txt_fullname=itemView.findViewById(R.id.txt_fullname);
            txt_followers=itemView.findViewById(R.id.txt_followers);

        }
    }

    public void sendFollowRequest(final ImageView follow, final VolunteersData volunteersData)
    {
        follow.setImageResource(R.mipmap.icon_requested);
        volunteersData.setIsFollowed("pending");
        volunteersData.setRequest_status("0");

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendRequestDataResponse> call = myInterface.sendFollowRequest(volunteersData.getVolunteer_id(),userInfo.get(UserLoggedInSession.USER_ID));
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {

                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        follow.setImageResource(R.mipmap.icon_requested);
                        volunteersData.setIsFollowed("pending");
                        volunteersData.setRequest_status("pending");

                        Toast.makeText(context, "Request sent successfully", Toast.LENGTH_SHORT).show();

                    } else {

                        follow.setImageResource(R.mipmap.icon_follow);
                        volunteersData.setIsFollowed("0");
                        volunteersData.setRequest_status("0");

                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();

                        Toast.makeText(context, "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    follow.setImageResource(R.mipmap.icon_follow);
                    volunteersData.setIsFollowed("0");
                    volunteersData.setRequest_status("0");

                    Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {

                Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                follow.setImageResource(R.mipmap.icon_follow);
                volunteersData.setIsFollowed("0");
                volunteersData.setRequest_status("0");

            }
        });
    }

    public void cancelFollowRequest(final ImageView follow, final VolunteersData volunteersData)
    {
        follow.setImageResource(R.mipmap.icon_follow);
        volunteersData.setIsFollowed("0");
        volunteersData.setRequest_status("0");

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendRequestDataResponse> call = myInterface.cancelFollowRequest(volunteersData.getRequest_id(),"0");
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {

                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        follow.setImageResource(R.mipmap.icon_follow);
                        volunteersData.setIsFollowed("0");
                        volunteersData.setRequest_status("0");

                        Toast.makeText(context, "Request cancelled successfully", Toast.LENGTH_SHORT).show();

                    } else {

                        follow.setImageResource(R.mipmap.icon_requested);
                        volunteersData.setIsFollowed("pending");
                        volunteersData.setRequest_status("pending");

                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();

                        Toast.makeText(context, "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    follow.setImageResource(R.mipmap.icon_requested);
                    volunteersData.setIsFollowed("pending");
                    volunteersData.setRequest_status("pending");

                    Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {

                Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                follow.setImageResource(R.mipmap.icon_requested);
                volunteersData.setIsFollowed("pending");
                volunteersData.setRequest_status("pending");

            }
        });
    }


}