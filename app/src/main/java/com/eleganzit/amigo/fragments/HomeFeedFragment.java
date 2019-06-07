package com.eleganzit.amigo.fragments;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.CommentsActivity;
import com.eleganzit.amigo.CreatePostActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.PostLikesActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.UserProfileActivity;
import com.eleganzit.amigo.adapter.PostPhotosAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.FragmentHomeFeedBinding;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.model.SaveLikeResponse;
import com.eleganzit.amigo.model.newsfeed.CommentsData;
import com.eleganzit.amigo.model.newsfeed.CommentsResponse;
import com.eleganzit.amigo.model.newsfeed.LikesData;
import com.eleganzit.amigo.model.newsfeed.LikesResponse;
import com.eleganzit.amigo.model.newsfeed.NewsFeedDataResponse;
import com.eleganzit.amigo.model.newsfeed.PhotoUrlResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.eleganzit.amigo.utils.InfiniteScrollListener;
import com.eleganzit.amigo.utils.RecyclerItemClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import me.nereo.multi_image_selector.bean.Image;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFeedFragment extends Fragment implements InfiniteScrollListener.OnLoadMoreListener{
    int currentitems,totalitems,scrollitem;
    InfiniteScrollListener infiniteScrollListener;
    RecyclerView rc_posts;
    NestedScrollView news_feed_scroll;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList;
    ArrayList<PhotosData> ar_photos;
    UserLoggedInSession userLoggedInSession;
    ShimmerFrameLayout postsShimmerLayout;
    RelativeLayout error_layout,home_shimmer,no_posts_layout;
    boolean isScrolling;
    ProgressBar progressbar;

    private static int firstVisibleInListview;
    private static final int SELECT_PICTURE = 100;
    LinearLayoutManager layoutManager;
    NewsFeedAdapter newsFeedAdapter;
    public HomeFeedFragment() {
        // Required empty public constructor
    }
    String user_id = "",photo="",name="";


    FragmentHomeFeedBinding fragmentHomeFeedBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeFeedBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_home_feed,container,false);
        View v = fragmentHomeFeedBinding.getRoot();

        userLoggedInSession = new UserLoggedInSession(getActivity());

        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        photo = map.get(UserLoggedInSession.PHOTO);
        name = map.get(UserLoggedInSession.FNAME);

        error_layout=v.findViewById(R.id.error);
        home_shimmer=v.findViewById(R.id.home_shimmer);
        no_posts_layout=v.findViewById(R.id.no_posts_layout);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_green);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_gray);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);

//        news_feed_scroll=v.findViewById(R.id.news_feed_scroll);

        rc_posts=v.findViewById(R.id.rc_posts);
      //  header_upload_photo=v.findViewById(R.id.header_upload_photo);



        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        news_feed_scroll.scrollTo(0,0);

         layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rc_posts.setLayoutManager(layoutManager);
        firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();
        infiniteScrollListener = new InfiniteScrollListener(layoutManager, this);
        infiniteScrollListener.setLoaded();
        //rc_posts.setNestedScrollingEnabled(false);
        rc_posts.addOnScrollListener(infiniteScrollListener);



/*
        fragmentHomeFeedBinding.rcPosts.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        NewsFeedData newsFeedData=new NewsFeedData(NewsFeedData.FOOTER_TYPE,"","","","","",null,0,0,"","");

                        dataArrayList.add(newsFeedData);

                        getUserFeeds(totalitems+1);
                        Log.d("ttttt","if");
                    }
                    else {
                        Log.d("ttttt","else");
                    }

                    firstVisibleInListview = currentFirstVisible;

                }
            }
        });*/
        /*
        fragmentHomeFeedBinding.newsFeedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    Log.i("nesssssted", "Scroll DOWN");

                }
                if (scrollY < oldScrollY) {
                    Log.i("nesssssted", "Scroll UP");
                }

                if (scrollY == 0) {
                    Log.i("nesssssted", "TOP SCROLL");
                }

                if (scrollY == ( v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight() )) {
                    Log.i("nesssssted", "BOTTOM SCROLL");
                }

                if (scrollY ==  v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())
                {

                    Log.i("nesssssted", "BOTTOM SCROLLLL");
                    totalitems=layoutManager.getItemCount();
getUserFeeds(totalitems+1);
                    // end of the scroll view
                }
            }
        });

        fragmentHomeFeedBinding.newsFeedScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View)fragmentHomeFeedBinding.newsFeedScroll.getChildAt(fragmentHomeFeedBinding.newsFeedScroll.getChildCount() - 1);

                int diff = (view.getBottom() - (fragmentHomeFeedBinding.newsFeedScroll.getHeight() + fragmentHomeFeedBinding.newsFeedScroll
                        .getScrollY()));

                if (diff == 0) {
                    Log.d("jngjygbukyg","paggg");
                    // your pagination code
                }
            }
        });*/
        /*fragmentHomeFeedBinding.rcPosts.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        getUserFeeds(totalitems+1);
                        Log.d("ttttt","if");


                    }
                    else {
                        Log.d("ttttt","else");

                    }

                    firstVisibleInListview = currentFirstVisible;

                }
            }
        });
*/


       /* imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");

        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");*/
        /*ImgarrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        */
     /*   NewsFeedData newsFeedData=new NewsFeedData("zahir",imgArrayList);

        if(dataArrayList.size()>0)
        {
            dataArrayList.clear();
        }

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        rc_posts.setAdapter(new NewsFeedAdapter(dataArrayList,getActivity()));*/



     fragmentHomeFeedBinding.pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
         @Override
         public void onRefresh() {
             if (dataArrayList.size()>0) {
             dataArrayList.clear();
                 getUserFeeds();
             }
            //  Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
         }
     });

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

    public void  getUserFeeds(int size){
       // Toast.makeText(getActivity(), "OKKK", Toast.LENGTH_SHORT).show();
        error_layout.setVisibility(View.GONE);
        imgArrayList=new ArrayList<>();
        ar_photos=new ArrayList<>();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<NewsFeedDataResponse> call=myInterface.user_newsfeedData(user_id,""+size,"2");
        call.enqueue(new Callback<NewsFeedDataResponse>() {
            @Override
            public void onResponse(Call<NewsFeedDataResponse> call, Response<NewsFeedDataResponse> response) {

                newsFeedAdapter.removeLast();
                fragmentHomeFeedBinding.pullToRefresh.setRefreshing(false);
                Log.d("responseseeee", "" + response.toString());
               // error_layout.setVisibility(View.GONE);
              //  Log.d("responseseeee","--"+response.body().getData().get(0).getUserId());
                ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();

                ArrayList<PhotosData> imgArrayList = null;
                ArrayList<CommentsData> cmntsArrayList= null;
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
                            Log.d("Useriddata","--"+response.body().getData().get(i).getUserId());
                            String is_like=response.body().getData().get(i).getIsLike();

                            String userid=response.body().getData().get(i).getUserId();
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
                            }

                            int countComment= response.body().getData().get(i).getCountComment();

                            /*if(response.body().getData().get(i).getComments().getStatus().toString().equalsIgnoreCase("1"))
                            {
                                cmntsArrayList = new ArrayList<>();
                                for(int j=0;j<response.body().getData().get(i).getComments().getCommentsData().size();j++)
                                {

                                    CommentsData commentsData=new CommentsData(response.body().getData().get(i).getComments().getCommentsData().get(j).getCommentId(),response.body().getData().get(i).getComments().getCommentsData().get(j).getPostId(),response.body().getData().get(i).getComments().getCommentsData().get(j).getUserId(),response.body().getData().get(i).getComments().getCommentsData().get(j).getCommentBy(),response.body().getData().get(i).getComments().getCommentsData().get(j).getComment(),response.body().getData().get(i).getComments().getCommentsData().get(j).getCreatedDate(),response.body().getData().get(i).getComments().getCommentsData().get(j).getFullname(),response.body().getData().get(i).getComments().getCommentsData().get(j).getUsername(),response.body().getData().get(i).getComments().getCommentsData().get(j).getPhoto());
                                    cmntsArrayList.add(commentsData);
                                }
                            }
*/
                            int countLikes= response.body().getData().get(i).getCountLikes();

                           /* if(response.body().getData().get(i).getLikes().getStatus().toString().equalsIgnoreCase("1"))
                            {
                                for(int j=0;j<response.body().getData().get(i).getLikes().getLikesData().size();j++)
                                {
                                    LikesData likesData=new LikesData(response.body().getData().get(i).getLikes().getLikesData().get(j).getLikeId(),response.body().getData().get(i).getLikes().getLikesData().get(j).getPostId(),response.body().getData().get(i).getLikes().getLikesData().get(j).getUserId(),response.body().getData().get(i).getLikes().getLikesData().get(j).getLikedBy(),response.body().getData().get(i).getLikes().getLikesData().get(j).getCreatedDate(),response.body().getData().get(i).getLikes().getLikesData().get(j).getFullname(),response.body().getData().get(i).getLikes().getLikesData().get(j).getUsername(),response.body().getData().get(i).getLikes().getLikesData().get(j).getPhoto());
                                    likesArrayList.add(likesData);
                                }
                            }*/

                            NewsFeedData newsFeedData=new NewsFeedData(NewsFeedData.MAIN_TYPE,post_id,user_photo,fullname,created_date,content,imgArrayList,countComment,countLikes,type,"false");

                            newsFeedData.setUser_id(userid);
                            newsFeedData.setIs_like(is_like);
                            dataArrayList.add(newsFeedData);

                        }
                        Log.d("scrollllll",""+dataArrayList.size());

                       newsFeedAdapter.addData(dataArrayList);
                        infiniteScrollListener.setLoaded();
                      // rc_posts.setAdapter(newsFeedAdapter);
                      //  no_posts_layout.setVisibility(View.GONE);


                    } else {

                        Log.d("errorrrr",""+response.body().getMessage());
                       // Toast.makeText(getActivity(), "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }




            }

            @Override
            public void onFailure(Call<NewsFeedDataResponse> call, Throwable t) {
               // error_layout.setVisibility(View.VISIBLE);
                newsFeedAdapter.removeLast();
                fragmentHomeFeedBinding.pullToRefresh.setRefreshing(false);
            }
        });



    }


    private void getUserFeeds() {

        home_shimmer.setVisibility(View.VISIBLE);
        error_layout.setVisibility(View.GONE);
        imgArrayList=new ArrayList<>();
        ar_photos=new ArrayList<>();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<NewsFeedDataResponse> call=myInterface.user_newsfeedData(user_id,"1","2");
        call.enqueue(new Callback<NewsFeedDataResponse>() {
            @Override
            public void onResponse(Call<NewsFeedDataResponse> call, Response<NewsFeedDataResponse> response) {

                fragmentHomeFeedBinding.pullToRefresh.setRefreshing(false);
                Log.d("responseseeee", "" + response.toString());
                home_shimmer.setVisibility(View.GONE);
                error_layout.setVisibility(View.GONE);

                ArrayList<PhotosData> imgArrayList = null;
               // ArrayList<CommentsData> cmntsArrayList= null;
                ArrayList<LikesData> likesArrayList = new ArrayList<>();
                NewsFeedData newsFeedData=new NewsFeedData(NewsFeedData.HEADER_TYPE,"","","","","",null,0,0,"","");


                dataArrayList.add(newsFeedData);
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
                            NewsFeedData newsFeedData2=new NewsFeedData(NewsFeedData.MAIN_TYPE,post_id,user_photo,fullname,created_date,content,imgArrayList,countComment,countLikes,type,"false");
                            newsFeedData2.setUser_id(userid);
                            newsFeedData2.setIs_like(is_like);
                            dataArrayList.add(newsFeedData2);

                        }

                        rc_posts.setAdapter(newsFeedAdapter);
                        no_posts_layout.setVisibility(View.GONE);


                    } else {
                        no_posts_layout.setVisibility(View.VISIBLE);

                        Toast.makeText(getActivity(), "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    error_layout.setVisibility(View.VISIBLE);

                }




            }

            @Override
            public void onFailure(Call<NewsFeedDataResponse> call, Throwable t) {
                error_layout.setVisibility(View.VISIBLE);
                home_shimmer.setVisibility(View.GONE);
                fragmentHomeFeedBinding.pullToRefresh.setRefreshing(false);
            }
        });



    }

    void openImageChooser() {
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int clumnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String mediapath = cursor.getString(clumnIndex);
                Bundle extra = new Bundle();
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    if(ar_photos.size()>0)
                    {
                        ar_photos.clear();
                    }
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        // Get the cursor
                        Cursor cursor2 = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor2.moveToFirst();

                        int columnIndex = cursor2.getColumnIndex(filePathColumn[0]);
                        mediapath  = cursor2.getString(columnIndex);
                        PhotosData photosData=new PhotosData("",mediapath);

                        ar_photos.add(photosData);

                        cursor2.close();

                    }

                    extra.putSerializable("objects", ar_photos);

                    startActivity(new Intent(getActivity(),CreatePostActivity.class).putExtra("imagesFilePath",extra));
                    getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    Log.d("LOG_TAG", "Selected Images" + mArrayUri.size());
                }

            }
        }
        if (resultCode==RESULT_CANCELED)
        {

        }

    }

    @Override
    public void onLoadMore() {

        NewsFeedData newsFeedData=new NewsFeedData(NewsFeedData.FOOTER_TYPE,"","","","","",null,0,0,"","");

        dataArrayList.add(newsFeedData);

        getUserFeeds(dataArrayList.size()+1);

    }

    class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder>
    {
        private static final int HEADER_VIEW = 1;

        private static final int MAIN_VIEW = 10;
        private static final int FOOTER_VIEW = 11;

        ArrayList<NewsFeedData> posts;
        ArrayList<String> photos=new ArrayList<>();
        Context context;
        Activity activity;
        String user_id="";

        public NewsFeedAdapter(ArrayList<NewsFeedData> posts, Context context,String user_id) {
            this.posts = posts;
            this.context = context;
            activity = (Activity) context;
            this.user_id=user_id;
        }

        @Override
        public int getItemViewType(int position) {
            switch (posts.get(position).getView_type())
            {
                case NewsFeedData.HEADER_TYPE:
                    return NewsFeedData.HEADER_TYPE;
                case NewsFeedData.MAIN_TYPE:
                    return NewsFeedData.MAIN_TYPE;
                case NewsFeedData.FOOTER_TYPE:
                    return NewsFeedData.FOOTER_TYPE;
                default:
                    return -1;
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v;
            switch (i) {
                case NewsFeedData.HEADER_TYPE:
                    Log.d("ooooo","header");
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_headerview, viewGroup, false);
                    return new HeaderViewHolder(v);
                case NewsFeedData.MAIN_TYPE:
                    Log.d("oooo","main");
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_feed_layout, viewGroup, false);
                    return new DataViewHolder(v);
                case NewsFeedData.FOOTER_TYPE:
                    Log.d("oooo","footer");
                    v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_view, viewGroup, false);
                    return new FooterViewHolder(v);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder  holder, final int i) {

            final NewsFeedData newsFeedData = posts.get(i);
            switch (newsFeedData.getView_type()) {
                case NewsFeedData.MAIN_TYPE:
                    ((DataViewHolder) holder).bindView(i);
                    Log.d("fghf", "" + newsFeedData.getContent() + "  " + i);

                    Log.d("hhhhhhhhh", "" + newsFeedData.getIs_like());
                    if (newsFeedData.getIs_like().equals("yes")) {
                        ((DataViewHolder) holder).like_post.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like));
                    } else {
                        ((DataViewHolder) holder).like_post.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
                        //issueView((DataViewHolder)holder).likesnumber.setTextColor(Color.GREY);
                    }
                    final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.scaleamin);
                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(interpolator);
                    ((DataViewHolder) holder).like_post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((DataViewHolder) holder).like_post.startAnimation(myAnim);
                            //Toast.makeText(context, newsFeedData.getIs_liked()+"-"+i, Toast.LENGTH_SHORT).show();

                            if (newsFeedData.getIs_like().equalsIgnoreCase("yes")) {
                                //upd.ate unlike drawable
                                // newsFeedData.setIs_liked("false");
                                newsFeedData.setTotal_likes((newsFeedData.getTotal_likes()) - 1);

                                ((DataViewHolder) holder).txt_likes.setText("" + ((newsFeedData.getTotal_likes())) + " Like");
                                ((DataViewHolder) holder).like_post.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
                                // notifyItemChanged(i, "preunlike");
                                newsFeedData.setIs_like("no");
                                savePostLike(newsFeedData.getPost_id(), newsFeedData.getUser_id(), user_id);

                                // Toast.makeText(context, newsFeedData.getIs_like()+"like"+i, Toast.LENGTH_SHORT).show();

                            } else {
                                // Toast.makeText(context, newsFeedData.getIs_like()+"like"+i, Toast.LENGTH_SHORT).show();
                                newsFeedData.setIs_like("yes");
                                newsFeedData.setTotal_likes((newsFeedData.getTotal_likes()) + 1);

                                //update like drawable
                                ((DataViewHolder) holder).txt_likes.setText("" + ((newsFeedData.getTotal_likes())) + " Like");
                                ((DataViewHolder) holder).like_post.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like));
                                // newsFeedData.setIs_liked("true");
                                // notifyItemChanged(i, "prelike");
                                savePostLike(newsFeedData.getPost_id(), newsFeedData.getUser_id(), user_id);
                            }
                            //make network request
                            //updateLike(i);


                        }
                    });


                    Glide
                            .with(context)
                            .asBitmap()
                            .load(newsFeedData.getUser_photo())

                            .apply(new RequestOptions().override(150, 150).transforms(new RoundedCorners(8)).centerCrop().circleCrop())
                            .thumbnail(.1f)
                            .into(((DataViewHolder) holder).profilePic);

                    ((DataViewHolder) holder).txt_fullname.setText(newsFeedData.getFullname());

                    ((DataViewHolder) holder).txt_date_time.setText(parseDateToddMMyyyy(newsFeedData.getDate_time()));

                    if (newsFeedData.getPrivacy().equalsIgnoreCase("friends")) {
                        ((DataViewHolder) holder).privacy_img.setImageResource(R.drawable.friends_dark);
                    } else if (newsFeedData.getPrivacy().equalsIgnoreCase("public")) {
                        ((DataViewHolder) holder).privacy_img.setImageResource(R.drawable.public_dark);
                    } else if (newsFeedData.getPrivacy().equalsIgnoreCase("only me")) {
                        ((DataViewHolder) holder).privacy_img.setImageResource(R.drawable.lock_dark);
                    }

                    ((DataViewHolder) holder).txt_content.setText(newsFeedData.getContent());
                    ((DataViewHolder) holder).txt_likes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.startActivity(new Intent(context, PostLikesActivity.class)
                                    .putExtra("postid", newsFeedData.getPost_id())
                            );
                            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


                        }
                    });
                    ((DataViewHolder) holder).txt_likes.setText(newsFeedData.getTotal_likes() + " Likes");

                    ((DataViewHolder) holder).txt_comments.setText(newsFeedData.getTotal_comments() + " Comments");


                    if (newsFeedData.getImagesList() != null) {
                        if (newsFeedData.getImagesList().size() > 0) {
                            Log.d("adaaaapter", "" + newsFeedData.getPost_id());
                        }


                        ((DataViewHolder) holder).flexboxLayout.setAdapter(new PostPhotosAdapter(newsFeedData.getImagesList(), context, "userfeed", "" + newsFeedData.getPost_id()));
                    } else {
                        Log.d("adaaaapter", "---hnhuh");

                    }

                    ((DataViewHolder) holder).flexboxLayout.addOnItemTouchListener(new RecyclerItemClickListener(context, ((DataViewHolder) holder).flexboxLayout, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Gson gson = new GsonBuilder().create();
                            final String json = gson.toJson(newsFeedData);// obj is your object

                            Bundle bundle = new Bundle();
                            bundle.putString("newsFeedData", json);
                            ViewPostFragment viewPostFragment = new ViewPostFragment();

                            viewPostFragment.setArguments(bundle);

                            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame, viewPostFragment, "TAG");
                            fragmentTransaction.addToBackStack("HomeFeedFragment");
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
                    ((DataViewHolder) holder).comment_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            context.startActivity(new Intent(context, CommentsActivity.class)
                                    .putExtra("userid", newsFeedData.getUser_id())
                                    .putExtra("postid", newsFeedData.getPost_id())


                            );
                            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                        }
                    });

                    ((DataViewHolder) holder).menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
                            View sheetView = activity.getLayoutInflater().inflate(R.layout.post_options_layout, null);
                            mBottomSheetDialog.setContentView(sheetView);
                            mBottomSheetDialog.show();
                        }
                    });

                    ((DataViewHolder) holder).profile_click.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(context, "**"+newsFeedData.getUser_id(), Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, UserProfileActivity.class).putExtra("userid", newsFeedData.getUser_id()));
                            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });

                    ((DataViewHolder) holder).profilePic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "---" + newsFeedData.getUser_id(), Toast.LENGTH_SHORT).show();

                            context.startActivity(new Intent(context, UserProfileActivity.class).putExtra("userid", newsFeedData.getUser_id()));
                            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });

//        for(int j=0;i<newsFeedData.getArrayList().size();j++)
//        {
//            photos.add(newsFeedData.getArrayList().get(j));
//        }

                    Log.d("ImageList", "" + newsFeedData.getImagesList());
                    if (newsFeedData.getImagesList() != null) {
                        ((DataViewHolder) holder).flexboxLayout.setAdapter(new PostPhotosAdapter(newsFeedData.getImagesList(), context, "newsfeed", "" + newsFeedData.getPost_id()));

                    }


                    ((DataViewHolder) holder).lin_main.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Gson gson = new GsonBuilder().create();
                            final String json = gson.toJson(newsFeedData);// obj is your object

                            Bundle bundle = new Bundle();
                            bundle.putString("newsFeedData", json);
                            ViewPostFragment viewPostFragment = new ViewPostFragment();

                            viewPostFragment.setArguments(bundle);

                            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame, viewPostFragment, "TAG");
                            fragmentTransaction.addToBackStack("HomeFeedFragment");
                            fragmentTransaction.commit();
                        }
                    });
                    break;


                case NewsFeedData.HEADER_TYPE:

                    Glide.with(getActivity()).load(photo).apply(RequestOptions.circleCropTransform()).into(((HeaderViewHolder) holder).photo);

                    ((HeaderViewHolder) holder).ed_status.setLongClickable(false);

                    ((HeaderViewHolder) holder).ed_status.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), CreatePostActivity.class));
                            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });

                    ((HeaderViewHolder) holder).header_upload_photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openImageChooser();
                        }
                    });
                    break;
                case NewsFeedData.FOOTER_TYPE:


                    break;

            }
        }

        private void savePostLike( String post_id, String post_user_id, String userid) {
            RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
            Call<SaveLikeResponse> call=myInterface.savePostLike(post_id,post_user_id,userid);
            call.enqueue(new Callback<SaveLikeResponse>() {
                @Override
                public void onResponse(Call<SaveLikeResponse> call, Response<SaveLikeResponse> response) {
                    if (response.isSuccessful())
                    {

                        Log.d("hhhh",""+response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<SaveLikeResponse> call, Throwable t) {
                    Log.d("hhhh",""+t.getMessage());
                    //newsFeedData.setIs_like("no");
                }
            });

        }

        @Override
        public int getItemCount()
        {

            // Add extra view to show the footer view
            return posts.size();
          //  return posts.size();
        }
        public class DataViewHolder extends MyViewHolder {
            // Define elements of a row here

            ImageView profilePic,menu,f,s,t,privacy_img,like_post,comment_post;
            RecyclerView flexboxLayout;
            LinearLayout lin_main,profile_click,post_main,comment_layout,like_layout;
            TextView txt_fullname,txt_date_time,txt_likes,txt_comments,txt_content;
            public DataViewHolder(View itemView) {
                super(itemView);
                menu=itemView.findViewById(R.id.menu);
                profilePic=itemView.findViewById(R.id.profilePic);
                flexboxLayout = itemView.findViewById(R.id.flexbox_layout);
                lin_main = itemView.findViewById(R.id.lin_main);
                profile_click = itemView.findViewById(R.id.profile_click);
                post_main = itemView.findViewById(R.id.post_main);
                privacy_img = itemView.findViewById(R.id.privacy_img);
                txt_content = itemView.findViewById(R.id.txt_content);
                like_post = itemView.findViewById(R.id.like_post);
                txt_fullname = itemView.findViewById(R.id.txt_fullname);
                txt_date_time = itemView.findViewById(R.id.txt_date_time);
                txt_likes = itemView.findViewById(R.id.txt_likes);
                comment_layout = itemView.findViewById(R.id.comment_layout);
                like_layout = itemView.findViewById(R.id.like_layout);
                txt_comments = itemView.findViewById(R.id.txt_comments);
                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
                layoutManager.setFlexDirection(FlexDirection.ROW);
                layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                flexboxLayout.setLayoutManager(layoutManager);

                // Find view by ID and initialize here
            }

            public void bindView(int position) {
                // bindView() method to implement actions
            }
        }

        public class HeaderViewHolder extends MyViewHolder {
            LinearLayout header_upload_photo;
            EditText ed_status;
            ImageView photo;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                header_upload_photo=itemView.findViewById(R.id.header_upload_photo);
                photo=itemView.findViewById(R.id.photo);
                ed_status=itemView.findViewById(R.id.ed_status);


            }
        }

        public class FooterViewHolder extends MyViewHolder {
            ProgressBar pb;
            public FooterViewHolder (View itemView) {
                super(itemView);
                pb=itemView.findViewById(R.id.progressbar);


            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                
            }
        }


        public void removeLast() {
            posts.remove(posts.size() - 1);
            notifyItemRemoved(posts.size());
        }

        public void addData(ArrayList<NewsFeedData> integersList) {
            posts.addAll(integersList);
            notifyDataSetChanged();
        }
        private void updateLike(final int adapterPosition) {
            /*Network Request code*/
// if success
            /*update the no. of likes or this card*/
// if fails, check first the status of 'liked', and revert the
// drawable to its previous state
            if (posts.get(adapterPosition).getIs_liked().equals("false"))
            {
                posts.get(adapterPosition).setIs_liked("true");
                notifyItemChanged(adapterPosition, "prelike");
            } else {
                posts.get(adapterPosition).setIs_liked("false");
                notifyItemChanged(adapterPosition, "preunlike");
            }
        }


        public String parseDateToddMMyyyy(String time) {
            String inputPattern = "yyyy-MM-dd HH:mm:ss";
            String outputPattern = "MMM dd 'at' HH:mm a";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            Date date = null;
            String str = null;

            try {
                date = inputFormat.parse(time);
                str = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return str;
        }
        class MyBounceInterpolator implements android.view.animation.Interpolator {
            private double mAmplitude = 1;
            private double mFrequency = 10;

            MyBounceInterpolator(double amplitude, double frequency) {
                mAmplitude = amplitude;
                mFrequency = frequency;
            }

            public float getInterpolation(float time) {
                return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                        Math.cos(mFrequency * time) + 1);
            }
        }




    }
}
