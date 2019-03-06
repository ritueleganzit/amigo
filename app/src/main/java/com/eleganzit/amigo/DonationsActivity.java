package com.eleganzit.amigo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.amigo.adapter.DonationsAdapter;
import com.eleganzit.amigo.model.DonationsData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DonationsActivity extends AppCompatActivity {

    RecyclerView rc_donations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_donations=findViewById(R.id.rc_donations);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_donations.setLayoutManager(layoutManager);

        DonationsData donationsData=new DonationsData("","","","");

        ArrayList<DonationsData> arrayList=new ArrayList<>();
        arrayList.add(donationsData);
        arrayList.add(donationsData);

        rc_donations.setAdapter(new DonationsAdapter(arrayList,this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
