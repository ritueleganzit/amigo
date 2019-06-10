package com.eleganzit.amigo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
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
import com.eleganzit.amigo.model.SendRequestDataResponse;
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
    RelativeLayout accept_reject;
    ProgressBar accept_progress,reject_progress;

    ImageView chat;
    TabLayout profile_tabs;
    ViewPager profile_view_pager;
    public static TextView tab_home,tab_about,tab_photos,tab_events,tab_opportunity;
LinearLayout follow;
    SharedPreferences viewUserPref;
    SharedPreferences.Editor viewUserEditor;
    String viewUserId,user_idd;
    public static RelativeLayout donate_layout;
    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;
ImageView img_follow;
TextViewRobotoBold txt_follow,txt_accept;
    String requestuserid,user_id;
    String email,mobile,about_me,web,mission;
    String request_id;
    int profileHeight,profileWidth,coverHeight,coverWidth;
    RelativeLayout rel_accept,rel_reject;

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
        user_idd = map.get(UserLoggedInSession.USER_ID);
        viewUserPref=getSharedPreferences("viewUserPref",MODE_PRIVATE);
        viewUserEditor=viewUserPref.edit();
        viewUserId=viewUserPref.getString("viewUserId","");
        rel_accept=findViewById(R.id.rel_accept_bg);
        rel_reject=findViewById(R.id.rel_reject_bg);
        accept_reject=findViewById(R.id.accept_reject);
        txt_accept=findViewById(R.id.txt_accept);
        accept_progress=findViewById(R.id.accept_progress);

        txt_follow=findViewById(R.id.txt_follow);
        follow=findViewById(R.id.follow);
        img_follow=findViewById(R.id.img_follow);
        tab_home=findViewById(R.id.tab_home);
        tab_about=findViewById(R.id.tab_about);
        tab_photos=findViewById(R.id.tab_photos);
        tab_events=findViewById(R.id.tab_events);
        tab_opportunity=findViewById(R.id.tab_opportunity);

        requestuserid=getIntent().getStringExtra("userid");

        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        Log.d(TAG,""+user_id+" "+requestuserid);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_follow.getText().toString().equalsIgnoreCase("Add Friend"))
                {
                    sendFollowRequest();
                }
                else if(txt_follow.getText().toString().equalsIgnoreCase("Requested"))
                {
                    cancelFollowRequest();
                }
            }
        });

        txt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptFriendRequest();

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

    private void acceptFriendRequest() {
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendRequestDataResponse> call = myInterface.acceptFriendRequest(request_id,"accept");
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {
                rel_accept.setEnabled(true);
                rel_reject.setEnabled(true);
                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        accept_reject.setVisibility(View.GONE);
                        follow.setVisibility(View.VISIBLE);
                        follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                        img_follow.setVisibility(View.GONE);
                        txt_follow.setText("Friends");

                    } else {
                        accept_progress.setVisibility(View.GONE);
                        txt_accept.setVisibility(View.VISIBLE);
                        Toast.makeText(UserProfileActivity.this, "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    accept_progress.setVisibility(View.GONE);
                    txt_accept.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {
                rel_accept.setEnabled(true);
                rel_reject.setEnabled(true);
                accept_progress.setVisibility(View.GONE);
                txt_accept.setVisibility(View.VISIBLE);

                Toast.makeText(UserProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });
    }




    public void sendFollowRequest()
    {
        follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
        img_follow.setVisibility(View.GONE);
        txt_follow.setText("Requested");

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendRequestDataResponse> call = myInterface.sendFriendRequest(requestuserid,user_idd);
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {

                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                        img_follow.setVisibility(View.GONE);
                        txt_follow.setText("Requested");
                        Toast.makeText(UserProfileActivity.this, "Request sent successfully", Toast.LENGTH_SHORT).show();

                    } else {

                        follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                        img_follow.setVisibility(View.GONE);
                        txt_follow.setText("Requested");
                        Toast.makeText(UserProfileActivity.this, "Request already sent", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    follow.setBackgroundResource(R.drawable.rounded_green_bg);
                    img_follow.setVisibility(View.VISIBLE);
                    txt_follow.setText("Follow");

                    Toast.makeText(UserProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {

                Toast.makeText(UserProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                follow.setBackgroundResource(R.drawable.rounded_green_bg);
                img_follow.setVisibility(View.VISIBLE);
                txt_follow.setText("Follow");

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    public void cancelFollowRequest()
    {

        follow.setBackgroundResource(R.drawable.rounded_green_bg);
        img_follow.setVisibility(View.VISIBLE);
        txt_follow.setText("Add Friend");

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendRequestDataResponse> call = myInterface.cancelFriendRequest(request_id,"0");
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {

                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        follow.setBackgroundResource(R.drawable.rounded_green_bg);
                        img_follow.setVisibility(View.VISIBLE);
                        txt_follow.setText("Add Friend");

                        Toast.makeText(UserProfileActivity.this, "Request has been cancelled", Toast.LENGTH_SHORT).show();

                    } else {
                        follow.setBackgroundResource(R.drawable.rounded_green_bg);
                        img_follow.setVisibility(View.VISIBLE);
                        txt_follow.setText("Add Friend");

                        Toast.makeText(UserProfileActivity.this, "Request has been already cancelled", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                    img_follow.setVisibility(View.GONE);
                    txt_follow.setText("Requested");
                    Toast.makeText(UserProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {

                Toast.makeText(UserProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                img_follow.setVisibility(View.GONE);
                txt_follow.setText("Requested");
            }
        });
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
    public void blockUser(final BottomSheetDialog mBottomSheetDialog)
    {

        progressDialog.show();

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendRequestDataResponse> call = myInterface.blockUser(user_idd,viewUserId);
        call.enqueue(new Callback<SendRequestDataResponse>() {
            @Override
            public void onResponse(Call<SendRequestDataResponse> call, final Response<SendRequestDataResponse> response) {
                progressDialog.dismiss();
                mBottomSheetDialog.dismiss();
                Log.d("responseseeee", "" + response.toString());

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        follow.setBackgroundResource(R.drawable.rounded_blue_bg);
                        img_follow.setVisibility(View.GONE);
                        txt_follow.setText("Blocked");
                        Toast.makeText(UserProfileActivity.this, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();

                    } else {

                    }
                } else {

                    Toast.makeText(UserProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendRequestDataResponse> call, Throwable t) {
                progressDialog.dismiss();
                mBottomSheetDialog.dismiss();
                Toast.makeText(UserProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    protected void onResume() {

        super.onResume();
        binding.followOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(UserProfileActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.event_options_layout, null);
                mBottomSheetDialog.setContentView(sheetView);
                LinearLayout lin_report = sheetView.findViewById(R.id.lin_report);
                LinearLayout lin_claim_owner = sheetView.findViewById(R.id.lin_claim_owner);
                LinearLayout lin_block = sheetView.findViewById(R.id.lin_block);

                lin_block.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        blockUser(mBottomSheetDialog);
                    }
                });

                mBottomSheetDialog.show();
            }

        });
        ViewTreeObserver vto = binding.userImg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    binding.userImg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    binding.userImg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                profileWidth = binding.userImg.getMeasuredWidth();
                profileHeight = binding.userImg.getMeasuredHeight();

            }
        });

        ViewTreeObserver vto2 = binding.userCover.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    binding.userCover.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    binding.userCover.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                coverWidth = binding.userCover.getMeasuredWidth();
                coverHeight = binding.userCover.getMeasuredHeight();

            }
        });
        getOtherUser();
    }

    public void getOtherUser()
    {
        progressDialog.show();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetOtherUserResponse> call=myInterface.getOtherUser(requestuserid,user_idd);

        call.enqueue(new Callback<GetOtherUserResponse>() {
            @Override
            public void onResponse(Call<GetOtherUserResponse> call, Response<GetOtherUserResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            String user_id = response.body().getData().get(i).getUserId();
                            email = response.body().getData().get(i).getEmailId();
                            String username = response.body().getData().get(i).getUsername();
                            mobile = response.body().getData().get(i).getMobile();
                            String fullname = response.body().getData().get(i).getFullname();
                            String photo = response.body().getData().get(i).getPhoto();
                            String background_image = response.body().getData().get(i).getBackgroundImage();
                            about_me = response.body().getData().get(i).getAboutMe();
                            web = response.body().getData().get(i).getWeb();
                            mission = response.body().getData().get(i).getMission();
                            String city = response.body().getData().get(i).getCity();
                            String hometown = response.body().getData().get(i).getHometown();
                            String country = response.body().getData().get(i).getCountry();
                            String state = response.body().getData().get(i).getState();
                            String pincode = response.body().getData().get(i).getPincode();
                            String online_status = response.body().getData().get(i).getOnlineStatus();
                            int countFollow = response.body().getData().get(i).getCountFollow();
                            int countPost = response.body().getData().get(i).getCountPost();
                            int countFollowing = response.body().getData().get(i).getCountFollowing();
                            String is_follow = response.body().getData().get(i).getIsFollow();
                            String is_block = response.body().getData().get(i).getIs_block();
                            String request_user_id = "";
                            String request_status = "";



                            if (is_block.equalsIgnoreCase("yes")) {
                                follow.setBackgroundResource(R.drawable.rounded_blue_bg);
                                img_follow.setVisibility(View.GONE);
                                txt_follow.setText("Blocked");
                            } else {
                                Log.d("UserProfileee",requestuserid+"-"+user_idd);

                                if (response.body().getData().get(i).getFollowdata().getFail_status().equalsIgnoreCase("0")) {
                                    request_user_id = response.body().getData().get(i).getFollowdata().getRequestUserId();
                                    request_id = response.body().getData().get(i).getFollowdata().getRequest_id();
                                    request_status = response.body().getData().get(i).getFollowdata().getStatus();
                                  //  Log.d("UserProfileee","---"+request_user_id);
                                  //  Log.d("UserProfileee","--"+request_id);
                                    Log.d("UserProfileee",requestuserid+"-"+user_idd);
                                    Log.d("UserProfileee","-"+user_id);
                                    if (request_status.equalsIgnoreCase("0") || request_status.equalsIgnoreCase("pending")) {
                                        if (requestuserid.equalsIgnoreCase(user_idd)) {
                                            accept_reject.setVisibility(View.GONE);
                                            follow.setVisibility(View.VISIBLE);
                                            if (request_status.equalsIgnoreCase("0")) {
                                                follow.setBackgroundResource(R.drawable.rounded_green_bg);
                                                img_follow.setVisibility(View.VISIBLE);
                                                txt_follow.setText("Add Friend");
                                            } else if (request_status.equalsIgnoreCase("pending")) {
                                                follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                                                img_follow.setVisibility(View.GONE);
                                                txt_follow.setText("Requested");
                                            }

                                        } else {
                                            accept_reject.setVisibility(View.VISIBLE);
                                            follow.setVisibility(View.GONE);
                                        }
                                    } else if (request_status.equalsIgnoreCase("accept")) {
                                        follow.setVisibility(View.VISIBLE);
                                        follow.setBackgroundResource(R.drawable.rounded_light_blue_bg);
                                        img_follow.setVisibility(View.GONE);
                                        txt_follow.setText("Friends");
                                    } else {
                                        follow.setVisibility(View.VISIBLE);
                                        follow.setBackgroundResource(R.drawable.rounded_green_bg);
                                        img_follow.setVisibility(View.VISIBLE);
                                        txt_follow.setText("Add Friend");
                                    }
                                } else {
                                    accept_reject.setVisibility(View.GONE);
                                    follow.setVisibility(View.VISIBLE);
                                }

                            }

                            if (countPost == 1) {
                                binding.txtPosts.setText(countPost + "");
                                binding.lblPosts.setText("Post");
                            } else {
                                binding.txtPosts.setText(countPost + "");
                            }

                            if (countFollow == 1) {
                                binding.txtFollowers.setText(countFollow + "");
                                binding.lblFriends.setText("Friend");
                            } else {
                                binding.txtFollowers.setText(countFollow + "");
                            }

                            binding.txtFollowing.setText(countFollowing+"");

                            Log.d("sdhfhsdgfshd", request_user_id + "");
                            Log.d("sdhfhsdgfshd", request_status + "");
                            Log.d("sdhfhsdgfshd", request_id + "");

                            binding.txtFullname.setText(fullname);

                            Log.d("ppphotttoooo", "" + photo);


                            if (photo.equalsIgnoreCase("http://itechgaints.com/Volunteerify-API/user_uploads/no_profile.png") || photo.equalsIgnoreCase("") || photo == null) {
                                Glide
                                        .with(UserProfileActivity.this)
                                        .asBitmap()
                                        .load(R.drawable.user)
                                        .apply(new RequestOptions().override(profileWidth, profileHeight).placeholder(R.drawable.user).transforms(new CircleCrop()))
                                        .thumbnail(.1f)
                                        .into(binding.userImg);

                            } else {
                                Glide
                                        .with(UserProfileActivity.this)
                                        .asBitmap()
                                        .load(photo)
                                        .apply(new RequestOptions().override(profileWidth, profileHeight).placeholder(R.drawable.user).transforms(new CircleCrop()))
                                        .thumbnail(.1f)
                                        .into(binding.userImg);

                            }
                            if (background_image.equalsIgnoreCase("http://itechgaints.com/Volunteerify-API/user_uploads/no_profile.png") || background_image.equalsIgnoreCase("") || background_image == null) {
                                binding.userCover.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                Glide
                                        .with(UserProfileActivity.this)
                                        .load(R.drawable.ic_camera)
                                        .apply(new RequestOptions().override(coverWidth, coverHeight).placeholder(R.drawable.ic_camera))
                                        .into(binding.userCover);
                            } else {
                                binding.userCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                Glide
                                        .with(UserProfileActivity.this)
                                        .asBitmap()
                                        .load(background_image)
                                        .apply(new RequestOptions().override(coverWidth, coverHeight).placeholder(R.drawable.ic_camera))
                                        .thumbnail(.1f)
                                        .into(  binding.userCover);
                            }

                        }


                    } else {

                        Toast.makeText(UserProfileActivity.this, "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
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
