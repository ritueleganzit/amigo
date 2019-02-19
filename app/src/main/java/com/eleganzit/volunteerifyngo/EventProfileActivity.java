package com.eleganzit.volunteerifyngo;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;

public class EventProfileActivity extends AppCompatActivity {

    ImageView img_interested;
    TextView txt_interested;
    boolean liked=false;
    LinearLayout event_options;
    EditText ed_search;
    ImageView notification_bell,chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_profile);

        img_interested=findViewById(R.id.img_interested);
        txt_interested=findViewById(R.id.txt_interested);
        event_options=findViewById(R.id.event_options);
        ed_search=findViewById(R.id.ed_search);
        notification_bell=findViewById(R.id.notification_bell);
        chat=findViewById(R.id.chat);

        ed_search.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventProfileActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EventProfileActivity.this,NotificationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EventProfileActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });


        img_interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(liked)
                {
                    liked=false;
                    img_interested.setImageResource(R.mipmap.icon_star_empty);
                    txt_interested.setTextColor(Color.parseColor("#919191"));
                }
                else
                {
                    liked=true;
                    img_interested.setImageResource(R.mipmap.icon_star_filled);
                    txt_interested.setTextColor(Color.parseColor("#0f2536"));
                }

            }
        });

        event_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
