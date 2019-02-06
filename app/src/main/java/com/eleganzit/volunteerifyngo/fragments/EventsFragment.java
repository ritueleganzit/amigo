package com.eleganzit.volunteerifyngo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.UserProfileActivity;
import com.eleganzit.volunteerifyngo.adapter.PastEventsAdapter;
import com.eleganzit.volunteerifyngo.adapter.UpcomingEventsAdapter;
import com.eleganzit.volunteerifyngo.model.EventsData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {


    public EventsFragment() {
        // Required empty public constructor
    }
    RecyclerView rc_upcoming_events,rc_past_events;
    TextView no_upcoming_events,no_past_events;

    ArrayList<EventsData> upcomingList =new ArrayList<>();
    ArrayList<EventsData> pastList =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_events, container, false);

        UserProfileActivity.donate_layout.setVisibility(View.GONE);

        rc_upcoming_events=v.findViewById(R.id.rc_upcoming_events);
        rc_upcoming_events.setHasFixedSize(true);
        rc_past_events=v.findViewById(R.id.rc_past_events);
        rc_past_events.setHasFixedSize(true);
        no_upcoming_events=v.findViewById(R.id.no_upcoming_events);
        no_past_events=v.findViewById(R.id.no_past_events);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_upcoming_events.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_past_events.setLayoutManager(layoutManager1);

        EventsData eventsData=new EventsData("","","","");

//        upcomingList.add(eventsData);
//        upcomingList.add(eventsData);
//        upcomingList.add(eventsData);

        if(upcomingList.size()==0)
        {
            no_upcoming_events.setVisibility(View.VISIBLE);
        }

        pastList.add(eventsData);
        pastList.add(eventsData);
        pastList.add(eventsData);
        pastList.add(eventsData);

        rc_upcoming_events.setAdapter(new UpcomingEventsAdapter(upcomingList,getActivity()));
        rc_past_events.setAdapter(new PastEventsAdapter(pastList,getActivity()));

        return v;
    }

}
