package com.eleganzit.amigo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.amigo.adapter.EventsAdapter;
import com.eleganzit.amigo.adapter.OngoingEventsAdapter;
import com.eleganzit.amigo.adapter.OpportuniesAdapter;
import com.eleganzit.amigo.adapter.PastEventsAdapter;
import com.eleganzit.amigo.adapter.UpcomingEventsAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityEventsBinding;
import com.eleganzit.amigo.model.EventsData;
import com.eleganzit.amigo.model.GetEvents;
import com.eleganzit.amigo.model.GetEventsResponse;
import com.eleganzit.amigo.model.GetOpportunitiesResponse;
import com.eleganzit.amigo.model.OpportunitiesListData;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity {



    String user_id;
    List<GetEvents> arrayList;
    UserLoggedInSession userLoggedInSession;
    ActivityEventsBinding binding;
    RecyclerView rc_events;
    ImageView filter,calendar;
    RelativeLayout search_events_layout;
    LinearLayout filter_layout;
    BottomSheetDialog mBottomSheetDialog;
    boolean isShowing;
    LinearLayoutManager layoutManager;
    boolean isScrolling;
    private static int firstVisibleInListview;
    int currentitems,totalitems,scrollitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(EventsActivity.this,R.layout.activity_events);
       // setContentView(R.layout.activity_events);
        userLoggedInSession=new UserLoggedInSession(EventsActivity.this);
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);


        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_events=findViewById(R.id.rc_events);
        filter=findViewById(R.id.filter);
        calendar=findViewById(R.id.calendar);

         layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_events.setLayoutManager(layoutManager);
        firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();

        binding.rcAddEventss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(EventsActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EventsActivity.this,CreateEventActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        binding.rcEvents.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentitems=layoutManager.getChildCount();
                totalitems=layoutManager.getItemCount();
                scrollitem=layoutManager.findFirstVisibleItemPosition();


                int currentFirstVisible = layoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentitems+scrollitem==totalitems))
                {
                    isScrolling=false;

                    if(currentFirstVisible > firstVisibleInListview)
                    {
                        getEvents1(totalitems+1);
                        Log.d("ttttt","if");
                    }
                    else {
                        Log.d("ttttt","else");
                    }

                    firstVisibleInListview = currentFirstVisible;

                }





            }
        });
//---->filter

       /* filter.setOnClickListener(new View.OnClickListener() {
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
                ongoingList.add(onGoingEventsData);
                ongoingList.add(onGoingEventsData);
                ongoingList.add(onGoingEventsData);

                upcomingList.add(upComingEventsData);
                upcomingList.add(upComingEventsData);
                upcomingList.add(upComingEventsData);
                upcomingList.add(upComingEventsData);
                upcomingList.add(upComingEventsData);
                upcomingList.add(upComingEventsData);

                pastList.add(pastEventsData);
                pastList.add(pastEventsData);
                pastList.add(pastEventsData);
                pastList.add(pastEventsData);
                pastList.add(pastEventsData);

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
                isShowing=true;
                mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            //isShowing=false;
                            *//*if(search_events_layout.getVisibility()==View.VISIBLE)
                            {
                                mBottomSheetDialog.show();

                                search_events_layout.setVisibility(View.GONE);
                                filter_layout.setVisibility(View.VISIBLE);
                            }
                            isShowing=false;*//*
                        }
                });


                mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            //isShowing=false;
                            *//*if(search_events_layout.getVisibility()==View.VISIBLE)
                            {
                                mBottomSheetDialog.show();

                                search_events_layout.setVisibility(View.GONE);
                                filter_layout.setVisibility(View.VISIBLE);

                            }
                            isShowing=false;*//*
                        }
                });

            }
        });*/

        /*final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
*/
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventsActivity.this, CalendarActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });


        //rc_events.setAdapter(new EventsAdapter(arrayList,this));




    }

    @Override
    protected void onResume() {
        super.onResume();
        getEvents(0);

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEvents(0);
            }
        });
    }

    private void getEvents1(int limit) {


        binding.progressbar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<GetEventsResponse> getOpportunitiesResponseCall=myInterface.getuserEvent(user_id,""+limit);
        getOpportunitiesResponseCall.enqueue(new Callback<GetEventsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GetEventsResponse> call, Response<GetEventsResponse> response) {
                binding.progressbar.setVisibility(View.GONE);

                if (response.isSuccessful())
                {

                    if (response.body().getData()!=null){


                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            Log.d("TAGGGG",""+response.body().getData().get(i).getEventDate());
                            GetEvents getEvents = new GetEvents(response.body().getData().get(i).getEventId(),
                                    response.body().getData().get(i).getEventName(),
                                    user_id,
                                    response.body().getData().get(i).getEventPhoto(),
                                    response.body().getData().get(i).getEventDate(),
                                    response.body().getData().get(i).getEventTime(),
                                    response.body().getData().get(i).getEventAddress(),
                                    response.body().getData().get(i).getEventDetails(),
                                    response.body().getData().get(i).getLat(),
                                    response.body().getData().get(i).getLng(),
                                    response.body().getData().get(i).getCoHost(),
                                    response.body().getData().get(i).getEventStatus());


                            arrayList.add(getEvents);
                        }

                    }

                    binding.rcEvents.getAdapter().notifyDataSetChanged();
                    //arrayList=response.body().getData();
                    // binding.rcOpportunities.setAdapter(new OpportuniesAdapter(arrayList,OpportunitiesActivity.this));

                }

            }

            @Override
            public void onFailure(retrofit2.Call<GetEventsResponse> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);

                Toast.makeText(EventsActivity.this, "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEvents(int limit) {
        arrayList=new ArrayList<>();
        binding.progressbar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<GetEventsResponse> getEventsResponseCall=myInterface.getuserEvent(user_id,"0");
        getEventsResponseCall.enqueue(new Callback<GetEventsResponse>() {
            @Override
            public void onResponse(Call<GetEventsResponse> call, Response<GetEventsResponse> response) {
                binding.progressbar.setVisibility(View.GONE);
                binding.relrefresh.setVisibility(View.GONE);
                binding.rcEvents.setVisibility(View.VISIBLE);


                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("0"))
                    {
                        binding.error.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        binding.error.setVisibility(View.GONE);
                    }
                    if (response.body().getData()!=null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            Log.d("TAGGGG","G"+response.body().getData().get(i).getEventTime());
                            GetEvents getEvents = new GetEvents(
                                    response.body().getData().get(i).getEventId(),
                                    response.body().getData().get(i).getEventName(),
                                    user_id,response.body().getData().get(i).getEventPhoto()
                                    ,response.body().getData().get(i).getEventDate(),
                                    response.body().getData().get(i).getEventTime(),
                                    response.body().getData().get(i).getEventAddress(),
                                    response.body().getData().get(i).getLat(),
                                    response.body().getData().get(i).getLng(),
                                    response.body().getData().get(i).getEventDetails(),
                                    response.body().getData().get(i).getCoHost(),
                                    response.body().getData().get(i).getEventStatus());


                            arrayList.add(getEvents);
                        }
                    }

                    //arrayList=response.body().getData();
                    binding.rcEvents.setAdapter(new EventsAdapter(arrayList,EventsActivity.this));

                }
                else
                {
                    binding.error.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<GetEventsResponse> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                binding.rcEvents.setVisibility(View.GONE);
                binding.relrefresh.setVisibility(View.VISIBLE);

                Toast.makeText(EventsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onBackPressed() {

       /* if(isShowing)
        {
            if(search_events_layout.getVisibility()==View.VISIBLE)
            {
                search_events_layout.setVisibility(View.GONE);
                filter_layout.setVisibility(View.VISIBLE);
                isShowing=false;
            }
        }
        else
        {*/
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
