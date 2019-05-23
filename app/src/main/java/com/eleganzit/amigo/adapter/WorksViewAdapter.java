package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.amigo.EditWorkActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.databinding.WorksRowLayoutBinding;
import com.eleganzit.amigo.databinding.WorksviewRowLayoutBinding;
import com.eleganzit.amigo.model.WorkData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class WorksViewAdapter extends RecyclerView.Adapter<WorksViewAdapter.MyViewHolder>
{

    List<WorkData> works;
    Context context;
    Activity activity;
    boolean liked=false;

    public WorksViewAdapter(List<WorkData> works, Context context) {
            this.works = works;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        WorksviewRowLayoutBinding worksRowLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.worksview_row_layout,viewGroup,false);



        return new MyViewHolder(worksRowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final WorkData getWorkList=works.get(i);
        Log.d("mjjj",""+getWorkList.getPlace());
        holder.worksRowLayoutBinding.designation.setText(getWorkList.getPosition()+" at ");
        holder.worksRowLayoutBinding.companyname.setText(getWorkList.getPlaceName());





    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        WorksviewRowLayoutBinding worksRowLayoutBinding;


        public MyViewHolder(@NonNull WorksviewRowLayoutBinding worksRowLayoutBinding) {
            super(worksRowLayoutBinding.getRoot());
            this.worksRowLayoutBinding=worksRowLayoutBinding  ;
        }
    }
}
