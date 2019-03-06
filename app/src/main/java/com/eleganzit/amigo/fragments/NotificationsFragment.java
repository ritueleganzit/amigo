package com.eleganzit.amigo.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.adapter.AllNotificationsAdapter;
import com.eleganzit.amigo.adapter.FollowersAdapter;
import com.eleganzit.amigo.model.FollowersData;
import com.eleganzit.amigo.model.NotificationData;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


    public NotificationsFragment() {
        // Required empty public constructor
    }

    RecyclerView rc_notifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_notifications, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_gray);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_green);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);

        rc_notifications=v.findViewById(R.id.rc_notifications);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_notifications.setLayoutManager(layoutManager);

        NotificationData notificationData =new NotificationData("","","","false");

        ArrayList<NotificationData> ar_notifications=new ArrayList<>();

        if(ar_notifications.size()>0)
        {
            ar_notifications.clear();
        }

        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);

        rc_notifications.setAdapter(new AllNotificationsAdapter(ar_notifications,getActivity()));

        return v;
    }

}
