package com.eleganzit.amigo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.adapter.OpportunityAdapter;
import com.eleganzit.amigo.adapter.UserNewsFeedAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityMyProfileBinding;
import com.eleganzit.amigo.fragments.MyPhotosFragment;
import com.eleganzit.amigo.fragments.PhotosFragment;
import com.eleganzit.amigo.model.GetLoginResponse;
import com.eleganzit.amigo.model.GetStateResponse;
import com.eleganzit.amigo.model.LoginData;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {

    public static TextView tab_photos,tab_following,tab_events,tab_milestone;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList=new ArrayList<>();
    ActivityMyProfileBinding binding;
    UserLoggedInSession userLoggedInSession;
String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(MyProfileActivity.this,R.layout.activity_my_profile);
        userLoggedInSession=new UserLoggedInSession(this);

        HashMap<String,String> hashMap=userLoggedInSession.getUserDetails();
        username=hashMap.get(UserLoggedInSession.USERNAME);
        binding.username.setText(username);
        Glide.with(this).load(hashMap.get(UserLoggedInSession.PHOTO)).into(binding.userphoto);





        tab_photos=findViewById(R.id.tab_photos);
        tab_following=findViewById(R.id.tab_following);
        tab_events=findViewById(R.id.tab_events);
        tab_milestone=findViewById(R.id.tab_milestone);

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this,EditProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        binding.edSearch.setLongClickable(false);

        binding.edSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MyProfileActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        MyPhotosFragment myPhotosFragment= new MyPhotosFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_activity_frame, myPhotosFragment,"TAG")
                .commit();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcMyPosts.setLayoutManager(layoutManager);

        //imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        /*imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
*/

        NewsFeedData newsFeedData=new NewsFeedData("zahir",imgArrayList);

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        binding.rcMyPosts.setAdapter(new UserNewsFeedAdapter(dataArrayList,this));


        binding.tabPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyPhotosFragment myPhotosFragment= new MyPhotosFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.profile_activity_frame, myPhotosFragment,"TAG")
                        .commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    public void getUserData()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<GetLoginResponse> call=myInterface.getUserData("15");
        call.enqueue(new Callback<GetLoginResponse>() {
            @Override
            public void onResponse(Call<GetLoginResponse> call, Response<GetLoginResponse> response) {

            }

            @Override
            public void onFailure(Call<GetLoginResponse> call, Throwable t) {

            }
        });

    }
}
