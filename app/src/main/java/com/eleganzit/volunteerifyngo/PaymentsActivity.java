package com.eleganzit.volunteerifyngo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.volunteerifyngo.adapter.FollowersAdapter;
import com.eleganzit.volunteerifyngo.adapter.PaymentsAdapter;
import com.eleganzit.volunteerifyngo.model.FollowersData;
import com.eleganzit.volunteerifyngo.model.PaymentsData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentsActivity extends AppCompatActivity {

    RecyclerView rc_payments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_payments=findViewById(R.id.rc_payments);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_payments.setLayoutManager(layoutManager);

        PaymentsData paymentsData=new PaymentsData("","","","","");

        ArrayList<PaymentsData> ar_payments=new ArrayList<>();

        if(ar_payments.size()>0)
        {
            ar_payments.clear();
        }

        ar_payments.add(paymentsData);
        ar_payments.add(paymentsData);

        rc_payments.setAdapter(new PaymentsAdapter(ar_payments,this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}