package com.eleganzit.amigo;

import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.eleganzit.amigo.adapter.CandidatesAdapter;
import com.eleganzit.amigo.adapter.OpportuniesAdapter;
import com.eleganzit.amigo.adapter.VolunteersAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityAppliedCandidatesBinding;
import com.eleganzit.amigo.model.AppliedList;
import com.eleganzit.amigo.model.AppliedListResponse;
import com.eleganzit.amigo.model.FollowersData;
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

public class AppliedCandidatesActivity extends AppCompatActivity {

    String user_id="",opportunity_id="";
    UserLoggedInSession userLoggedInSession;
    ActivityAppliedCandidatesBinding binding;
    List<AppliedList> arrayList=new ArrayList<>();
    int currentitems,totalitems,scrollitem;
    LinearLayoutManager layoutManager;
    boolean isScrolling;
    private static int firstVisibleInListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(AppliedCandidatesActivity.this,R.layout.activity_applied_candidates);
        userLoggedInSession=new UserLoggedInSession(AppliedCandidatesActivity.this);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);
        opportunity_id=getIntent().getStringExtra("opportunity_id");

        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcCandidates.setLayoutManager(layoutManager);
        firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();



        binding.rcCandidates.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                scrollitem=layoutManager.findFirstCompletelyVisibleItemPosition();


                int currentFirstVisible = layoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentitems+scrollitem==totalitems))
                {
                    isScrolling=false;

                    if(currentFirstVisible > firstVisibleInListview)
                    {
                        getCandidate(totalitems+1);
                        Log.d("ttttt","if");
                    }
                    else {
                        Log.d("ttttt","else");
                    }

                    firstVisibleInListview = currentFirstVisible;

                }





            }
        });



        getCandidate1(arrayList.size());

       // rc_candidates.setAdapter(new CandidatesAdapter(ar_candidates,AppliedCandidatesActivity.this,"applied"));

    }

    private void getCandidate(int i) {


        binding.progressbar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        retrofit2.Call<AppliedListResponse>  call=myInterface.getRequestedUserOpportunity(opportunity_id,""+i);
        call.enqueue(new Callback<AppliedListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AppliedListResponse> call, Response<AppliedListResponse> response) {

                binding.progressbar.setVisibility(View.GONE);
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
                            Log.d("TAGGGG","G"+response.body().getData().get(i).getFullname());
                            AppliedList appliedList=new AppliedList(response.body().getData().get(i).getApplyId(),
                                    response.body().getData().get(i).getOpportunityId(),
                                    response.body().getData().get(i).getUserId(),
                                    response.body().getData().get(i).getPhoto(),
                                    response.body().getData().get(i).getStatus(),
                                    response.body().getData().get(i).getCreatedDate(),
                                    response.body().getData().get(i).getFullname(),
                                    response.body().getData().get(i).getUsername());



                            arrayList.add(appliedList);
                        }
                    }

                    //arrayList=response.body().getData();
                    binding.rcCandidates.getAdapter().notifyDataSetChanged();

                }
                else
                {
                    binding.error.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(retrofit2.Call<AppliedListResponse> call, Throwable t) {
                binding.error.setVisibility(View.VISIBLE);
                binding.progressbar.setVisibility(View.GONE);
            }
        });

    }

    private void getCandidate1(int size) {

        binding.progressbar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        retrofit2.Call<AppliedListResponse>  call=myInterface.getRequestedUserOpportunity(opportunity_id,"0");
        call.enqueue(new Callback<AppliedListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AppliedListResponse> call, Response<AppliedListResponse> response) {

                binding.progressbar.setVisibility(View.GONE);
                binding.error.setVisibility(View.GONE);

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
                            Log.d("TAGGGG","G"+response.body().getData().get(i).getFullname());
                            AppliedList appliedList=new AppliedList(response.body().getData().get(i).getApplyId(),
                                    response.body().getData().get(i).getOpportunityId(),
                                    response.body().getData().get(i).getUserId(),
                                    response.body().getData().get(i).getPhoto(),
                                    response.body().getData().get(i).getStatus(),
                                    response.body().getData().get(i).getCreatedDate(),
                                    response.body().getData().get(i).getFullname(),
                                    response.body().getData().get(i).getUsername());



                            arrayList.add(appliedList);
                        }
                    }

                    //arrayList=response.body().getData();
                    binding.rcCandidates.setAdapter(new CandidatesAdapter(arrayList,AppliedCandidatesActivity.this,"applied"));

                }
            }

            @Override
            public void onFailure(retrofit2.Call<AppliedListResponse> call, Throwable t) {
                binding.error.setVisibility(View.VISIBLE);
                binding.progressbar.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}