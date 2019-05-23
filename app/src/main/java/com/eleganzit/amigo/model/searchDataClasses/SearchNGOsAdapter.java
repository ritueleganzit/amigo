package com.eleganzit.amigo.model.searchDataClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//public class SearchNGOsAdapter extends RecyclerView.Adapter<SearchNGOsAdapter.MyViewHolder>
//{
/*
    ArrayList<NGOData> ngos;
    Context context;
    Activity activity;
    UserLoggedInSession userSessionManager;
    HashMap<String, String> userInfo;

    public SearchNGOsAdapter(ArrayList<NGOData> ngos, Context context) {
        this.ngos = ngos;
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

        final NGOData ngoData=ngos.get(i);


        if(ngoData.getFail_status().equalsIgnoreCase("0"))
        {
            if(ngoData.getRequest_user_id().equalsIgnoreCase(userInfo.get(UserLoggedInSession.USER_ID)))
            {
                if(ngoData.getRequest_status().equalsIgnoreCase("0") || ngoData.getRequest_status().equalsIgnoreCase(""))
                {
                    holder.follow.setImageResource(R.mipmap.icon_follow);
                }
                else if(ngoData.getRequest_status().equalsIgnoreCase("pending"))
                {
                    holder.follow.setImageResource(R.mipmap.icon_requested);
                }
                else if(ngoData.getRequest_status().equalsIgnoreCase("accept"))
                {
                    holder.follow.setVisibility(View.GONE);
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
                .load(ngoData.getPhoto())
                .apply(new RequestOptions().override(150, 150).transforms(new RoundedCorners(8)).centerCrop())
                .thumbnail(.1f)
                .into(holder.profilePhoto);

        holder.txt_fullname.setText(ngoData.getFullname()+"");
        holder.txt_followers.setText(ngoData.getFollowers()+" followers");

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ngoData.getIsFollowed().equalsIgnoreCase("0"))
                {
                    sendFollowRequest(holder.follow,ngoData);
                }
                else if(ngoData.getIsFollowed().equalsIgnoreCase("pending"))
                {
                    cancelFollowRequest(holder.follow,ngoData);
                }

            }
        });

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences viewUserPref=context.getSharedPreferences("viewUserPref",MODE_PRIVATE);
                SharedPreferences.Editor viewUserEditor=viewUserPref.edit();

                viewUserEditor.putString("viewUserId",ngoData.getAmigo_id());
                viewUserEditor.commit();

                context.startActivity(new Intent(context,UserProfileActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ngos.size();
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

    public void sendFollowRequest(final ImageView follow, final NGOData ngoData)
    {
        follow.setImageResource(R.mipmap.icon_requested);
        ngoData.setIsFollowed("pending");
        follow.setEnabled(false);

        MyInterface myInterface = RetrofitApiClient.getRetrofit().create(MyInterface.class);
        Call<SendRequestDataResponse> call = myInterface.sendFollowRequest(ngoData.getAmigo_id(),userInfo.get(UserSessionManager.KEY_USER_ID));
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {

                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    follow.setEnabled(true);
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        follow.setImageResource(R.mipmap.icon_requested);
                        ngoData.setIsFollowed("pending");

                        Toast.makeText(context, "Request sent successfully", Toast.LENGTH_SHORT).show();

                    } else {

                        follow.setImageResource(R.mipmap.icon_follow);
                        ngoData.setIsFollowed("0");

                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();

                        Toast.makeText(context, "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    follow.setImageResource(R.mipmap.icon_follow);
                    ngoData.setIsFollowed("0");
                    follow.setEnabled(true);
                    Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {

                Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                follow.setImageResource(R.mipmap.icon_follow);
                ngoData.setIsFollowed("0");
                follow.setEnabled(true);
            }
        });
    }

    public void cancelFollowRequest(final ImageView follow, final NGOData ngoData)
    {
        follow.setImageResource(R.mipmap.icon_follow);
        ngoData.setIsFollowed("0");
        follow.setEnabled(false);
        MyInterface myInterface = RetrofitApiClient.getRetrofit().create(MyInterface.class);
        Call<SendRequestDataResponse> call = myInterface.cancelFollowRequest(ngoData.getRequest_id(),"0");
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {

                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    follow.setEnabled(true);
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        follow.setImageResource(R.mipmap.icon_follow);
                        ngoData.setIsFollowed("0");

                        Toast.makeText(context, "Request cancelled successfully", Toast.LENGTH_SHORT).show();

                    } else {

                        follow.setImageResource(R.mipmap.icon_requested);
                        ngoData.setIsFollowed("pending");

                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();

                        Toast.makeText(context, "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    follow.setImageResource(R.mipmap.icon_requested);
                    ngoData.setIsFollowed("pending");
                    follow.setEnabled(true);
                    Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {

                Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                follow.setImageResource(R.mipmap.icon_requested);
                ngoData.setIsFollowed("pending");
                follow.setEnabled(true);
            }
        });
    }*/

//}
