package com.eleganzit.volunteerifyngo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eleganzit.volunteerifyngo.fragments.FollowersFragment;
import com.eleganzit.volunteerifyngo.fragments.HomeFeedFragment;
import com.eleganzit.volunteerifyngo.fragments.MenuFragment;
import com.eleganzit.volunteerifyngo.fragments.ViewPostFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


public class NewsFeedActivity extends AppCompatActivity {

    public static ImageView btm_feed,btm_event,btm_user,btm_menu;
    public static RelativeLayout rbtm_event;
    public static RelativeLayout news_feed_toolbar,view_post_toolbar;
    public static EditText ed_search;
    public static ImageView notification_bell,chat,camera;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_STORAGE_PERMISSION_CODE = 200;
    String imageFilePath;
    private static final int REQUEST_CAPTURE_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        news_feed_toolbar=findViewById(R.id.news_feed_toolbar);
        view_post_toolbar=findViewById(R.id.view_post_toolbar);
        ed_search=findViewById(R.id.ed_search);
        btm_feed=findViewById(R.id.btm_feed);
        rbtm_event=findViewById(R.id.rbtm_event);
        btm_event=findViewById(R.id.btm_event);
        btm_user=findViewById(R.id.btm_user);
        btm_menu=findViewById(R.id.btm_menu);
        camera=findViewById(R.id.camera);
        notification_bell=findViewById(R.id.notification_bell);
        chat=findViewById(R.id.chat);

        btm_feed.setImageResource(R.drawable.feed_green);
        btm_event.setImageResource(R.drawable.event_gray);
        btm_user.setImageResource(R.drawable.user_gray);
        btm_menu.setImageResource(R.drawable.menu_gray);

        ed_search.setLongClickable(false);

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsFeedActivity.this,SearchActivity.class));
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

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NewsFeedActivity.this,NotificationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NewsFeedActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        HomeFeedFragment homeFeedFragment= new HomeFeedFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, homeFeedFragment,"TAG")
                .commit();

        btm_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFeedFragment homeFeedFragment= new HomeFeedFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NewsFeedActivity");
                fragmentTransaction.replace(R.id.frame, homeFeedFragment, "TAG");
                fragmentTransaction.commit();

            }
        });

        btm_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btm_feed.setImageResource(R.drawable.feed_gray);
                btm_event.setImageResource(R.drawable.event_green);
                btm_user.setImageResource(R.drawable.user_gray);
                btm_menu.setImageResource(R.drawable.menu_gray);


            }
        });

        btm_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FollowersFragment followersFragment= new FollowersFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NewsFeedActivity");
                fragmentTransaction.replace(R.id.frame, followersFragment, "TAG");
                fragmentTransaction.commit();
            }
        });

        btm_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment menuFragment= new MenuFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NewsFeedActivity");
                fragmentTransaction.replace(R.id.frame, menuFragment, "TAG");
                fragmentTransaction.commit();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
        else if (requestCode == MY_STORAGE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "storage permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "storage permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
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

                startActivity(new Intent(NewsFeedActivity.this,CreatePostActivity.class).putExtra("imageFilePath",imageFilePath+""));
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        Log.d("finalFile","path   "+path);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

}
