package com.eleganzit.amigo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityEditEducationBinding;
import com.eleganzit.amigo.model.Educationdata;
import com.eleganzit.amigo.model.GetWorkResponse;
import com.eleganzit.amigo.model.WorkData;
import com.eleganzit.amigo.model.addedu.AddEduResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEducationActivity extends AppCompatActivity {
Educationdata educationdata;
    ActivityEditEducationBinding binding;
    private static final String TAG ="EditEduActivity" ;
    UserLoggedInSession userLoggedInSession;


    String user_id,place,description,date_from,date_to,physical_place,edu_id;

    static final String[] Months = new String[] { "Jan", "Feb",
            "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec" };
    ProgressDialog progressDialog;

    ArrayList<String> years;
    String from,to,currentlywork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(EditEducationActivity.this,R.layout.activity_edit_education);
        edu_id=getIntent().getStringExtra("edu_id");
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userLoggedInSession=new UserLoggedInSession(EditEducationActivity.this);
educationdata= (Educationdata) getIntent().getSerializableExtra("edu");

        progressDialog=new ProgressDialog(EditEducationActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);


        if (educationdata!=null) {

            binding.edEducation.setText(educationdata.getPlace());
            binding.edDescription.setText(educationdata.getDescription());
            binding.edPhysical.setText(educationdata.getPhysicalPlace());
            edu_id=educationdata.getId();

            if (educationdata.getDateFrom()!=null && !(educationdata.getDateFrom().isEmpty())) {
                if (!(educationdata.getDateFrom().equalsIgnoreCase("0000-00-00")))
                {
                    String datefrom[]=parseDateToddMMyyyy2(educationdata.getDateFrom()).split("-");
                    binding.txtYear.setText(""+datefrom[0]);
                    binding.txtMonth.setText(""+datefrom[1]);
                    binding.txtDate.setText(""+datefrom[2]);
                }
            }


            if (educationdata.getDateTo()!=null && !(educationdata.getDateTo().isEmpty())) {
                if (!(educationdata.getDateTo().equalsIgnoreCase("0000-00-00"))) {
                    String dateto[] = parseDateToddMMyyyy2(educationdata.getDateTo()).split("-");
                    binding.txtToYear.setText("" + dateto[0]);
                    binding.txtToMonth.setText("" + dateto[1]);
                    binding.txtToDate.setText("" + dateto[2]);
                }
            }

        }

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserEdu();
            }
        });

            binding.saveedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,""+binding.txtMonth.getText().toString());


                from=binding.txtYear.getText().toString()+"-"+binding.txtMonth.getText().toString()+"-"+binding.txtDate.getText().toString();


                from=parseDateToddMMyyyy(from);





                to=binding.txtToYear.getText().toString()+"-"+binding.txtToMonth.getText().toString()+"-"+binding.txtToDate.getText().toString();
                to=parseDateToddMMyyyy(to);


                if (isValid1()) {
                    binding.saveedu.setVisibility(View.GONE);
                    binding.progress.setVisibility(View.VISIBLE);


                    addEducation();
                }



            }
        });

        binding.txtYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                years = new ArrayList<String>();
                int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = 1900; i <= thisYear; i++) {
                    years.add(Integer.toString(i));
                }
                Collections.reverse(years);
                final ListAdapter adapter = new ArrayAdapter(EditEducationActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, years);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(EditEducationActivity.this, R.style.AlertDialogCustom));
                builder.setTitle("Select Year");
                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();


                        binding.txtYear.setText(years.get(i));
                    }
                });
                builder.show();
            }
        });


        binding.txtMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditEducationActivity.this);
                builder.setTitle("Select Month");
                builder.setItems(Months, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        binding.txtMonth.setText(Months[i]);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        binding.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> days = new ArrayList<String>();
                for (int i = 1; i <= 31; i++) {
                    days.add(Integer.toString(i));
                }


                final ListAdapter adapter = new ArrayAdapter(EditEducationActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, days);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(EditEducationActivity.this, R.style.AlertDialogCustom));
                builder.setTitle("Select Date");
                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();


                        binding.txtDate.setText(days.get(i));
                    }
                });
                builder.show();
            }
        });
        binding.txtToYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                years = new ArrayList<String>();
                int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = 1900; i <= thisYear; i++) {
                    years.add(Integer.toString(i));
                }
                Collections.reverse(years);
                final ListAdapter adapter = new ArrayAdapter(EditEducationActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, years);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(EditEducationActivity.this, R.style.AlertDialogCustom));
                builder.setTitle("Select Year");
                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();


                        binding.txtToYear.setText(years.get(i));

                    }
                });
                builder.show();
            }
        });

        binding.txtToMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditEducationActivity.this);
                builder.setTitle("Select Month");
                builder.setItems(Months, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        binding.txtToMonth.setText(Months[i]);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        binding.txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> days = new ArrayList<String>();
                for (int i = 1; i <= 31; i++) {
                    days.add(Integer.toString(i));
                }


                final ListAdapter adapter = new ArrayAdapter(EditEducationActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, days);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(EditEducationActivity.this, R.style.AlertDialogCustom));
                builder.setTitle("Select Date");
                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();


                        binding.txtToDate.setText(days.get(i));
                    }
                });
                builder.show();
            }
        });

    }



    private void deleteUserEdu() {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        progressDialog.show();

        Call<AddEduResponse> userResponseCall=myInterface.deleteuserEducation(educationdata.getId());
        userResponseCall.enqueue(new Callback<AddEduResponse>() {
            @Override
            public void onResponse(Call<AddEduResponse> call, Response<AddEduResponse> response) {

                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))

                    {
                        Toast.makeText(EditEducationActivity.this, "Deleted Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditEducationActivity.this,EditProfileActivity.class));
                        finish();
                    }
                }

            }

            @Override
            public void onFailure(Call<AddEduResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditEducationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addEducation()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
progressDialog.show();

        Log.d(TAG,""+user_id+" "+binding.edEducation.getText().toString()+" "+binding.edDescription.getText().toString()+" "+from+" "+to+" "+binding.edPhysical.getText().toString());
        Call<AddEduResponse> userResponseCall=myInterface.updateuserEducation(edu_id,user_id,binding.edEducation.getText().toString(),binding.edDescription.getText().toString(),from,to,binding.edPhysical.getText().toString());

        userResponseCall.enqueue(new Callback<AddEduResponse>() {
            @Override
            public void onResponse(Call<AddEduResponse> call, Response<AddEduResponse> response) {
                binding.saveedu.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
progressDialog.dismiss();
                Toast.makeText(EditEducationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditEducationActivity.this,EditProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }

            @Override
            public void onFailure(Call<AddEduResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditEducationActivity.this, "", Toast.LENGTH_SHORT).show();
                binding.saveedu.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }
        });

    }


    public boolean isValid1() {

        if (binding.edEducation.getText().toString().equals("")) {
            binding.edEducation.setError(""+getResources().getString(R.string.Please_enter_clg));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.edEducation);

            binding.edEducation.requestFocus();
            return false;
        }



        return true;
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MMM-dd";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }




    public String parseDateToddMMyyyy2(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "yyyy-MMM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
