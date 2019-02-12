package com.eleganzit.volunteerifyngo;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import com.eleganzit.volunteerifyngo.adapter.GroupAddedUsersAdapter;
import com.eleganzit.volunteerifyngo.adapter.NewGroupUsersAdapter;
import com.eleganzit.volunteerifyngo.model.GUsersdata;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddNewGroupActivity extends AppCompatActivity {

    RecyclerView rc_gusers,rc_added_users;
    ArrayList<GUsersdata> ar_users=new ArrayList<>();
    FloatingActionButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        next=findViewById(R.id.next);
        rc_gusers=findViewById(R.id.rc_gusers);
        rc_added_users=findViewById(R.id.rc_added_gusers);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_gusers.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rc_added_users.setLayoutManager(layoutManager2);

        GUsersdata gUsersdata=new GUsersdata("","","","");

        ar_users.add(gUsersdata);
        ar_users.add(gUsersdata);
        ar_users.add(gUsersdata);
        ar_users.add(gUsersdata);

        rc_gusers.setAdapter(new NewGroupUsersAdapter(ar_users,this));
        rc_added_users.setAdapter(new GroupAddedUsersAdapter(ar_users,this));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNewGroupActivity.this,NewGroupActivity.class));
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
