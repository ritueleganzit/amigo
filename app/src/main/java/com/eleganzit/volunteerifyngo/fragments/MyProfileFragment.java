package com.eleganzit.volunteerifyngo.fragments;


import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.EditProfileActivity;
import com.eleganzit.volunteerifyngo.MessageActivity;
import com.eleganzit.volunteerifyngo.MyProfileActivity;
import com.eleganzit.volunteerifyngo.NewsFeedActivity;
import com.eleganzit.volunteerifyngo.NotificationsActivity;
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.SearchActivity;
import com.eleganzit.volunteerifyngo.adapter.UserNewsFeedAdapter;
import com.eleganzit.volunteerifyngo.model.NewsFeedData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {


    public MyProfileFragment() {
        // Required empty public constructor
    }

    LinearLayout edit_profile;
    public static TextView tab_photos,tab_following,tab_events,tab_milestone;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList=new ArrayList<>();
    RecyclerView rc_my_posts;
    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_profile, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_event.setImageResource(R.drawable.event_gray);
        NewsFeedActivity.btm_user.setImageResource(R.drawable.user_green);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);


        edit_profile=v.findViewById(R.id.edit_profile);

        tab_photos=v.findViewById(R.id.tab_photos);
        tab_following=v.findViewById(R.id.tab_following);
        tab_events=v.findViewById(R.id.tab_events);
        tab_milestone=v.findViewById(R.id.tab_milestone);
        rc_my_posts=v.findViewById(R.id.rc_my_posts);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditProfileActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        MyPhotosFragment myPhotosFragment= new MyPhotosFragment();
        myContext.getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_frag_frame, myPhotosFragment,"TAG")
                .commit();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
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

        rc_my_posts.setAdapter(new UserNewsFeedAdapter(dataArrayList,getActivity()));

        tab_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyPhotosFragment myPhotosFragment= new MyPhotosFragment();
                myContext.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.profile_frag_frame, myPhotosFragment,"TAG")
                        .commit();
            }
        });


        return v;
    }

}
