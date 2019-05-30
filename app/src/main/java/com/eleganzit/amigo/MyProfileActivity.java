package com.eleganzit.amigo;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.adapter.EducationsAdapter;
import com.eleganzit.amigo.adapter.EducationsViewAdapter;
import com.eleganzit.amigo.adapter.NewsFeedAdapter;
import com.eleganzit.amigo.adapter.OpportunityAdapter;
import com.eleganzit.amigo.adapter.UserNewsFeedAdapter;
import com.eleganzit.amigo.adapter.WorksAdapter;
import com.eleganzit.amigo.adapter.WorksViewAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityMyProfileBinding;
import com.eleganzit.amigo.fragments.MyPhotosFragment;
import com.eleganzit.amigo.fragments.PhotosFragment;
import com.eleganzit.amigo.model.Educationdata;
import com.eleganzit.amigo.model.GetLoginResponse;
import com.eleganzit.amigo.model.GetStateResponse;
import com.eleganzit.amigo.model.GetUserResponse;
import com.eleganzit.amigo.model.GetWorkEduResponse;
import com.eleganzit.amigo.model.LoginData;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.model.UpdateUserResponse;
import com.eleganzit.amigo.model.WorkData;
import com.eleganzit.amigo.model.newsfeed.CommentsResponse;
import com.eleganzit.amigo.model.newsfeed.LikesData;
import com.eleganzit.amigo.model.newsfeed.LikesResponse;
import com.eleganzit.amigo.model.newsfeed.NewsFeedDataResponse;
import com.eleganzit.amigo.model.newsfeed.PhotoUrlResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

public class MyProfileActivity extends AppCompatActivity {

    private static final String TAG = "MyProfileActivity";
    ArrayList<String> imgArrayList = new ArrayList<>();
    public static ActivityMyProfileBinding binding;
    UserLoggedInSession userLoggedInSession;
    String username="", user_id="";
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_IMAGE = 2;
    String mediapath = "";
    File file ;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    private ArrayList<String> mSelectPath;
    private int REQUEST_BANNER_IMAGE=3;
    UserNewsFeedAdapter newsFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MyProfileActivity.this, R.layout.activity_my_profile);
        userLoggedInSession = new UserLoggedInSession(this);
        HashMap<String, String> hashMap = userLoggedInSession.getUserDetails();
        username = hashMap.get(UserLoggedInSession.USERNAME);
        user_id = hashMap.get(UserLoggedInSession.USER_ID);
        binding.username.setText(username);
        Glide.with(getApplicationContext()).load(hashMap.get(UserLoggedInSession.PHOTO)).apply(new RequestOptions().circleCrop().placeholder(R.drawable.user)).into(binding.circleUser);
/*
binding.circleUser.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        
    }
});
   */     binding.shimmerlayout.startShimmerAnimation();

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this, EditProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        binding.edSearch.setLongClickable(false);

        binding.edSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MyProfileActivity.this, MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

       /* MyPhotosFragment myPhotosFragment = new MyPhotosFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_activity_frame, myPhotosFragment, "TAG")
                .commit();*/

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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


        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcWorks.setLayoutManager(layoutManager1);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcEdu.setLayoutManager(layoutManager2);

       /* NewsFeedData newsFeedData = new NewsFeedData("zahir", imgArrayList);

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        binding.rcMyPosts.setAdapter(new UserNewsFeedAdapter(dataArrayList, this));*/
binding.userphoto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        pickImage2();
    }
});
binding.circleUser.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        pickImage();
        
    }
});
        binding.tabPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyPhotosFragment myPhotosFragment = new MyPhotosFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.profile_activity_frame, myPhotosFragment, "TAG")
                        .commit();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getWorkLoad();
        getUserData();
        if (dataArrayList.size()>0)
        {
            dataArrayList.clear();
        }
        newsFeedAdapter=new UserNewsFeedAdapter(dataArrayList, MyProfileActivity.this,user_id);

        getUserFeeds();

    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MyProfileActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                }

                mediapath = "" + sb.toString();


                Glide.with(getApplicationContext())
                        .load("" + mediapath.trim()).thumbnail(0.1f).

                        apply(RequestOptions.circleCropTransform().placeholder(R.drawable.user))

                        .into(binding.circleUser);
                Log.d("sdadad", "" + mediapath);
                file=new File(""+mediapath.trim());
                updateImage("photo");
            }


        }if (requestCode == REQUEST_BANNER_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                }

                mediapath = "" + sb.toString();


                Glide.with(getApplicationContext())
                        .load("" + mediapath.trim()).

                        apply(RequestOptions.placeholderOf(R.drawable.background))

                        .into(binding.userphoto);
                Log.d("sdadad", "" + mediapath);
                file=new File(""+mediapath.trim());
                updateImage("background_image");
            }


        }


    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(MyProfileActivity.this);
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath);
            selector.start(MyProfileActivity.this, REQUEST_IMAGE);
        }
    }
 private void pickImage2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(MyProfileActivity.this);
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath);
            selector.start(MyProfileActivity.this, REQUEST_BANNER_IMAGE);
        }
    }


    private void updateImage(final String image) {
        Log.d("Success",""+user_id+" "+file+" "+mediapath);


        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), ""+user_id);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData(""+image,file.getName(),requestFile);

        Call<UpdateUserResponse> getUserResponseCall=myInterface.updateProfile(userid,multipartBody);

        getUserResponseCall.enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                if (response.isSuccessful())
                {

                    if (response.body().getData()!=null)
                    {
                        if (image.equalsIgnoreCase("photo")) {
                            userLoggedInSession.updateImage("" + response.body().getData().get(0).getPhoto());
                        }
                        Log.d("Success",""+response.body().getData().get(0).getPhoto());
                        Log.d("Success",""+response.body().getData().get(0).getBackgroundImage());
                    }

                }

            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {

                Log.d("Success","error"+t.getMessage());
                Log.d("Success","error"+t.getCause());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
                            NewsFeedData newsFeedData=new NewsFeedData(post_id,user_photo,fullname,created_date,content,imgArrayList,countComment,countLikes,type,"false");
                            newsFeedData.setUser_id(userid);
                            newsFeedData.setIs_like(is_like);
                            dataArrayList.add(newsFeedData);

                        }

                        binding.rcMyPosts.setAdapter(newsFeedAdapter);
                        //no_posts_layout.setVisibility(View.GONE);


                    } else {
                        //  no_posts_layout.setVisibility(View.VISIBLE);

                        Toast.makeText(getApplicationContext(), "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //error_layout.setVisibility(View.VISIBLE);

                }




            }

            @Override
            public void onFailure(Call<NewsFeedDataResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "OK"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void getUserData() {
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<GetLoginResponse> call = myInterface.getUserData(user_id);
        call.enqueue(new Callback<GetLoginResponse>() {
            @Override
            public void onResponse(Call<GetLoginResponse> call, Response<GetLoginResponse> response) {


                List<LoginData> loginData = response.body().getData();

                if (loginData != null) {
                    for (int i = 0; i < loginData.size(); i++) {
                        LoginData loginData1 = loginData.get(i);

                        Glide.with(getApplicationContext()).load(loginData.get(i).getBackgroundImage()).into(binding.userphoto);

                        Log.d("Success", "----" + loginData1.getBackgroundImage());
                        if (loginData1.getCity() != null && !(loginData1.getCity().isEmpty())) {

                            binding.livesin.setText("Lives in " + loginData1.getCity());


                        } else {
                            binding.livesinlin.setVisibility(View.GONE);
                        }

                        if (loginData1.getHometown() != null && !(loginData1.getHometown().isEmpty())) {
                            binding.fromlocation.setText("From " + loginData1.getHometown());

                        } else {
                            binding.fromlocationlin.setVisibility(View.GONE);
                        }


                    }
                }


            }

            @Override
            public void onFailure(Call<GetLoginResponse> call, Throwable t) {

            }
        });

    }


    public void getWorkLoad() {
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);


        Call<GetWorkEduResponse> workresponse = myInterface.getWorkLoad(user_id);
        workresponse.enqueue(new Callback<GetWorkEduResponse>() {
            @Override
            public void onResponse(Call<GetWorkEduResponse> call, Response<GetWorkEduResponse> response) {

                Log.d(TAG, "" + response.body().getDatawork() + " " + user_id
                );

                binding.shimmerlayout.stopShimmerAnimation();
                binding.shimmerlayout.setVisibility(View.GONE);

                if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                    List<WorkData> datawork = response.body().getDatawork().getData();

                    if (datawork != null) {
                        if (datawork.size() > 0) {
                            binding.rcWorks.setAdapter(new WorksViewAdapter(datawork, MyProfileActivity.this));

                        }
                    }

                    List<Educationdata> list = response.body().getDataeducation().getData();

                    if (list.size() > 0) {
                        binding.rcEdu.setAdapter(new EducationsViewAdapter(list, MyProfileActivity.this));

                    }


                } else {
                    Toast.makeText(MyProfileActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetWorkEduResponse> call, Throwable t) {
                Log.d(TAG, "" + t.getMessage());
                binding.shimmerlayout.stopShimmerAnimation();
                binding.shimmerlayout.setVisibility(View.GONE);
            }
        });


    }
}
