package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.LoginSelectedActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.databinding.AccountsLayoutBinding;
import com.eleganzit.amigo.model.Accounts;
import com.google.gson.JsonObject;
import com.hzn.lib.EasyTransition;
import com.hzn.lib.EasyTransitionOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder>
{

    ArrayList<Accounts> accounts;
    Context context;
    Activity activity;

    public AccountsAdapter(ArrayList<Accounts> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AccountsLayoutBinding accountsLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.accounts_layout,viewGroup,false);



        return new MyViewHolder(accountsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final Accounts account=accounts.get(i);

        Log.d("hhhhh",account.getId()+"   "+account.getObject());

        try {
            JSONObject jsonObject = new JSONObject("" + account.getObject());
            holder.accountsLayoutBinding.username.setText(jsonObject.getString("fullname"));
            Glide.with(context).load(jsonObject.getString("photo")).into(holder.accountsLayoutBinding.profilePhoto);

        } catch (JSONException e) {
            e.printStackTrace();
        }




        holder.accountsLayoutBinding.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, ""+account.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LoginSelectedActivity.class);
                intent.putExtra("userdata", account);

                // ready for transition options
                EasyTransitionOptions options =
                        EasyTransitionOptions.makeTransitionOptions(
                                activity,
                                holder.accountsLayoutBinding.userdata);

                // start transition
                EasyTransition.startActivity(intent, options);
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AccountsLayoutBinding accountsLayoutBinding;


        public MyViewHolder(@NonNull AccountsLayoutBinding accountsLayoutBinding) {
            super(accountsLayoutBinding.getRoot());
            this.accountsLayoutBinding=accountsLayoutBinding  ;
        }
    }
}
