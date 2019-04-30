package com.eleganzit.amigo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class EventProfileActivity extends AppCompatActivity {

    ImageView img_interested;
    TextView txt_interested;
    boolean liked=false;
    EditText ed_search;
    ImageView chat,camera;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_STORAGE_PERMISSION_CODE = 200;
    String imageFilePath;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_profile);

        img_interested=findViewById(R.id.img_interested);
        txt_interested=findViewById(R.id.txt_interested);
        ed_search=findViewById(R.id.ed_search);
        chat=findViewById(R.id.chat);
        camera=findViewById(R.id.camera);

        ed_search.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventProfileActivity.this,SearchActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EventProfileActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

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
                }
                else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_STORAGE_PERMISSION_CODE);
                }
                else {
                    /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
                    openCameraIntent();
                }
            }
        });

        img_interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(liked)
                {
                    liked=false;
                    img_interested.setImageResource(R.mipmap.icon_star_empty);
                    txt_interested.setTextColor(Color.parseColor("#919191"));
                }
                else
                {
                    liked=true;
                    img_interested.setImageResource(R.mipmap.icon_star_filled);
                    txt_interested.setTextColor(Color.parseColor("#0f2536"));
                }

            }
        });

    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.eleganzit.volunteerifyngo.provider", photoFile);
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

        Log.d("imageFilePath",imageFilePath+"");
        return image;
    }


    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.photo_dialog_layout);
            ImageView imageView = dialog.findViewById(R.id.imageView1);

            imageView.setImageBitmap(photo);

            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);
            int column_index_data = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToLast();

            String imagePath = cursor.getString(column_index_data);
            Bitmap bitmapImage = BitmapFactory.decodeFile(imagePath );
            imageView.setImageBitmap(bitmapImage );

            Log.d("finalFile","getRealPathFromURI   "+imagePath);

            dialog.show();


        }
        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                Dialog dialog=new Dialog(this);
                dialog.setContentView(R.layout.photo_dialog_layout);
                ImageView imageView = dialog.findViewById(R.id.imageView1);

                imageView.setImageBitmap(imageBitmap);

                startActivity(new Intent(EventProfileActivity.this,CreatePostActivity.class).putExtra("imageFilePath",imageFilePath+""));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                //dialog.show();

                Log.d("imageFilePath",imageFilePath+"");
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}