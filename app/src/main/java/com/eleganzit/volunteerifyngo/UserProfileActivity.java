package com.eleganzit.volunteerifyngo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.fragments.AboutFragment;
import com.eleganzit.volunteerifyngo.fragments.EventsFragment;
import com.eleganzit.volunteerifyngo.fragments.HomeFragment;
import com.eleganzit.volunteerifyngo.fragments.OpportunityFragment;
import com.eleganzit.volunteerifyngo.fragments.PhotosFragment;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class UserProfileActivity extends AppCompatActivity {

    ImageView notification_bell,chat;
    TabLayout profile_tabs;
    ViewPager profile_view_pager;
    EditText ed_search;
    TextView tab_home,tab_about,tab_photos,tab_events,tab_opportunity,donate;
    public static RelativeLayout donate_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ed_search=findViewById(R.id.ed_search);
        notification_bell=findViewById(R.id.notification_bell);
        chat=findViewById(R.id.chat);
        donate=findViewById(R.id.donate);
        tab_home=findViewById(R.id.tab_home);
        tab_about=findViewById(R.id.tab_about);
        tab_photos=findViewById(R.id.tab_photos);
        tab_events=findViewById(R.id.tab_events);
        tab_opportunity=findViewById(R.id.tab_opportunity);
        donate_layout=findViewById(R.id.donate_layout);

        ed_search.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserProfileActivity.this,NotificationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserProfileActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(UserProfileActivity.this);
                dialog.setContentView(R.layout.donation_layout);

                RelativeLayout d_main=dialog.findViewById(R.id.d_main);
                d_main.getLayoutParams().width=getScreenWidthInPXs(UserProfileActivity.this,UserProfileActivity.this)*10/15;
                SeekBar donation_seekbar=dialog.findViewById(R.id.donation_seekbar);
                final TextView txtamount=dialog.findViewById(R.id.txtamount);
                final TextView donate=dialog.findViewById(R.id.donate);
                donation_seekbar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                donation_seekbar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                donation_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                        // TODO Auto-generated method stub
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String yourFormattedString = "$"+formatter.format(progress*200);

                        txtamount.setText(yourFormattedString);
                        //Toast.makeText(getActivity(), String.valueOf(progress),Toast.LENGTH_LONG).show();
                    }
                });

                donate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        tab_home.setTextColor(Color.parseColor("#000000"));
        HomeFragment homeFrag= new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, homeFrag,"HomeFragment")
                .commit();

        tab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab_home.setTextColor(Color.parseColor("#000000"));
                tab_about.setTextColor(Color.parseColor("#8c8c8c"));
                tab_photos.setTextColor(Color.parseColor("#8c8c8c"));
                tab_events.setTextColor(Color.parseColor("#8c8c8c"));
                tab_opportunity.setTextColor(Color.parseColor("#8c8c8c"));
                HomeFragment homeFrag= new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, homeFrag,"HomeFragment")
                        .commit();
            }
        });

        tab_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab_home.setTextColor(Color.parseColor("#8c8c8c"));
                tab_about.setTextColor(Color.parseColor("#000000"));
                tab_photos.setTextColor(Color.parseColor("#8c8c8c"));
                tab_events.setTextColor(Color.parseColor("#8c8c8c"));
                tab_opportunity.setTextColor(Color.parseColor("#8c8c8c"));
                AboutFragment aboutFragment= new AboutFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, aboutFragment,"AboutFragment")
                        .commit();
            }
        });

        tab_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab_home.setTextColor(Color.parseColor("#8c8c8c"));
                tab_about.setTextColor(Color.parseColor("#8c8c8c"));
                tab_photos.setTextColor(Color.parseColor("#000000"));
                tab_events.setTextColor(Color.parseColor("#8c8c8c"));
                tab_opportunity.setTextColor(Color.parseColor("#8c8c8c"));
                PhotosFragment photosFragment= new PhotosFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, photosFragment,"PhotosFragment")
                        .commit();
            }
        });

        tab_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab_home.setTextColor(Color.parseColor("#8c8c8c"));
                tab_about.setTextColor(Color.parseColor("#8c8c8c"));
                tab_photos.setTextColor(Color.parseColor("#8c8c8c"));
                tab_events.setTextColor(Color.parseColor("#000000"));
                tab_opportunity.setTextColor(Color.parseColor("#8c8c8c"));
                EventsFragment eventsFragment= new EventsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, eventsFragment,"EventsFragment")
                        .commit();
            }
        });

        tab_opportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab_home.setTextColor(Color.parseColor("#8c8c8c"));
                tab_about.setTextColor(Color.parseColor("#8c8c8c"));
                tab_photos.setTextColor(Color.parseColor("#8c8c8c"));
                tab_events.setTextColor(Color.parseColor("#8c8c8c"));
                tab_opportunity.setTextColor(Color.parseColor("#000000"));
                OpportunityFragment opportunityFragment= new OpportunityFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, opportunityFragment,"OpportunityFragment")
                        .commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public static int getScreenWidthInPXs(Context context, Activity activity){

        DisplayMetrics dm = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }
}
