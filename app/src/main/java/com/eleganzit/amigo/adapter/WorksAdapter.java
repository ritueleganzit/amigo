package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eleganzit.amigo.EditWorkActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.databinding.AccountsLayoutBinding;
import com.eleganzit.amigo.databinding.WorksRowLayoutBinding;
import com.eleganzit.amigo.model.GetWorkList;
import com.eleganzit.amigo.model.PagesData;
import com.eleganzit.amigo.model.WorkData;
import com.eleganzit.amigo.model.WorksData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.MyViewHolder>
{

    List<WorkData> works;
    Context context;
    Activity activity;
    boolean liked=false;

    public WorksAdapter(List<WorkData> works, Context context) {
            this.works = works;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        WorksRowLayoutBinding worksRowLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.works_row_layout,viewGroup,false);



        return new MyViewHolder(worksRowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final WorkData getWorkList=works.get(i);
        Log.d("mjjj",""+getWorkList.getDateTo());
        Log.d("mjjj",""+getWorkList.getWorkHere());
        holder.worksRowLayoutBinding.designation.setText(getWorkList.getPosition()+" at ");
        holder.worksRowLayoutBinding.companyname.setText(getWorkList.getPlaceName());



        holder.worksRowLayoutBinding.editWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EditWorkActivity.class).putExtra("work",getWorkList));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        WorksRowLayoutBinding worksRowLayoutBinding;


        public MyViewHolder(@NonNull WorksRowLayoutBinding worksRowLayoutBinding) {
            super(worksRowLayoutBinding.getRoot());
            this.worksRowLayoutBinding=worksRowLayoutBinding  ;
        }
    }
}
