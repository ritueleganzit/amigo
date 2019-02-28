package com.eleganzit.volunteerifyngo;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import com.eleganzit.volunteerifyngo.adapter.AddedGroupUsersAdapter;
import com.eleganzit.volunteerifyngo.adapter.NewGroupUsersAdapter;
import com.eleganzit.volunteerifyngo.model.GUsersdata;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewGroupActivity extends AppCompatActivity {

    RecyclerView rc_gusers;
    ArrayList<GUsersdata> ar_users=new ArrayList<>();
    FloatingActionButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        next=findViewById(R.id.next);
        rc_gusers=findViewById(R.id.rc_gusers);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rc_gusers.setLayoutManager(layoutManager);

        GUsersdata gUsersdata=new GUsersdata("","","","","","false");

        ar_users.add(gUsersdata);
        ar_users.add(gUsersdata);
        ar_users.add(gUsersdata);
        ar_users.add(gUsersdata);

        rc_gusers.setAdapter(new AddedGroupUsersAdapter(ar_users,this));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewGroupActivity.this,ChatActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
