package com.eleganzit.amigo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.searchDataClasses.EventsData;
import com.eleganzit.amigo.model.searchDataClasses.NGOData;
import com.eleganzit.amigo.model.searchDataClasses.SearchAllDataResponse;
import com.eleganzit.amigo.model.searchDataClasses.VolunteersData;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {


   /* RecyclerView rc_ngos,rc_events,rc_volunteer;

    ImageView chat,notification_bell;
    LinearLayout all_shimmer;
    RelativeLayout error_layout,no_results_layout;
    UserLoggedInSession userLoggedInSession;
    HashMap<String, String> userData;
    private String search;
    LinearLayout ngo_header,events_header,volunteers_header;
    RelativeLayout ngo_seeAll,event_seeAll,volunteer_seeAll;
    EditText ed_search;
    SharedPreferences viewUserPref;
    SharedPreferences.Editor viewUserEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        userLoggedInSession=new UserLoggedInSession(this);
        userData=userLoggedInSession.getUserDetails();
        viewUserPref=getSharedPreferences("viewUserPref",MODE_PRIVATE);
        viewUserEditor=viewUserPref.edit();

        if(getIntent()!=null)
        {
            search=getIntent().getStringExtra("search");
        }
        rc_ngos=findViewById(R.id.rc_ngos);
        rc_events=findViewById(R.id.rc_events);
        rc_volunteer=findViewById(R.id.rc_volunteer);
        all_shimmer=findViewById(R.id.all_shimmer);
        ed_search=findViewById(R.id.ed_search);
        error_layout=findViewById(R.id.error_layout);
        no_results_layout=findViewById(R.id.no_results_layout);
        chat=findViewById(R.id.chat);
        notification_bell=findViewById(R.id.notification_bell);
        ngo_header=findViewById(R.id.ngo_header);
        events_header=findViewById(R.id.events_header);
        volunteers_header=findViewById(R.id.volunteers_header);
        ngo_seeAll=findViewById(R.id.ngo_seeAll);
        event_seeAll=findViewById(R.id.event_seeAll);
        volunteer_seeAll=findViewById(R.id.volunteer_seeAll);

        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager layoutManager3=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rc_ngos.setLayoutManager(layoutManager1);
        rc_events.setLayoutManager(layoutManager2);
        rc_volunteer.setLayoutManager(layoutManager3);

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SearchResultsActivity.this,NotificationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
        ed_search.setText(search);
        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SearchResultsActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        searchAllData();

    }

    public void searchAllData()
    {
        all_shimmer.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SearchAllDataResponse> call=myInterface.searchData(userData.get(UserLoggedInSession.USER_ID),search,"1");
        call.enqueue(new Callback<SearchAllDataResponse>() {
            @Override
            public void onResponse(Call<SearchAllDataResponse> call, final Response<SearchAllDataResponse> response) {
                all_shimmer.setVisibility(View.GONE);
                ArrayList<NGOData> ar_ngoData =new ArrayList<>();
                ArrayList<EventsData> ar_eventsData =new ArrayList<>();
                ArrayList<VolunteersData> ar_volunteerData =new ArrayList<>();
                if(response.isSuccessful()) {
                    Log.d("whereeeeeeeeee", "     onresponse ");

                    //Toast.makeText(SearchActivity.this, ""+response.body().getStatus().toString(), Toast.LENGTH_SHORT).show();

                    if(response.body()!=null)
                    {
                        Log.d("whereeeeeeeeee", "   response is not null   ");

                        if(response.body().getDataNGO()!=null)
                        {
                            Log.d("whereeeeeeeeee", "   response body data list is not null   ");
                            if(response.body().getDataNGO().getData()!=null) {
                                if(response.body().getDataNGO().getStatus().toString().equalsIgnoreCase("1"))
                                {
                                    for (int i = 0; i < response.body().getDataNGO().getData().size(); i++) {

                                        NGOData ngoData;
                                        if(response.body().getDataNGO().getData().get(i).getFollowdata().getFail_status().equalsIgnoreCase("0"))
                                        {
                                            ngoData = new NGOData(response.body().getDataNGO().getData().get(i).getUserId(),response.body().getDataNGO().getData().get(i).getFollowdata().getRequestUserId(),response.body().getDataNGO().getData().get(i).getFollowdata().getRequest_id(), response.body().getDataNGO().getData().get(i).getFullname(), response.body().getDataNGO().getData().get(i).getPhoto(), response.body().getDataNGO().getData().get(i).getCountFollow(), response.body().getDataNGO().getData().get(i).getIs_follow(),response.body().getDataNGO().getData().get(i).getFollowdata().getStatus(),"0");
                                        }
                                        else
                                        {
                                            ngoData = new NGOData(response.body().getDataNGO().getData().get(i).getUserId(),"","", response.body().getDataNGO().getData().get(i).getFullname(), response.body().getDataNGO().getData().get(i).getPhoto(), response.body().getDataNGO().getData().get(i).getCountFollow(), response.body().getDataNGO().getData().get(i).getIs_follow(),"","1");
                                        }

                                        ar_ngoData.add(ngoData);

                                    }
                                    rc_ngos.setAdapter(new SearchNGOsAdapter(ar_ngoData,SearchResultsActivity.this));
                                }
                                else
                                {
                                    ngo_header.setVisibility(View.GONE);
                                    ngo_seeAll.setVisibility(View.GONE);
                                }
                            }
                            else
                            {
                                ngo_header.setVisibility(View.GONE);
                                ngo_seeAll.setVisibility(View.GONE);
                            }


                        }
                        else
                        {
                            ngo_header.setVisibility(View.GONE);
                            ngo_seeAll.setVisibility(View.GONE);
                        }
                        if(response.body().getDataevent()!=null)
                        {
                            Log.d("whereeeeeeeeee", "   response body data list is not null   ");
                            if(response.body().getDataevent().getData()!=null) {
                                if(response.body().getDataevent().getStatus().toString().equalsIgnoreCase("1"))
                                {
                                    for (int i = 0; i < response.body().getDataevent().getData().size(); i++) {

                                        EventsData eventsData = new EventsData(response.body().getDataevent().getData().get(i).getEventId(), response.body().getDataevent().getData().get(i).getEventDate(), response.body().getDataevent().getData().get(i).getEventName(), response.body().getDataevent().getData().get(i).getEventTime(), response.body().getDataevent().getData().get(i).getEventAddress(), response.body().getDataevent().getData().get(i).getEventPhoto());

                                        ar_eventsData.add(eventsData);

                                    }

                                    rc_events.setAdapter(new SearchEventsAdapter(ar_eventsData,SearchResultsActivity.this));
                                }
                                else
                                {
                                    events_header.setVisibility(View.GONE);
                                    event_seeAll.setVisibility(View.GONE);
                                }
                            }
                            else
                            {
                                events_header.setVisibility(View.GONE);
                                event_seeAll.setVisibility(View.GONE);
                            }

                        }
                        else
                        {
                            events_header.setVisibility(View.GONE);
                            event_seeAll.setVisibility(View.GONE);
                        }
                        if(response.body().getDataVolunteer()!=null)
                        {
                            Log.d("whereeeeeeeeee", "   response body data list is not null   ");
                            if(response.body().getDataVolunteer().getData()!=null) {
                                if(response.body().getDataVolunteer().getStatus().toString().equalsIgnoreCase("1")) {
                                    for (int i = 0; i < response.body().getDataVolunteer().getData().size(); i++) {

                                        VolunteersData VolunteersData1;

                                        if(response.body().getDataVolunteer().getData().get(i).getFollowdata().getFail_status().equalsIgnoreCase("0"))
                                        {
                                            VolunteersData1 = new VolunteersData(response.body().getDataVolunteer().getData().get(i).getUserId(),response.body().getDataVolunteer().getData().get(i).getFollowdata().getRequestUserId(),response.body().getDataVolunteer().getData().get(i).getFollowdata().getRequest_id(), response.body().getDataVolunteer().getData().get(i).getFullname(), response.body().getDataVolunteer().getData().get(i).getPhoto(), response.body().getDataVolunteer().getData().get(i).getCountFollow(), response.body().getDataVolunteer().getData().get(i).getIs_follow(),response.body().getDataVolunteer().getData().get(i).getFollowdata().getStatus(),"0");
                                        }
                                        else
                                        {
                                            VolunteersData1 = new VolunteersData(response.body().getDataVolunteer().getData().get(i).getUserId(),"","", response.body().getDataVolunteer().getData().get(i).getFullname(), response.body().getDataVolunteer().getData().get(i).getPhoto(), response.body().getDataVolunteer().getData().get(i).getCountFollow(), response.body().getDataVolunteer().getData().get(i).getIs_follow(),response.body().getDataVolunteer().getData().get(i).getFollowdata().getStatus(),"1");
                                        }

                                        Log.d("bygsdfsdfh",response.body().getDataVolunteer().getData().get(i).getFollowdata().getStatus()+"    <--");

                                        ar_volunteerData.add(VolunteersData1);


                                    }
                                    rc_volunteer.setAdapter(new SearchVolunteersAdapter(ar_volunteerData, SearchResultsActivity.this));
                                }
                                else
                                {
                                    volunteers_header.setVisibility(View.GONE);
                                    volunteer_seeAll.setVisibility(View.GONE);
                                }
                            }
                            else
                            {
                                volunteers_header.setVisibility(View.GONE);
                                volunteer_seeAll.setVisibility(View.GONE);
                            }


                        }
                        else
                        {
                            volunteers_header.setVisibility(View.GONE);
                            volunteer_seeAll.setVisibility(View.GONE);
                        }

                    }

                }
                else
                {
                    Log.d("whereeeeeeeeee", "   "+response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<SearchAllDataResponse> call, Throwable t) {
                Toast.makeText(SearchResultsActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                all_shimmer.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }*/
}