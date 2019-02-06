package com.eleganzit.volunteerifyngo;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.eleganzit.volunteerifyngo.adapter.NewsFeedAdapter;
import com.eleganzit.volunteerifyngo.model.NewsFeedData;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.OverlayView;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltipUtils;


public class NewsFeedActivity extends AppCompatActivity {

    EditText ed_search,ed_status;
    RecyclerView rc_posts;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList=new ArrayList<>();
    ImageView btm_feed,btm_event,btm_user,btm_menu,notification_bell,chat;
    RelativeLayout rbtm_event;
    NestedScrollView news_feed_scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        news_feed_scroll=findViewById(R.id.news_feed_scroll);
        ed_search=findViewById(R.id.ed_search);
        ed_status=findViewById(R.id.ed_status);
        rc_posts=findViewById(R.id.rc_posts);
        notification_bell=findViewById(R.id.notification_bell);
        chat=findViewById(R.id.chat);
        btm_feed=findViewById(R.id.btm_feed);
        rbtm_event=findViewById(R.id.rbtm_event);
        btm_event=findViewById(R.id.btm_event);
        btm_user=findViewById(R.id.btm_user);
        btm_menu=findViewById(R.id.btm_menu);


        btm_feed.setImageResource(R.drawable.feed_green);
        btm_event.setImageResource(R.drawable.event_gray);
        btm_user.setImageResource(R.drawable.user_gray);
        btm_menu.setImageResource(R.drawable.menu_gray);

        news_feed_scroll.scrollTo(0,0);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_posts.setLayoutManager(layoutManager);

        ed_search.setLongClickable(false);
        ed_status.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsFeedActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        ed_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsFeedActivity.this,CreatePostActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        /*ImgarrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        ImgarrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        */
        NewsFeedData newsFeedData=new NewsFeedData("zahir",imgArrayList);

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        rc_posts.setAdapter(new NewsFeedAdapter(dataArrayList,NewsFeedActivity.this));

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

        btm_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btm_feed.setImageResource(R.drawable.feed_green);
                btm_event.setImageResource(R.drawable.event_gray);
                btm_user.setImageResource(R.drawable.user_gray);
                btm_menu.setImageResource(R.drawable.menu_gray);
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
                btm_feed.setImageResource(R.drawable.feed_gray);
                btm_event.setImageResource(R.drawable.event_gray);
                btm_user.setImageResource(R.drawable.user_green);
                btm_menu.setImageResource(R.drawable.menu_gray);
            }
        });

        btm_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btm_feed.setImageResource(R.drawable.feed_gray);
                btm_event.setImageResource(R.drawable.event_gray);
                btm_user.setImageResource(R.drawable.user_gray);
                btm_menu.setImageResource(R.drawable.menu_green);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

}
