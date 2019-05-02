package com.eleganzit.amigo;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.eleganzit.amigo.adapter.AccountsAdapter;
import com.eleganzit.amigo.databinding.ActivityLoginSessionBinding;
import com.eleganzit.amigo.model.Accounts;
import com.eleganzit.amigo.session.LoggedInPreferences;

import java.util.ArrayList;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoginSessionActivity extends AppCompatActivity {

    ArrayList<Accounts> arrayList=new ArrayList<>();

ActivityLoginSessionBinding binding;
    LoggedInPreferences loggedInPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(LoginSessionActivity.this,R.layout.activity_login_session);

        loggedInPreferences=new LoggedInPreferences(LoginSessionActivity.this);


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcAccounts.setLayoutManager(layoutManager);


        if (loggedInPreferences.getAll().isEmpty())
        {
              startActivity(new Intent(LoginSessionActivity.this,LoginActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();


        }else
        {
            Map<String, ?> allEntries = loggedInPreferences.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                Accounts accounts=new Accounts(entry.getKey(),entry.getValue().toString());


                arrayList.add(accounts);

            }

        }



        binding.rcAccounts.setAdapter(new AccountsAdapter(arrayList,this));
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSessionActivity.this,RegistrationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        binding.signinAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSessionActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
