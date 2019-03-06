package com.eleganzit.amigo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.amigo.adapter.CandidatesAdapter;
import com.eleganzit.amigo.adapter.VolunteersAdapter;
import com.eleganzit.amigo.model.FollowersData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AppliedCandidatesActivity extends AppCompatActivity {

    RecyclerView rc_candidates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_candidates);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_candidates=findViewById(R.id.rc_candidates);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_candidates.setLayoutManager(layoutManager);
        ArrayList<FollowersData> ar_candidates=new ArrayList<>();

        FollowersData followersData =new FollowersData("","","","");
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);
        ar_candidates.add(followersData);

        rc_candidates.setAdapter(new CandidatesAdapter(ar_candidates,AppliedCandidatesActivity.this,"applied"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}