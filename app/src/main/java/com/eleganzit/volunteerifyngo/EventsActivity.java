package com.eleganzit.volunteerifyngo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.adapter.EventsAdapter;
import com.eleganzit.volunteerifyngo.adapter.OngoingEventsAdapter;
import com.eleganzit.volunteerifyngo.adapter.PastEventsAdapter;
import com.eleganzit.volunteerifyngo.adapter.UpcomingEventsAdapter;
import com.eleganzit.volunteerifyngo.model.EventsData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EventsActivity extends AppCompatActivity {

    RecyclerView rc_events;
    ImageView filter;
    RelativeLayout search_events_layout;
    LinearLayout filter_layout;
    BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        rc_events=findViewById(R.id.rc_events);
        filter=findViewById(R.id.filter);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_events.setLayoutManager(layoutManager);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog = new BottomSheetDialog(EventsActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.filter_events_layout, null);

                TextView search;
                RecyclerView rc_upcoming_events,rc_past_events,rc_ongoing_events;
                TextView no_ongoing_events,no_upcoming_events,no_past_events;

                ArrayList<EventsData> ongoingList =new ArrayList<>();
                ArrayList<EventsData> upcomingList =new ArrayList<>();
                ArrayList<EventsData> pastList =new ArrayList<>();

                search=sheetView.findViewById(R.id.txt_search);
                filter_layout=sheetView.findViewById(R.id.filter_layout);
                search_events_layout=sheetView.findViewById(R.id.search_events_layout);
                rc_ongoing_events=sheetView.findViewById(R.id.rc_ongoing_events);
                rc_ongoing_events.setHasFixedSize(true);
                rc_upcoming_events=sheetView.findViewById(R.id.rc_upcoming_events);
                rc_upcoming_events.setHasFixedSize(true);
                rc_past_events=sheetView.findViewById(R.id.rc_past_events);
                rc_past_events.setHasFixedSize(true);
                no_ongoing_events=sheetView.findViewById(R.id.no_ongoing_events);
                no_upcoming_events=sheetView.findViewById(R.id.no_upcoming_events);
                no_past_events=sheetView.findViewById(R.id.no_past_events);

                RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(EventsActivity.this,LinearLayoutManager.VERTICAL,false);
                rc_ongoing_events.setLayoutManager(layoutManager1);
                RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(EventsActivity.this,LinearLayoutManager.VERTICAL,false);
                rc_upcoming_events.setLayoutManager(layoutManager2);
                RecyclerView.LayoutManager layoutManager3=new LinearLayoutManager(EventsActivity.this,LinearLayoutManager.VERTICAL,false);
                rc_past_events.setLayoutManager(layoutManager3);

                EventsData onGoingEventsData=new EventsData("","","","","");
                EventsData upComingEventsData=new EventsData("","","","","");
                EventsData pastEventsData=new EventsData("","","","","");

                ongoingList.add(onGoingEventsData);

                upcomingList.add(upComingEventsData);
                upcomingList.add(upComingEventsData);
                upcomingList.add(upComingEventsData);

                rc_ongoing_events.setAdapter(new OngoingEventsAdapter(ongoingList,EventsActivity.this));
                rc_upcoming_events.setAdapter(new UpcomingEventsAdapter(upcomingList,EventsActivity.this));
                rc_past_events.setAdapter(new PastEventsAdapter(pastList,EventsActivity.this));

                if(ongoingList.size()==0)
                {
                    no_ongoing_events.setVisibility(View.VISIBLE);
                }
                if(upcomingList.size()==0)
                {
                    no_upcoming_events.setVisibility(View.VISIBLE);
                }
                if(pastList.size()==0)
                {
                    no_past_events.setVisibility(View.VISIBLE);
                }


                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filter_layout.setVisibility(View.GONE);
                        search_events_layout.setVisibility(View.VISIBLE);
                    }
                });

                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
            }
        });

        EventsData eventsData=new EventsData("","","","","");

        ArrayList<EventsData> arrayList=new ArrayList<>();

        arrayList.add(eventsData);
        arrayList.add(eventsData);
        arrayList.add(eventsData);
        arrayList.add(eventsData);
        arrayList.add(eventsData);

        rc_events.setAdapter(new EventsAdapter(arrayList,this));

    }

    @Override
    public void onBackPressed() {

        if(mBottomSheetDialog.isShowing())
        {
            if(search_events_layout.getVisibility()==View.VISIBLE)
            {
                search_events_layout.setVisibility(View.GONE);
                filter_layout.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            super.onBackPressed();
        }
    }
}
