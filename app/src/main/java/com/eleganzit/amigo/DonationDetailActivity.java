package com.eleganzit.amigo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.donation.DeleteDonationResponse;
import com.eleganzit.amigo.model.donation.SingleDonationResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.eleganzit.amigo.utils.TextViewRobotoRegular;

import java.text.DecimalFormat;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationDetailActivity extends AppCompatActivity {
TextViewRobotoBold donate,totaldonars,raisedAmount,need_amount,viewdonate;
TextViewRobotoRegular start_date,end_date,description;
ImageView donationphoto,options;

UserLoggedInSession userLoggedInSession;
    String user_id,donation_id;
    SeekBar donation_seekbar;
     TextView txtamount,txt_need;
     String viewlist="";

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);
        donate=findViewById(R.id.donate);
        start_date=findViewById(R.id.start_date);
        end_date=findViewById(R.id.end_date);
        totaldonars=findViewById(R.id.totaldonars);
        txtamount=findViewById(R.id.txtamount);
        options=findViewById(R.id.options);
        txt_need=findViewById(R.id.txt_need);
        description=findViewById(R.id.description);
        raisedAmount=findViewById(R.id.raisedAmount);
        need_amount=findViewById(R.id.need_amount);
        donationphoto=findViewById(R.id.donationphoto);
        viewdonate=findViewById(R.id.viewdonate);
        userLoggedInSession=new UserLoggedInSession(DonationDetailActivity.this);
        donation_seekbar=findViewById(R.id.donation_seekbar);
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);
        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        viewdonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewlist.equalsIgnoreCase("0")){
                    Toast.makeText(DonationDetailActivity.this, "No Donation received", Toast.LENGTH_SHORT).show();
                }else
                {

                    startActivity(new Intent(DonationDetailActivity.this,ViewDonarsListActivity.class)
                            .putExtra("donation_id",donation_id));

                }

            }
        });
        donation_id=getIntent().getStringExtra("donation_id");

                //donation_seekbar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                donation_seekbar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        //donation_seekbar.setEnabled(false);
        donation_seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
                /*donation_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                        // TODO Auto-generated method stub
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String yourFormattedString = "$"+formatter.format(progress*200);

                        txtamount.setText(yourFormattedString);
                        //Toast.makeText(UserProfileActivity.this, String.valueOf(progress),Toast.LENGTH_LONG).show();
                    }
                });*/


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(DonationDetailActivity.this, options);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.donation_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.delete) {
                            AlertDialog diaBox = AskOption();
                            diaBox.show();

                        }

                        if (item.getItemId() == R.id.edit) {
                            startActivity(new Intent(DonationDetailActivity.this, EditDonationActivity.class)
                                    .putExtra("donation_id", donation_id));
                            // finish();
                        }


                        //Toast.makeText(ChatActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });


    }

    private void deleteDonation() {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<DeleteDonationResponse> call=myInterface.delateDonation(donation_id);
        call.enqueue(new Callback<DeleteDonationResponse>() {
            @Override
            public void onResponse(Call<DeleteDonationResponse> call, Response<DeleteDonationResponse> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(DonationDetailActivity.this, "Successfully  Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DonationDetailActivity.this,DonationListActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DeleteDonationResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSingleDonation();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void getSingleDonation()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SingleDonationResponse> singleDonationResponseCall=myInterface.getSingleDonation(donation_id);
        singleDonationResponseCall.enqueue(new Callback<SingleDonationResponse>() {
            @Override
            public void onResponse(Call<SingleDonationResponse> call, Response<SingleDonationResponse> response) {

                if (response.isSuccessful())
                {

                    if (response.body().getData()!=null)
                    {
                        start_date.setText("Start Date : "+response.body().getData().get(0).getDonationStartDate());
                        end_date.setText("End Date :"+response.body().getData().get(0).getDonationEndDate());
                        description.setText(""+response.body().getData().get(0).getDescription());
                        totaldonars.setText(""+response.body().getData().get(0).getCountDonors());
                        txt_need.setText("$ "+response.body().getData().get(0).getNeedAmount());
                        need_amount.setText("$ "+response.body().getData().get(0).getNeedAmount());

                        if (response.body().getData().get(0).getCountDonors().toString().equalsIgnoreCase("0"))
                        {
                            viewlist="0";

                        }

                        if ( (response.body().getData().get(0).getRaisedAmount()!=null) && (!(response.body().getData().get(0).getRaisedAmount().isEmpty())))
                        {

                            int amount= Integer.parseInt(response.body().getData().get(0).getRaisedAmount());
                            int needamount= Integer.parseInt(response.body().getData().get(0).getNeedAmount());
                            Log.d("amount",""+amount);
                            Log.d("amount",""+needamount);

                            double res = ( amount * 100.0f) / needamount;

                            int v= (int) res;
                            txtamount.setText("$ "+response.body().getData().get(0).getRaisedAmount());
                          //  Toast.makeText(DonationDetailActivity.this, ""+v, Toast.LENGTH_SHORT).show();
                            donation_seekbar.setProgress(v);

                            raisedAmount.setText("$ " + response.body().getData().get(0).getRaisedAmount());

                        }
                        else {
                            raisedAmount.setText("0");


                        }
                        Glide.with(getApplicationContext()).load(response.body().getData().get(0).getDonationImage()).into(donationphoto);

                    }
                }

            }

            @Override
            public void onFailure(Call<SingleDonationResponse> call, Throwable t) {

            }
        });


    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this).create();
        myQuittingDialogBox.setMessage("Are you sure you want to delete?");
        myQuittingDialogBox .setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new       DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //yes button clicked
                dialog.dismiss();
                deleteDonation();
            }
        });
        myQuittingDialogBox.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        myQuittingDialogBox.show();


        return myQuittingDialogBox;

    }
}
