package com.eleganzit.amigo;

import android.Manifest;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityLoginBinding;
import com.eleganzit.amigo.model.GetLoginResponse;
import com.eleganzit.amigo.model.LoginData;
import com.eleganzit.amigo.session.LoggedInPreferences;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;
UserLoggedInSession userLoggedInSession;
    LoggedInPreferences loggedInPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

 binding= DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CAMERA


                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
userLoggedInSession=new UserLoggedInSession(LoginActivity.this);
loggedInPreferences=new LoggedInPreferences(LoginActivity.this);


        binding.savework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid1())
                {
                    binding.savework.setVisibility(View.GONE);
                    binding.progress.setVisibility(View.VISIBLE);
                    userLogin();
                }

            }
        });
        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }


    public void userLogin()
    {

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetLoginResponse> getLoginResponseCall=myInterface.getLogin(binding.edUsername.getText().toString(),binding.edPassword.getText().toString());
        getLoginResponseCall.enqueue(new Callback<GetLoginResponse>() {
            @Override
            public void onResponse(Call<GetLoginResponse> call, Response<GetLoginResponse> response) {
                binding.savework.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                if (response.isSuccessful())
                {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                R.drawable.white_check_small);
                        List<LoginData> loginData=response.body().getData();

                        Log.d(TAG,""+loginData.get(0).getFullname());
                        for (int i=0;i<loginData.size();i++)
                        {
                            LoginData loginData1=loginData.get(i);

                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("email", loginData1.getEmailId());
                                obj.put("username", loginData1.getUsername());
                                obj.put("fullname", loginData1.getFullname());
                                obj.put("photo", loginData1.getPhoto());

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            Map<String,?> getAll = loggedInPreferences.getAll();

                            if(getAll.size()==0 || getAll.size()==2)
                            {
                                Log.d("loginsesSize","added to first");
                                loggedInPreferences.createSession("first",obj);
                            }
                            else if(getAll.size()==1)
                            {
                                Log.d("loginsesSize","added to second");
                                loggedInPreferences.createSession("second",obj);
                               // loginsSessionManager.addLoginSession("second",userData);
                            }


                            userLoggedInSession.createLoginSession(loginData1.getEmailId(),loginData1.getUsername(),loginData1.getUserId(),loginData1.getFullname(),binding.edPassword.getText().toString(),loginData1.getPhoto());


                        }

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                }

            }

            @Override
            public void onFailure(Call<GetLoginResponse> call, Throwable t) {
                binding.savework.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }
        });


    }


    public boolean isValid1() {

        if (binding.edUsername.getText().toString().equals("")) {
            binding.edUsername.setError(""+getResources().getString(R.string.Please_Enter_username));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edUsername);

            binding.edUsername.requestFocus();
            return false;
        }

        else  if (binding.edPassword.getText().toString().equals("")) {
            binding.edPassword.setError(""+getResources().getString(R.string.Please_Enter_Password));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edPassword);

            binding.edPassword.requestFocus();
            return false;
        }


        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
