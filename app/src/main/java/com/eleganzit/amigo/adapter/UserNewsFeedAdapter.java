package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.CommentsActivity;
import com.eleganzit.amigo.PostLikesActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.UserProfileActivity;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.fragments.ViewPostFragment;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.model.SaveLikeResponse;
import com.eleganzit.amigo.utils.RecyclerItemClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserNewsFeedAdapter extends RecyclerView.Adapter<UserNewsFeedAdapter.MyViewHolder>
{

    ArrayList<NewsFeedData> posts;
    ArrayList<String> photos=new ArrayList<>();
    Context context;
    Activity activity;
    String user_id="";

    public UserNewsFeedAdapter(ArrayList<NewsFeedData> posts, Context context,String user_id) {
        this.posts = posts;
        this.context = context;
        activity = (Activity) context;
        this.user_id=user_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_news_feed_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        final NewsFeedData newsFeedData=posts.get(i);

        Log.d("hhhhhhhhh",""+newsFeedData.getIs_like());
        if (newsFeedData.getIs_like().equals("yes")) {
            holder.like_post.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like));
        } else {
            holder.like_post.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
            //issueViewHolder.likesnumber.setTextColor(Color.GREY);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.scaleamin);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        holder.like_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.like_post.startAnimation(myAnim);
                //Toast.makeText(context, newsFeedData.getIs_liked()+"-"+i, Toast.LENGTH_SHORT).show();

                if (newsFeedData.getIs_like().equalsIgnoreCase("yes")) {
                    //upd.ate unlike drawable
                    // newsFeedData.setIs_liked("false");
                    newsFeedData.setTotal_likes((newsFeedData.getTotal_likes()) - 1);

                    holder.txt_likes.setText("" + ((newsFeedData.getTotal_likes())) + " Like" );
                    holder.like_post.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
                    // notifyItemChanged(i, "preunlike");
                    newsFeedData.setIs_like("no");
                    savePostLike(newsFeedData.getPost_id(),newsFeedData.getUser_id(),user_id);

                    // Toast.makeText(context, newsFeedData.getIs_like()+"like"+i, Toast.LENGTH_SHORT).show();

                } else {
                    // Toast.makeText(context, newsFeedData.getIs_like()+"like"+i, Toast.LENGTH_SHORT).show();
                    newsFeedData.setIs_like("yes");
                    newsFeedData.setTotal_likes((newsFeedData.getTotal_likes()) + 1);

                    //update like drawable
                    holder.txt_likes.setText("" + ((newsFeedData.getTotal_likes())) + " Like");
                    holder.like_post.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like));
                    // newsFeedData.setIs_liked("true");
                    // notifyItemChanged(i, "prelike");
                    savePostLike(newsFeedData.getPost_id(),newsFeedData.getUser_id(),user_id);
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
                .into(holder.profilePic);

        holder.txt_fullname.setText(newsFeedData.getFullname());

        holder.txt_date_time.setText(parseDateToddMMyyyy(newsFeedData.getDate_time()));

        if(newsFeedData.getPrivacy().equalsIgnoreCase("friends"))
        {
            holder.privacy_img.setImageResource(R.drawable.friends_dark);
        }
        else if(newsFeedData.getPrivacy().equalsIgnoreCase("public"))
        {
            holder.privacy_img.setImageResource(R.drawable.public_dark);
        }
        else if(newsFeedData.getPrivacy().equalsIgnoreCase("only me"))
        {
            holder.privacy_img.setImageResource(R.drawable.lock_dark);
        }

        holder.txt_content.setText(newsFeedData.getContent());
        holder.txt_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PostLikesActivity.class)
                        .putExtra("postid",newsFeedData.getPost_id())
                );
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


            }
        });
        holder.txt_likes.setText(newsFeedData.getTotal_likes()+" Likes");

        holder.txt_comments.setText(newsFeedData.getTotal_comments()+" Comments");


        if(newsFeedData.getImagesList()!=null)
        {
            if (newsFeedData.getImagesList().size()>0) {
                Log.d("adaaaapter", "" + newsFeedData.getPost_id());
            }


            holder.flexboxLayout.setAdapter(new PostPhotosAdapter(newsFeedData.getImagesList(),context,"userfeed",""+newsFeedData.getPost_id()));
        }
        else
        {
            Log.d("adaaaapter", "---hnhuh" );

        }


        holder.comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, CommentsActivity.class)
                        .putExtra("userid",newsFeedData.getUser_id())
                        .putExtra("postid",newsFeedData.getPost_id())


                );
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
                View sheetView = activity.getLayoutInflater().inflate(R.layout.post_options_layout, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
            }
        });


//        for(int j=0;i<newsFeedData.getArrayList().size();j++)
//        {
//            photos.add(newsFeedData.getArrayList().get(j));
//        }

        Log.d("ImageList",""+newsFeedData.getImagesList());
        if (newsFeedData.getImagesList()!=null)
        {
            holder.flexboxLayout.setAdapter(new PostPhotosAdapter(newsFeedData.getImagesList(),context,"newsfeed",""+newsFeedData.getPost_id()));

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
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic,menu,f,s,t,privacy_img,like_post,comment_post;
        RecyclerView flexboxLayout;
        LinearLayout lin_main,profile_click,post_main,comment_layout,like_layout;
        TextView txt_fullname,txt_date_time,txt_likes,txt_comments,txt_content;
        public MyViewHolder(@NonNull View itemView) {
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

        }
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
