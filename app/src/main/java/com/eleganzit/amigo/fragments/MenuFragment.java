package com.eleganzit.amigo.fragments;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eleganzit.amigo.CalendarActivity;
import com.eleganzit.amigo.CampaignsActivity;
import com.eleganzit.amigo.DonationsActivity;
import com.eleganzit.amigo.EventsActivity;
import com.eleganzit.amigo.FollowingActivity;
import com.eleganzit.amigo.HelpSupportActivity;
import com.eleganzit.amigo.InviteFrindsActivity;
import com.eleganzit.amigo.LoginActivity;
import com.eleganzit.amigo.LoginSessionActivity;
import com.eleganzit.amigo.MyProfileActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.OpportunityActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.ReferEntityActivity;
import com.eleganzit.amigo.SendFeedbackActivity;
import com.eleganzit.amigo.SettingsActivity;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }
    RelativeLayout header;
    LinearLayout lin_events,lin_opportunity,lin_following,lin_donation,lin_calendar,lin_help,lin_settings,lin_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_menu, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_gray);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_green);

        header=v.findViewById(R.id.header);
        lin_events=v.findViewById(R.id.lin_events);
        lin_opportunity=v.findViewById(R.id.lin_opportunity);
        lin_following=v.findViewById(R.id.lin_following);
        lin_donation=v.findViewById(R.id.lin_donation);
        lin_calendar=v.findViewById(R.id.lin_calendar);
        lin_help=v.findViewById(R.id.lin_help);
        lin_settings=v.findViewById(R.id.lin_settings);
        lin_logout=v.findViewById(R.id.lin_logout);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyProfileActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lin_opportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OpportunityActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FollowingActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DonationsActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CalendarActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HelpSupportActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginSessionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        return v;
    }

}
