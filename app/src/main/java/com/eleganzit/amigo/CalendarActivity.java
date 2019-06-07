package com.eleganzit.amigo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.amigo.adapter.UpcomingEventsAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityCalendarBinding;
import com.eleganzit.amigo.model.EventsData;
import com.eleganzit.amigo.model.GetEvents;
import com.eleganzit.amigo.model.GetEventsResponse;
import com.eleganzit.amigo.model.GetOpportunitiesEventsResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {

    List<GetEvents> arrayList;
    ProgressDialog progressDialog;
    String user_id, strdate;
    ActivityCalendarBinding binding;
    private String TAG = "CalendarActivityTag";

    UserLoggedInSession userLoggedInSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CalendarActivity.this, R.layout.activity_calendar);
        progressDialog = new ProgressDialog(CalendarActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        userLoggedInSession = new UserLoggedInSession(CalendarActivity.this);
        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        binding.calendar.setFirstDayOfWeek(Calendar.MONDAY);
        binding.calendar.setMinDate(System.currentTimeMillis() - 1000);


        binding.rcEvents.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcEvents.setLayoutManager(layoutManager);


strdate=""+currentCalendar.get(Calendar.YEAR)+"-"+(currentCalendar.get(Calendar.MONTH) + 1)+"-"+currentCalendar.get(Calendar.DAY_OF_MONTH);


Log.d("Createeee",""+strdate);
       // binding.rcEvents.setAdapter(new UpcomingEventsAdapter(eventsList, this));

        if (strdate!=null && !(strdate.isEmpty())) {

            amigoEventByDate();

        }
        binding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month,
                                            int dayOfMonth) {


                Log.d(TAG, year + "-" + (month + 1) + "-" + dayOfMonth);

                strdate = "" + year + "-" + (month + 1) + "-" + dayOfMonth;
                amigoEventByDate();


            }
        });
    }


    private void amigoEventByDate() {
        progressDialog.show();
        arrayList= new ArrayList<>();
        Log.d("TAGGGG",""+strdate);

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetOpportunitiesEventsResponse> call = myInterface.amigoEventByDate(user_id, strdate);
        call.enqueue(new Callback<GetOpportunitiesEventsResponse>() {
            @Override
            public void onResponse(Call<GetOpportunitiesEventsResponse> call, Response<GetOpportunitiesEventsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {
                        binding.rcEvents.setVisibility(View.VISIBLE);


                        binding.totalevents.setText(""+response.body().getData().size()+" Events Found");
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
                        binding.rcEvents.setAdapter(new UpcomingEventsAdapter(arrayList, CalendarActivity.this));
                    }
                    else
                    {
                        binding.rcEvents.setVisibility(View.GONE);
                        binding.totalevents.setText("No Events Found");


                        //Toast.makeText(CalendarActivity.this, "No Event", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetOpportunitiesEventsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CalendarActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
