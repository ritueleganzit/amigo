package com.eleganzit.amigo.fragments;


import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.adapter.AllNotificationsAdapter;
import com.eleganzit.amigo.adapter.FollowersAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.FollowersData;
import com.eleganzit.amigo.model.NotificationData;
import com.eleganzit.amigo.model.getfriendrequest.GetRequestDataResponse;
import com.eleganzit.amigo.model.getfriendrequest.RequestData;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


    public NotificationsFragment() {
        // Required empty public constructor
    }

    RecyclerView rc_notifications;
    String user_id;
    UserLoggedInSession userLoggedInSession;
    List<RequestData> requestData;
ProgressBar progressbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_notifications, container, false);
userLoggedInSession=new UserLoggedInSession(getActivity());
HashMap<String,String> hashMap=userLoggedInSession.getUserDetails();
user_id=hashMap.get(UserLoggedInSession.USER_ID);
        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_gray);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_green);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);

        rc_notifications=v.findViewById(R.id.rc_notifications);
        progressbar=v.findViewById(R.id.progressbar);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_notifications.setLayoutManager(layoutManager);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getRequestData();
    }

    private void getRequestData() {
        progressbar.setVisibility(View.VISIBLE);
        requestData=new ArrayList<>();
        RetrofitInterface retrofitInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<GetRequestDataResponse>  call=retrofitInterface.getRequests(user_id);
        call.enqueue(new Callback<GetRequestDataResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GetRequestDataResponse> call, Response<GetRequestDataResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    if (response.body().getData()!=null){
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            RequestData rdata=new RequestData(
                                    response.body().getData().get(i).getRequestId(),
                                    response.body().getData().get(i).getUserId(),
                                    response.body().getData().get(i).getRequestUserId(),
                                    response.body().getData().get(i).getStatus(),
                                    response.body().getData().get(i).getCreatedDate(),
                                    response.body().getData().get(i).getFullname(),
                                    response.body().getData().get(i).getUsername(),
                                    response.body().getData().get(i).getPhoto(),
                                    response.body().getData().get(i).getCountRequest()

                            );
                            requestData.add(rdata);
                            rc_notifications.setAdapter(new AllNotificationsAdapter(requestData,getActivity()));


                        }

                    }

                }
                else
                {
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(retrofit2.Call<GetRequestDataResponse> call, Throwable t) {
                progressbar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
