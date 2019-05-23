package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.CandidatesLayoutBinding;
import com.eleganzit.amigo.model.AppliedList;
import com.eleganzit.amigo.model.RejectedList;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class RejectedCandidatesAdapter extends RecyclerView.Adapter<RejectedCandidatesAdapter.MyViewHolder>
{

    List<RejectedList> candidates;
    Context context;
    Activity activity;
    String type;
    ProgressDialog progressDialog;

    public RejectedCandidatesAdapter(List<RejectedList> candidates, Context context, String type) {
        this.candidates = candidates;
        this.context = context;
        this.type = type;
        activity = (Activity) context;
        progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CandidatesLayoutBinding candidatesLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.candidates_layout,viewGroup,false);



        return new MyViewHolder(candidatesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final RejectedList appliedList=candidates.get(i);
        holder.candidatesLayoutBinding.fullname.setText(""+appliedList.getFullname());
        Glide.with(context).load(appliedList.getPhoto()).into(holder.candidatesLayoutBinding.photo);
      // holder.candidatesLayoutBinding.txtFollowers.setText(""+appliedList.);



        if(type.equalsIgnoreCase("rejected"))
        {
           // Toast.makeText(context, ""+type, Toast.LENGTH_SHORT).show();
            holder.candidatesLayoutBinding.relAcceptBg.setVisibility(View.GONE);
            holder.candidatesLayoutBinding.txtReject.setText("Rejected");
            holder.candidatesLayoutBinding.txtReject.setEnabled(false);
        }
        else
        {
            //oast.makeText(context, ""+type, Toast.LENGTH_SHORT).show();

        }
    }

    private void updateApplyUserStatus(String applyId, final String status, final int i, final MyViewHolder holder) {
        progressDialog.show();

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
            retrofit2.Call<ResponseBody> call=myInterface.updateApplyUserStatus(applyId,status);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                StringBuilder stringBuilder=new StringBuilder();
                  try {
                       Log.d("Adappp",""+response.body().string());
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                if (status.equalsIgnoreCase("1"))
                {
                    holder.candidatesLayoutBinding.txtAccept.setText("Accepted");
                    holder.candidatesLayoutBinding.txtReject.setText("Reject");

                }
                if (status.equalsIgnoreCase("2"))
                {
                    holder.candidatesLayoutBinding.txtReject.setText("Rejected");
                    holder.candidatesLayoutBinding.txtAccept.setText("Accept");

                }

                  // removeAt(i);
               //1 notifyDataSetChanged();


            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.d("Adappp","eer"+t.getMessage());

            }
        });

    }


    private void removeAt(int i) {
        if (candidates.size()==1)
        {
            candidates.remove(i);
            notifyDataSetChanged();
        }
        else
        {
            candidates.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i,candidates.size());
        }
    }
    @Override
    public int getItemCount() {
        return candidates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CandidatesLayoutBinding candidatesLayoutBinding;

        public MyViewHolder(@NonNull CandidatesLayoutBinding candidatesLayoutBinding) {
            super(candidatesLayoutBinding.getRoot());
            this.candidatesLayoutBinding=candidatesLayoutBinding  ;

        }
    }
}
