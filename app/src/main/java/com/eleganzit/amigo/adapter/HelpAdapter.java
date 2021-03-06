package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.UserProfileActivity;
import com.eleganzit.amigo.fragments.ViewPostFragment;
import com.eleganzit.amigo.model.NewsFeedData;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder>
{

    ArrayList<NewsFeedData> posts;
    ArrayList<String> photos=new ArrayList<>();
    Context context;
    Activity activity;

    public HelpAdapter(ArrayList<NewsFeedData> posts, Context context) {
        this.posts = posts;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_feed_layout,viewGroup,false);
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

        holder.profile_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, UserProfileActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        holder.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, UserProfileActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

//        for(int j=0;i<newsFeedData.getArrayList().size();j++)
//        {
//            photos.add(newsFeedData.getArrayList().get(j));
//        }
        /*holder.flexboxLayout.setAdapter(new PostPhotosAdapter(newsFeedData.getArrayList(),context,"help"));

        holder.post_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic,menu,f,s,t;
        RecyclerView flexboxLayout;
        LinearLayout lin_main,profile_click,post_main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            menu=itemView.findViewById(R.id.menu);
            profilePic=itemView.findViewById(R.id.profilePic);
            flexboxLayout = itemView.findViewById(R.id.flexbox_layout);
            lin_main = itemView.findViewById(R.id.lin_main);
            profile_click = itemView.findViewById(R.id.profile_click);
            post_main = itemView.findViewById(R.id.post_main);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            flexboxLayout.setLayoutManager(layoutManager);

        }
    }
}
