package com.eleganzit.amigo;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.eleganzit.amigo.adapter.OpportuniesAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityOpportunitiesBinding;
import com.eleganzit.amigo.model.GetOpportunitiesResponse;
import com.eleganzit.amigo.model.OpportunitiesListData;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Callback;
import retrofit2.Response;

public class OpportunitiesActivity extends AppCompatActivity {

    ActivityOpportunitiesBinding binding;
    String user_id;
    UserLoggedInSession userLoggedInSession;
  List<OpportunitiesListData> arrayList;
    int currentitems,totalitems,scrollitem;
   LinearLayoutManager layoutManager;
    boolean isScrolling;
    private static int firstVisibleInListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(OpportunitiesActivity.this,R.layout.activity_opportunities);
        userLoggedInSession=new UserLoggedInSession(OpportunitiesActivity.this);
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);



     layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();

        binding.rcOpportunities.setLayoutManager(layoutManager);

        binding.rcOpportunities.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            getOpportunities(totalitems+1);
                            Log.d("ttttt","if");
                        }
                        else {
                            Log.d("ttttt","else");
                        }

                        firstVisibleInListview = currentFirstVisible;

                    }





            }
        });



binding.addopportunity.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(OpportunitiesActivity.this,CreateOpportunityActivity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
});
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOpportunities1(0);
        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOpportunities1(0);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    public void getOpportunities(final int limit)
    {
        binding.progressbar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<GetOpportunitiesResponse> getOpportunitiesResponseCall=myInterface.getuserOpportunity(user_id,""+limit);
        getOpportunitiesResponseCall.enqueue(new Callback<GetOpportunitiesResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GetOpportunitiesResponse> call, Response<GetOpportunitiesResponse> response) {
                binding.progressbar.setVisibility(View.GONE);

                if (response.isSuccessful())
                {
                    Log.d("hhhh",limit+"   "+response.body().getStatus());


                    if (response.body().getData()!=null){


                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            Log.d("TAGGGG",""+response.body().getData().get(i).getLookingFor());
                            OpportunitiesListData opportunitiesListData=new OpportunitiesListData(response.body().getData().get(i).getOpportunityId(),
                                    response.body().getData().get(i).getUserId(),
                                    response.body().getData().get(i).getOpportunityImage(),
                                    response.body().getData().get(i).getOpportunityDate(),
                                    response.body().getData().get(i).getOpportunityTime(),
                                    response.body().getData().get(i).getAddress(),
                                    response.body().getData().get(i).getDetails(),
                                    response.body().getData().get(i).getPositions(),
                                    response.body().getData().get(i).getLookingFor(),
                                    response.body().getData().get(i).getOpportunityStatus()
                            );

                            arrayList.add(opportunitiesListData);
                    }

                    }

                    binding.rcOpportunities.getAdapter().notifyDataSetChanged();
                    //arrayList=response.body().getData();
                   // binding.rcOpportunities.setAdapter(new OpportuniesAdapter(arrayList,OpportunitiesActivity.this));

                }

            }

            @Override
            public void onFailure(retrofit2.Call<GetOpportunitiesResponse> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
               // binding.rcOpportunities.setVisibility(View.GONE);
                //binding.error.setVisibility(View.VISIBLE);
                Toast.makeText(OpportunitiesActivity.this, "Server error", Toast.LENGTH_SHORT).show();
            }
        });



    }public void getOpportunities1(int limit)
    {
        arrayList=new ArrayList<>();

        binding.progressbar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<GetOpportunitiesResponse> getOpportunitiesResponseCall=myInterface.getuserOpportunity(user_id,"0");
        getOpportunitiesResponseCall.enqueue(new Callback<GetOpportunitiesResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GetOpportunitiesResponse> call, Response<GetOpportunitiesResponse> response) {
                binding.progressbar.setVisibility(View.GONE);
                binding.relrefresh.setVisibility(View.GONE);
                binding.rcOpportunities.setVisibility(View.VISIBLE);
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
                            Log.d("TAGGGG","G"+response.body().getData().get(i).getLookingFor());
                            OpportunitiesListData opportunitiesListData = new OpportunitiesListData(response.body().getData().get(i).getOpportunityId(),
                                    response.body().getData().get(i).getUserId(),
                                    response.body().getData().get(i).getOpportunityImage(),
                                    response.body().getData().get(i).getOpportunityDate(),
                                    response.body().getData().get(i).getOpportunityTime(),
                                    response.body().getData().get(i).getAddress(),
                                    response.body().getData().get(i).getDetails(),
                                    response.body().getData().get(i).getPositions(),
                                    response.body().getData().get(i).getLookingFor(),
                                    response.body().getData().get(i).getOpportunityStatus()
                            );

                            arrayList.add(opportunitiesListData);
                        }
                    }

                    //arrayList=response.body().getData();
                    binding.rcOpportunities.setAdapter(new OpportuniesAdapter(arrayList,OpportunitiesActivity.this));

                }
                else
                {
                    binding.rcOpportunities.setVisibility(View.GONE);
                    binding.error.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(retrofit2.Call<GetOpportunitiesResponse> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                binding.rcOpportunities.setVisibility(View.GONE);
                binding.relrefresh.setVisibility(View.VISIBLE);
              // binding.error.setVisibility(View.VISIBLE);
                Toast.makeText(OpportunitiesActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
