package com.eleganzit.amigo;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eleganzit.amigo.adapter.AllNotificationsAdapter;
import com.eleganzit.amigo.model.NotificationData;
import com.eleganzit.amigo.model.getfriendrequest.RequestData;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsActivity extends AppCompatActivity {

    RelativeLayout rel_all,rel_alert,rel_offers;
    RecyclerView rc_all;
List<RequestData> ar_notifications=new ArrayList<>();
    ImageView chat;
    EditText ed_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ed_search=findViewById(R.id.ed_search);
        rel_all=findViewById(R.id.rel_all);
        rel_alert=findViewById(R.id.rel_alert);
        rel_offers=findViewById(R.id.rel_offers);
        chat=findViewById(R.id.chat);
        rc_all=findViewById(R.id.rc_all);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_all.setLayoutManager(layoutManager);

        rel_all.setBackgroundColor(Color.parseColor("#236C3F"));
        rel_alert.setBackgroundColor(Color.parseColor("#37b34a"));
        rel_offers.setBackgroundColor(Color.parseColor("#37b34a"));

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotificationsActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        rel_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_all.setBackgroundColor(Color.parseColor("#236C3F"));
                rel_alert.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_offers.setBackgroundColor(Color.parseColor("#37b34a"));
            }
        });

        rel_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_all.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_alert.setBackgroundColor(Color.parseColor("#236C3F"));
                rel_offers.setBackgroundColor(Color.parseColor("#37b34a"));
            }
        });

        rel_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_all.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_alert.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_offers.setBackgroundColor(Color.parseColor("#236C3F"));
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NotificationsActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        /*NotificationData notificationData=new NotificationData("","","","");
        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);
        ar_notifications.add(notificationData);*/

        rc_all.setAdapter(new AllNotificationsAdapter(ar_notifications,NotificationsActivity.this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
