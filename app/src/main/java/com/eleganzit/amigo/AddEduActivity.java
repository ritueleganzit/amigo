package com.eleganzit.amigo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityAddEduBinding;
import com.eleganzit.amigo.model.AddWorkResponse;
import com.eleganzit.amigo.model.GetWorkResponse;
import com.eleganzit.amigo.model.addedu.AddEduResponse;
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
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEduActivity extends AppCompatActivity {


    ActivityAddEduBinding binding;
    private static final String TAG ="AddEduActivity" ;
    UserLoggedInSession userLoggedInSession;


    String user_id,place,description,date_from,date_to,physical_place;

    static final String[] Months = new String[] { "Jan", "Feb",
            "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec" };


    ArrayList<String> years;
    String from,to,currentlywork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(AddEduActivity.this,R.layout.activity_add_edu);
        userLoggedInSession=new UserLoggedInSession(AddEduActivity.this);

        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);

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
                final ListAdapter adapter = new ArrayAdapter(AddEduActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, years);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddEduActivity.this, R.style.AlertDialogCustom));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEduActivity.this);
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


                final ListAdapter adapter = new ArrayAdapter(AddEduActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, days);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddEduActivity.this, R.style.AlertDialogCustom));
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
                final ListAdapter adapter = new ArrayAdapter(AddEduActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, years);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddEduActivity.this, R.style.AlertDialogCustom));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEduActivity.this);
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


                final ListAdapter adapter = new ArrayAdapter(AddEduActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, days);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddEduActivity.this, R.style.AlertDialogCustom));
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


    public void addEducation()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);


        Log.d(TAG,""+user_id+" "+binding.edEducation.getText().toString()+" "+binding.edDescription.getText().toString()+" "+from+" "+to+" "+binding.edPhysical.getText().toString());
        Call<AddEduResponse> userResponseCall=myInterface.addEducation(user_id,binding.edEducation.getText().toString(),binding.edDescription.getText().toString(),from,to,binding.edPhysical.getText().toString());

        userResponseCall.enqueue(new Callback<AddEduResponse>() {
            @Override
            public void onResponse(Call<AddEduResponse> call, Response<AddEduResponse> response) {
                binding.saveedu.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);

                Toast.makeText(AddEduActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddEduActivity.this,EditProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }

            @Override
            public void onFailure(Call<AddEduResponse> call, Throwable t) {

                Toast.makeText(AddEduActivity.this, "", Toast.LENGTH_SHORT).show();
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

}
