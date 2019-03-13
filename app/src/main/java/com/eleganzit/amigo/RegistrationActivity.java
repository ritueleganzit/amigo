package com.eleganzit.amigo;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    ImageView register_bg;
    RelativeLayout register_input1,register_input2,register_input3,register_input4,register_input5,register_input6;
    FloatingActionButton next1,next2,next3,next4,next5,next6;
    LinearLayout back_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        register_bg=findViewById(R.id.register_bg);
        register_input1=findViewById(R.id.register_input1);
        register_input2=findViewById(R.id.register_input2);
        register_input3=findViewById(R.id.register_input3);
        register_input4=findViewById(R.id.register_input4);
        register_input5=findViewById(R.id.register_input5);
        register_input6=findViewById(R.id.register_input6);
        next1=findViewById(R.id.next1);
        next2=findViewById(R.id.next2);
        next3=findViewById(R.id.next3);
        next4=findViewById(R.id.next4);
        next5=findViewById(R.id.next5);
        next6=findViewById(R.id.next6);
        back_text=findViewById(R.id.back_text);

        next1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                register_bg.setImageResource(R.drawable.register_2);
                register_input2.setVisibility(View.VISIBLE);
                register_input1.setVisibility(View.GONE);
                register_input3.setVisibility(View.GONE);
                register_input4.setVisibility(View.GONE);
                register_input5.setVisibility(View.GONE);
                register_input6.setVisibility(View.GONE);
                next1.setVisibility(View.GONE);
                next2.setVisibility(View.VISIBLE);
                next3.setVisibility(View.GONE);
                next4.setVisibility(View.GONE);
                next5.setVisibility(View.GONE);
                next6.setVisibility(View.GONE);
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                register_bg.setImageResource(R.drawable.register_3);
                register_input3.setVisibility(View.VISIBLE);
                register_input1.setVisibility(View.GONE);
                register_input2.setVisibility(View.GONE);
                register_input4.setVisibility(View.GONE);
                register_input5.setVisibility(View.GONE);
                register_input6.setVisibility(View.GONE);
                next1.setVisibility(View.GONE);
                next2.setVisibility(View.GONE);
                next3.setVisibility(View.VISIBLE);
                next4.setVisibility(View.GONE);
                next5.setVisibility(View.GONE);
                next6.setVisibility(View.GONE);
            }
        });

        next3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                register_bg.setImageResource(R.drawable.register_4);
                register_input4.setVisibility(View.VISIBLE);
                register_input1.setVisibility(View.GONE);
                register_input2.setVisibility(View.GONE);
                register_input3.setVisibility(View.GONE);
                register_input5.setVisibility(View.GONE);
                register_input6.setVisibility(View.GONE);
                next1.setVisibility(View.GONE);
                next2.setVisibility(View.GONE);
                next3.setVisibility(View.GONE);
                next4.setVisibility(View.VISIBLE);
                next5.setVisibility(View.GONE);
                next6.setVisibility(View.GONE);
            }
        });

        next4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                register_bg.setImageResource(R.drawable.register_5);
                register_input5.setVisibility(View.VISIBLE);
                register_input1.setVisibility(View.GONE);
                register_input2.setVisibility(View.GONE);
                register_input3.setVisibility(View.GONE);
                register_input4.setVisibility(View.GONE);
                register_input6.setVisibility(View.GONE);
                next1.setVisibility(View.GONE);
                next2.setVisibility(View.GONE);
                next3.setVisibility(View.GONE);
                next4.setVisibility(View.GONE);
                next5.setVisibility(View.VISIBLE);
                next6.setVisibility(View.GONE);
            }
        });

        next5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                register_bg.setImageResource(R.drawable.register_6);
                register_input6.setVisibility(View.VISIBLE);
                register_input1.setVisibility(View.GONE);
                register_input2.setVisibility(View.GONE);
                register_input3.setVisibility(View.GONE);
                register_input4.setVisibility(View.GONE);
                register_input5.setVisibility(View.GONE);
                next1.setVisibility(View.GONE);
                next2.setVisibility(View.GONE);
                next3.setVisibility(View.GONE);
                next4.setVisibility(View.GONE);
                next5.setVisibility(View.GONE);
                next6.setVisibility(View.VISIBLE);
            }
        });

        next6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegistrationActivity.this,RegisterConfirmationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        });

        back_text.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if(register_input1.getVisibility()==View.VISIBLE)
                {
                    onBackPressed();
                }
                else if(register_input2.getVisibility()==View.VISIBLE)
                {
                    register_bg.setImageResource(R.drawable.register_1);
                    register_input1.setVisibility(View.VISIBLE);
                    register_input2.setVisibility(View.GONE);
                    register_input3.setVisibility(View.GONE);
                    register_input4.setVisibility(View.GONE);
                    register_input5.setVisibility(View.GONE);
                    register_input6.setVisibility(View.GONE);
                    next1.setVisibility(View.VISIBLE);
                    next2.setVisibility(View.GONE);
                    next3.setVisibility(View.GONE);
                    next4.setVisibility(View.GONE);
                    next5.setVisibility(View.GONE);
                    next6.setVisibility(View.GONE);
                }
                else if(register_input3.getVisibility()==View.VISIBLE)
                {
                    register_bg.setImageResource(R.drawable.register_2);
                    register_input1.setVisibility(View.GONE);
                    register_input2.setVisibility(View.VISIBLE);
                    register_input3.setVisibility(View.GONE);
                    register_input4.setVisibility(View.GONE);
                    register_input5.setVisibility(View.GONE);
                    register_input6.setVisibility(View.GONE);
                    next1.setVisibility(View.GONE);
                    next2.setVisibility(View.VISIBLE);
                    next3.setVisibility(View.GONE);
                    next4.setVisibility(View.GONE);
                    next5.setVisibility(View.GONE);
                    next6.setVisibility(View.GONE);
                }
                else if(register_input4.getVisibility()==View.VISIBLE)
                {
                    register_bg.setImageResource(R.drawable.register_3);
                    register_input1.setVisibility(View.GONE);
                    register_input2.setVisibility(View.GONE);
                    register_input3.setVisibility(View.VISIBLE);
                    register_input4.setVisibility(View.GONE);
                    register_input5.setVisibility(View.GONE);
                    register_input6.setVisibility(View.GONE);
                    next1.setVisibility(View.GONE);
                    next2.setVisibility(View.GONE);
                    next3.setVisibility(View.VISIBLE);
                    next4.setVisibility(View.GONE);
                    next5.setVisibility(View.GONE);
                    next6.setVisibility(View.GONE);
                }
                else if(register_input5.getVisibility()==View.VISIBLE)
                {
                    register_bg.setImageResource(R.drawable.register_4);
                    register_input1.setVisibility(View.GONE);
                    register_input2.setVisibility(View.GONE);
                    register_input3.setVisibility(View.GONE);
                    register_input4.setVisibility(View.VISIBLE);
                    register_input5.setVisibility(View.GONE);
                    register_input6.setVisibility(View.GONE);
                    next1.setVisibility(View.GONE);
                    next2.setVisibility(View.GONE);
                    next3.setVisibility(View.GONE);
                    next4.setVisibility(View.VISIBLE);
                    next5.setVisibility(View.GONE);
                    next6.setVisibility(View.GONE);
                }
                else if(register_input6.getVisibility()==View.VISIBLE)
                {
                    register_bg.setImageResource(R.drawable.register_5);
                    register_input1.setVisibility(View.GONE);
                    register_input2.setVisibility(View.GONE);
                    register_input3.setVisibility(View.GONE);
                    register_input4.setVisibility(View.GONE);
                    register_input5.setVisibility(View.VISIBLE);
                    register_input6.setVisibility(View.GONE);
                    next1.setVisibility(View.GONE);
                    next2.setVisibility(View.GONE);
                    next3.setVisibility(View.GONE);
                    next4.setVisibility(View.GONE);
                    next5.setVisibility(View.VISIBLE);
                    next6.setVisibility(View.GONE);
                }
            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {

        if(register_input1.getVisibility()==View.VISIBLE)
        {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
        else if(register_input2.getVisibility()==View.VISIBLE)
        {
            register_bg.setImageResource(R.drawable.register_1);
            register_input1.setVisibility(View.VISIBLE);
            register_input2.setVisibility(View.GONE);
            register_input3.setVisibility(View.GONE);
            register_input4.setVisibility(View.GONE);
            register_input5.setVisibility(View.GONE);
            register_input6.setVisibility(View.GONE);
            next1.setVisibility(View.VISIBLE);
            next2.setVisibility(View.GONE);
            next3.setVisibility(View.GONE);
            next4.setVisibility(View.GONE);
            next5.setVisibility(View.GONE);
            next6.setVisibility(View.GONE);
        }
        else if(register_input3.getVisibility()==View.VISIBLE)
        {
            register_bg.setImageResource(R.drawable.register_2);
            register_input1.setVisibility(View.GONE);
            register_input2.setVisibility(View.VISIBLE);
            register_input3.setVisibility(View.GONE);
            register_input4.setVisibility(View.GONE);
            register_input5.setVisibility(View.GONE);
            register_input6.setVisibility(View.GONE);
            next1.setVisibility(View.GONE);
            next2.setVisibility(View.VISIBLE);
            next3.setVisibility(View.GONE);
            next4.setVisibility(View.GONE);
            next5.setVisibility(View.GONE);
            next6.setVisibility(View.GONE);
        }
        else if(register_input4.getVisibility()==View.VISIBLE)
        {
            register_bg.setImageResource(R.drawable.register_3);
            register_input1.setVisibility(View.GONE);
            register_input2.setVisibility(View.GONE);
            register_input3.setVisibility(View.VISIBLE);
            register_input4.setVisibility(View.GONE);
            register_input5.setVisibility(View.GONE);
            register_input6.setVisibility(View.GONE);
            next1.setVisibility(View.GONE);
            next2.setVisibility(View.GONE);
            next3.setVisibility(View.VISIBLE);
            next4.setVisibility(View.GONE);
            next5.setVisibility(View.GONE);
            next6.setVisibility(View.GONE);
        }
        else if(register_input5.getVisibility()==View.VISIBLE)
        {
            register_bg.setImageResource(R.drawable.register_4);
            register_input1.setVisibility(View.GONE);
            register_input2.setVisibility(View.GONE);
            register_input3.setVisibility(View.GONE);
            register_input4.setVisibility(View.VISIBLE);
            register_input5.setVisibility(View.GONE);
            register_input6.setVisibility(View.GONE);
            next1.setVisibility(View.GONE);
            next2.setVisibility(View.GONE);
            next3.setVisibility(View.GONE);
            next4.setVisibility(View.VISIBLE);
            next5.setVisibility(View.GONE);
            next6.setVisibility(View.GONE);
        }
        else if(register_input6.getVisibility()==View.VISIBLE)
        {
            register_bg.setImageResource(R.drawable.register_5);
            register_input1.setVisibility(View.GONE);
            register_input2.setVisibility(View.GONE);
            register_input3.setVisibility(View.GONE);
            register_input4.setVisibility(View.GONE);
            register_input5.setVisibility(View.VISIBLE);
            register_input6.setVisibility(View.GONE);
            next1.setVisibility(View.GONE);
            next2.setVisibility(View.GONE);
            next3.setVisibility(View.GONE);
            next4.setVisibility(View.GONE);
            next5.setVisibility(View.VISIBLE);
            next6.setVisibility(View.GONE);
        }

    }
}
