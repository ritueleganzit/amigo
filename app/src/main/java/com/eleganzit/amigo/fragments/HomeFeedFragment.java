package com.eleganzit.amigo.fragments;


import android.content.ClipData;
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
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.CreatePostActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.adapter.NewsFeedAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.FragmentHomeFeedBinding;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.model.newsfeed.CommentsData;
import com.eleganzit.amigo.model.newsfeed.CommentsResponse;
import com.eleganzit.amigo.model.newsfeed.LikesData;
import com.eleganzit.amigo.model.newsfeed.LikesResponse;
import com.eleganzit.amigo.model.newsfeed.NewsFeedDataResponse;
import com.eleganzit.amigo.model.newsfeed.PhotoUrlResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFeedFragment extends Fragment {
    int currentitems,totalitems,scrollitem;

    EditText ed_status;
    RecyclerView rc_posts;
    NestedScrollView news_feed_scroll;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList;
    ArrayList<PhotosData> ar_photos;
    UserLoggedInSession userLoggedInSession;
    LinearLayout header_upload_photo;
    ShimmerFrameLayout postsShimmerLayout;
    RelativeLayout error_layout,home_shimmer,no_posts_layout;
    boolean isScrolling;

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
        Glide.with(getActivity()).load(photo).apply(RequestOptions.circleCropTransform()).into(fragmentHomeFeedBinding.photo);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_green);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_gray);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);

//        news_feed_scroll=v.findViewById(R.id.news_feed_scroll);

        ed_status=v.findViewById(R.id.ed_status);
        rc_posts=v.findViewById(R.id.rc_posts);
        header_upload_photo=v.findViewById(R.id.header_upload_photo);



        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        news_feed_scroll.scrollTo(0,0);

         layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rc_posts.setLayoutManager(layoutManager);
        firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();

        //rc_posts.setNestedScrollingEnabled(false);
        ed_status.setLongClickable(false);

        ed_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CreatePostActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        header_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });


        fragmentHomeFeedBinding.rcPosts.setNestedScrollingEnabled(false);
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
        });
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
        fragmentHomeFeedBinding.progressbar.setVisibility(View.VISIBLE);
        error_layout.setVisibility(View.GONE);
        imgArrayList=new ArrayList<>();
        ar_photos=new ArrayList<>();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<NewsFeedDataResponse> call=myInterface.user_newsfeedData(user_id,""+size,"2");
        call.enqueue(new Callback<NewsFeedDataResponse>() {
            @Override
            public void onResponse(Call<NewsFeedDataResponse> call, Response<NewsFeedDataResponse> response) {
                fragmentHomeFeedBinding.progressbar.setVisibility(View.GONE);

                fragmentHomeFeedBinding.pullToRefresh.setRefreshing(false);
                Log.d("responseseeee", "" + response.toString());
               // error_layout.setVisibility(View.GONE);
              //  Log.d("responseseeee","--"+response.body().getData().get(0).getUserId());

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

                            NewsFeedData newsFeedData=new NewsFeedData(post_id,user_photo,fullname,created_date,content,imgArrayList,countComment,countLikes,type,"false");

                            newsFeedData.setUser_id(userid);
                            newsFeedData.setIs_like(is_like);
                            dataArrayList.add(newsFeedData);

                        }
                        Log.d("scrollllll",""+dataArrayList.size());

                       newsFeedAdapter.notifyDataSetChanged();

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
                fragmentHomeFeedBinding.progressbar.setVisibility(View.GONE);

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
                            NewsFeedData newsFeedData=new NewsFeedData(post_id,user_photo,fullname,created_date,content,imgArrayList,countComment,countLikes,type,"false");
                            newsFeedData.setUser_id(userid);
                            newsFeedData.setIs_like(is_like);
                            dataArrayList.add(newsFeedData);

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
}
