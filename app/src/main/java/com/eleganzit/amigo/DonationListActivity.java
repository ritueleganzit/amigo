package com.eleganzit.amigo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eleganzit.amigo.adapter.DonationsAdapter;
import com.eleganzit.amigo.adapter.OpportuniesAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.OpportunitiesListData;
import com.eleganzit.amigo.model.donation.Donations;
import com.eleganzit.amigo.model.donation.GetDonationResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationListActivity extends AppCompatActivity {
    String user_id;
    UserLoggedInSession userLoggedInSession;
    ImageView adddonations;
    LinearLayoutManager layoutManager;
    RecyclerView rc_donation_list;
ProgressBar progressbar;
    List<Donations> arrayList;


    boolean isScrolling;
    private static int firstVisibleInListview;
    int currentitems,totalitems,scrollitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list);
        adddonations = findViewById(R.id.adddonations);
        rc_donation_list = findViewById(R.id.rc_donation_list);
        progressbar = findViewById(R.id.progressbar);
        userLoggedInSession = new UserLoggedInSession(DonationListActivity.this);
        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_donation_list.setLayoutManager(layoutManager);

        firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();

        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        adddonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonationListActivity.this, DonationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        rc_donation_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        getDonations1(totalitems+1);
                        Log.d("ttttt","if");
                    }
                    else {
                        Log.d("ttttt","else");
                    }

                    firstVisibleInListview = currentFirstVisible;

                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDonations(0);
    }

    private void getDonations(int i) {
        arrayList=new ArrayList<>();
        progressbar.setVisibility(View.VISIBLE);

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetDonationResponse> call=myInterface.getAllDonations(user_id,""+i,"1");
        call.enqueue(new Callback<GetDonationResponse>() {
            @Override
            public void onResponse(Call<GetDonationResponse> call, Response<GetDonationResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful())
                {

                   // Toast.makeText(DonationListActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   // Log.d("hhhhh",""+response.body().getData().size());

if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
    for (int i=0;i<response.body().getData().size();i++)
    {
        Donations donations=new Donations();
        donations.setCreatedDate(response.body().getData().get(i).getCreatedDate());
        donations.setDescription(response.body().getData().get(i).getDescription());
        donations.setDonationEndDate(response.body().getData().get(i).getDonationEndDate());
        donations.setDonationStartDate(response.body().getData().get(i).getDonationStartDate());
        donations.setDonationImage(response.body().getData().get(i).getDonationImage());
        donations.setNeedAmount(response.body().getData().get(i).getNeedAmount());
        donations.setDonationId(response.body().getData().get(i).getDonationId());
        donations.setTitle(response.body().getData().get(i).getTitle());
        donations.setUserId(response.body().getData().get(i).getUserId());
        arrayList.add(donations);
    }
    rc_donation_list.setAdapter(new DonationsAdapter(arrayList, DonationListActivity.this));
}

                }
            }

            @Override
            public void onFailure(Call<GetDonationResponse> call, Throwable t) {
            progressbar.setVisibility(View.GONE);
                Toast.makeText(DonationListActivity.this, "Server Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }  private void getDonations1(int i) {
       // arrayList=new ArrayList<>();
        progressbar.setVisibility(View.VISIBLE);

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetDonationResponse> call=myInterface.getAllDonations(user_id,""+i,"1");
        call.enqueue(new Callback<GetDonationResponse>() {
            @Override
            public void onResponse(Call<GetDonationResponse> call, Response<GetDonationResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful())
                {

                   // Toast.makeText(DonationListActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   // Log.d("hhhhh",""+response.body().getData().size());

if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
    for (int i=0;i<response.body().getData().size();i++)
    {
        Donations donations=new Donations();
        donations.setCreatedDate(response.body().getData().get(i).getCreatedDate());
        donations.setDescription(response.body().getData().get(i).getDescription());
        donations.setDonationEndDate(response.body().getData().get(i).getDonationEndDate());
        donations.setDonationStartDate(response.body().getData().get(i).getDonationStartDate());
        donations.setDonationImage(response.body().getData().get(i).getDonationImage());
        donations.setNeedAmount(response.body().getData().get(i).getNeedAmount());
        donations.setDonationId(response.body().getData().get(i).getDonationId());
        donations.setTitle(response.body().getData().get(i).getTitle());
        donations.setUserId(response.body().getData().get(i).getUserId());
        arrayList.add(donations);
    }
    rc_donation_list.getAdapter().notifyDataSetChanged();
}

                }
            }

            @Override
            public void onFailure(Call<GetDonationResponse> call, Throwable t) {
            progressbar.setVisibility(View.GONE);
                Toast.makeText(DonationListActivity.this, "Server Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
