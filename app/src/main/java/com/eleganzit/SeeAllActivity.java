package com.eleganzit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.searchDataClasses.NGOData;
import com.eleganzit.amigo.model.searchDataClasses.SearchAllDataResponse;
import com.eleganzit.amigo.model.searchDataClasses.SearchNGOsAdapter;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllActivity extends AppCompatActivity {
    RecyclerView rc_see_all;
    TextView txt_title;
    String search,type;
    UserLoggedInSession userLoggedInSession;
    HashMap<String, String> userData;
    ArrayList<NGOData> ar_ngoData =new ArrayList<>();
    LinearLayoutManager layoutManager;
    private String to_limit;
    SearchNGOsAdapter searchNGOsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);
        userLoggedInSession=new UserLoggedInSession(this);
        userData=userLoggedInSession.getUserDetails();
        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search=getIntent().getStringExtra("search");
        type=getIntent().getStringExtra("type");
        if(type.equalsIgnoreCase("ngo"))
        {
            to_limit="20";
        }
        rc_see_all=findViewById(R.id.rc_see_all);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rc_see_all.setLayoutManager(layoutManager);
        searchAllData1();
    }

    private void searchAllData1() {
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SearchAllDataResponse> call=myInterface.searchData("ngo",userData.get(UserLoggedInSession.USER_ID),search,"1",to_limit);
        call.enqueue(new Callback<SearchAllDataResponse>() {
            @Override
            public void onResponse(Call<SearchAllDataResponse> call, Response<SearchAllDataResponse> response) {
                if(response.isSuccessful()) {
                    if(type.equalsIgnoreCase("ngo"))
                    {
                        if(response.body().getDataNGO()!=null)
                        {
                            if(response.body().getDataNGO().getStatus().toString().equalsIgnoreCase("1"))
                            {
                                for (int i = 0; i < response.body().getDataNGO().getData().size(); i++) {

                                    NGOData ngoData;
                                    if(response.body().getDataNGO().getData().get(i).getFollowdata().getFail_status().equalsIgnoreCase("0"))
                                    {
                                        ngoData = new NGOData(response.body().getDataNGO().getData().get(i).getUserId(),response.body().getDataNGO().getData().get(i).getFollowdata().getRequestUserId(),response.body().getDataNGO().getData().get(i).getFollowdata().getRequest_id(), response.body().getDataNGO().getData().get(i).getFullname(), response.body().getDataNGO().getData().get(i).getPhoto(), response.body().getDataNGO().getData().get(i).getCountFollow(), response.body().getDataNGO().getData().get(i).getIs_follow(),response.body().getDataNGO().getData().get(i).getFollowdata().getStatus(),"0");
                                    }
                                    else
                                    {
                                        ngoData = new NGOData(response.body().getDataNGO().getData().get(i).getUserId(),"","", response.body().getDataNGO().getData().get(i).getFullname(), response.body().getDataNGO().getData().get(i).getPhoto(), response.body().getDataNGO().getData().get(i).getCountFollow(), response.body().getDataNGO().getData().get(i).getIs_follow(),"","1");
                                    }

                                    ar_ngoData.add(ngoData);

                                }
                                Log.d("yutwreywer",ar_ngoData.size()+"");

                                searchNGOsAdapter=new SearchNGOsAdapter(ar_ngoData, SeeAllActivity.this);

                                rc_see_all.setAdapter(searchNGOsAdapter);

                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<SearchAllDataResponse> call, Throwable t) {
                Toast.makeText(SeeAllActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
