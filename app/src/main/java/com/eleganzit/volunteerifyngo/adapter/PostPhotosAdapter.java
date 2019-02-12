package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.NewsFeedData;
import com.eleganzit.volunteerifyngo.utils.TextViewRobotoBold;
import com.google.android.flexbox.FlexDirection;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import static android.content.Context.WINDOW_SERVICE;

public class PostPhotosAdapter extends RecyclerView.Adapter<PostPhotosAdapter.MyViewHolder>
{

    ArrayList<String> photos;
    Context context;
    Activity activity;

    public PostPhotosAdapter(ArrayList<String> photos, Context context) {
        this.photos = photos;
        this.context = context;
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
                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
            }
            else
            {
                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/304;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/304;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/304;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/304;
            }
            FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
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

        }
        if(photos.size()==1)
        {

                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity);
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity);

            FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            holder.rel_main.setLayoutParams(params);
        }
        if(photos.size()==2)
        {

            holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
            holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
            holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
            holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;

            FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
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
            holder.rel_main.setLayoutParams(params);
        }
        if(photos.size()==3)
        {

            if(i<1)
            {
                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
            }
            else
            {
                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
            }
            FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
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
        }
        if(photos.size()==4)
        {

                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
            FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
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
        }
        if(photos.size()>5)
        {
            if(i==4)
            {
                holder.pframe.setVisibility(View.VISIBLE);
                holder.plus_count.setText("+"+(photos.size()-5));
                holder.pframe.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/304;
                holder.pframe.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/304;
            }
        }
        Glide
                .with(context)
                .load(photos.get(i))
                .apply(new RequestOptions().centerCrop()).into(holder.feed_photo);

    }

    @Override
    public int getItemCount() {
        if(photos.size()>5)
        {
            return 5;
        }
        else
        {
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