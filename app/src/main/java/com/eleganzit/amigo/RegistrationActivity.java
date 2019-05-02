package com.eleganzit.amigo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.api.ApiUtil;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityRegistrationBinding;
import com.eleganzit.amigo.model.GetStateResponse;
import com.eleganzit.amigo.model.GetUserResponse;
import com.eleganzit.amigo.model.StateData;
import com.eleganzit.amigo.model.UserResponse;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.NormalFile;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

public class RegistrationActivity extends AppCompatActivity {

List<String> stateArrayList=new ArrayList();
    private static final int FILE_REQUEST_CODE =101 ;
    ProgressDialog progressDialog;
    private static final String TAG ="RegistrationActivity" ;
    ActivityRegistrationBinding binding;
    String email="",fullname="",mobile="",username="",password="",city="",state="",pincode="",contact_person="",contact_person_number="",proof="",lat="",lng="",device_id="android",device_token="",mediapath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(RegistrationActivity.this,R.layout.activity_registration);
progressDialog=new ProgressDialog(RegistrationActivity.this);
progressDialog.setMessage("Please Wait");
progressDialog.setCancelable(false);
progressDialog.setCanceledOnTouchOutside(false);



        binding.fab1.next1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (isValid1())
                {
                    email=binding.registerInput1.edEmail.getText().toString();
                    username=binding.registerInput1.edUsername.getText().toString();

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

        binding.registerInput5.edNameofstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListAdapter adapter = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, stateArrayList);

                final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(RegistrationActivity.this, R.style.AlertDialogCustom));

                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();


                        binding.registerInput5.edNameofstate.setText(stateArrayList.get(i));
                    }
                });
                builder.show();
            }
        });

        binding.fab2.next2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (isValid2()) {

                    mobile=binding.registerInput2.edMobile.getText().toString();
                    fullname=binding.registerInput2.edName.getText().toString();
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
            }
        });
        binding.registerInput6.edDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(RegistrationActivity.this);
                dialog.setContentView(R.layout.chooser_dialog);
                final TextView image=dialog.findViewById(R.id.select_image);
                final TextView file=dialog.findViewById(R.id.select_file);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openImageChooser();
                        dialog.dismiss();
                    }
                });

                file.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openFileChooser();
                        dialog.dismiss();
                    }
                });


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        binding.fab3.next3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                if (isValid3())
                {
                    password=binding.registerInput3.edPassword.getText().toString();
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

            }
        });

        binding.fab4.next4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (isValid4()) {
                    contact_person=binding.registerInput4.edSecContact.getText().toString();
                    contact_person_number=binding.registerInput4.edState.getText().toString();
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
                    progressDialog.show();
                    getStateList();
                }
            }
        });

        binding.fab5.next5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (isValid5())

                {
                    city=binding.registerInput5.edLocation.getText().toString();
                    state=binding.registerInput5.edNameofstate.getText().toString();
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
                    binding.fab6.fabProgressCircle.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.fab6.next6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (isValid6())

{

    pincode=binding.registerInput6.edPincode.getText().toString();


    registerUser();

}


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

    private void getStateList() {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<GetStateResponse> call=myInterface.getAllstate("38");
        call.enqueue(new Callback<GetStateResponse>() {
            @Override
            public void onResponse(Call<GetStateResponse> call, Response<GetStateResponse> response) {
                if (response.isSuccessful())
                {
                    for (int i=0;i<response.body().getData().size();i++)
                    {
                        stateArrayList.add(response.body().getData().get(i).getName());
                    }

                    progressDialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<GetStateResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void openFileChooser() {

        Intent intent4 = new Intent(this, NormalFilePickActivity.class);
        intent4.putExtra(Constant.MAX_NUMBER, 1);
        intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"png", "jpg", "jpeg", "pdf"});
        startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);

    }

    public void openImageChooser() {

        Intent intent1 = new Intent(this, ImagePickActivity.class);
        intent1.putExtra(IS_NEED_CAMERA, true);
        intent1.putExtra(Constant.MAX_NUMBER, 1);
        startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);

    }
    private void registerUser() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.fab6.next6.setImageDrawable(null);
        } else {
            binding.fab6.next6.setImageDrawable(null);
        }
        binding.fab6.fabProgressCircle.show();
        File file = new File(""+mediapath);
        Log.d("hhhhh",""+mediapath);

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Log.d("dataaaaaaaaa",email+"   "+username+"   "+fullname+"   "+mobile+"   "+password+"   "+city+"   "+state+"   "+pincode+"   "+mediapath);

        RequestBody user_type = RequestBody.create(MediaType.parse("text/plain"), "2");
        RequestBody email_id = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody usernamee = RequestBody.create(MediaType.parse("text/plain"),username );
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), fullname);
        RequestBody mobilee = RequestBody.create(MediaType.parse("text/plain"), mobile);
        RequestBody passwordd = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody cityy = RequestBody.create(MediaType.parse("text/plain"), city);
        RequestBody statee = RequestBody.create(MediaType.parse("text/plain"), state);
        RequestBody pin_codee = RequestBody.create(MediaType.parse("text/plain"), pincode);
        RequestBody con = RequestBody.create(MediaType.parse("text/plain"), contact_person);
        RequestBody con_p_num = RequestBody.create(MediaType.parse("text/plain"), contact_person_number);
        RequestBody device_idd = RequestBody.create(MediaType.parse("text/plain"), "android");
        RequestBody device_tokenn = RequestBody.create(MediaType.parse("text/plain"), ""+device_token);
        RequestBody latt = RequestBody.create(MediaType.parse("text/plain"), "23.2323");
        RequestBody lngg = RequestBody.create(MediaType.parse("text/plain"), "72.323");

        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("proof",file.getName(),requestFile);
        Call<GetUserResponse> call=myInterface.addUser(
                user_type,
                email_id,name,mobilee,usernamee,passwordd,cityy,statee,pin_codee,con,con_p_num,multipartBody,latt,lngg,device_idd,device_tokenn);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {

                Log.d("responseseeee",""+response.body().getMessage());
                Log.d("responseseeee",""+response.body().getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.fab6.next6.setImageDrawable(getResources().getDrawable(R.drawable.white_check_small));
                } else {
                    binding.fab6.next6.setImageDrawable(getResources().getDrawable(R.drawable.white_check_small));
                }
                binding.fab6.fabProgressCircle.hide();

            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.fab6.next6.setImageDrawable(getResources().getDrawable(R.drawable.white_check_small));
                } else {
                    binding.fab6.next6.setImageDrawable(getResources().getDrawable(R.drawable.white_check_small));
                }
                binding.fab6.fabProgressCircle.hide();
                Toast.makeText(RegistrationActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

                 /* GetUserResponse getUserResponse=response.body();
                    List<UserResponse> userResponse=getUserResponse.getData();

                    Log.d(TAG,""+response.body().getMessage());
                    Toast.makeText(RegistrationActivity.this, ""+getUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    pincode=binding.registerInput6.edPincode.getText().toString();
                    proof=binding.registerInput6.edDocs.getText().toString();
                   *//* startActivity(new Intent(RegistrationActivity.this,RegisterConfirmationActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();*/

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
            binding.fab6.fabProgressCircle.setVisibility(View.GONE);
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
            binding.fab6.fabProgressCircle.setVisibility(View.GONE);

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
            binding.fab6.fabProgressCircle.setVisibility(View.GONE);

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
            binding.fab6.fabProgressCircle.setVisibility(View.GONE);

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
            binding.fab6.fabProgressCircle.setVisibility(View.GONE);

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

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput1.edEmail);

            binding.registerInput1.edEmail.requestFocus();
            return false;
        }
        else if (!matcher.matches()) {
            binding.registerInput1.edEmail.setError(""+getResources().getString(R.string.Please_Enter_Valid_Email));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput1.edEmail);

            binding.registerInput1.edEmail.requestFocus();
            return false;
        }
        else  if (binding.registerInput1.edUsername.getText().toString().equals("")) {
            binding.registerInput1.edUsername.setError(""+getResources().getString(R.string.Please_Enter_username));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput1.edUsername);

            binding.registerInput1.edUsername.requestFocus();
            return false;
        }


        return true;
    }public boolean isValid2() {


        if (binding.registerInput2.edName.getText().toString().equals("")) {
            binding.registerInput2.edName.setError(""+getResources().getString(R.string.Please_enter_name));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput2.edName);

            binding.registerInput2.edName.requestFocus();
            return false;
        }

        else  if (binding.registerInput2.edMobile.getText().toString().equals("")) {
            binding.registerInput2.edMobile.setError(""+getResources().getString(R.string.Please_Enter_mobile_number));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput2.edMobile);

            binding.registerInput2.edMobile.requestFocus();
            return false;
        }


        return true;
    }

    public boolean isValid3() {


          if (binding.registerInput3.edPassword.getText().toString().equals("")) {
              binding.registerInput3.edPassword.setError(""+getResources().getString(R.string.Please_Enter_Password));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput3.edPassword);
              binding.registerInput3.edPassword.requestFocus();
            return false;
        }
        else if(binding.registerInput3.edPassword.getText().toString().trim().length() < 6)
        {
            binding.registerInput3.edPassword.setError(""+getResources().getString(R.string.Please_Enter_Password_length));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput3.edPassword);
            binding.registerInput3.edPassword.requestFocus();
            return false;
        }
        else if (!(binding.registerInput3.edPassword.getText().toString().equals(binding.registerInput3.edCpassword.getText().toString()))) {
              binding.registerInput3.edCpassword.setError(""+getResources().getString(R.string.Password_doesnt_match));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput3.edCpassword);

              binding.registerInput3.edCpassword.requestFocus();
            return false;
        }


        return true;
    }

    public boolean isValid4() {


          if (binding.registerInput4.edSecContact.getText().toString().equals("")) {
              binding.registerInput4.edSecContact.setError(""+getResources().getString(R.string.Please_enter_secondary));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput4.edSecContact);
              binding.registerInput4.edSecContact.requestFocus();
            return false;
        }

        else if (!(binding.registerInput4.edState.getText().toString().equals(binding.registerInput4.edState.getText().toString()))) {
              binding.registerInput4.edState.setError(""+getResources().getString(R.string.Please_enter_contact));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput4.edState);

              binding.registerInput4.edState.requestFocus();
            return false;
        }


        return true;
    } public boolean isValid5() {


          if (binding.registerInput5.edLocation.getText().toString().equals("")) {
              binding.registerInput5.edLocation.setError(""+getResources().getString(R.string.Please_enter_location));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput5.edLocation);
              binding.registerInput5.edLocation.requestFocus();
            return false;
        }

        else if (!(binding.registerInput5.edNameofstate.getText().toString().equals(binding.registerInput5.edNameofstate.getText().toString()))) {
              binding.registerInput5.edNameofstate.setError(""+getResources().getString(R.string.Please_enter_state));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput5.edNameofstate);

              binding.registerInput5.edNameofstate.requestFocus();
            return false;
        }


        return true;
    }public boolean isValid6() {


          if (binding.registerInput6.edPincode.getText().toString().equals("")) {
              binding.registerInput6.edPincode.setError(""+getResources().getString(R.string.Please_enter_pincode));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput6.edPincode);
              binding.registerInput6.edPincode.requestFocus();
            return false;
        }

        else if (!(binding.registerInput6.edDocs.getText().toString().equals(binding.registerInput6.edDocs.getText().toString()))) {
              binding.registerInput6.edDocs.setError(""+getResources().getString(R.string.Please_select_doc));
            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(binding.registerInput6.edDocs);

              binding.registerInput5.edNameofstate.requestFocus();
            return false;
        }


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                String Token= FirebaseInstanceId.getInstance().getToken();
                if (Token!=null)
                {

                    Log.d("thisismytoken", ""+Token);
                    device_token=Token;
                    StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().build();
                    StrictMode.setThreadPolicy(threadPolicy);
                    // getGoogleLogin(str_email,fname,lname,idtoken);


                }
                else
                {
                    Log.d("thisismytoken", "No token"+Token);

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });t.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {

            ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            mediapath = list.get(0).getPath();
           File file = new File(mediapath);
            int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
            Log.d("file_sizeeeee", "mediapath" + mediapath);
            String filename=mediapath.substring(mediapath.lastIndexOf("/")+1);
            binding.registerInput6.edDocs.setText(filename);

        }
        if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {

            ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            mediapath = list.get(0).getPath();
            File   file = new File(mediapath);
            int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
            Log.d("file_sizeeeee", "mediapath" + mediapath);
            String filename=mediapath.substring(mediapath.lastIndexOf("/")+1);
            binding.registerInput6.edDocs.setText(filename);
        }

    }
}
