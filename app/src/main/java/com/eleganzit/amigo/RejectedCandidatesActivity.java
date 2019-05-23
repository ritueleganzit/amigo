package com.eleganzit.amigo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.eleganzit.amigo.adapter.CandidatesAdapter;
import com.eleganzit.amigo.adapter.RejectedCandidatesAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityAppliedCandidatesBinding;
import com.eleganzit.amigo.databinding.ActivityRejectedCandidatesBinding;
import com.eleganzit.amigo.model.AppliedList;
import com.eleganzit.amigo.model.RejectedListResponse;
import com.eleganzit.amigo.model.FollowersData;
import com.eleganzit.amigo.model.RejectedList;
import com.eleganzit.amigo.model.RejectedListResponse;
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

public class RejectedCandidatesActivity extends AppCompatActivity {

    String user_id="",opportunity_id="";
    UserLoggedInSession userLoggedInSession;
    ActivityRejectedCandidatesBinding binding;
    List<RejectedList> arrayList=new ArrayList<>();
    int currentitems,totalitems,scrollitem;
    LinearLayoutManager layoutManager;
    boolean isScrolling;
    private static int firstVisibleInListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(RejectedCandidatesActivity.this,R.layout.activity_rejected_candidates);
        userLoggedInSession=new UserLoggedInSession(RejectedCandidatesActivity.this);


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);
        opportunity_id=getIntent().getStringExtra("opportunity_id");
       /// Toast.makeText(this, ""+opportunity_id, Toast.LENGTH_SHORT).show();
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
    }
    private void getCandidate(int i) {


        binding.progressbar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        retrofit2.Call<RejectedListResponse>  call=myInterface.getRejectedUserOpportunity(opportunity_id,""+i,"2");
        call.enqueue(new Callback<RejectedListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RejectedListResponse> call, Response<RejectedListResponse> response) {
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
                            RejectedList rejectedList=new RejectedList(response.body().getData().get(i).getApplyId(),
                                    response.body().getData().get(i).getOpportunityId(),
                                    response.body().getData().get(i).getUserId(),
                                    response.body().getData().get(i).getPhoto(),
                                    response.body().getData().get(i).getStatus(),
                                    response.body().getData().get(i).getCreatedDate(),
                                    response.body().getData().get(i).getFullname(),
                                    response.body().getData().get(i).getUsername(),response.body().getData().get(i).getCountUsers());


                            arrayList.add(rejectedList);
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
            public void onFailure(retrofit2.Call<RejectedListResponse> call, Throwable t) {
                Log.d("TAGGGG","G"+t.getMessage());
                binding.progressbar.setVisibility(View.GONE);
                binding.error.setVisibility(View.VISIBLE);

            }
        });

    }

    private void getCandidate1(int size) {

        binding.progressbar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        retrofit2.Call<RejectedListResponse>  call=myInterface.getRejectedUserOpportunity(opportunity_id,"0","2");
        call.enqueue(new Callback<RejectedListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RejectedListResponse> call, Response<RejectedListResponse> response) {

                binding.progressbar.setVisibility(View.GONE);
                Log.d("TAGGGG","G"+response.body().getMessage());
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
                            RejectedList rejectedList=new RejectedList(response.body().getData().get(i).getApplyId(),
                                    response.body().getData().get(i).getOpportunityId(),
                                    response.body().getData().get(i).getUserId(),
                                    response.body().getData().get(i).getPhoto(),
                                    response.body().getData().get(i).getStatus(),
                                    response.body().getData().get(i).getCreatedDate(),
                                    response.body().getData().get(i).getFullname(),
                                    response.body().getData().get(i).getUsername(),response.body().getData().get(i).getCountUsers());
                            //appliedList.setCountUsers();



                            arrayList.add(rejectedList);
                        }
                    }

                    //arrayList=response.body().getData();
                    binding.rcCandidates.setAdapter(new RejectedCandidatesAdapter(arrayList,RejectedCandidatesActivity.this,"rejected"));

                }
                else
                {
                    binding.error.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(retrofit2.Call<RejectedListResponse> call, Throwable t) {
                Log.d("TAGGGG","G"+t.getMessage());
                binding.progressbar.setVisibility(View.GONE);
                binding.error.setVisibility(View.VISIBLE);
               // Toast.makeText(RejectedCandidatesActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}