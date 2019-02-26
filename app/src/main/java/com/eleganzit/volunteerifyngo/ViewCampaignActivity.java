package com.eleganzit.volunteerifyngo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.fragments.HomeFragment;
import com.eleganzit.volunteerifyngo.fragments.InformationFragment;

import androidx.appcompat.app.AppCompatActivity;

public class ViewCampaignActivity extends AppCompatActivity {

    public static TextView tab_information,tab_campaigns,tab_offer,tab_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_campaign);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tab_information=findViewById(R.id.tab_information);
        tab_campaigns=findViewById(R.id.tab_campaigns);
        tab_offer=findViewById(R.id.tab_offer);
        tab_news=findViewById(R.id.tab_news);

        InformationFragment informationFragment= new InformationFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, informationFragment,"TAG")
                .commit();

        tab_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InformationFragment informationFragment= new InformationFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, informationFragment,"TAG")
                        .commit();
            }
        });

    }
}
