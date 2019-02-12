package com.eleganzit.volunteerifyngo;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eleganzit.volunteerifyngo.adapter.MessagesAdapter;
import com.eleganzit.volunteerifyngo.model.MessagesData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessageActivity extends AppCompatActivity {

    RelativeLayout rel_people,rel_entities,rel_support,rel_events;
    RecyclerView rc_chats;
    ArrayList<MessagesData> ar_chats=new ArrayList<>();
    FloatingActionButton message_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rel_people=findViewById(R.id.rel_people);
        rel_entities=findViewById(R.id.rel_entities);
        rel_support=findViewById(R.id.rel_support);
        rel_events=findViewById(R.id.rel_events);
        message_add=findViewById(R.id.message_add);

        rc_chats=findViewById(R.id.rc_chats);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_chats.setLayoutManager(layoutManager);

        rel_people.setBackgroundColor(Color.parseColor("#236C3F"));
        rel_entities.setBackgroundColor(Color.parseColor("#37b34a"));
        rel_support.setBackgroundColor(Color.parseColor("#37b34a"));
        rel_events.setBackgroundColor(Color.parseColor("#37b34a"));

        rel_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_people.setBackgroundColor(Color.parseColor("#236C3F"));
                rel_entities.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_support.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_events.setBackgroundColor(Color.parseColor("#37b34a"));
            }
        });

        rel_entities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_people.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_entities.setBackgroundColor(Color.parseColor("#236C3F"));
                rel_support.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_events.setBackgroundColor(Color.parseColor("#37b34a"));
            }
        });

        rel_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_people.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_entities.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_support.setBackgroundColor(Color.parseColor("#236C3F"));
                rel_events.setBackgroundColor(Color.parseColor("#37b34a"));
            }
        });

        rel_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_people.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_entities.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_support.setBackgroundColor(Color.parseColor("#37b34a"));
                rel_events.setBackgroundColor(Color.parseColor("#236C3F"));
            }
        });

        MessagesData messagesData =new MessagesData("","","","","","");
        ar_chats.add(messagesData);
        ar_chats.add(messagesData);
        ar_chats.add(messagesData);

        rc_chats.setAdapter(new MessagesAdapter(ar_chats,MessageActivity.this));

        message_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this,AddNewGroupActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
