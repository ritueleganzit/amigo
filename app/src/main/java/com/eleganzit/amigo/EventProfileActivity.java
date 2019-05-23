package com.eleganzit.amigo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityEventProfileBinding;
import com.eleganzit.amigo.model.DeleteEventResponse;
import com.eleganzit.amigo.model.GetEvents;
import com.eleganzit.amigo.model.GetSingleEventResponse;
import com.eleganzit.amigo.model.SingleEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import retrofit2.Callback;
import retrofit2.Response;

public class EventProfileActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView txt_interested;
    boolean liked = false;
    GoogleMap googleMap;
    EditText ed_search;
    ImageView chat, camera;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_STORAGE_PERMISSION_CODE = 200;
    String imageFilePath, event_id;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int CAMERA_REQUEST = 1888;


    ActivityEventProfileBinding binding;
    GetEvents getEvents;
    ProgressDialog progressDialog;
    private String TAG="EventProfileActivityTAG";
    private String lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(EventProfileActivity.this, R.layout.activity_event_profile);
        progressDialog=new ProgressDialog(EventProfileActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        txt_interested = findViewById(R.id.txt_interested);
        ed_search = findViewById(R.id.ed_search);
        chat = findViewById(R.id.chat);
        camera = findViewById(R.id.camera);
        binding.aboutMap.getMapAsync(this);
        if( binding.aboutMap != null)
        {
            binding.aboutMap.onCreate(null);
            binding.aboutMap.onResume();
            binding.aboutMap.getMapAsync(this);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        com.google.android.gms.maps.MapsInitializer.initialize(this);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);


        this.googleMap=googleMap;
        Log.d(TAG,"map"+lat);



    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"resume");
        getEvents = (GetEvents) getIntent().getSerializableExtra("event");
        event_id = getEvents.getEventId();

        if (event_id!=null && !(event_id.isEmpty())) {
            getsingleEvent(event_id);
        }


        ed_search.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventProfileActivity.this, SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EventProfileActivity.this, MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_STORAGE_PERMISSION_CODE);
                } else {
                    /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
                    openCameraIntent();
                }
            }
        });

       /* img_interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liked) {
                    liked = false;
                    img_interested.setImageResource(R.mipmap.icon_star_empty);
                    txt_interested.setTextColor(Color.parseColor("#919191"));
                } else {
                    liked = true;
                    img_interested.setImageResource(R.mipmap.icon_star_filled);
                    txt_interested.setTextColor(Color.parseColor("#0f2536"));
                }

            }
        });
*/

        binding.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(EventProfileActivity.this, binding.options);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.event_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.delete) {
                            deleteEvent();
                        }

                        if (item.getItemId() == R.id.edit) {
                            startActivity(new Intent(EventProfileActivity.this, EditEventActivity.class)
                                    .putExtra("event_id", event_id));
                            // finish();
                        }


                        //Toast.makeText(ChatActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getsingleEvent(String event_id) {
progressDialog.show();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        retrofit2.Call<GetSingleEventResponse> getSingleEventResponseCall=myInterface.getsingleEvent(event_id);
        getSingleEventResponseCall.enqueue(new Callback<GetSingleEventResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GetSingleEventResponse> call, Response<GetSingleEventResponse> response) {
progressDialog.dismiss();
                if (response.isSuccessful())
                {

                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            SingleEvent singleEvent=response.body().getData().get(i);

                            Log.d(TAG,"--->"+singleEvent.getLat());


                            if (singleEvent.getLat()!=null && !(singleEvent.getLat().isEmpty())){

                                lat=singleEvent.getLat();
                                lng=singleEvent.getLng();

                                  LatLng loc2=new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(loc2);
                                    googleMap.clear();
                                    googleMap.getUiSettings().setAllGesturesEnabled(false);

                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));

                                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
                                    googleMap.addMarker(markerOptions);



                            }



                            if (singleEvent.getEventTime() != null && (!singleEvent.getEventTime().isEmpty())) {
                                binding.events.setText(singleEvent.getEventTime());
                            }
                            if (singleEvent.getEventName() != null && (!singleEvent.getEventName().isEmpty())) {
                                binding.txtname.setText(singleEvent.getEventName());
                            }

                            if (singleEvent.getEventDetails() != null && (!singleEvent.getEventDetails().isEmpty())) {

                                binding.details.setText(singleEvent.getEventDetails());
                            }
                            if (singleEvent.getEventPhoto() != null && (!singleEvent.getEventPhoto().isEmpty())) {
                                if (!(singleEvent.getEventPhoto().equalsIgnoreCase("null")))

                                {
                                    Glide.with(getApplicationContext()).load("" + singleEvent.getEventPhoto()).into(binding.userphoto);

                                }


                            }
                            if (singleEvent.getAttendcount()!=null && !(singleEvent.getAttendcount().isEmpty()))
                            {
                                binding.attendcount.setText(singleEvent.getAttendcount());
                            }  if (singleEvent.getRejectcount()!=null && !(singleEvent.getRejectcount().isEmpty()))
                            {
                                binding.rejectcount.setText(singleEvent.getRejectcount());
                            }

                            if (singleEvent.getEventDate() != null && (!singleEvent.getEventDate().isEmpty())) {
                                if (!getEvents.getEventDate().equalsIgnoreCase("0000-00-00")) {
                                    String input_date = "" + singleEvent.getEventDate();
                                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                    Date dt1 = null;
                                    try {
                                        dt1 = format1.parse(input_date);
                                        DateFormat format2 = new SimpleDateFormat("MMM");
                                        DateFormat format = new SimpleDateFormat("dd");
                                        String month = format2.format(dt1);
                                        String day = format.format(dt1);

                                        Log.d("aadapter", "--" + month);
                                        Log.d("aadapter", "" + day + " " + singleEvent.getEventDate());
                                        binding.evMonth.setText("" + month);
                                        binding.evDay.setText("" + day);


                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            if (singleEvent.getEventAddress() != null && (!singleEvent.getEventAddress().isEmpty())) {

                                binding.txtloc.setText(singleEvent.getEventAddress());
                            }



                        }
                    }


                }

            }

            @Override
            public void onFailure(retrofit2.Call<GetSingleEventResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EventProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteEvent() {
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        retrofit2.Call<DeleteEventResponse> deleteEventResponseCall=myInterface.deleteEvent(event_id);
        deleteEventResponseCall.enqueue(new Callback<DeleteEventResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DeleteEventResponse> call, Response<DeleteEventResponse> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(EventProfileActivity.this, "Successfully  Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EventProfileActivity.this,EventsActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();

                }
            }

            @Override
            public void onFailure(retrofit2.Call<DeleteEventResponse> call, Throwable t) {
                Toast.makeText(EventProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.eleganzit.volunteerifyngo.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();

        Log.d("imageFilePath", imageFilePath + "");
        return image;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.photo_dialog_layout);
            ImageView imageView = dialog.findViewById(R.id.imageView1);

            imageView.setImageBitmap(photo);

            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);
            int column_index_data = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToLast();

            String imagePath = cursor.getString(column_index_data);
            Bitmap bitmapImage = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmapImage);

            Log.d("finalFile", "getRealPathFromURI   " + imagePath);

            dialog.show();


        }
        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.photo_dialog_layout);
                ImageView imageView = dialog.findViewById(R.id.imageView1);

                imageView.setImageBitmap(imageBitmap);

                startActivity(new Intent(EventProfileActivity.this, CreatePostActivity.class).putExtra("imageFilePath", imageFilePath + ""));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                //dialog.show();

                Log.d("imageFilePath", imageFilePath + "");
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
