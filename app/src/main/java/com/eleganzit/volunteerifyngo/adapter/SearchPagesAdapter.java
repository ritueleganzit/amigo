package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.PagesData;

import java.util.ArrayList;

public class SearchPagesAdapter extends RecyclerView.Adapter<SearchPagesAdapter.MyViewHolder>
{

    ArrayList<PagesData> pages;
    Context context;
    Activity activity;
    boolean liked=false;

    public SearchPagesAdapter(ArrayList<PagesData> pages, Context context) {
        this.pages = pages;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_pages_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(liked)
                {
                    liked=false;
                    holder.like.setImageResource(R.mipmap.like_gray);
                }
                else
                {
                    liked=true;
                    holder.like.setImageResource(R.mipmap.like_green);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView like;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            like=itemView.findViewById(R.id.like);


        }
    }
}
