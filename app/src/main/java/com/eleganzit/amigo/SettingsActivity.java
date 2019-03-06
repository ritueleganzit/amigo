package com.eleganzit.amigo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout lin_currency,lin_personal,lin_payments,lin_kyc,lin_delete,lin_blocked;
    SharedPreferences settings_pref;
    SharedPreferences.Editor settings_editor;
    TextView txt_currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        settings_pref=getSharedPreferences("settings_pref",MODE_PRIVATE);
        settings_editor=settings_pref.edit();

        lin_currency=findViewById(R.id.lin_currency);
        lin_personal=findViewById(R.id.lin_personal);
        lin_payments=findViewById(R.id.lin_payments);
        lin_kyc=findViewById(R.id.lin_kyc);
        lin_delete=findViewById(R.id.lin_delete);
        txt_currency=findViewById(R.id.txt_currency);
        lin_blocked=findViewById(R.id.lin_blocked);

        lin_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,CurrencyActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,PersonalInformationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,PaymentsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_kyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,KYCActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(SettingsActivity.this);
                dialog.setContentView(R.layout.delete_dialog);

                TextView delete,cancel;
                
                delete=dialog.findViewById(R.id.delete);
                cancel=dialog.findViewById(R.id.cancel);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        lin_blocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,BlockedConnectionsActivity.class));
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
            txt_currency.setText("INR");
        }
        else
        {
            txt_currency.setText("USD");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
