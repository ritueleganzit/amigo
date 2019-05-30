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
import com.eleganzit.amigo.databinding.ActivityEditEventBinding;
import com.eleganzit.amigo.model.AddEvent;
import com.eleganzit.amigo.model.AddEventResponse;
import com.eleganzit.amigo.model.GetSingleEventResponse;
import com.eleganzit.amigo.model.SingleEvent;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
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

public class EditEventActivity extends AppCompatActivity {

ActivityEditEventBinding binding;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_IMAGE = 2;
    String mediapath = "", user_id = "",lat="",lng="";
    UserLoggedInSession userLoggedInSession;
    private ArrayList<String> mSelectPath;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String TAG="EditEventActivity",event_id;
    File file;
    private static final int PLACE_PICKER_REQUEST2 = 1001;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding= DataBindingUtil.setContentView(EditEventActivity.this,R.layout.activity_edit_event);
        event_id=  getIntent().getStringExtra("event_id");
        progressDialog=new ProgressDialog(EditEventActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        userLoggedInSession = new UserLoggedInSession(EditEventActivity.this);

        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);

        binding.linAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });


        binding.edEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String compareStringOne = binding.edDate.getText().toString() + " " + hourOfDay + ":" + minute;
                                String pick_mytime = "";

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
                                    Toast.makeText(EditEventActivity.this, "Please select the future hours", Toast.LENGTH_SHORT).show();
                                }else {
                                    if(minute<10)
                                    {
                                        pick_mytime=hourOfDay+":0"+minute+":00";
                                    }
                                    else
                                    {
                                        pick_mytime=hourOfDay+":"+minute+":00";
                                    }
                                    binding.edEventTime.setText(pick_mytime);


                                }
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
        binding.selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.selectLocation.setEnabled(false);
                Intent intent = null;
                try {
                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(EditEventActivity.this);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, PLACE_PICKER_REQUEST2);


            }
        });

        binding.edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditEventActivity.this,
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
                    if (file!=null) {



                        binding.createOpportunity.setVisibility(View.GONE);
                        binding.progress.setVisibility(View.VISIBLE);
                        updateEventpostwithimage();

                    }
                    else {
                        binding.createOpportunity.setVisibility(View.GONE);
                        binding.progress.setVisibility(View.VISIBLE);
                        updateEventpost();

                       // Toast.makeText(EditEventActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getsingleEvent();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void updateEventpost() {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Log.d(TAG,""+binding.edDate.getText().toString());
        Log.d(TAG,""+event_id);
        Log.d(TAG,""+user_id);

        Call<AddEventResponse> call=myInterface.updateEventpost(binding.edDate.getText().toString(),event_id,user_id,binding.edEventTime.getText().toString(),binding.edEventAddress.getText().toString(),binding.edEventDetais.getText().toString(),binding.edCoHost.getText().toString(),"",binding.edEventName.getText().toString(),lat,lng);
        call.enqueue(new Callback<AddEventResponse>() {
            @Override
            public void onResponse(Call<AddEventResponse> call, Response<AddEventResponse> response) {
                binding.createOpportunity.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        Toast.makeText(EditEventActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(EditEventActivity.this,EventProfileActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                    else
                    {
                        //Toast.makeText(EditEventActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddEventResponse> call, Throwable t) {
                binding.createOpportunity.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(EditEventActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getsingleEvent(){
progressDialog.show();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<GetSingleEventResponse> getSingleEventResponseCall=myInterface.getsingleEvent(event_id);
        getSingleEventResponseCall.enqueue(new Callback<GetSingleEventResponse>() {
            @Override
            public void onResponse(Call<GetSingleEventResponse> call, Response<GetSingleEventResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {

                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            SingleEvent singleEvent=response.body().getData().get(i);

                            Glide.with(EditEventActivity.this)
                                    .load(singleEvent.getEventPhoto())
                                    .into(binding.imageOpportunity);

                            binding.edEventName.setText(singleEvent.getEventName());
                            binding.edDate.setText(singleEvent.getEventDate());
                            binding.edEventTime.setText(singleEvent.getEventTime());
                            binding.edEventAddress.setText(singleEvent.getEventAddress());
                            binding.edCoHost.setText(singleEvent.getCoHost());
                            binding.edEventDetais.setText(singleEvent.getEventDetails());

                            lat=singleEvent.getLat();
                            lng=singleEvent.getLng();

                        }
                    }


                }

            }

            @Override
            public void onFailure(Call<GetSingleEventResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditEventActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateEventpostwithimage() {

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("event_photo",file.getName(),requestFile);

        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), ""+user_id);
        RequestBody eventid = RequestBody.create(MediaType.parse("text/plain"), ""+event_id);
        RequestBody eventdate = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edDate.getText().toString());
        RequestBody eventname = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edEventName.getText().toString());
        RequestBody eventime = RequestBody.create(MediaType.parse("text/plain"),""+binding.edEventTime.getText().toString() );
        RequestBody  addres= RequestBody.create(MediaType.parse("text/plain"), ""+binding.edEventAddress.getText().toString());
        RequestBody details = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edEventDetais.getText().toString());
        RequestBody host = RequestBody.create(MediaType.parse("text/plain"), ""+binding.edCoHost.getText().toString());
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "active");
        RequestBody rlat = RequestBody.create(MediaType.parse("text/plain"), ""+lat);
        RequestBody rlng = RequestBody.create(MediaType.parse("text/plain"), ""+lng);


        Call<AddEventResponse> call=myInterface.updateEventpostwithimage(eventdate,eventid,userid,multipartBody,eventime,addres,details,host,status,eventname,rlat,rlng);
        call.enqueue(new Callback<AddEventResponse>() {
            @Override
            public void onResponse(Call<AddEventResponse> call, Response<AddEventResponse> response) {
                binding.createOpportunity.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        Toast.makeText(EditEventActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(EditEventActivity.this,EventProfileActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(EditEventActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    Toast.makeText(EditEventActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddEventResponse> call, Throwable t) {
                binding.createOpportunity.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(EditEventActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
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

            MultiImageSelector selector = MultiImageSelector.create(EditEventActivity.this);
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath);
            selector.start(EditEventActivity.this, REQUEST_IMAGE);
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
                            ActivityCompat.requestPermissions(EditEventActivity.this, new String[]{permission}, requestCode);
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

                Glide.with(EditEventActivity.this)
                        .load("" + mediapath.trim())

                        .into(binding.imageOpportunity);
                Log.d("sdadad", "" + mediapath);
            }


        }
        if (requestCode == PLACE_PICKER_REQUEST2) {
            if (resultCode == RESULT_OK) {
                binding.selectLocation.setEnabled(true);

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

             //   Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public boolean isValid1() {

        if (binding.edEventName.getText().toString().equals("")) {
            binding.edEventName.setError("" + getResources().getString(R.string.Please_enter_event_name));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEventName);

            binding.edEventName.requestFocus();
            return false;
        } else if (binding.edDate.getText().toString().equals("")) {
//            binding.edDate.setError("" + getResources().getString(R.string.Please_select_date));


            Toast.makeText(this, ""+getResources().getString(R.string.Please_select_date), Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edDate);

            binding.edDate.requestFocus();
            return false;
        }else if (binding.edEventTime.getText().toString().equals("")) {
            Toast.makeText(this, ""+getResources().getString(R.string.Please_select_time), Toast.LENGTH_SHORT).show();

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEventTime);

            binding.edDate.requestFocus();
            return false;
        } else if (binding.edEventAddress.getText().toString().equals("")) {
            binding.edEventAddress.setError("" + getResources().getString(R.string.Please_enter_event_address));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEventAddress);

            binding.edEventAddress.requestFocus();
            return false;
        } else if (binding.edEventDetais.getText().toString().equals("")) {
            binding.edEventDetais.setError("" + getResources().getString(R.string.Please_enter_event_details));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEventDetais);

            binding.edEventDetais.requestFocus();
            return false;
        } else if (binding.edCoHost.getText().toString().equals("")) {
            binding.edCoHost.setError("" + getResources().getString(R.string.Please_enter_co_host));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edCoHost);

            binding.edCoHost.requestFocus();
            return false;
        }


        return true;
    }

}
