package com.eleganzit.volunteerifyngo.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.ViewCampaignActivity;
import com.eleganzit.volunteerifyngo.adapter.HelpAdapter;
import com.eleganzit.volunteerifyngo.adapter.NewsFeedAdapter;
import com.eleganzit.volunteerifyngo.model.NewsFeedData;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferRequestHelpFragment extends Fragment {


    public OfferRequestHelpFragment() {
        // Required empty public constructor
    }
    RecyclerView rc_help_posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_offer_request_help, container, false);

        ViewCampaignActivity.tab_information.setTextColor(Color.parseColor("#8c8c8c"));
        ViewCampaignActivity.tab_campaigns.setTextColor(Color.parseColor("#8c8c8c"));
        ViewCampaignActivity.tab_offer.setTextColor(Color.parseColor("#0f2536"));
        ViewCampaignActivity.tab_news.setTextColor(Color.parseColor("#8c8c8c"));

        rc_help_posts=v.findViewById(R.id.rc_help_posts);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rc_help_posts.setLayoutManager(layoutManager);

        ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
        ArrayList<String> imgArrayList=new ArrayList<>();

        if(imgArrayList.size()>0)
        {
            imgArrayList.clear();
        }

       /* imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");

        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        ImgarrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
         */
        NewsFeedData newsFeedData=new NewsFeedData("zahir",imgArrayList);

        if(dataArrayList.size()>0)
        {
            dataArrayList.clear();
        }

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        rc_help_posts.setAdapter(new HelpAdapter(dataArrayList,getActivity()));


    }
}
