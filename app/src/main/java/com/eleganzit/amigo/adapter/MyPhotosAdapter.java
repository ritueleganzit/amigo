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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyPhotosAdapter extends RecyclerView.Adapter<MyPhotosAdapter.MyViewHolder>
{

    ArrayList<PhotosData> photos;
    Context context;
    Activity activity;
    private int height;

    public MyPhotosAdapter(ArrayList<PhotosData> photos, Context context) {
        this.photos = photos;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_photos_layout,viewGroup,false);
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
        if(photos.size()>6)
        {
            return 6;
        }
        else
        {
            return photos.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView search_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            search_photo=itemView.findViewById(R.id.search_photo);

        }
    }

}