package com.eleganzit.volunteerifyngo.fragments;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eleganzit.volunteerifyngo.MyProfileActivity;
import com.eleganzit.volunteerifyngo.NewsFeedActivity;
import com.eleganzit.volunteerifyngo.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }
    RelativeLayout header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_menu, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_event.setImageResource(R.drawable.event_gray);
        NewsFeedActivity.btm_user.setImageResource(R.drawable.user_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_green);

        header=v.findViewById(R.id.header);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyProfileActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        return v;
    }

}
