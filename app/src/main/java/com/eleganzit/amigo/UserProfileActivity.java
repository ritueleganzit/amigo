package com.eleganzit.amigo;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.eleganzit.amigo.fragments.AboutFragment;
import com.eleganzit.amigo.fragments.EventsFragment;
import com.eleganzit.amigo.fragments.HomeFragment;
import com.eleganzit.amigo.fragments.OpportunityFragment;
import com.eleganzit.amigo.fragments.PhotosFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class UserProfileActivity extends AppCompatActivity {

    EditText ed_search;
    ImageView chat;
    TabLayout profile_tabs;
    ViewPager profile_view_pager;
    public static TextView tab_home,tab_about,tab_photos,tab_events,tab_opportunity;

    public static RelativeLayout donate_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ed_search=findViewById(R.id.ed_search);
        chat=findViewById(R.id.chat);
        tab_home=findViewById(R.id.tab_home);
        tab_about=findViewById(R.id.tab_about);
        tab_photos=findViewById(R.id.tab_photos);
        tab_events=findViewById(R.id.tab_events);
        tab_opportunity=findViewById(R.id.tab_opportunity);

        ed_search.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this,SearchActivity.class));
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

        HomeFragment homeFrag= new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, homeFrag,"TAG")
                .commit();

        tab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragment homeFrag= new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, homeFrag,"TAG")
                        .commit();
            }
        });

        tab_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AboutFragment aboutFragment= new AboutFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, aboutFragment,"TAG")
                        .addToBackStack("HomeFragment")
                        .commit();
            }
        });

        tab_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotosFragment photosFragment= new PhotosFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, photosFragment,"TAG")
                        .addToBackStack("HomeFragment")
                        .commit();
            }
        });

        tab_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventsFragment eventsFragment= new EventsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, eventsFragment,"TAG")
                        .addToBackStack("HomeFragment")
                        .commit();
            }
        });

        tab_opportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpportunityFragment opportunityFragment= new OpportunityFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, opportunityFragment,"TAG")
                        .addToBackStack("HomeFragment")
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
