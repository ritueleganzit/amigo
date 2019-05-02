package com.eleganzit.amigo;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;

import com.eleganzit.amigo.session.LoggedInPreferences;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    UserLoggedInSession userLoggedInSession;
    LoggedInPreferences loggedInPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        userLoggedInSession=new UserLoggedInSession(SplashActivity.this);
        loggedInPreferences=new LoggedInPreferences(SplashActivity.this);


        Map<String, ?> allEntries = loggedInPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userLoggedInSession.isLoggedIn())
                {
                    startActivity(new Intent(SplashActivity.this,NewsFeedActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
                else  if (!(loggedInPreferences.getAll().isEmpty()))
                {
                    startActivity(new Intent(SplashActivity.this,LoginSessionActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();

                }

            }
        },3000);
    }
}
