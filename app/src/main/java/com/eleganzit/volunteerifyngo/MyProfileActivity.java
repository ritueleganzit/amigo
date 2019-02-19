package com.eleganzit.volunteerifyngo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MyProfileActivity extends AppCompatActivity {

    LinearLayout edit_profile;
    EditText ed_search;
    ImageView notification_bell,chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        edit_profile=findViewById(R.id.edit_profile);
        ed_search=findViewById(R.id.ed_search);
        notification_bell=findViewById(R.id.notification_bell);
        chat=findViewById(R.id.chat);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this,EditProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        ed_search.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MyProfileActivity.this,NotificationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MyProfileActivity.this,MessageActivity.class));
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
