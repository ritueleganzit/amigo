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
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityAddWorkActiviryBinding;
import com.eleganzit.amigo.model.AddWorkResponse;
import com.eleganzit.amigo.model.GetUserResponse;
import com.eleganzit.amigo.model.GetWorkResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWorkActivity extends AppCompatActivity {

    private static final String TAG ="AddWorkActivity" ;
    UserLoggedInSession userLoggedInSession;
    static final String[] Months = new String[] { "Jan", "Feb",
            "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec" };


    ArrayList<String> years;


    String from,to,currentlywork="no";

    ProgressDialog progressDialog;

    String user_id,place_name,position,place,description,date_from,work_here,date_to;
ActivityAddWorkActiviryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                userLoggedInSession=new UserLoggedInSession(AddWorkActivity.this);
                
        binding= DataBindingUtil.setContentView(AddWorkActivity.this,R.layout.activity_add_work_activiry);
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);
        progressDialog=new ProgressDialog(AddWorkActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.selectHomeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.present.setVisibility(View.VISIBLE);
                    binding.past.setVisibility(View.GONE);
                    currentlywork="yes";
                    to="";
                }
                else
                {
                    currentlywork="no";

                    binding.present.setVisibility(View.GONE);
                    binding.past.setVisibility(View.VISIBLE);
                }
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
                final ListAdapter adapter = new ArrayAdapter(AddWorkActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, years);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddWorkActivity.this, R.style.AlertDialogCustom));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddWorkActivity.this);
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


                final ListAdapter adapter = new ArrayAdapter(AddWorkActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, days);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddWorkActivity.this, R.style.AlertDialogCustom));
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



        binding.txtYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                years = new ArrayList<String>();
                int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = 1900; i <= thisYear; i++) {
                    years.add(Integer.toString(i));
                }
                Collections.reverse(years);
                final ListAdapter adapter = new ArrayAdapter(AddWorkActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, years);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddWorkActivity.this, R.style.AlertDialogCustom));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddWorkActivity.this);
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


                final ListAdapter adapter = new ArrayAdapter(AddWorkActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, days);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddWorkActivity.this, R.style.AlertDialogCustom));
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

        binding.savework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                from=binding.txtYear.getText().toString()+"-"+binding.txtMonth.getText().toString()+"-"+binding.txtDate.getText().toString();
                to=binding.txtToYear.getText().toString()+"-"+binding.txtToMonth.getText().toString()+"-"+binding.txtToDate.getText().toString();

                from=parseDateToddMMyyyy(from);
                to=parseDateToddMMyyyy(to);








                if (binding.checkboxHometown.isEnabled())
                {
                    to="";
                }



                if(binding.edWork.getText().toString().isEmpty())
                {
                    binding.edWork.setError("Where did you work?");
                    binding.edWork.requestFocus();
                }
                else if(binding.edPosition.getText().toString().isEmpty())
                {
                    binding.edPosition.setError("Please enter position");
                    binding.edPosition.requestFocus();
                }
                else if(binding.edCity.getText().toString().isEmpty())
                {
                    binding.edCity.setError("Please enter city");
                    binding.edCity.requestFocus();
                }
                else if(binding.edPhysical.getText().toString().isEmpty())
                {
                    binding.edPhysical.setError("Is it physical place?");
                    binding.edPhysical.requestFocus();
                }
                else if(binding.edDescription.getText().toString().isEmpty())
                {
                    binding.edDescription.setError("Please enter description");
                    binding.edDescription.requestFocus();
                }
                else if(binding.txtYear.getText().toString().equalsIgnoreCase("year"))
                {
                    Toast.makeText(AddWorkActivity.this, "Please select From year", Toast.LENGTH_SHORT).show();
                }
                else if(binding.txtMonth.getText().toString().equalsIgnoreCase("month"))
                {
                    Toast.makeText(AddWorkActivity.this, "Please select From month", Toast.LENGTH_SHORT).show();
                }
                else if(binding.txtDate.getText().toString().equalsIgnoreCase("date"))
                {
                    Toast.makeText(AddWorkActivity.this, "Please select From date", Toast.LENGTH_SHORT).show();
                }
                else if(binding.txtToYear.getText().toString().equalsIgnoreCase("year"))
                {
                    Toast.makeText(AddWorkActivity.this, "Please select To year", Toast.LENGTH_SHORT).show();
                }
                else if(binding.txtToMonth.getText().toString().equalsIgnoreCase("month"))
                {
                    Toast.makeText(AddWorkActivity.this, "Please select To month", Toast.LENGTH_SHORT).show();
                }
                else if(binding.txtToDate.getText().toString().equalsIgnoreCase("date"))
                {
                    Toast.makeText(AddWorkActivity.this, "Please select To date", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    binding.savework.setVisibility(View.GONE);
                    binding.progress.setVisibility(View.VISIBLE);
                    addWorkData();

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    public void addWorkData()
    {

        progressDialog.show();
        Log.d(TAG,"--->"+to);
        Log.d(TAG,""+currentlywork);

        Log.d(TAG,""+user_id+" "+binding.edWork.getText().toString()+" "+binding.edPosition.getText().toString()+" "+binding.edCity.getText().toString()+" "+binding.edPhysical.getText().toString()+" "+binding.edDescription.getText().toString()+" "+from+" "+to+currentlywork);


        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);


      Call<GetWorkResponse> userResponseCall=myInterface.addWorkData(user_id,binding.edWork.getText().toString(),binding.edPosition.getText().toString(),binding.edCity.getText().toString(),binding.edPhysical.getText().toString(),binding.edDescription.getText().toString(),from,to,currentlywork);

      userResponseCall.enqueue(new Callback<GetWorkResponse>() {
          @Override
          public void onResponse(Call<GetWorkResponse> call, Response<GetWorkResponse> response) {
progressDialog.dismiss();
              binding.savework.setVisibility(View.VISIBLE);
              binding.progress.setVisibility(View.GONE);
            if (  response.body().getStatus().toString().equalsIgnoreCase("1"))
            {

                Log.d(TAG,""+response.body().getData());
                List<AddWorkResponse> addWorkResponse=response.body().getData();
                startActivity(new Intent(AddWorkActivity.this, EditProfileActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();

            }
            else
            {
                Toast.makeText(AddWorkActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

            }






          }

          @Override
          public void onFailure(Call<GetWorkResponse> call, Throwable t) {
              binding.savework.setVisibility(View.VISIBLE);
              progressDialog.dismiss();
              binding.progress.setVisibility(View.GONE);
              Toast.makeText(AddWorkActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

          }
      });
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
