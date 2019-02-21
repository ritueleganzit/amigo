package com.eleganzit.volunteerifyngo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.adapter.UpcomingEventsAdapter;
import com.eleganzit.volunteerifyngo.model.EventsData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarActivity extends AppCompatActivity {

    RecyclerView rc_events;
    ArrayList<EventsData> eventsList =new ArrayList<>();
    TextView no_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        no_events=findViewById(R.id.no_events);
        rc_events=findViewById(R.id.rc_events);
        rc_events.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_events.setLayoutManager(layoutManager);

        EventsData eventsData=new EventsData("","","","","");

        eventsList.add(eventsData);
        eventsList.add(eventsData);

        if(eventsList.size()==0)
        {
            no_events.setVisibility(View.VISIBLE);
        }

        rc_events.setAdapter(new UpcomingEventsAdapter(eventsList,this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
