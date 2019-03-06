package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eleganzit.amigo.EditWorkActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.PagesData;
import com.eleganzit.amigo.model.WorksData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.MyViewHolder>
{

    ArrayList<WorksData> works;
    Context context;
    Activity activity;
    boolean liked=false;

    public WorksAdapter(ArrayList<WorksData> works, Context context) {
            this.works = works;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.works_row_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.edit_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EditWorkActivity.class));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView edit_work;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            edit_work=itemView.findViewById(R.id.edit_work);

        }
    }
}
