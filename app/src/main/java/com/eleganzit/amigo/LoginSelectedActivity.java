package com.eleganzit.amigo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityLoginSelectedBinding;
import com.eleganzit.amigo.model.Accounts;
import com.eleganzit.amigo.model.GetLoginResponse;
import com.eleganzit.amigo.model.LoginData;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.hzn.lib.EasyTransition;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSelectedActivity extends AppCompatActivity {

    private static final String TAG ="LoginSelectedActivity" ;
    private boolean finishEnter;
    ActivityLoginSelectedBinding binding;
    Accounts accounts;
    UserLoggedInSession userLoggedInSession;

    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLoggedInSession=new UserLoggedInSession(LoginSelectedActivity.this);

        binding= DataBindingUtil.setContentView(LoginSelectedActivity.this,R.layout.activity_login_selected);
     accounts=   (Accounts) getIntent().getSerializableExtra("userdata");
        try {
            JSONObject jsonObject = new JSONObject("" + accounts.getObject());
            binding.username.setText(jsonObject.getString("fullname"));
            username=jsonObject.getString("username");
            Glide.with(this).load(jsonObject.getString("photo")).into(binding.profilePhoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSelectedActivity.this,ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        long transitionDuration = 300;
        finishEnter = false;
        EasyTransition.enter(
                this,
                transitionDuration,
                new DecelerateInterpolator(),
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // init other views after transition anim
                        finishEnter = true;
                    }
                });
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSelectedActivity.this,RegistrationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });


        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid1() )
                {
                    userLogin();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (finishEnter) {
            finishEnter = false;
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }

    }

    public void userLogin()
    {

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetLoginResponse> getLoginResponseCall=myInterface.getLogin(username,binding.edPassword.getText().toString());
        getLoginResponseCall.enqueue(new Callback<GetLoginResponse>() {
            @Override
            public void onResponse(Call<GetLoginResponse> call, Response<GetLoginResponse> response) {

                if (response.isSuccessful())
                {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {

                        List<LoginData> loginData=response.body().getData();

                        Log.d(TAG,""+loginData.get(0).getUserId());
                        for (int i=0;i<loginData.size();i++)
                        {
                            LoginData loginData1=loginData.get(i);


                            userLoggedInSession.createLoginSession(loginData1.getEmailId(),loginData1.getUsername(),loginData1.getUserId(),loginData1.getFullname(),binding.edPassword.getText().toString(),loginData1.getPhoto());


                        }

                    }
                    else
                    {

                    }
                }
                else
                {

                }

            }

            @Override
            public void onFailure(Call<GetLoginResponse> call, Throwable t) {

            }
        });


    }

    public boolean isValid1() {

         if (binding.edPassword.getText().toString().equals("")) {
            binding.edPassword.setError(""+getResources().getString(R.string.Please_Enter_Password));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edPassword);

            binding.edPassword.requestFocus();
            return false;
        }


        return true;
    }
}
