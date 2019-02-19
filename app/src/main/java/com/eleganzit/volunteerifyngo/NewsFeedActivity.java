package com.eleganzit.volunteerifyngo;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eleganzit.volunteerifyngo.fragments.FollowersFragment;
import com.eleganzit.volunteerifyngo.fragments.HomeFeedFragment;
import com.eleganzit.volunteerifyngo.fragments.MenuFragment;
import com.eleganzit.volunteerifyngo.fragments.ViewPostFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


public class NewsFeedActivity extends AppCompatActivity {

    public static ImageView btm_feed,btm_event,btm_user,btm_menu;
    public static RelativeLayout rbtm_event;
    public static RelativeLayout news_feed_toolbar,view_post_toolbar;
    public static EditText ed_search;
    public static ImageView notification_bell,chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        news_feed_toolbar=findViewById(R.id.news_feed_toolbar);
        view_post_toolbar=findViewById(R.id.view_post_toolbar);
        ed_search=findViewById(R.id.ed_search);
        btm_feed=findViewById(R.id.btm_feed);
        rbtm_event=findViewById(R.id.rbtm_event);
        btm_event=findViewById(R.id.btm_event);
        btm_user=findViewById(R.id.btm_user);
        btm_menu=findViewById(R.id.btm_menu);
        notification_bell=findViewById(R.id.notification_bell);
        chat=findViewById(R.id.chat);

        btm_feed.setImageResource(R.drawable.feed_green);
        btm_event.setImageResource(R.drawable.event_gray);
        btm_user.setImageResource(R.drawable.user_gray);
        btm_menu.setImageResource(R.drawable.menu_gray);

        ed_search.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsFeedActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NewsFeedActivity.this,NotificationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NewsFeedActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        HomeFeedFragment homeFeedFragment= new HomeFeedFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, homeFeedFragment,"TAG")
                .commit();

        btm_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFeedFragment homeFeedFragment= new HomeFeedFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NewsFeedActivity");
                fragmentTransaction.replace(R.id.frame, homeFeedFragment, "TAG");
                fragmentTransaction.commit();

            }
        });

        btm_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btm_feed.setImageResource(R.drawable.feed_gray);
                btm_event.setImageResource(R.drawable.event_green);
                btm_user.setImageResource(R.drawable.user_gray);
                btm_menu.setImageResource(R.drawable.menu_gray);


            }
        });

        btm_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FollowersFragment followersFragment= new FollowersFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NewsFeedActivity");
                fragmentTransaction.replace(R.id.frame, followersFragment, "TAG");
                fragmentTransaction.commit();
            }
        });

        btm_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment menuFragment= new MenuFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NewsFeedActivity");
                fragmentTransaction.replace(R.id.frame, menuFragment, "TAG");
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

}
