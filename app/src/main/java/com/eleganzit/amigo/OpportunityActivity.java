package com.eleganzit.amigo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityOpportunityBinding;
import com.eleganzit.amigo.model.GetOpportunitiesResponse;
import com.eleganzit.amigo.model.GetSingleOpportunityResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpportunityActivity extends AppCompatActivity {



    String opportunity_id="",user_id="";
    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;


    ActivityOpportunityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(OpportunityActivity.this,R.layout.activity_opportunity);
progressDialog=new ProgressDialog(OpportunityActivity.this);
progressDialog.setCanceledOnTouchOutside(false);
progressDialog.setCancelable(false);
progressDialog.setMessage("Please Wait");

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userLoggedInSession=new UserLoggedInSession(OpportunityActivity.this);
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);
        opportunity_id=getIntent().getStringExtra("opportunity_id");


        binding.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(OpportunityActivity.this, binding.options);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.opportunity_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
if (item.getItemId()==R.id.delete)
{
    deleteOpportunity();
}

if (item.getItemId()==R.id.edit)
{
    startActivity(new Intent(OpportunityActivity.this,EditOpportunity.class)
    .putExtra("opportunity_id",opportunity_id));
   // finish();
}


                        //Toast.makeText(ChatActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        binding.linApplied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpportunityActivity.this,AppliedCandidatesActivity.class).putExtra("opportunity_id",opportunity_id));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        binding.linRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpportunityActivity.this,RejectedCandidatesActivity.class).putExtra("opportunity_id",opportunity_id));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSingleData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void getSingleData()
    {
        progressDialog.show();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<GetSingleOpportunityResponse> getOpportunitiesResponseCall=myInterface.getsingleOpportunity(opportunity_id);
getOpportunitiesResponseCall.enqueue(new Callback<GetSingleOpportunityResponse>() {
    @Override
    public void onResponse(Call<GetSingleOpportunityResponse> call, Response<GetSingleOpportunityResponse> response) {
        progressDialog.dismiss();
        if (response.isSuccessful())
        {
            if (response.body()!=null)
            {
                if (response.body().getData()!=null)
                {
                    binding.applycount.setText(response.body().getData().get(0).getApplycount());
                    binding.rejectcount.setText(response.body().getData().get(0).getRejectcount());
                    binding.address.setText(response.body().getData().get(0).getAddress());
                    binding.opportunityTime.setText(response.body().getData().get(0).getOpportunityTime());
                    binding.positions.setText("Number of Positions: "+response.body().getData().get(0).getPositions());
                    binding.lookingFor.setText("Looking for: "+response.body().getData().get(0).getLookingFor());
                    binding.details.setText(""+response.body().getData().get(0).getDetails());



                    Glide.with(getApplicationContext()).load(""+response.body().getData().get(0).getOpportunityImage()).into(binding.opportunityImage);
                    Log.d("dsccs",""+response.body().getData().get(0).getOpportunity_name()+" "+response.body().getData().get(0).getOpportunityImage());
                    if (response.body().getData().get(0).getOpportunity_name()!=null && !(response.body().getData().get(0).getOpportunity_name().isEmpty()))

                    {
                        binding.name.setText(""+response.body().getData().get(0).getOpportunity_name());
                    }

                    String input_date=""+response.body().getData().get(0).getOpportunityDate();
                    SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                    Date dt1= null;
                    try {
                        dt1 = format1.parse(input_date);
                        DateFormat format2=new SimpleDateFormat("MMM");
                        DateFormat format=new SimpleDateFormat("dd");
                        String month=format2.format(dt1);
                        String day=format.format(dt1);

                        Log.d("aadapter",""+month);
                        Log.d("aadapter",""+day+" "+response.body().getData().get(0).getApplycount());
                        binding.month.setText(""+month);
                        binding.day.setText(""+day);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call<GetSingleOpportunityResponse> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(OpportunityActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
    }
});


    }

    public void deleteOpportunity()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<GetSingleOpportunityResponse> getOpportunitiesResponseCall=myInterface.deleteOpportunity(opportunity_id);
        getOpportunitiesResponseCall.enqueue(new Callback<GetSingleOpportunityResponse>() {
            @Override
            public void onResponse(Call<GetSingleOpportunityResponse> call, Response<GetSingleOpportunityResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        Toast.makeText(OpportunityActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OpportunityActivity.this,OpportunitiesActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();

                    }
                }
            }

            @Override
            public void onFailure(Call<GetSingleOpportunityResponse> call, Throwable t) {

            }
        });

    }

}
