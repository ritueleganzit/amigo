package com.eleganzit.volunteerifyngo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.volunteerifyngo.adapter.BlockedUserAdapter;
import com.eleganzit.volunteerifyngo.model.BlockedUserData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BlockedConnectionsActivity extends AppCompatActivity {

    RecyclerView rc_blocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_connections);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_blocked=findViewById(R.id.rc_blocked);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_blocked.setLayoutManager(layoutManager);

        BlockedUserData blockedUserData=new BlockedUserData("","","");

        ArrayList<BlockedUserData> arrayList=new ArrayList<>();

        arrayList.add(blockedUserData);
        arrayList.add(blockedUserData);

        rc_blocked.setAdapter(new BlockedUserAdapter(arrayList,this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
