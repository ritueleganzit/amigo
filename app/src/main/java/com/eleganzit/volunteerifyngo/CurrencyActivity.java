package com.eleganzit.volunteerifyngo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class CurrencyActivity extends AppCompatActivity {

    SharedPreferences settings_pref;
    SharedPreferences.Editor settings_editor;
    CheckBox radio_inr,radio_usd;
    RelativeLayout rel_inr,rel_usd;
    LinearLayout save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        radio_inr=findViewById(R.id.radio_inr);
        radio_usd=findViewById(R.id.radio_usd);
        rel_inr=findViewById(R.id.rel_inr);
        rel_usd=findViewById(R.id.rel_usd);
        save=findViewById(R.id.save);

        rel_inr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_inr.setChecked(true);
                rel_usd.setEnabled(false);

            }
        });

        rel_usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_usd.setChecked(true);
                rel_inr.setEnabled(false);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        settings_pref=getSharedPreferences("settings_pref",MODE_PRIVATE);
        settings_editor=settings_pref.edit();

        if(settings_pref.getString("currency","INR").equalsIgnoreCase("INR"))
        {
            radio_inr.setChecked(true);
        }
        else
        {
            radio_usd.setChecked(true);
        }

        radio_inr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    radio_usd.setChecked(false);
                    settings_editor.putString("currency","INR");
                    settings_editor.commit();
                }
            }
        });

        radio_usd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    radio_inr.setChecked(false);
                    settings_editor.putString("currency","USD");
                    settings_editor.commit();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
