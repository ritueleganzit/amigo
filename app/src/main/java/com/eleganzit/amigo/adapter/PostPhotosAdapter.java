package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.fragments.ViewPostFragment;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.google.android.flexbox.FlexDirection;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import static android.content.Context.WINDOW_SERVICE;

public class PostPhotosAdapter extends RecyclerView.Adapter<PostPhotosAdapter.MyViewHolder>
{

    ArrayList<PhotosData> photos;
    Context context;
    Activity activity;
    String from,postid;

    public PostPhotosAdapter(ArrayList<PhotosData> photos, Context context,String from,String postid) {
        this.photos = photos;
        this.context = context;
        this.from = from;
        this.postid = postid;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_photo_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        if(photos.size()>=5)
        {
            if(i<2)
            {
                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)/2;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)/2;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
            }
            else
            {
                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)/3;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)/3;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)/3;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)/3;
            }
            /*FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
            );
            Resources r = context.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, 0, px, px);
            holder.rel_main.setLayoutParams(params);
*/
        }
        if(photos.size()==1)
        {

            holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity);
            holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity);
            holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity);
            holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity);

            /*FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            holder.rel_main.setLayoutParams(params);*/
        }
        if(photos.size()==2)
        {

            holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)/2;
            holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
            holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)/2;
            holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;

           /* FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
            );
            Resources r = context.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, px, 0);
            holder.rel_main.setLayoutParams(params);*/
        }
        if(photos.size()==3)
        {

            if(i<1)
            {
                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
            }
            else
            {
                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)/2;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)/2;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
            }
            /*FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
            );
            Resources r = context.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, 0, px, px);
            holder.rel_main.setLayoutParams(params);*/
        }
        if(photos.size()==4)
        {

            holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)/2;
            holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
            holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)/2;
            holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)/2;
            /*FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
            );
            Resources r = context.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, 0, px, px);
            holder.rel_main.setLayoutParams(params);*/
        }
        if(photos.size()>5)
        {
            if(i==4)
            {
                holder.pframe.setVisibility(View.VISIBLE);
                holder.plus_count.setText("+"+(photos.size()-5));
                holder.pframe.getLayoutParams().width=getScreenWidthInPXs(context,activity)/3;
                holder.pframe.getLayoutParams().height=getScreenWidthInPXs(context,activity)/3;
            }
        }
        Log.d("adaaaapter", "" + photos.get(i).getPhoto());


        Glide
                .with(context)
                .asBitmap()
                .load(photos.get(i).getPhoto())
                .thumbnail(0.1f)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(new RequestOptions().override(150, 150).centerCrop())
                .into(holder.feed_photo);

        holder.feed_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(from.equalsIgnoreCase("newsfeed"))
                {






                   /* ViewPostFragment viewPostFragment= new ViewPostFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("newsFeedData", postid);

                    viewPostFragment.setArguments(bundle);


                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack("NewsFeedActivity");
                    fragmentTransaction.replace(R.id.frame, viewPostFragment, "TAG");
                    fragmentTransaction.commit();*/
                }

            }
        });

    }

    @Override
    public int getItemCount() {

            if (photos.size() > 5) {
                return 5;
            } else {
                return photos.size();
            }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView feed_photo;
        TextViewRobotoBold plus_count;
        RelativeLayout rel_main,pframe;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feed_photo=itemView.findViewById(R.id.feed_photo);
            rel_main=itemView.findViewById(R.id.rel_main);
            pframe=itemView.findViewById(R.id.pframe);
            plus_count=itemView.findViewById(R.id.plus_count);

        }
    }

    public static int getScreenWidthInPXs(Context context,Activity activity){

        DisplayMetrics dm = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int getScreenHeightInDPs(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);

        int heightInDP = Math.round(dm.heightPixels / dm.density);
        return heightInDP;
    }
}