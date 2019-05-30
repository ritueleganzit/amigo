package com.eleganzit.amigo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityUserProfileBinding;
import com.eleganzit.amigo.fragments.AboutFragment;
import com.eleganzit.amigo.fragments.EventsFragment;
import com.eleganzit.amigo.fragments.HomeFragment;
import com.eleganzit.amigo.fragments.OpportunityFragment;
import com.eleganzit.amigo.fragments.PhotosFragment;
import com.eleganzit.amigo.model.GetOtherUserResponse;
import com.eleganzit.amigo.model.OtherUserData;
import com.eleganzit.amigo.model.newsfeed.NewsFeedDataResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.eleganzit.amigo.utils.TextViewRobotoRegular;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG ="UserProfileActivityTAG" ;
    EditText ed_search;
    RelativeLayout accept_reject;
    ImageView chat;
    TabLayout profile_tabs;
    ViewPager profile_view_pager;
    public static TextView tab_home,tab_about,tab_photos,tab_events,tab_opportunity;
LinearLayout follow;
    public static RelativeLayout donate_layout;
    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;
ImageView img_follow;
TextViewRobotoBold txt_follow;
    String requestuserid,user_id;
    ActivityUserProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        progressDialog=new ProgressDialog(UserProfileActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        userLoggedInSession = new UserLoggedInSession(UserProfileActivity.this);
binding= DataBindingUtil.setContentView(UserProfileActivity.this,R.layout.activity_user_profile);
        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);

        ed_search=findViewById(R.id.ed_search);
        accept_reject=findViewById(R.id.accept_reject);
        txt_follow=findViewById(R.id.txt_follow);
        follow=findViewById(R.id.follow);
        img_follow=findViewById(R.id.img_follow);
        chat=findViewById(R.id.chat);
        tab_home=findViewById(R.id.tab_home);
        tab_about=findViewById(R.id.tab_about);
        tab_photos=findViewById(R.id.tab_photos);
        tab_events=findViewById(R.id.tab_events);
        tab_opportunity=findViewById(R.id.tab_opportunity);

        ed_search.setLongClickable(false);
        requestuserid=getIntent().getStringExtra("userid");
        Log.d(TAG,""+user_id+" "+requestuserid);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserProfileActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        HomeFragment homeFrag= new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, homeFrag,"TAG")
                .commit();

        tab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragment homeFrag= new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, homeFrag,"TAG")
                        .commit();
            }
        });

        tab_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AboutFragment aboutFragment= new AboutFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, aboutFragment,"TAG")
                        .addToBackStack("HomeFragment")
                        .commit();
            }
        });

        tab_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotosFragment photosFragment= new PhotosFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, photosFragment,"TAG")
                        .addToBackStack("HomeFragment")
                        .commit();
            }
        });

        tab_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventsFragment eventsFragment= new EventsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, eventsFragment,"TAG")
                        .addToBackStack("HomeFragment")
                        .commit();
            }
        });

        tab_opportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpportunityFragment opportunityFragment= new OpportunityFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, opportunityFragment,"TAG")
                        .addToBackStack("HomeFragment")
                        .commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public static int getScreenWidthInPXs(Context context, Activity activity){

        DisplayMetrics dm = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOtherUser();
    }

    public void getOtherUser()
    {
        progressDialog.show();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetOtherUserResponse> call=myInterface.getOtherUser(requestuserid,user_id);

        call.enqueue(new Callback<GetOtherUserResponse>() {
            @Override
            public void onResponse(Call<GetOtherUserResponse> call, Response<GetOtherUserResponse> response) {
                progressDialog.dismiss();
               if (response.isSuccessful())
               {
                   if (response.body().getData()!=null)
                   {
                       for (int i=0;i<response.body().getData().size();i++)
                       {
                           OtherUserData otherUserData=response.body().getData().get(i);

                           Glide.with(UserProfileActivity.this).load(otherUserData.getBackgroundImage()).into(binding.userphoto);
                           Glide.with(UserProfileActivity.this).load(otherUserData.getPhoto()).apply(RequestOptions.circleCropTransform()).into(binding.userimage);

                           binding.userName.setText(otherUserData.getFullname());
                           binding.followersdata.setText(""+otherUserData.getCountFollow());
                           binding.followingdata.setText(""+otherUserData.getCountFollowing());
                           binding.postdata.setText(""+otherUserData.getCountPost());


                           if (otherUserData.getFollowdata().getFail_status().equalsIgnoreCase("0"))
                           {
                              String request_user_id=response.body().getData().get(i).getFollowdata().getRequestUserId();
                               String  request_id=response.body().getData().get(i).getFollowdata().getRequest_id();
                               String request_status=response.body().getData().get(i).getFollowdata().getStatus();
                               if(request_user_id.equalsIgnoreCase(user_id))
                               {

                                   follow.setVisibility(View.VISIBLE);
                                   accept_reject.setVisibility(View.GONE);
                                   if(request_status.equalsIgnoreCase("0"))
                                   {
                                       follow.setBackgroundResource(R.drawable.rounded_green_bg);
                                       img_follow.setVisibility(View.VISIBLE);
                                       txt_follow.setText("Follow");
                                       Toast.makeText(UserProfileActivity.this, "", Toast.LENGTH_SHORT).show();
                                   }
                                   else if(request_status.equalsIgnoreCase("pending"))
                                   {
                                       follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                                       img_follow.setVisibility(View.GONE);
                                       txt_follow.setText("Requested");
                                   }
                                   else if(request_status.equalsIgnoreCase("accept"))
                                   {
                                       follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                                       img_follow.setVisibility(View.GONE);
                                       txt_follow.setText("Friends");
                                   }

                               }
                               else
                               {
                                   follow.setVisibility(View.GONE);
                                   accept_reject.setVisibility(View.VISIBLE);
                               }
                           }

                       }
                   }
               }
               else
               {

               }

            }

            @Override
            public void onFailure(Call<GetOtherUserResponse> call, Throwable t) {
                progressDialog.dismiss();


                Log.d(TAG,""+t.getMessage());
            }
        });
    }
}
