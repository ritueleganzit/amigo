package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eleganzit.amigo.EditEducationActivity;
import com.eleganzit.amigo.OpportunityActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.databinding.EducationsRowLayoutBinding;
import com.eleganzit.amigo.databinding.OpportunityRowBinding;
import com.eleganzit.amigo.model.DonationsData;
import com.eleganzit.amigo.model.Educationdata;
import com.eleganzit.amigo.model.OpportunitiesListData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OpportuniesAdapter extends RecyclerView.Adapter<OpportuniesAdapter.MyViewHolder> {

    List<OpportunitiesListData> opportunitiesListData;
    Context context;
    Activity activity;

    public OpportuniesAdapter(List<OpportunitiesListData> opportunitiesListData, Context context) {
        this.opportunitiesListData = opportunitiesListData;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        OpportunityRowBinding worksRowLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.opportunity_row,viewGroup,false);



        return new MyViewHolder(worksRowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final OpportunitiesListData educationdata=opportunitiesListData.get(i);

        holder.opportunityRowBinding.address.setText(""+educationdata.getAddress());
        holder.opportunityRowBinding.name.setText(""+educationdata.getLookingFor());

        String input_date=""+educationdata.getOpportunityDate();
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
        Date dt1= null;
        try {
            dt1 = format1.parse(input_date);
            DateFormat format2=new SimpleDateFormat("MMM");
            DateFormat format=new SimpleDateFormat("dd");
            String month=format2.format(dt1);
            String day=format.format(dt1);

            Log.d("aadapter",""+month);
            Log.d("aadapter",""+day+" "+educationdata.getOpportunityDate());
            holder.opportunityRowBinding.month.setText(""+month);
            holder.opportunityRowBinding.day.setText(""+day);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.opportunityRowBinding.eventsTime.setText(""+educationdata.getOpportunityTime());
holder.opportunityRowBinding.donationMain.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        context.startActivity(new Intent(context, OpportunityActivity.class)
        .putExtra("opportunity_id",""+educationdata.getOpportunityId()));
        activity.finish();

    }
});


    }

    @Override
    public int getItemCount() {
        return opportunitiesListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        OpportunityRowBinding opportunityRowBinding;

        public MyViewHolder(@NonNull OpportunityRowBinding opportunityRowBinding) {
            super(opportunityRowBinding.getRoot());
            this.opportunityRowBinding=opportunityRowBinding  ;

        }
    }

}
