package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eleganzit.amigo.EditEducationActivity;
import com.eleganzit.amigo.EditWorkActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.databinding.EducationsRowLayoutBinding;
import com.eleganzit.amigo.databinding.WorksRowLayoutBinding;
import com.eleganzit.amigo.model.Educationdata;
import com.eleganzit.amigo.model.EducationsData;
import com.eleganzit.amigo.model.WorksData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class EducationsAdapter extends RecyclerView.Adapter<EducationsAdapter.MyViewHolder>
{

    List<Educationdata> educations;
    Context context;
    Activity activity;
    boolean liked=false;

    public EducationsAdapter(List<Educationdata> educations, Context context) {
        this.educations = educations;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        EducationsRowLayoutBinding worksRowLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.educations_row_layout,viewGroup,false);



        return new MyViewHolder(worksRowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        final Educationdata educationdata=educations.get(i);

        holder.educationsRowLayoutBinding.university.setText(""+educationdata.getPlace());
        holder.educationsRowLayoutBinding.editEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EditEducationActivity.class).putExtra("edu",educationdata));
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return educations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        EducationsRowLayoutBinding educationsRowLayoutBinding;

        public MyViewHolder(@NonNull EducationsRowLayoutBinding educationsRowLayoutBinding) {
            super(educationsRowLayoutBinding.getRoot());
            this.educationsRowLayoutBinding=educationsRowLayoutBinding  ;

        }
    }
}
