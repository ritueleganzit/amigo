package com.eleganzit.amigo;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eleganzit.amigo.adapter.MessagesAdapter;
import com.eleganzit.amigo.model.MessagesData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rc_chats;
    ArrayList<MessagesData> ar_chats=new ArrayList<>();

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

        rc_chats=findViewById(R.id.rc_chats);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_chats.setLayoutManager(layoutManager);

        MessagesData messagesData =new MessagesData("","","","","","");
        ar_chats.add(messagesData);
        ar_chats.add(messagesData);
        ar_chats.add(messagesData);

        rc_chats.setAdapter(new MessagesAdapter(ar_chats,MessageActivity.this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
