package com.eleganzit.volunteerifyngo.fragments;


import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eleganzit.volunteerifyngo.MessageActivity;
import com.eleganzit.volunteerifyngo.NewsFeedActivity;
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.adapter.FollowersAdapter;
import com.eleganzit.volunteerifyngo.adapter.MessagesAdapter;
import com.eleganzit.volunteerifyngo.model.FollowersData;
import com.eleganzit.volunteerifyngo.model.MessagesData;

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

    RelativeLayout rel_people,rel_ngos,rel_companies,rel_school;
    RecyclerView rc_followers;
    ArrayList<FollowersData> ar_followers=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_followers, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_event.setImageResource(R.drawable.event_gray);
        NewsFeedActivity.btm_user.setImageResource(R.drawable.user_green);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);


        rel_people=v.findViewById(R.id.rel_people);
        rel_ngos=v.findViewById(R.id.rel_ngos);
        rel_companies=v.findViewById(R.id.rel_companies);
        rel_school=v.findViewById(R.id.rel_school);

        rc_followers=v.findViewById(R.id.rc_followers);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_followers.setLayoutManager(layoutManager);

        rel_people.setBackgroundColor(Color.parseColor("#236C3F"));
        rel_ngos.setBackgroundColor(Color.parseColor("#37b34a"));
        rel_companies.setBackgroundColor(Color.parseColor("#37b34a"));
        rel_school.setBackgroundColor(Color.parseColor("#37b34a"));

        rel_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_people.setBackgroundColor(Color.parseColor("#236C3F"));
                rel_ngos.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_companies.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_school.setBackgroundColor(Color.parseColor("#37b34a"));
            }
        });

        rel_ngos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_people.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_ngos.setBackgroundColor(Color.parseColor("#236C3F"));
                rel_companies.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_school.setBackgroundColor(Color.parseColor("#37b34a"));
            }
        });

        rel_companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_people.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_ngos.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_companies.setBackgroundColor(Color.parseColor("#236C3F"));
                rel_school.setBackgroundColor(Color.parseColor("#37b34a"));
            }
        });

        rel_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_people.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_ngos.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_companies.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_school.setBackgroundColor(Color.parseColor("#236C3F"));
            }
        });

        FollowersData followersData =new FollowersData("","","","false");

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
