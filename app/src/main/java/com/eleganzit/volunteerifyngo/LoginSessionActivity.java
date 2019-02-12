package com.eleganzit.volunteerifyngo;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;

import com.eleganzit.volunteerifyngo.adapter.AccountsAdapter;
import com.eleganzit.volunteerifyngo.model.Accounts;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoginSessionActivity extends AppCompatActivity {

    RecyclerView rc_accounts;
    ArrayList<Accounts> arrayList=new ArrayList<>();
    LinearLayout signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_session);

        rc_accounts=findViewById(R.id.rc_accounts);
        signup=findViewById(R.id.signup);


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_accounts.setLayoutManager(layoutManager);

        Accounts accounts=new Accounts("","");
        arrayList.add(accounts);
        arrayList.add(accounts);
        arrayList.add(accounts);

        rc_accounts.setAdapter(new AccountsAdapter(arrayList,this));
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSessionActivity.this,RegistrationActivity.class));
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
