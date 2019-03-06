package com.eleganzit.amigo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.amigo.adapter.VolunteersAdapter;
import com.eleganzit.amigo.model.FollowersData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AttendingVolunteersActivity extends AppCompatActivity {

    RecyclerView rc_volunteers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attending_volunteers);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_volunteers=findViewById(R.id.rc_volunteers);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_volunteers.setLayoutManager(layoutManager);
        ArrayList<FollowersData> ar_volunteers=new ArrayList<>();

        FollowersData followersData =new FollowersData("","","","");
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);
        ar_volunteers.add(followersData);

        rc_volunteers.setAdapter(new VolunteersAdapter(ar_volunteers,AttendingVolunteersActivity.this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}