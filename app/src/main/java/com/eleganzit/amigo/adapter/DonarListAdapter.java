package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.donation.DonarList;
import com.eleganzit.amigo.model.donation.Donations;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.eleganzit.amigo.utils.TextViewRobotoRegular;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DonarListAdapter extends RecyclerView.Adapter<DonarListAdapter.MyViewHolder>{

    List<DonarList> donations;
    Context context;
    Activity activity;

    public DonarListAdapter(List<DonarList> donations, Context context, Activity activity) {
        this.donations = donations;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_donar_list,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DonarList donarList=donations.get(position);
        Glide.with(context).load(donarList.getPhoto()).into(holder.photo);
        holder.fullname.setText(donarList.getFullname());
        holder.description.setText(donarList.getDescription());
        holder.send_amount.setText("$ "+donarList.getSendAmount());



    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
ImageView photo;
TextViewRobotoRegular fullname,description;
TextViewRobotoBold send_amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.photo);
            fullname=itemView.findViewById(R.id.fullname);
            description=itemView.findViewById(R.id.description);
            send_amount=itemView.findViewById(R.id.send_amount);



        }
    }
}
