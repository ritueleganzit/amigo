package com.eleganzit.amigo.fragments;


import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eleganzit.amigo.MessageActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.adapter.FollowersAdapter;
import com.eleganzit.amigo.adapter.MessagesAdapter;
import com.eleganzit.amigo.model.FollowersData;
import com.eleganzit.amigo.model.MessagesData;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {


    public FollowersFragment() {
        // Required empty public constructor
    }

    RecyclerView rc_followers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_followers, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_green);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);

        rc_followers=v.findViewById(R.id.rc_followers);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_followers.setLayoutManager(layoutManager);

        FollowersData followersData =new FollowersData("","","","false");

        ArrayList<FollowersData> ar_followers=new ArrayList<>();

        if(ar_followers.size()>0)
        {
            ar_followers.clear();
        }

        ar_followers.add(followersData);
        ar_followers.add(followersData);
        ar_followers.add(followersData);
        ar_followers.add(followersData);
        ar_followers.add(followersData);
        ar_followers.add(followersData);
        ar_followers.add(followersData);
        ar_followers.add(followersData);

        rc_followers.setAdapter(new FollowersAdapter(ar_followers,getActivity()));

        return v;
    }

}
