package com.eleganzit.amigo.fragments;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.CalendarActivity;
import com.eleganzit.amigo.CampaignsActivity;
import com.eleganzit.amigo.DonationListActivity;
import com.eleganzit.amigo.DonationsActivity;
import com.eleganzit.amigo.EventsActivity;
import com.eleganzit.amigo.FollowingActivity;
import com.eleganzit.amigo.HelpSupportActivity;
import com.eleganzit.amigo.InviteFrindsActivity;
import com.eleganzit.amigo.LoginActivity;
import com.eleganzit.amigo.LoginSessionActivity;
import com.eleganzit.amigo.MyProfileActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.OpportunitiesActivity;
import com.eleganzit.amigo.OpportunityActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.ReferEntityActivity;
import com.eleganzit.amigo.SendFeedbackActivity;
import com.eleganzit.amigo.SettingsActivity;
import com.eleganzit.amigo.databinding.FragmentMenuBinding;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.HashMap;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    FragmentMenuBinding fragmentMenuBinding;

    public MenuFragment() {
        // Required empty public constructor
    }
    UserLoggedInSession userLoggedInSession;
    String username,photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        fragmentMenuBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_menu, container, false);
        View view = fragmentMenuBinding.getRoot();


        // Inflate the layout for this fragment
        userLoggedInSession=new UserLoggedInSession(getActivity());
        HashMap<String,String> hashMap=userLoggedInSession.getUserDetails();
        username=hashMap.get(UserLoggedInSession.USERNAME);
        photo=hashMap.get(UserLoggedInSession.PHOTO);
        fragmentMenuBinding.eventName.setText(username);


        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_gray);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_green);


        fragmentMenuBinding.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyProfileActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        fragmentMenuBinding.linEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EventsActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        fragmentMenuBinding.linOpportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OpportunitiesActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        fragmentMenuBinding.linFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FollowingActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        fragmentMenuBinding.linDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DonationListActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        fragmentMenuBinding.linCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CalendarActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        fragmentMenuBinding.linHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HelpSupportActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        fragmentMenuBinding.linSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        fragmentMenuBinding.linLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLoggedInSession.logoutUser();

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this).load(photo).apply(new RequestOptions().circleCrop()).into(fragmentMenuBinding.profilePic);

    }
}
