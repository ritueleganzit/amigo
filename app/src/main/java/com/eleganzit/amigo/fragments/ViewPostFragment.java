package com.eleganzit.amigo.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.CommentsActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.PostLikesActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.adapter.ViewPostImagesAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.model.PostImages;
import com.eleganzit.amigo.model.newsfeed.CommentsData;
import com.eleganzit.amigo.model.newsfeed.LikesData;
import com.eleganzit.amigo.model.newsfeed.NewsFeedDataResponse;
import com.eleganzit.amigo.model.newsfeed.PhotoUrlResponse;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.eleganzit.amigo.utils.TextViewRobotoRegular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPostFragment extends Fragment {


    public ViewPostFragment() {
        // Required empty public constructor
    }

    String newsFeedData,userid;
    RecyclerView rc_view_post;
    ImageView profilePic;
    TextViewRobotoBold name;
    TextViewRobotoRegular txtStatusMsg,timestamp,likes,comments;
    Bundle bundle;
    RelativeLayout relrefresh;
    ImageView posttype;
ProgressDialog progressDialog;
NestedScrollView nested;
ImageView refresh,heart;

JSONObject jsonObj;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_post, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.GONE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.VISIBLE);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            newsFeedData = bundle.getString("newsFeedData");

            try {
                jsonObj = new JSONObject(newsFeedData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.d("ViewPOstData",""+jsonObj.toString());
           // Toast.makeText(getActivity(), "n"+newsFeedData, Toast.LENGTH_SHORT).show();

        }
else {

          //  Log.d("ViewPOstData",""+newsFeedData);
           // Toast.makeText(getActivity(), "nooo", Toast.LENGTH_SHORT).show();
        }


        rc_view_post = v.findViewById(R.id.rc_view_post);
        name = v.findViewById(R.id.name);
        posttype = v.findViewById(R.id.posttype);
        profilePic = v.findViewById(R.id.profilePic);
        refresh = v.findViewById(R.id.refresh);
        txtStatusMsg = v.findViewById(R.id.txtStatusMsg);
        timestamp = v.findViewById(R.id.timestamp);
        relrefresh = v.findViewById(R.id.relrefresh);
        likes = v.findViewById(R.id.likes);
        comments = v.findViewById(R.id.comments);
        nested = v.findViewById(R.id.nested);
        heart = v.findViewById(R.id.heart);

        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getActivity(), PostLikesActivity.class)
                        .putExtra("postid",newsFeedData)
                );
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rc_view_post.setLayoutManager(layoutManager);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSingleUserPost();
            }
        });


/*        String image5 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1";
        String image6 = "https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg";*/
        ArrayList<PostImages> arrayList = new ArrayList<>();

/*if (newsFeedData!=null)
{
    for (int i=0;i<newsFeedData.getImagesList().size();i++)
    {
        PhotosData photosData=newsFeedData.getImagesList().get(i);
        PostImages postImages1 = new PostImages(photosData.getPhoto());
        arrayList.add(postImages1);

    }

    if (newsFeedData.getUser_photo()!=null && !(newsFeedData.getUser_photo().isEmpty()))

    {
        Glide.with(getActivity()).load(newsFeedData.getUser_photo()).apply(RequestOptions.circleCropTransform()).into(profilePic);
    } if (newsFeedData.getFullname()!=null && !(newsFeedData.getFullname().isEmpty()))

    {
        name.setText(newsFeedData.getFullname());

    }if (newsFeedData.getContent()!=null && !(newsFeedData.getContent().isEmpty()))

    {
        txtStatusMsg.setText(newsFeedData.getContent());

    }
    if (newsFeedData.getDate_time()!=null && !(newsFeedData.getDate_time().isEmpty()))

    {
        timestamp.setText(parseDateToddMMyyyy(newsFeedData.getDate_time()));

    }

}

  *//*      PostImages postImages5 = new PostImages(image5);
        PostImages postImages6 = new PostImages(image6);
*//*



  *//*      arrayList.add(postImages5);
        arrayList.add(postImages6);*//*

        rc_view_post.setAdapter(new ViewPostImagesAdapter(arrayList, getActivity()));

        return v;*/

likes.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try {
            startActivity(new Intent(getActivity(), PostLikesActivity.class)
                    .putExtra("postid",jsonObj.getString("post_id"))


            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
});
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("uuuuuuu",""+userid);

                startActivity(new Intent(getActivity(), CommentsActivity.class)
                        .putExtra("userid",userid)
                        .putExtra("postid",newsFeedData)


                );


            }
        });
        return v;



    }

    private void getSingleUserPost() {

        ArrayList<PhotosData> imgArrayList = null;


        try {
            Glide
                    .with(getActivity())
                    .asBitmap()
                    .load(jsonObj.getString("user_photo"))
                    .apply(new RequestOptions().override(150, 150).transforms(new RoundedCorners(8)).centerCrop().circleCrop())
                    .thumbnail(.1f)
                    .into(profilePic);

            name.setText(jsonObj.getString("fullname"));
            timestamp.setText(parseDateToddMMyyyy(jsonObj.getString("date_time")));
            if (jsonObj.getString("is_like").equalsIgnoreCase("yes"))
            {
                heart.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_like));

              //  Toast.makeText(getActivity(), "if"+jsonObj.getString("is_like"), Toast.LENGTH_SHORT).show();
            }
            else
            {
                heart.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.heart));

             //   Toast.makeText(getActivity(), ""+jsonObj.getString("is_like"), Toast.LENGTH_SHORT).show();

            }
            if(jsonObj.getString("privacy").equalsIgnoreCase("friends"))
            {
                posttype.setImageResource(R.drawable.friends_dark);
            }
            else if(jsonObj.getString("privacy").equalsIgnoreCase("public"))
            {
                posttype.setImageResource(R.drawable.public_dark);
            }
            else if(jsonObj.getString("privacy").equalsIgnoreCase("only me"))
            {
                posttype.setImageResource(R.drawable.lock_dark);
            }

            txtStatusMsg.setText(jsonObj.getString("content"));

            if(String.valueOf(jsonObj.getInt("total_likes")).equalsIgnoreCase("0"))
            {
                likes.setText("Like");
            }

            else
            {
                likes.setText(jsonObj.getInt("total_likes")+" Likes");
            }

            if(String.valueOf(jsonObj.getInt("total_comments")).equalsIgnoreCase("0"))
            {
                comments.setText("Comment");
            }
            else
            {
                comments.setText(jsonObj.getInt("total_comments")+" Comments");
            }

userid=jsonObj.getString("user_id");
            JSONArray jsonArray=jsonObj.getJSONArray("imagesList");
            imgArrayList = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                PhotosData photosData=new PhotosData(jsonObject.getString("photo_id"),jsonObject.getString("photo"));
                imgArrayList.add(photosData);
            }

            rc_view_post.setAdapter(new ViewPostImagesAdapter(imgArrayList, getActivity()));


        } catch (JSONException e) {
            e.printStackTrace();
        }




       /* progressDialog.show();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<NewsFeedDataResponse> call=myInterface.getUserpost(newsFeedData);
        call.enqueue(new Callback<NewsFeedDataResponse>() {
            @Override
            public void onResponse(Call<NewsFeedDataResponse> call, Response<NewsFeedDataResponse> response) {
                progressDialog.dismiss();
                relrefresh.setVisibility(View.GONE);
                nested.setVisibility(View.VISIBLE);
                ArrayList<NewsFeedData> dataArrayList = new ArrayList<>();
                ArrayList<PhotosData> imgArrayList = null;
                ArrayList<CommentsData> cmntsArrayList= null;
                ArrayList<LikesData> likesArrayList = new ArrayList<>();
                if (response.isSuccessful())
                {
                    String post_id,type,user_photo="",fullname,content,created_date;
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        for (int i = 0; i < response.body().getData().size(); i++) {
                             post_id= response.body().getData().get(i).getPostId();
                             userid= response.body().getData().get(i).getUserId();
                             type = response.body().getData().get(i).getType();
                             user_photo = response.body().getData().get(i).getPhoto();
                             fullname = response.body().getData().get(i).getFullname();
                             content = response.body().getData().get(i).getContent();
                             created_date = response.body().getData().get(i).getCreatedDate();
                             txtStatusMsg.setText(content);
                             timestamp.setText(parseDateToddMMyyyy(created_date));
                             likes.setText(""+response.body().getData().get(i).getCountLikes()+" Likes");
                             comments.setText(""+response.body().getData().get(i).getCountComment()+" Comments");


                            PhotoUrlResponse photoUrlResponse=response.body().getData().get(i).getPhotoUrl();
                            if(photoUrlResponse.getStatus().toString().equalsIgnoreCase("1"))
                            {
                                imgArrayList = new ArrayList<>();
                                for(int j=0;j<photoUrlResponse.getPhotoUrl().size();j++)
                                {

                                    PhotosData photosData=new PhotosData(photoUrlResponse.getPhotoUrl().get(j).getPostDataId(),
                                            photoUrlResponse.getPhotoUrl().get(j).getPhotoUrl());
                                    Log.d("jasgdagsdhash",photoUrlResponse.getPhotoUrl().get(j).getPostDataId()+"");
                                    Log.d("jasgdagsdhash",user_photo+" "+fullname);
                                    imgArrayList.add(photosData);

                                    rc_view_post.setAdapter(new ViewPostImagesAdapter(imgArrayList, getActivity()));

                                }
                            }
                        }

                        Glide.with(getActivity()).load(""+user_photo).apply(RequestOptions.circleCropTransform()).into(profilePic);

                    }

                    }

            }

            @Override
            public void onFailure(Call<NewsFeedDataResponse> call, Throwable t) {
                progressDialog.dismiss();
                relrefresh.setVisibility(View.VISIBLE);
                nested.setVisibility(View.GONE);
            }
        });*/


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        getSingleUserPost();
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


}
