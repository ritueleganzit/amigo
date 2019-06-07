package com.eleganzit.amigo.fragments;


import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.amigo.CommentsActivity;
import com.eleganzit.amigo.MessageActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.adapter.FollowersAdapter;
import com.eleganzit.amigo.adapter.MessagesAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.FollowersData;
import com.eleganzit.amigo.model.MessagesData;
import com.eleganzit.amigo.model.friendlist.FriendList;
import com.eleganzit.amigo.model.friendlist.FriendListResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {


    public FollowersFragment() {
        // Required empty public constructor
    }
    UserLoggedInSession userLoggedInSession;
    ArrayList<FriendList> ar_followers=new ArrayList<>();

    RecyclerView rc_followers;
    String user_id;
    TextView followerstxt;
    ProgressBar progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_followers, container, false);
        userLoggedInSession = new UserLoggedInSession(getActivity());
        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);
        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_green);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);

        rc_followers=v.findViewById(R.id.rc_followers);
        progress=v.findViewById(R.id.progress);
        followerstxt=v.findViewById(R.id.followerstxt);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_followers.setLayoutManager(layoutManager);

        //FollowersData followersData =new FollowersData("","","","false");
        getFriendList();



        return v;
    }
    public void getFriendList()
    {

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<FriendListResponse> friendListResponseCall=myInterface.getmyFriendsList(user_id);
        friendListResponseCall.enqueue(new Callback<FriendListResponse>() {
            @Override
            public void onResponse(Call<FriendListResponse> call, Response<FriendListResponse> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful())
                {


                    Log.d("sssss",""+response.body().getData());
                    if (response.body().getData()!=null)
                    {
                        followerstxt.setVisibility(View.GONE);
                     //   Toast.makeText(getActivity(), "sucess", Toast.LENGTH_SHORT).show();

                        rc_followers.setAdapter(new FollowersAdapter(response.body().getData(),getActivity()));

                    }
                    else
                    {
                        followerstxt.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendListResponse> call, Throwable t) {
                followerstxt.setVisibility(View.VISIBLE);
                rc_followers.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);

                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
