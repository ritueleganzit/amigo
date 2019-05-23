package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.amigo.EditEducationActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.databinding.EducationsRowLayoutBinding;
import com.eleganzit.amigo.databinding.EducationsviewRowLayoutBinding;
import com.eleganzit.amigo.model.Educationdata;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class EducationsViewAdapter extends RecyclerView.Adapter<EducationsViewAdapter.MyViewHolder>
{

    List<Educationdata> educations;
    Context context;
    Activity activity;
    boolean liked=false;

    public EducationsViewAdapter(List<Educationdata> educations, Context context) {
        this.educations = educations;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        EducationsviewRowLayoutBinding worksRowLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.educationsview_row_layout,viewGroup,false);



        return new MyViewHolder(worksRowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        Educationdata educationdata=educations.get(i);

        holder.educationsRowLayoutBinding.university.setText(""+educationdata.getPlace());
      holder.educationsRowLayoutBinding.designation.setText(""+educationdata.getDescription());

    }

    @Override
    public int getItemCount() {
        return educations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        EducationsviewRowLayoutBinding educationsRowLayoutBinding;

        public MyViewHolder(@NonNull EducationsviewRowLayoutBinding educationsRowLayoutBinding) {
            super(educationsRowLayoutBinding.getRoot());
            this.educationsRowLayoutBinding=educationsRowLayoutBinding  ;

        }
    }
}
