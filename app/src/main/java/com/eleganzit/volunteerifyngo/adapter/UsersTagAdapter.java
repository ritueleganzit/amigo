package com.eleganzit.volunteerifyngo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.volunteerifyngo.R;

import java.util.ArrayList;
/*

public class UsersTagAdapter extends RecyclerView.Adapter<PassengerAdapter.MyViewHolder>
{
    ArrayList<UsersData> arrayList;
    Context context;
    boolean isSelectedAll;

    public PassengerAdapter(ArrayList<PassengerData> arrayList, Context context)
    {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.route_history_layout,parent,false);

        MyViewHolder myViewHolder=new PassengerAdapter.MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final PassengerData passengerData=arrayList.get(position);
        Glide
                .with(context)
                .load(passengerData.getPhoto()).apply(new RequestOptions().placeholder(R.drawable.pr))
                .into(holder.p_photo);

        holder.p_name.setText(passengerData.getFname()+" "+passengerData.getLname());

        select_allcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    selectAll(true);
                }
                else
                {
                    selectAll(false);
                }
            }
        });
        if (!isSelectedAll) holder.select_radioButton.setChecked(false);
        else holder.select_radioButton.setChecked(true);

        if(trip_status.equalsIgnoreCase("ongoing") || from.equalsIgnoreCase("update") || from.equalsIgnoreCase("home"))
        {
            holder.select_radioButton.setVisibility(View.GONE);
        }

        int total_passengers=Integer.parseInt(passengerData.getId());

        holder.select_radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    userslist.add(passengerData.getUser_id());
                    //Toast.makeText(context, "added "+passengerData.getFname()+" "+passengerData.getLname()+" passengers", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    userslist.remove(passengerData.getUser_id());
                    //Toast.makeText(context, "removed "+passengerData.getFname()+" "+passengerData.getLname()+" passengers", Toast.LENGTH_SHORT).show();
                }
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(PassengerListActivity.this)) {

                    //If the draw over permission is not available open the settings screen
                    //to grant the permission.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                } else {
                    if(trip_text.getText().toString().equalsIgnoreCase("start trip"))
                    {
                        if(userslist.size()>0)
                        {
                            addPassengers("active",holder);
                        }
                        else
                        {
                            Toast.makeText(PassengerListActivity.this, "Please select passengers", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


                if(trip_text.getText().toString().equalsIgnoreCase("end trip"))
                {
                    updateDeactiveStatus("deactive",null);
                    p_editor.putBoolean("firstTime",false);
                    p_editor.commit();
                }


            }
        });

    }

    public void selectAll(boolean status){
        Log.e("onClickSelectAll","yes");
        isSelectedAll=status;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout radio;
        CircleImageView p_photo;
        TextView p_name;
        CheckBox select_radioButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            radio=itemView.findViewById(R.id.radio);
            p_photo=itemView.findViewById(R.id.p_photo);
            p_name=itemView.findViewById(R.id.p_name);
            select_radioButton=itemView.findViewById(R.id.select_radioButton);

        }
    }
}*/
