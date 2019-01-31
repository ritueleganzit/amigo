package com.eleganzit.volunteerifyngo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.volunteerifyngo.NewsFeedActivity;
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.adapter.NewsFeedAdapter;
import com.eleganzit.volunteerifyngo.adapter.UserNewsFeedAdapter;
import com.eleganzit.volunteerifyngo.model.NewsFeedData;

import java.util.ArrayList;

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
        home_posts=v.findViewById(R.id.home_posts);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        home_posts.setLayoutManager(layoutManager);

        imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");


        NewsFeedData newsFeedData=new NewsFeedData("zahir",imgArrayList);

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        home_posts.setAdapter(new UserNewsFeedAdapter(dataArrayList,getActivity()));


        return v;
    }

}
