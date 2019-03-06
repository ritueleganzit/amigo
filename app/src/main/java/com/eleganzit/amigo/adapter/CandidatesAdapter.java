package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.FollowersData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CandidatesAdapter extends RecyclerView.Adapter<CandidatesAdapter.MyViewHolder>
{

    ArrayList<FollowersData> candidates;
    Context context;
    Activity activity;
    String type;

    public CandidatesAdapter(ArrayList<FollowersData> candidates, Context context,String type) {
        this.candidates = candidates;
        this.context = context;
        this.type = type;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.candidates_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        if(type.equalsIgnoreCase("rejected"))
        {
            holder.rel_accept_bg.setVisibility(View.GONE);
            holder.txt_reject.setText("Rejected");
        }

    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel_accept_bg,rel_reject_bg;
        TextView txt_accept,txt_reject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rel_accept_bg=itemView.findViewById(R.id.rel_accept_bg);
            rel_reject_bg=itemView.findViewById(R.id.rel_reject_bg);
            txt_accept=itemView.findViewById(R.id.txt_accept);
            txt_reject=itemView.findViewById(R.id.txt_reject);

        }
    }
}
