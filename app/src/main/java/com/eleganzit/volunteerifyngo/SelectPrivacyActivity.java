package com.eleganzit.volunteerifyngo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SelectPrivacyActivity extends AppCompatActivity {

    RadioButton radioBtn_public,radioBtn_friends,radioBtn_only_me;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RelativeLayout rel_public,rel_friends,rel_only_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_privacy);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        preferences=getSharedPreferences("post_data",MODE_PRIVATE);
        editor=preferences.edit();

        rel_public=findViewById(R.id.rel_public);
        rel_friends=findViewById(R.id.rel_friends);
        rel_only_me=findViewById(R.id.rel_only_me);
        radioBtn_public=findViewById(R.id.radioBtn_public);
        radioBtn_friends=findViewById(R.id.radioBtn_friends);
        radioBtn_only_me=findViewById(R.id.radioBtn_only_me);

        if(preferences.getString("post_privacy","Public").equalsIgnoreCase("public"))
        {
            radioBtn_public.setChecked(true);
        }
        else if(preferences.getString("post_privacy","Public").equalsIgnoreCase("friends"))
        {
            radioBtn_friends.setChecked(true);
        }
        else
        {
            radioBtn_only_me.setChecked(true);
        }

        radioBtn_public.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    radioBtn_friends.setEnabled(false);
                    radioBtn_only_me.setEnabled(false);
                    editor.putString("post_privacy","Public");
                    editor.commit();
                    radioBtn_public.setChecked(true);
                    radioBtn_friends.setChecked(false);
                    radioBtn_only_me.setChecked(false);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
            }
        });

        radioBtn_friends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    radioBtn_public.setEnabled(false);
                    radioBtn_only_me.setEnabled(false);
                    editor.putString("post_privacy", "Friends");
                    editor.commit();
                    radioBtn_public.setChecked(false);
                    radioBtn_friends.setChecked(true);
                    radioBtn_only_me.setChecked(false);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });

        radioBtn_only_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    radioBtn_public.setEnabled(false);
                    radioBtn_friends.setEnabled(false);
                    editor.putString("post_privacy", "Only me");
                    editor.commit();
                    radioBtn_public.setChecked(false);
                    radioBtn_friends.setChecked(false);
                    radioBtn_only_me.setChecked(true);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });

        rel_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_public.setChecked(true);
            }
        });

        rel_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_friends.setChecked(true);
            }
        });

        rel_only_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn_only_me.setChecked(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
