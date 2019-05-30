package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eleganzit.amigo.DonationDetailActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.ViewEventActivity;
import com.eleganzit.amigo.model.DonationsData;
import com.eleganzit.amigo.model.EventsData;
import com.eleganzit.amigo.model.donation.Donations;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.eleganzit.amigo.utils.TextViewRobotoRegular;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.MyViewHolder>
{

  List<Donations> donations;
    Context context;
    Activity activity;

    public DonationsAdapter(List<Donations> donations, Context context) {
        this.donations = donations;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.donations_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {


        final Donations donations1=donations.get(i);
        holder.name.setText(donations1.getTitle());
        holder.txtamount.setText("$ "+donations1.getNeedAmount());
holder.end_date.setText("End Date: "+donations1.getDonationEndDate());
        Log.d("adapterdonation","$ "+donations1.getNeedAmount());
        Log.d("adapterdonation",""+donations1.getTitle());

        holder.donation_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, DonationDetailActivity.class)
                        .putExtra("donation_id",""+donations1.getDonationId()));
                activity.finish();
              //  Toast.makeText(context, ""+donations1.getDonationId(), Toast.LENGTH_SHORT).show();
            }
        });

        String input_date=""+donations1.getDonationStartDate();
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
        Date dt1= null;
        try {
            dt1 = format1.parse(input_date);
            DateFormat format2=new SimpleDateFormat("MMM");
            DateFormat format=new SimpleDateFormat("dd");
            String month=format2.format(dt1);
            String day=format.format(dt1);

            Log.d("aadapter",""+month);
            Log.d("aadapter",""+day+" "+donations1.getDonationStartDate());
            holder.month.setText(""+month);
            holder.day.setText(""+day);


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextViewRobotoBold name,txtamount,day,month;
        TextViewRobotoRegular end_date;
        RelativeLayout donation_main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            donation_main=itemView.findViewById(R.id.donation_main);
            txtamount=itemView.findViewById(R.id.txtamount);
            end_date=itemView.findViewById(R.id.end_date);
            name=itemView.findViewById(R.id.name);
            month=itemView.findViewById(R.id.month);
            day=itemView.findViewById(R.id.day);

        }
    }
}