package com.eleganzit.amigo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityCreateOpportunityBinding;
import com.eleganzit.amigo.databinding.ActivityEditOpportunityBinding;
import com.eleganzit.amigo.model.GetSingleOpportunityResponse;
import com.eleganzit.amigo.model.opportunity.OpportuntyResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditOpportunity extends AppCompatActivity {

    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_IMAGE = 2;
    String mediapath = "", user_id = "",opportunity_id="",lat="",lng="";
    UserLoggedInSession userLoggedInSession;
    ActivityEditOpportunityBinding binding;
    private ArrayList<String> mSelectPath;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String TAG="OpportunityCreate";
    File file;
    ProgressDialog progressDialog;
    private static final int PLACE_PICKER_REQUEST2 = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLoggedInSession = new UserLoggedInSession(EditOpportunity.this);
        opportunity_id=getIntent().getStringExtra("opportunity_id");
        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        binding = DataBindingUtil.setContentView(EditOpportunity.this, R.layout.activity_edit_opportunity);
        binding.linAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        progressDialog=new ProgressDialog(EditOpportunity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.edEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edDate.getText().toString()!=null && !(binding.edDate.getText().toString().isEmpty())) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(EditOpportunity.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    String compareStringOne = binding.edDate.getText().toString() + " " + hourOfDay + ":" + minute;


                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                    Date strDate = null;
                                    try {
                                        strDate = formatter.parse(compareStringOne);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("timeessss",""+new Date().getTime() +"     "+ strDate.getTime());
                                    if (new Date().getTime() > strDate.getTime()) {
                                        binding.edEventTime.setText("");
                                        Toast.makeText(EditOpportunity.this, "Please select the future hours", Toast.LENGTH_SHORT).show();
                                    }else {
                                        binding.edEventTime.setText(hourOfDay + ":" + minute);

                                    }
                                    //binding.edEventTime.setText(hourOfDay + ":" + minute);

                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
                else
                {
                    Toast.makeText(EditOpportunity.this, "Please Select Date", Toast.LENGTH_SHORT).show();

                }

            }
        });

        binding.edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditOpportunity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.edEventTime.setText("");

                                binding.edDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            }
        });

        binding.createOpportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isValid1()) {
                    binding.createOpportunity.setVisibility(View.GONE);
                    binding.progress.setVisibility(View.VISIBLE);

                    if (file!=null)
                    {
                        addOpportunityImage();
                    }
                    else
                    {
                        addOpportunity();
                    }

                }
            }
        });

        binding.selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.selectLocation.setEnabled(false);
                Intent intent = null;
                try {
                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(EditOpportunity.this);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, PLACE_PICKER_REQUEST2);


            }
        });
        getSingleData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getSingleData()
    {
        progressDialog.show();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<GetSingleOpportunityResponse> getOpportunitiesResponseCall=myInterface.getsingleOpportunity(opportunity_id);
        getOpportunitiesResponseCall.enqueue(new Callback<GetSingleOpportunityResponse>() {
            @Override
            public void onResponse(Call<GetSingleOpportunityResponse> call, Response<GetSingleOpportunityResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getData()!=null)
                        {
                            binding.edDate.setText(response.body().getData().get(0).getOpportunityDate());
                            //binding.rejectcount.setText(response.body().getData().get(0).getRejectcount());
                            binding.edEventAddress.setText(response.body().getData().get(0).getAddress());
                            binding.edEventTime.setText(response.body().getData().get(0).getOpportunityTime());
                            binding.edPositions.setText(""+response.body().getData().get(0).getPositions());
                            binding.edLookingfor.setText(""+response.body().getData().get(0).getLookingFor());
                            binding.edEventDetais.setText(""+response.body().getData().get(0).getDetails());
                            if (response.body().getData().get(0).getOpportunity_name()!=null && !(response.body().getData().get(0).getOpportunity_name().isEmpty()))

                            {
                                binding.edEventName.setText(""+response.body().getData().get(0).getOpportunity_name());
                            }

Glide.with(EditOpportunity.this).load(response.body().getData().get(0).getOpportunityImage()).into(binding.imageOpportunity);


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSingleOpportunityResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditOpportunity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(EditOpportunity.this);
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath);
            selector.start(EditOpportunity.this, REQUEST_IMAGE);
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
                            ActivityCompat.requestPermissions(EditOpportunity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
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

                Glide.with(EditOpportunity.this)
                        .load("" + mediapath.trim())

                        .into(binding.imageOpportunity);
                Log.d("sdadad", "" + mediapath);
            }


        }

        if (requestCode == PLACE_PICKER_REQUEST2) {
            binding.selectLocation.setEnabled(true);
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("%s", place.getName());
                LatLng latLng=place.getLatLng();
                lat=""+latLng.latitude;
                lng=""+latLng.longitude;
                Log.d(TAG,""+latLng.latitude);
                Log.d(TAG,""+latLng.longitude);
                if (toastMsg.equalsIgnoreCase(""))
                {

                }
                else {
                    binding.edEventAddress.setText(""+toastMsg);

                }



            }
            else {

                //Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
            }
        }




    }

    public boolean isValid1() {

        if (binding.edEventName.getText().toString().equals("")) {
            binding.edEventName.setError("" + getResources().getString(R.string.Please_enter_opportunity_name));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEventName);

            binding.edEventName.requestFocus();
            return false;
        }
        else if (binding.edDate.getText().toString().equals("")) {
            Toast.makeText(this, ""+getResources().getString(R.string.Please_select_date), Toast.LENGTH_SHORT).show();

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edDate);

            binding.edDate.requestFocus();
            return false;
        }else if (binding.edEventTime.getText().toString().equals("")) {
            Toast.makeText(this, ""+getResources().getString(R.string.Please_select_time), Toast.LENGTH_SHORT).show();

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEventTime);

            binding.edEventTime.requestFocus();
            return false;
        }

        else if (binding.edEventAddress.getText().toString().equals("")) {
            binding.edEventAddress.setError("" + getResources().getString(R.string.Please_enter_event_address));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEventAddress);

            binding.edEventAddress.requestFocus();
            return false;
        }

        else if (binding.edEventDetais.getText().toString().equals("")) {
            binding.edEventDetais.setError("" + getResources().getString(R.string.Please_enter_event_details));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEventDetais);

            binding.edEventDetais.requestFocus();
            return false;
        }

        else if (binding.edPositions.getText().toString().equals("")) {
            binding.edPositions.setError("" + getResources().getString(R.string.Please_enter_event_positions));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edPositions);

            binding.edPositions.requestFocus();
            return false;
        }
        else if (binding.edLookingfor.getText().toString().equals("")) {
            binding.edLookingfor.setError("" + getResources().getString(R.string.Please_enter_event_lookingfor));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edLookingfor);

            binding.edLookingfor.requestFocus();
            return false;
        }


        return true;
    }


    public void addOpportunity()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<OpportuntyResponse> opportuntyResponseCall=myInterface.updateOpportunity(opportunity_id,
                binding.edEventName.getText().toString(),
                binding.edDate.getText().toString(),
                user_id,
                binding.edEventTime.getText().toString(),
                binding.edEventAddress.getText().toString(),
                binding.edEventDetais.getText().toString(),
                binding.edPositions.getText().toString(),
                binding.edLookingfor.getText().toString()
                ,lat,lng);

        opportuntyResponseCall.enqueue(new Callback<OpportuntyResponse>() {
            @Override
            public void onResponse(Call<OpportuntyResponse> call, Response<OpportuntyResponse> response) {
                binding.createOpportunity.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);

                if (response.isSuccessful())
                {
                    Toast.makeText(EditOpportunity.this, "Success", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(EditOpportunity.this,OpportunitiesActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<OpportuntyResponse> call, Throwable t) {
                binding.createOpportunity.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    public void addOpportunityImage()

    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        //Log.d(TAG,file+" "+user_id+" "+opportunity_date+" "+opportunity_time+" "+binding.edPositions.getText().toString()+" "+binding.edEventAddress.getText().toString()+" "+binding.edEventDetais.getText().toString()+" "+ binding.edLookingfor.getText().toString());
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("opportunity_image",file.getName(),requestFile);

        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), ""+user_id);
        RequestBody opid = RequestBody.create(MediaType.parse("text/plain"), ""+opportunity_id);
        RequestBody opname = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edEventName.getText().toString());
        RequestBody opportunitydate = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edDate.getText().toString());
        RequestBody opportunitytime = RequestBody.create(MediaType.parse("text/plain"),""+binding.edEventTime.getText().toString() );
        RequestBody  addres= RequestBody.create(MediaType.parse("text/plain"), ""+binding.edEventAddress.getText().toString());
        RequestBody details = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edEventDetais.getText().toString());
        RequestBody positions = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edPositions.getText().toString());
        RequestBody looking_for = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edLookingfor.getText().toString());
        RequestBody rlat = RequestBody.create(MediaType.parse("text/plain"), ""+lat);
        RequestBody rlng = RequestBody.create(MediaType.parse("text/plain"), ""+lng);

        Call<OpportuntyResponse> opportuntyResponseCall=myInterface.updatewithOpportunity(opid,opname,opportunitydate,userid,multipartBody,opportunitytime,addres,details,positions,looking_for,rlat,rlng);
        opportuntyResponseCall.enqueue(new Callback<OpportuntyResponse>() {
            @Override
            public void onResponse(Call<OpportuntyResponse> call, Response<OpportuntyResponse> response) {
                binding.createOpportunity.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);

                if (response.isSuccessful())
                {
                    Toast.makeText(EditOpportunity.this, "Success", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(EditOpportunity.this,OpportunitiesActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<OpportuntyResponse> call, Throwable t) {
                binding.createOpportunity.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                Log.d(TAG,"error"+t.getMessage());
            }
        });


    }
}
