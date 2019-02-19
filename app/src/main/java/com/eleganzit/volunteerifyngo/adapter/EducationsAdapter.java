package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eleganzit.volunteerifyngo.EditEducationActivity;
import com.eleganzit.volunteerifyngo.EditWorkActivity;
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.EducationsData;
import com.eleganzit.volunteerifyngo.model.WorksData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EducationsAdapter extends RecyclerView.Adapter<EducationsAdapter.MyViewHolder>
{

    ArrayList<EducationsData> educations;
    Context context;
    Activity activity;
    boolean liked=false;

    public EducationsAdapter(ArrayList<EducationsData> educations, Context context) {
        this.educations = educations;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.educations_row_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.edit_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EditEducationActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return educations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView edit_education;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            edit_education=itemView.findViewById(R.id.edit_education);

        }
    }
}
