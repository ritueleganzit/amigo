package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.model.PostImages;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPostImagesAdapter extends RecyclerView.Adapter<ViewPostImagesAdapter.MyViewHolder>
{

    ArrayList<PhotosData> images;
    Context context;
    Activity activity;
    private int height;

    public ViewPostImagesAdapter(ArrayList<PhotosData> images, Context context) {
        this.images = images;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_image_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        PhotosData postImages=images.get(i);
        Glide
                .with(context)
                .load(postImages.getPhoto())
                .apply(new RequestOptions().centerCrop()).into(holder.post_image);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView post_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            post_image=itemView.findViewById(R.id.post_image);

        }
    }

}