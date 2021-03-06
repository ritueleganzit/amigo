package com.eleganzit.amigo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.AddEventResponse;
import com.eleganzit.amigo.model.donation.AddDonationResponse;
import com.eleganzit.amigo.model.donation.SingleDonationResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDonationActivity extends AppCompatActivity {
    private ArrayList<String> mSelectPath;
    File file;
    UserLoggedInSession userLoggedInSession;

    LinearLayout create_donation,progress;
    RecyclerView rc_donations;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_IMAGE = 2;
    ImageView donation_image;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int endmYear, endmMonth, endmDay;
    EditText donation_title,donation_start_date,donation_end_date,donation_description,need_amount;
    String mediapath = "", user_id = "",donation_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_donation);
        ImageView back=findViewById(R.id.back);
        donation_image=findViewById(R.id.donation_image);
        progress=findViewById(R.id.progress);
        userLoggedInSession = new UserLoggedInSession(EditDonationActivity.this);

        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        donation_id=getIntent().getStringExtra("donation_id");
        create_donation=findViewById(R.id.create_donation);
        donation_title=findViewById(R.id.donation_title);
        donation_start_date=findViewById(R.id.donation_start_date);
        donation_end_date=findViewById(R.id.donation_end_date);
        donation_description=findViewById(R.id.donation_description);
        need_amount=findViewById(R.id.need_amount);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        donation_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        create_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid1())
                {
                    if (file!=null) {
                        create_donation.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        updateDonationwithImage();
                    }
                    else {
                        create_donation.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        updateDonationWithoutImage();
                        //Toast.makeText(EditDonationActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        donation_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDonationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                donation_start_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


            }
        });
        donation_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                endmYear = c.get(Calendar.YEAR);
                endmMonth = c.get(Calendar.MONTH);
                endmDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDonationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                donation_end_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, endmYear, endmMonth, endmDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


            }
        });
        getSingleDonation();
        
    }



    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(EditDonationActivity.this);
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath);
            selector.start(EditDonationActivity.this, REQUEST_IMAGE);
        }
    }
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(EditDonationActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                }

                mediapath = "" + sb.toString();
                file = new File(""+mediapath.trim());

                Glide.with(EditDonationActivity.this)
                        .load("" + mediapath.trim())

                        .into(donation_image);
                Log.d("sdadad", "" + mediapath);
            }


        }
    }

    public boolean isValid1() {

        if (donation_title.getText().toString().equals("")) {
            donation_title.setError("" + getResources().getString(R.string.Please_enter_title));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(donation_title);

            donation_title.requestFocus();
            return false;
        } else if (donation_start_date.getText().toString().equals("")) {
            // binding.edDate.setError("" + getResources().getString(R.string.Please_select_date));
            Toast.makeText(this, ""+getResources().getString(R.string.Please_select_date), Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(donation_start_date);

            donation_start_date.requestFocus();
            return false;
        } else if (donation_end_date.getText().toString().equals("")) {
            // binding.edDate.setError("" + getResources().getString(R.string.Please_select_date));
            Toast.makeText(this, ""+getResources().getString(R.string.Please_select_end_date), Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(donation_end_date);

            donation_end_date.requestFocus();
            return false;
        } else if (donation_description.getText().toString().equals("")) {
            donation_description.setError("" + getResources().getString(R.string.Please_enter_donation_details));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(donation_description);

            donation_description.requestFocus();
            return false;
        }
        else if (need_amount.getText().toString().equals("")) {
            need_amount.setError("" + getResources().getString(R.string.Please_enter_amount));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(need_amount);

            need_amount.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

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
                        donation_title.setText(""+response.body().getData().get(0).getTitle());

                        donation_start_date.setText(""+response.body().getData().get(0).getDonationStartDate());
                        donation_end_date.setText(""+response.body().getData().get(0).getDonationEndDate());
                        donation_description.setText(""+response.body().getData().get(0).getDescription());
                       // totaldonars.setText(""+response.body().getData().get(0).getCountDonors());
                        need_amount.setText(""+response.body().getData().get(0).getNeedAmount());
                       // need_amount.setText("$ "+response.body().getData().get(0).getNeedAmount());

                        Glide.with(getApplicationContext()).load(response.body().getData().get(0).getDonationImage()).into(donation_image);

                    }
                }

            }

            @Override
            public void onFailure(Call<SingleDonationResponse> call, Throwable t) {

            }
        });


    }

    private void updateDonationwithImage()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("donation_image",file.getName(),requestFile);

        RequestBody donationid = RequestBody.create(MediaType.parse("text/plain"), ""+donation_id);
        RequestBody donationstart = RequestBody.create(MediaType.parse("text/plain"), ""+donation_start_date.getText().toString());
        RequestBody donationend = RequestBody.create(MediaType.parse("text/plain"), ""+donation_end_date.getText().toString());
        RequestBody  title= RequestBody.create(MediaType.parse("text/plain"), ""+donation_title.getText().toString());
        RequestBody details = RequestBody.create(MediaType.parse("text/plain"), ""+donation_description.getText().toString());
        RequestBody amount = RequestBody.create(MediaType.parse("text/plain"), ""+need_amount.getText().toString());
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "active");
        Call<AddDonationResponse> call=myInterface.updateDonation(donationid,title,amount,details,donationstart,donationend,status,multipartBody);
        call.enqueue(new Callback<AddDonationResponse>() {
            @Override
            public void onResponse(Call<AddDonationResponse> call, Response<AddDonationResponse> response) {
                create_donation.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    Toast.makeText(EditDonationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(EditDonationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddDonationResponse> call, Throwable t) {
                create_donation.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                Toast.makeText(EditDonationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void updateDonationWithoutImage()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AddDonationResponse> call=myInterface.updateDonationWithoutImage(donation_id,""+donation_title.getText().toString(),""+need_amount.getText().toString(),""+donation_description.getText().toString(),""+donation_start_date.getText().toString(),""+donation_end_date.getText().toString(),"active");
        call.enqueue(new Callback<AddDonationResponse>() {
            @Override
            public void onResponse(Call<AddDonationResponse> call, Response<AddDonationResponse> response) {
                create_donation.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    Toast.makeText(EditDonationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(EditDonationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddDonationResponse> call, Throwable t) {

                create_donation.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                Toast.makeText(EditDonationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
