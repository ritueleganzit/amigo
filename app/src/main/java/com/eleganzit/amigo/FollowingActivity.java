package com.eleganzit.amigo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.amigo.adapter.FollowersAdapter;
import com.eleganzit.amigo.model.FollowersData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FollowingActivity extends AppCompatActivity {

    RecyclerView rc_following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_following=findViewById(R.id.rc_following);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_following.setLayoutManager(layoutManager);

        FollowersData followersData =new FollowersData("","","","true");

        ArrayList<FollowersData> ar_following=new ArrayList<>();

        if(ar_following.size()>0)
        {
            ar_following.clear();
        }

        ar_following.add(followersData);
        ar_following.add(followersData);
        ar_following.add(followersData);
        ar_following.add(followersData);
        ar_following.add(followersData);
        ar_following.add(followersData);
        ar_following.add(followersData);
        ar_following.add(followersData);
        ar_following.add(followersData);
        ar_following.add(followersData);

        rc_following.setAdapter(new FollowersAdapter(ar_following,this));
    }
}
