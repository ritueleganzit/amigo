package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.PhotosData;
import com.eleganzit.volunteerifyngo.model.PlacesData;

import java.util.ArrayList;

import static android.content.Context.WINDOW_SERVICE;

public class SearchPhotosAdapter extends RecyclerView.Adapter<SearchPhotosAdapter.MyViewHolder>
{

    ArrayList<PhotosData> photos;
    Context context;
    Activity activity;
    private int height;

    public SearchPhotosAdapter(ArrayList<PhotosData> photos, Context context) {
        this.photos = photos;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_photos_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        PhotosData photosData=photos.get(i);
        Glide
                .with(context)
                .load(photosData.getPhoto())
                .apply(new RequestOptions().centerCrop()).into(holder.search_photo);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView search_photo;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            search_photo=itemView.findViewById(R.id.search_photo);

        }
    }

}