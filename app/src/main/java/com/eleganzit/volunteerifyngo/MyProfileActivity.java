package com.eleganzit.volunteerifyngo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.adapter.OpportunityAdapter;
import com.eleganzit.volunteerifyngo.adapter.UserNewsFeedAdapter;
import com.eleganzit.volunteerifyngo.fragments.MyPhotosFragment;
import com.eleganzit.volunteerifyngo.fragments.PhotosFragment;
import com.eleganzit.volunteerifyngo.model.NewsFeedData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyProfileActivity extends AppCompatActivity {

    LinearLayout edit_profile;
    EditText ed_search;
    ImageView notification_bell,chat;
    public static TextView tab_photos,tab_following,tab_events,tab_milestone;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList=new ArrayList<>();
    RecyclerView rc_my_posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        edit_profile=findViewById(R.id.edit_profile);
        ed_search=findViewById(R.id.ed_search);
        notification_bell=findViewById(R.id.notification_bell);
        chat=findViewById(R.id.chat);
        tab_photos=findViewById(R.id.tab_photos);
        tab_following=findViewById(R.id.tab_following);
        tab_events=findViewById(R.id.tab_events);
        tab_milestone=findViewById(R.id.tab_milestone);
        rc_my_posts=findViewById(R.id.rc_my_posts);

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

        MyPhotosFragment myPhotosFragment= new MyPhotosFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_activity_frame, myPhotosFragment,"TAG")
                .commit();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_my_posts.setLayoutManager(layoutManager);

        //imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        /*imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
*/

        NewsFeedData newsFeedData=new NewsFeedData("zahir",imgArrayList);

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        rc_my_posts.setAdapter(new UserNewsFeedAdapter(dataArrayList,this));


        tab_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyPhotosFragment myPhotosFragment= new MyPhotosFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.profile_activity_frame, myPhotosFragment,"TAG")
                        .commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
