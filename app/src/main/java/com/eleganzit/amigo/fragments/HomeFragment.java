package com.eleganzit.amigo.fragments;


import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.UserProfileActivity;
import com.eleganzit.amigo.adapter.NewsFeedAdapter;
import com.eleganzit.amigo.adapter.UserNewsFeedAdapter;
import com.eleganzit.amigo.model.NewsFeedData;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    RecyclerView home_posts;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList=new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_home, container, false);

        UserProfileActivity.tab_home.setTextColor(Color.parseColor("#0f2536"));
        UserProfileActivity.tab_about.setTextColor(Color.parseColor("#8c8c8c"));
        UserProfileActivity.tab_photos.setTextColor(Color.parseColor("#8c8c8c"));
        UserProfileActivity.tab_events.setTextColor(Color.parseColor("#8c8c8c"));
        UserProfileActivity.tab_opportunity.setTextColor(Color.parseColor("#8c8c8c"));

        home_posts=v.findViewById(R.id.home_posts);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        home_posts.setLayoutManager(layoutManager);

        if(imgArrayList.size()>0)
        {
            imgArrayList.clear();
        }

        imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");

 /*       NewsFeedData newsFeedData=new NewsFeedData("zahir",imgArrayList);

        if(dataArrayList.size()>0)
        {
            dataArrayList.clear();
        }

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        home_posts.setAdapter(new UserNewsFeedAdapter(dataArrayList,getActivity()));*/

        return v;
    }

}
