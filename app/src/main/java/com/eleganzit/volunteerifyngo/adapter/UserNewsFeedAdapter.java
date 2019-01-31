package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.NewsFeedData;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class UserNewsFeedAdapter extends RecyclerView.Adapter<UserNewsFeedAdapter.MyViewHolder>
{

    ArrayList<NewsFeedData> posts;
    ArrayList<String> photos=new ArrayList<>();
    Context context;
    Activity activity;

    public UserNewsFeedAdapter(ArrayList<NewsFeedData> posts, Context context) {
        this.posts = posts;
        this.context = context;
        activity = (Activity) context;
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

        NewsFeedData newsFeedData=posts.get(i);
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
        holder.flexboxLayout.setAdapter(new PostPhotosAdapter(newsFeedData.getArrayList(),context));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView menu,f,s,t;
        RecyclerView flexboxLayout;
        LinearLayout lin_main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            menu=itemView.findViewById(R.id.menu);
            flexboxLayout = itemView.findViewById(R.id.flexbox_layout);
            lin_main = itemView.findViewById(R.id.lin_main);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            flexboxLayout.setLayoutManager(layoutManager);

        }
    }
}
