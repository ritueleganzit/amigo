package com.eleganzit.amigo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.eleganzit.amigo.adapter.DonarListAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.donation.DonarList;
import com.eleganzit.amigo.model.donation.DonarListResponse;
import com.eleganzit.amigo.model.donation.Donations;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDonarsListActivity extends AppCompatActivity {

    ProgressBar progressbar;
    String user_id="",donation_id="";
    UserLoggedInSession userLoggedInSession;
    List<DonarList> arrayList;
    RecyclerView rc_donar_list;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donars_list);
        progressbar=findViewById(R.id.progressbar);
        rc_donar_list=findViewById(R.id.rc_donar_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_donar_list.setLayoutManager(layoutManager);
        ImageView back=findViewById(R.id.back);
        userLoggedInSession = new UserLoggedInSession(ViewDonarsListActivity.this);
        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        donation_id=getIntent().getStringExtra("donation_id");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDonorlist(0);
    }
    public void getDonorlist(int i)
    {
        arrayList=new ArrayList<>();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<DonarListResponse> donarListResponseCall=myInterface.getDonorlist(donation_id);
        donarListResponseCall.enqueue(new Callback<DonarListResponse>() {
            @Override
            public void onResponse(Call<DonarListResponse> call, Response<DonarListResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getData()!=null)
                    {
                        rc_donar_list.setAdapter(new DonarListAdapter(response.body().getData(),getApplicationContext(),ViewDonarsListActivity.this));

                    }
                }

            }

            @Override
            public void onFailure(Call<DonarListResponse> call, Throwable t) {

            }
        });
    }
}
