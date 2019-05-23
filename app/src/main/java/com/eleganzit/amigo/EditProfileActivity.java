package com.eleganzit.amigo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.amigo.adapter.EducationsAdapter;
import com.eleganzit.amigo.adapter.WorksAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityEditProfileBinding;
import com.eleganzit.amigo.model.Datawork;
import com.eleganzit.amigo.model.Educationdata;
import com.eleganzit.amigo.model.EducationsData;
import com.eleganzit.amigo.model.GetLoginResponse;
import com.eleganzit.amigo.model.GetUserResponse;
import com.eleganzit.amigo.model.GetWorkEduResponse;
import com.eleganzit.amigo.model.GetWorkList;
import com.eleganzit.amigo.model.LoginData;
import com.eleganzit.amigo.model.WorkData;
import com.eleganzit.amigo.model.WorksData;
import com.eleganzit.amigo.model.addedu.EduResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {


    String city,hometown;
     EditText edData,edDatahome;

    private static final String TAG ="EditProfile" ;
    ActivityEditProfileBinding binding;
    String user_id;
    List<WorkData> getWorkListArrayList=new ArrayList<>();
    List<Educationdata> educationdataArrayList=new ArrayList<>();
    UserLoggedInSession userLoggedInSession;
     ProgressBar progressBar;
     ImageView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(EditProfileActivity.this,R.layout.activity_edit_profile);

        userLoggedInSession=new UserLoggedInSession(EditProfileActivity.this);


        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcWorks.setLayoutManager(layoutManager1);

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcEducations.setLayoutManager(layoutManager2);

        ArrayList<WorksData> arrayList=new ArrayList<>();
        ArrayList<EducationsData> arrayList1=new ArrayList<>();

        getWorkLoad();



        binding.adduserwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, AddWorkActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        binding.addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, AddEduActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        binding.editHometown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.add_location);
               edDatahome=dialog.findViewById(R.id.ed_location);
                edDatahome.setText(hometown);
               save=dialog.findViewById(R.id.save);
                progressBar=dialog.findViewById(R.id.progress);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (edDatahome.getText().toString()!=null  && !(edDatahome.getText().toString().isEmpty()))
                        {
                            hometown=edDatahome.getText().toString();
                            progressBar.setVisibility(View.VISIBLE);
                            save.setVisibility(View.GONE);

                            updateprofile(dialog);
                        }
                        else
                        {
                        edDatahome.requestFocus();
                        }

                    }
                });


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        binding.addHometown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.add_location);
                 edDatahome=dialog.findViewById(R.id.ed_location);

                 save=dialog.findViewById(R.id.save);
                progressBar=dialog.findViewById(R.id.progress);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edDatahome.getText().toString()!=null  && !(edDatahome.getText().toString().isEmpty()))
                        {
                            hometown=edDatahome.getText().toString();
                            progressBar.setVisibility(View.VISIBLE);
                            save.setVisibility(View.GONE);
                            updateprofile(dialog);

                        }
                        else
                        {
                            edDatahome.requestFocus();

                        }

                    }
                });


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        binding.editCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.add_location);
                edData=dialog.findViewById(R.id.ed_location);
                edData.setText(city);
                save=dialog.findViewById(R.id.save);
                 progressBar=dialog.findViewById(R.id.progress);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (edData.getText().toString()!=null  && !(edData.getText().toString().isEmpty()))
                        {
                            city=edData.getText().toString();
                            progressBar.setVisibility(View.VISIBLE);
                            save.setVisibility(View.GONE);
                            updateprofile(dialog);
                        }
                        else
                        {
                            edData.requestFocus();
                        }

                    }
                });


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        binding.relAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.add_location);
               edData=dialog.findViewById(R.id.ed_location);
                 save=dialog.findViewById(R.id.save);
                progressBar=dialog.findViewById(R.id.progress);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (edData.getText().toString()!=null  && !(edData.getText().toString().isEmpty()))
                        {
                            city=edData.getText().toString();
                            progressBar.setVisibility(View.VISIBLE);
                            save.setVisibility(View.GONE);
                            updateprofile(dialog);
                        }
                        else
                        {
                          edData.requestFocus();

                        }

                    }
                });


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        getUserData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }



    public void updateprofile(final Dialog dialog)
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<GetLoginResponse> call=myInterface.updateProfile(user_id,city,hometown);
        call.enqueue(new Callback<GetLoginResponse>() {
            @Override
            public void onResponse(Call<GetLoginResponse> call, Response<GetLoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                dialog.dismiss();
                if (response.isSuccessful())
                {
                    binding.hometown.setText("" + hometown);
                    binding.city.setText(""+city);

                  if (city!=null && !(city.isEmpty()))
                  {
                      binding.relAddCity.setVisibility(View.GONE);
                      binding.relEdCity.setVisibility(View.VISIBLE);
                  }
                   if (hometown!=null && !(hometown.isEmpty()))
                  {
                      binding.relAddHometown.setVisibility(View.GONE);
                      binding.relEditHometown.setVisibility(View.VISIBLE);
                  }



                }
            }

            @Override
            public void onFailure(Call<GetLoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                Toast.makeText(EditProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getWorkLoad()
    {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);


        Call<GetWorkEduResponse> workresponse=myInterface.getWorkLoad(user_id);
        workresponse.enqueue(new Callback<GetWorkEduResponse>() {
            @Override
            public void onResponse(Call<GetWorkEduResponse> call, Response<GetWorkEduResponse> response) {

                Log.d(TAG,""+response.body().getDatawork()+" "+user_id
                );

                if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                {
                    List<WorkData> datawork=response.body().getDatawork().getData();


                    getWorkListArrayList=datawork;
                    if (getWorkListArrayList!=null) {
                        if (getWorkListArrayList.size() > 0) {
                            binding.rcWorks.setAdapter(new WorksAdapter(getWorkListArrayList, EditProfileActivity.this));

                        }
                    }

                    List<Educationdata> list=response.body().getDataeducation().getData();

                    if (list.size()>0)
                    {
                        binding.rcEducations.setAdapter(new EducationsAdapter(list,EditProfileActivity.this));

                    }






                }
                else
                {
                    Toast.makeText(EditProfileActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetWorkEduResponse> call, Throwable t) {
                Log.d(TAG,""+t.getMessage());

            }
        });




    }

    public void getUserData() {
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<GetLoginResponse> call = myInterface.getUserData(user_id);
        call.enqueue(new Callback<GetLoginResponse>() {
            @Override
            public void onResponse(Call<GetLoginResponse> call, Response<GetLoginResponse> response) {


                List<LoginData> loginData = response.body().getData();

                if (loginData != null) {
                    for (int i = 0; i < loginData.size(); i++) {
                        LoginData loginData1 = loginData.get(i);

                        Log.d(TAG, "----" + loginData1.getCity());
                        if (loginData1.getCity() != null && !(loginData1.getCity().isEmpty())) {

                            binding.city.setText("" + loginData1.getCity());
                            city=loginData1.getCity();
                            binding.relAddCity.setVisibility(View.GONE);
                            binding.relEdCity.setVisibility(View.VISIBLE);


                        } else {
                            binding.relAddCity.setVisibility(View.VISIBLE);
                            binding.relEdCity.setVisibility(View.GONE);

                            // binding.livesinlin.setVisibility(View.GONE);
                        }

                        if (loginData1.getHometown() != null && !(loginData1.getHometown().isEmpty())) {
                            binding.hometown.setText("" + loginData1.getHometown());
                            binding.relEditHometown.setVisibility(View.VISIBLE);
                            binding.relAddHometown.setVisibility(View.GONE);
                            hometown=loginData1.getHometown();


                        } else {
                            binding.relAddHometown.setVisibility(View.VISIBLE);
                            binding.relEditHometown.setVisibility(View.GONE);

                           // binding.fromlocationlin.setVisibility(View.GONE);
                        }


                    }
                }


            }

            @Override
            public void onFailure(Call<GetLoginResponse> call, Throwable t) {

            }
        });

    }
}
