package com.eleganzit.amigo;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.databinding.ActivityRegistrationBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class RegistrationActivity extends AppCompatActivity {
    
    ActivityRegistrationBinding binding;
    String email="",fullname="",mobile="",username="",password="",city="",state="",pincode="",contact_person="",contact_person_number="",proof="",lat="",lng="",device_id="",device_token="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding=DataBindingUtil.setContentView(RegistrationActivity.this,R.layout.activity_registration);


        binding.fab1.next1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (isValid1())
                {
                    binding.registerBg.setImageResource(R.drawable.register_2);
                    binding.registerInput2.registerInput2.setVisibility(View.VISIBLE);
                    binding.registerInput1.registerInput1.setVisibility(View.GONE);
                    binding.registerInput3.registerInput3.setVisibility(View.GONE);
                    binding.registerInput4.registerInput4.setVisibility(View.GONE);
                    binding.registerInput5.registerInput5.setVisibility(View.GONE);
                    binding.registerInput6.registerInput6.setVisibility(View.GONE);
                    binding.fab1.next1.setVisibility(View.GONE);
                    binding.fab2.next2.setVisibility(View.VISIBLE);
                    binding.fab3.next3.setVisibility(View.GONE);
                    binding.fab4.next4.setVisibility(View.GONE);
                    binding.fab5.next5.setVisibility(View.GONE);
                    binding.fab6.next6.setVisibility(View.GONE);
                }

            }
        });

        binding.fab2.next2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                binding.registerBg.setImageResource(R.drawable.register_3);
                binding.registerInput3.registerInput3.setVisibility(View.VISIBLE);
                binding.registerInput1.registerInput1.setVisibility(View.GONE);
                binding.registerInput2.registerInput2.setVisibility(View.GONE);
                binding.registerInput4.registerInput4.setVisibility(View.GONE);
                binding.registerInput5.registerInput5.setVisibility(View.GONE);
                binding.registerInput6.registerInput6.setVisibility(View.GONE);
                binding.fab1.next1.setVisibility(View.GONE);
                binding.fab2.next2.setVisibility(View.GONE);
                binding.fab3.next3.setVisibility(View.VISIBLE);
                binding.fab4.next4.setVisibility(View.GONE);
                binding.fab5.next5.setVisibility(View.GONE);
                binding.fab6.next6.setVisibility(View.GONE);
            }
        });

        binding.fab3.next3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                binding.registerBg.setImageResource(R.drawable.register_4);
                binding.registerInput4.registerInput4.setVisibility(View.VISIBLE);
                binding.registerInput1.registerInput1.setVisibility(View.GONE);
                binding.registerInput2.registerInput2.setVisibility(View.GONE);
                binding.registerInput3.registerInput3.setVisibility(View.GONE);
                binding.registerInput5.registerInput5.setVisibility(View.GONE);
                binding.registerInput6.registerInput6.setVisibility(View.GONE);
                binding.fab1.next1.setVisibility(View.GONE);
                binding.fab2.next2.setVisibility(View.GONE);
                binding.fab3.next3.setVisibility(View.GONE);
                binding.fab4.next4.setVisibility(View.VISIBLE);
                binding.fab5.next5.setVisibility(View.GONE);
                binding.fab6.next6.setVisibility(View.GONE);
            }
        });

        binding.fab4.next4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                binding.registerBg.setImageResource(R.drawable.register_5);
                binding.registerInput5.registerInput5.setVisibility(View.VISIBLE);
                binding.registerInput1.registerInput1.setVisibility(View.GONE);
                binding.registerInput2.registerInput2.setVisibility(View.GONE);
                binding.registerInput3.registerInput3.setVisibility(View.GONE);
                binding.registerInput4.registerInput4.setVisibility(View.GONE);
                binding.registerInput6.registerInput6.setVisibility(View.GONE);
                binding.fab1.next1.setVisibility(View.GONE);
                binding.fab2.next2.setVisibility(View.GONE);
                binding.fab3.next3.setVisibility(View.GONE);
                binding.fab4.next4.setVisibility(View.GONE);
                binding.fab5.next5.setVisibility(View.VISIBLE);
                binding.fab6.next6.setVisibility(View.GONE);
            }
        });

        binding.fab5.next5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                binding.registerBg.setImageResource(R.drawable.register_6);
                binding.registerInput6.registerInput6.setVisibility(View.VISIBLE);
                binding.registerInput1.registerInput1.setVisibility(View.GONE);
                binding.registerInput2.registerInput2.setVisibility(View.GONE);
                binding.registerInput3.registerInput3.setVisibility(View.GONE);
                binding.registerInput4.registerInput4.setVisibility(View.GONE);
                binding.registerInput5.registerInput5.setVisibility(View.GONE);
                binding.fab1.next1.setVisibility(View.GONE);
                binding.fab2.next2.setVisibility(View.GONE);
                binding.fab3.next3.setVisibility(View.GONE);
                binding.fab4.next4.setVisibility(View.GONE);
                binding.fab5.next5.setVisibility(View.GONE);
                binding.fab6.next6.setVisibility(View.VISIBLE);
            }
        });

        binding.fab6.next6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegistrationActivity.this,RegisterConfirmationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        });

        binding.backText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if(binding.registerInput1.registerInput1.getVisibility()==View.VISIBLE)
                {
                    onBackPressed();
                }
                else if(binding.registerInput2.registerInput2.getVisibility()==View.VISIBLE)
                {
                    binding.registerBg.setImageResource(R.drawable.register_1);
                    binding.registerInput1.registerInput1.setVisibility(View.VISIBLE);
                    binding.registerInput2.registerInput2.setVisibility(View.GONE);
                    binding.registerInput3.registerInput3.setVisibility(View.GONE);
                    binding.registerInput4.registerInput4.setVisibility(View.GONE);
                    binding.registerInput5.registerInput5.setVisibility(View.GONE);
                    binding.registerInput6.registerInput6.setVisibility(View.GONE);
                    binding.fab1.next1.setVisibility(View.VISIBLE);
                    binding.fab2.next2.setVisibility(View.GONE);
                    binding.fab3.next3.setVisibility(View.GONE);
                    binding.fab4.next4.setVisibility(View.GONE);
                    binding.fab5.next5.setVisibility(View.GONE);
                    binding.fab6.next6.setVisibility(View.GONE);
                }
                else if(binding.registerInput3.registerInput3.getVisibility()==View.VISIBLE)
                {
                    binding.registerBg.setImageResource(R.drawable.register_2);
                    binding.registerInput1.registerInput1.setVisibility(View.GONE);
                    binding.registerInput2.registerInput2.setVisibility(View.VISIBLE);
                    binding.registerInput3.registerInput3.setVisibility(View.GONE);
                    binding.registerInput4.registerInput4.setVisibility(View.GONE);
                    binding.registerInput5.registerInput5.setVisibility(View.GONE);
                    binding.registerInput6.registerInput6.setVisibility(View.GONE);
                    binding.fab1.next1.setVisibility(View.GONE);
                    binding.fab2.next2.setVisibility(View.VISIBLE);
                    binding.fab3.next3.setVisibility(View.GONE);
                    binding.fab4.next4.setVisibility(View.GONE);
                    binding.fab5.next5.setVisibility(View.GONE);
                    binding.fab6.next6.setVisibility(View.GONE);
                }
                else if(binding.registerInput4.registerInput4.getVisibility()==View.VISIBLE)
                {
                    binding.registerBg.setImageResource(R.drawable.register_3);
                    binding.registerInput1.registerInput1.setVisibility(View.GONE);
                    binding.registerInput2.registerInput2.setVisibility(View.GONE);
                    binding.registerInput3.registerInput3.setVisibility(View.VISIBLE);
                    binding.registerInput4.registerInput4.setVisibility(View.GONE);
                    binding.registerInput5.registerInput5.setVisibility(View.GONE);
                    binding.registerInput6.registerInput6.setVisibility(View.GONE);
                    binding.fab1.next1.setVisibility(View.GONE);
                    binding.fab2.next2.setVisibility(View.GONE);
                    binding.fab3.next3.setVisibility(View.VISIBLE);
                    binding.fab4.next4.setVisibility(View.GONE);
                    binding.fab5.next5.setVisibility(View.GONE);
                    binding.fab6.next6.setVisibility(View.GONE);
                }
                else if(binding.registerInput5.registerInput5.getVisibility()==View.VISIBLE)
                {
                    binding.registerBg.setImageResource(R.drawable.register_4);
                    binding.registerInput1.registerInput1.setVisibility(View.GONE);
                    binding.registerInput2.registerInput2.setVisibility(View.GONE);
                    binding.registerInput3.registerInput3.setVisibility(View.GONE);
                    binding.registerInput4.registerInput4.setVisibility(View.VISIBLE);
                    binding.registerInput5.registerInput5.setVisibility(View.GONE);
                    binding.registerInput6.registerInput6.setVisibility(View.GONE);
                    binding.fab1.next1.setVisibility(View.GONE);
                    binding.fab2.next2.setVisibility(View.GONE);
                    binding.fab3.next3.setVisibility(View.GONE);
                    binding.fab4.next4.setVisibility(View.VISIBLE);
                    binding.fab5.next5.setVisibility(View.GONE);
                    binding.fab6.next6.setVisibility(View.GONE);
                }
                else if(binding.registerInput6.registerInput6.getVisibility()==View.VISIBLE)
                {
                    binding.registerBg.setImageResource(R.drawable.register_5);
                    binding.registerInput1.registerInput1.setVisibility(View.GONE);
                    binding.registerInput2.registerInput2.setVisibility(View.GONE);
                    binding.registerInput3.registerInput3.setVisibility(View.GONE);
                    binding.registerInput4.registerInput4.setVisibility(View.GONE);
                    binding.registerInput5.registerInput5.setVisibility(View.VISIBLE);
                    binding.registerInput6.registerInput6.setVisibility(View.GONE);
                    binding.fab1.next1.setVisibility(View.GONE);
                    binding.fab2.next2.setVisibility(View.GONE);
                    binding.fab3.next3.setVisibility(View.GONE);
                    binding.fab4.next4.setVisibility(View.GONE);
                    binding.fab5.next5.setVisibility(View.VISIBLE);
                    binding.fab6.next6.setVisibility(View.GONE);
                }
            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {

        if(binding.registerInput1.registerInput1.getVisibility()==View.VISIBLE)
        {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
        else if(binding.registerInput2.registerInput2.getVisibility()==View.VISIBLE)
        {
            binding.registerBg.setImageResource(R.drawable.register_1);
            binding.registerInput1.registerInput1.setVisibility(View.VISIBLE);
            binding.registerInput2.registerInput2.setVisibility(View.GONE);
            binding.registerInput3.registerInput3.setVisibility(View.GONE);
            binding.registerInput4.registerInput4.setVisibility(View.GONE);
            binding.registerInput5.registerInput5.setVisibility(View.GONE);
            binding.registerInput6.registerInput6.setVisibility(View.GONE);
            binding.fab1.next1.setVisibility(View.VISIBLE);
            binding.fab2.next2.setVisibility(View.GONE);
            binding.fab3.next3.setVisibility(View.GONE);
            binding.fab4.next4.setVisibility(View.GONE);
            binding.fab5.next5.setVisibility(View.GONE);
            binding.fab6.next6.setVisibility(View.GONE);
        }
        else if(binding.registerInput3.registerInput3.getVisibility()==View.VISIBLE)
        {
            binding.registerBg.setImageResource(R.drawable.register_2);
            binding.registerInput1.registerInput1.setVisibility(View.GONE);
            binding.registerInput2.registerInput2.setVisibility(View.VISIBLE);
            binding.registerInput3.registerInput3.setVisibility(View.GONE);
            binding.registerInput4.registerInput4.setVisibility(View.GONE);
            binding.registerInput5.registerInput5.setVisibility(View.GONE);
            binding.registerInput6.registerInput6.setVisibility(View.GONE);
            binding.fab1.next1.setVisibility(View.GONE);
            binding.fab2.next2.setVisibility(View.VISIBLE);
            binding.fab3.next3.setVisibility(View.GONE);
            binding.fab4.next4.setVisibility(View.GONE);
            binding.fab5.next5.setVisibility(View.GONE);
            binding.fab6.next6.setVisibility(View.GONE);
        }
        else if(binding.registerInput4.registerInput4.getVisibility()==View.VISIBLE)
        {
            binding.registerBg.setImageResource(R.drawable.register_3);
            binding.registerInput1.registerInput1.setVisibility(View.GONE);
            binding.registerInput2.registerInput2.setVisibility(View.GONE);
            binding.registerInput3.registerInput3.setVisibility(View.VISIBLE);
            binding.registerInput4.registerInput4.setVisibility(View.GONE);
            binding.registerInput5.registerInput5.setVisibility(View.GONE);
            binding.registerInput6.registerInput6.setVisibility(View.GONE);
            binding.fab1.next1.setVisibility(View.GONE);
            binding.fab2.next2.setVisibility(View.GONE);
            binding.fab3.next3.setVisibility(View.VISIBLE);
            binding.fab4.next4.setVisibility(View.GONE);
            binding.fab5.next5.setVisibility(View.GONE);
            binding.fab6.next6.setVisibility(View.GONE);
        }
        else if(binding.registerInput5.registerInput5.getVisibility()==View.VISIBLE)
        {
            binding.registerBg.setImageResource(R.drawable.register_4);
            binding.registerInput1.registerInput1.setVisibility(View.GONE);
            binding.registerInput2.registerInput2.setVisibility(View.GONE);
            binding.registerInput3.registerInput3.setVisibility(View.GONE);
            binding.registerInput4.registerInput4.setVisibility(View.VISIBLE);
            binding.registerInput5.registerInput5.setVisibility(View.GONE);
            binding.registerInput6.registerInput6.setVisibility(View.GONE);
            binding.fab1.next1.setVisibility(View.GONE);
            binding.fab2.next2.setVisibility(View.GONE);
            binding.fab3.next3.setVisibility(View.GONE);
            binding.fab4.next4.setVisibility(View.VISIBLE);
            binding.fab5.next5.setVisibility(View.GONE);
            binding.fab6.next6.setVisibility(View.GONE);
        }
        else if(binding.registerInput6.registerInput6.getVisibility()==View.VISIBLE)
        {
            binding.registerBg.setImageResource(R.drawable.register_5);
            binding.registerInput1.registerInput1.setVisibility(View.GONE);
            binding.registerInput2.registerInput2.setVisibility(View.GONE);
            binding.registerInput3.registerInput3.setVisibility(View.GONE);
            binding.registerInput4.registerInput4.setVisibility(View.GONE);
            binding.registerInput5.registerInput5.setVisibility(View.VISIBLE);
            binding.registerInput6.registerInput6.setVisibility(View.GONE);
            binding.fab1.next1.setVisibility(View.GONE);
            binding.fab2.next2.setVisibility(View.GONE);
            binding.fab3.next3.setVisibility(View.GONE);
            binding.fab4.next4.setVisibility(View.GONE);
            binding.fab5.next5.setVisibility(View.VISIBLE);
            binding.fab6.next6.setVisibility(View.GONE);
        }

    }


    public boolean isValid1() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(binding.registerInput1.edEmail.getText().toString());

        if (binding.registerInput1.edEmail.getText().toString().equals("")) {
            binding.registerInput1.edEmail.setError(""+getResources().getString(R.string.Please_enter_email));
/*
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput1.edEmail);
*/
            binding.registerInput1.edEmail.requestFocus();
            return false;
        }
        else if (!matcher.matches()) {
            binding.registerInput1.edEmail.setError(""+getResources().getString(R.string.Please_Enter_Valid_Email));
/*
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput1.edEmail);
*/
            binding.registerInput1.edEmail.requestFocus();
            return false;
        }
        else  if (binding.registerInput1.edUsername.getText().toString().equals("")) {
            binding.registerInput1.edUsername.setError(""+getResources().getString(R.string.Please_Enter_Password));
/*
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(password);
*/
            binding.registerInput1.edUsername.requestFocus();
            return false;
        }


        return true;
    }
}
