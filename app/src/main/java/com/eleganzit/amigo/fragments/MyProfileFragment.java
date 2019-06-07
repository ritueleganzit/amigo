package com.eleganzit.amigo.fragments;


import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.amigo.EditProfileActivity;
import com.eleganzit.amigo.MessageActivity;
import com.eleganzit.amigo.MyProfileActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.NotificationsActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.SearchActivity;
import com.eleganzit.amigo.adapter.NewsFeedAdapter;
import com.eleganzit.amigo.adapter.UserNewsFeedAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.model.newsfeed.CommentsResponse;
import com.eleganzit.amigo.model.newsfeed.LikesData;
import com.eleganzit.amigo.model.newsfeed.LikesResponse;
import com.eleganzit.amigo.model.newsfeed.NewsFeedDataResponse;
import com.eleganzit.amigo.model.newsfeed.PhotoUrlResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.eleganzit.amigo.utils.TextViewRobotoBold;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {


    public MyProfileFragment() {
        // Required empty public constructor
    }
    UserLoggedInSession userLoggedInSession;
    String user_id = "",photo="",name="";

    LinearLayout edit_profile;
    public static TextView tab_photos,tab_following,tab_events,tab_milestone;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList=new ArrayList<>();
    RecyclerView rc_my_posts;
    NewsFeedAdapter newsFeedAdapter;

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_profile, container, false);
        userLoggedInSession = new UserLoggedInSession(getActivity());

        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        photo = map.get(UserLoggedInSession.PHOTO);
        name = map.get(UserLoggedInSession.FNAME);
        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_gray);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_green);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);

        edit_profile=v.findViewById(R.id.edit_profile);

        tab_photos=v.findViewById(R.id.tab_photos);
        tab_following=v.findViewById(R.id.tab_following);
        tab_events=v.findViewById(R.id.tab_events);
        tab_milestone=v.findViewById(R.id.tab_milestone);
        rc_my_posts=v.findViewById(R.id.rc_my_posts);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditProfileActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        MyPhotosFragment myPhotosFragment= new MyPhotosFragment();
        myContext.getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_frag_frame, myPhotosFragment,"TAG")
                .commit();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_my_posts.setLayoutManager(layoutManager);

        //imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        /*imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
*/

      /*  NewsFeedData newsFeedData=new NewsFeedData("","","",created_date,content,imgArrayList,cmntsArrayList,likesArrayList,countComment,countLikes,type);

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        rc_my_posts.setAdapter(new UserNewsFeedAdapter(dataArrayList,getActivity()));
*/
        tab_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyPhotosFragment myPhotosFragment= new MyPhotosFragment();
                myContext.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.profile_frag_frame, myPhotosFragment,"TAG")
                        .commit();
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dataArrayList.size()>0)
        {
            dataArrayList.clear();
        }
        newsFeedAdapter=new NewsFeedAdapter(dataArrayList, getActivity(),user_id);

        getUserFeeds();
    }

    private void getUserFeeds() {

       // home_shimmer.setVisibility(View.VISIBLE);
        //error_layout.setVisibility(View.GONE);
        imgArrayList=new ArrayList<>();
        //ar_photos=new ArrayList<>();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<NewsFeedDataResponse> call=myInterface.user_newsfeedData(user_id,"1","1");
        call.enqueue(new Callback<NewsFeedDataResponse>() {
            @Override
            public void onResponse(Call<NewsFeedDataResponse> call, Response<NewsFeedDataResponse> response) {

//                fragmentHomeFeedBinding.pullToRefresh.setRefreshing(false);
                Log.d("responseseeee", "" + response.toString());
  //              home_shimmer.setVisibility(View.GONE);
    //            error_layout.setVisibility(View.GONE);

                ArrayList<PhotosData> imgArrayList = null;
                // ArrayList<CommentsData> cmntsArrayList= null;
                ArrayList<LikesData> likesArrayList = new ArrayList<>();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            String post_id = response.body().getData().get(i).getPostId();
                            String type= response.body().getData().get(i).getType();
                            String user_photo = response.body().getData().get(i).getPhoto();
                            String fullname = response.body().getData().get(i).getFullname();
                            String content = response.body().getData().get(i).getContent();
                            String created_date = response.body().getData().get(i).getCreatedDate();
                            String userid=response.body().getData().get(i).getUserId();
                            String is_like=response.body().getData().get(i).getIsLike();

                            Log.d("rrrr",i+"--"+response.body().getData().get(i).getIsLike()+"----"+post_id);
                            PhotoUrlResponse photoUrlResponse=response.body().getData().get(i).getPhotoUrl();
                            CommentsResponse commentsResponse=response.body().getData().get(i).getComments();
                            LikesResponse likesResponse=response.body().getData().get(i).getLikes();

                            if(photoUrlResponse.getStatus().toString().equalsIgnoreCase("1"))
                            {
                                imgArrayList = new ArrayList<>();
                                for(int j=0;j<photoUrlResponse.getPhotoUrl().size();j++)
                                {

                                    PhotosData photosData=new PhotosData(photoUrlResponse.getPhotoUrl().get(j).getPostDataId(),
                                            photoUrlResponse.getPhotoUrl().get(j).getPhotoUrl());
                                    Log.d("jasgdagsdhash",photoUrlResponse.getPhotoUrl().get(j).getPostDataId()+"");
                                    imgArrayList.add(photosData);
                                }
                            }
                            else {
                                imgArrayList = new ArrayList<>();
                                Log.d("adaaaaaa",""+post_id);
                            }

                            int countComment= response.body().getData().get(i).getCountComment();

                           /* if(response.body().getData().get(i).getComments().getStatus().toString().equalsIgnoreCase("1"))
                            {
                                cmntsArrayList = new ArrayList<>();
                                for(int j=0;j<response.body().getData().get(i).getComments().getCommentsData().size();j++)
                                {

                                    CommentsData commentsData=new CommentsData(response.body().getData().get(i).getComments().getCommentsData().get(j).getCommentId(),response.body().getData().get(i).getComments().getCommentsData().get(j).getPostId(),response.body().getData().get(i).getComments().getCommentsData().get(j).getUserId(),response.body().getData().get(i).getComments().getCommentsData().get(j).getCommentBy(),response.body().getData().get(i).getComments().getCommentsData().get(j).getComment(),response.body().getData().get(i).getComments().getCommentsData().get(j).getCreatedDate(),response.body().getData().get(i).getComments().getCommentsData().get(j).getFullname(),response.body().getData().get(i).getComments().getCommentsData().get(j).getUsername(),response.body().getData().get(i).getComments().getCommentsData().get(j).getPhoto());
                                    cmntsArrayList.add(commentsData);
                                }
                            }*/

                            int countLikes= response.body().getData().get(i).getCountLikes();

                            /*if(response.body().getData().get(i).getLikes().getStatus().toString().equalsIgnoreCase("1"))
                            {
                                for(int j=0;j<response.body().getData().get(i).getLikes().getLikesData().size();j++)
                                {
                                    LikesData likesData=new LikesData(response.body().getData().get(i).getLikes().getLikesData().get(j).getLikeId(),response.body().getData().get(i).getLikes().getLikesData().get(j).getPostId(),response.body().getData().get(i).getLikes().getLikesData().get(j).getUserId(),response.body().getData().get(i).getLikes().getLikesData().get(j).getLikedBy(),response.body().getData().get(i).getLikes().getLikesData().get(j).getCreatedDate(),response.body().getData().get(i).getLikes().getLikesData().get(j).getFullname(),response.body().getData().get(i).getLikes().getLikesData().get(j).getUsername(),response.body().getData().get(i).getLikes().getLikesData().get(j).getPhoto());
                                    likesArrayList.add(likesData);
                                }
                            }
*/
                            NewsFeedData newsFeedData=new NewsFeedData(NewsFeedData.MAIN_TYPE,post_id,user_photo,fullname,created_date,content,imgArrayList,countComment,countLikes,type,"false");
                            newsFeedData.setUser_id(userid);
                            newsFeedData.setIs_like(is_like);
                            dataArrayList.add(newsFeedData);

                        }

                        rc_my_posts.setAdapter(newsFeedAdapter);
                        //no_posts_layout.setVisibility(View.GONE);


                    } else {
                      //  no_posts_layout.setVisibility(View.VISIBLE);

                        Toast.makeText(getActivity(), "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //error_layout.setVisibility(View.VISIBLE);

                }




            }

            @Override
            public void onFailure(Call<NewsFeedDataResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "OK"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}
