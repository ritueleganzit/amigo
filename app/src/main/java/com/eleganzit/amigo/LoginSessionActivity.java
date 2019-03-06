package com.eleganzit.amigo;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;

import com.eleganzit.amigo.adapter.AccountsAdapter;
import com.eleganzit.amigo.model.Accounts;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoginSessionActivity extends AppCompatActivity {

    RecyclerView rc_accounts;
    ArrayList<Accounts> arrayList=new ArrayList<>();
    LinearLayout signup,signin_another;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_session);

        rc_accounts=findViewById(R.id.rc_accounts);
        signup=findViewById(R.id.signup);
        signin_another=findViewById(R.id.signin_another);

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

        signin_another.setOnClickListener(new View.OnClickListener() {
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
