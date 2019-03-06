package com.eleganzit.amigo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.amigo.adapter.CampaignsAdapter;
import com.eleganzit.amigo.adapter.EventsAdapter;
import com.eleganzit.amigo.model.CampaignsData;
import com.eleganzit.amigo.model.EventsData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CampaignsActivity extends AppCompatActivity {

    RecyclerView rc_campaigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaigns);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_campaigns=findViewById(R.id.rc_campaigns);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_campaigns.setLayoutManager(layoutManager);

        CampaignsData campaignsData=new CampaignsData("","","","","","");

        ArrayList<CampaignsData> arrayList=new ArrayList<>();

        arrayList.add(campaignsData);
        arrayList.add(campaignsData);
        arrayList.add(campaignsData);
        arrayList.add(campaignsData);
        arrayList.add(campaignsData);

        rc_campaigns.setAdapter(new CampaignsAdapter(arrayList,this));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}
