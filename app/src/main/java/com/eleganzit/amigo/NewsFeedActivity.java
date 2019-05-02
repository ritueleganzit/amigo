package com.eleganzit.amigo;

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

import com.eleganzit.amigo.fragments.FollowersFragment;
import com.eleganzit.amigo.fragments.HomeFeedFragment;
import com.eleganzit.amigo.fragments.MenuFragment;
import com.eleganzit.amigo.fragments.MyPhotosFragment;
import com.eleganzit.amigo.fragments.MyProfileFragment;
import com.eleganzit.amigo.fragments.NotificationsFragment;
import com.eleganzit.amigo.fragments.ViewPostFragment;
import com.eleganzit.amigo.session.UserLoggedInSession;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class NewsFeedActivity extends AppCompatActivity {

    public static ImageView btm_feed,btm_notification,btm_followers,btm_menu;
    public static RelativeLayout rel_feed,rel_notification,rel_followers,rel_menu;
    public static RelativeLayout news_feed_toolbar,view_post_toolbar;
    public static EditText ed_search;
    public static ImageView chat,camera;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_STORAGE_PERMISSION_CODE = 200;
    String imageFilePath;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    UserLoggedInSession userLoggedInSession;

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
userLoggedInSession=new UserLoggedInSession(NewsFeedActivity.this);

        news_feed_toolbar=findViewById(R.id.news_feed_toolbar);
        view_post_toolbar=findViewById(R.id.view_post_toolbar);
        ed_search=findViewById(R.id.ed_search);
        btm_feed=findViewById(R.id.btm_feed);
        rel_feed=findViewById(R.id.rel_feed);
        rel_notification=findViewById(R.id.rel_notification);
        rel_followers=findViewById(R.id.rel_followers);
        rel_menu=findViewById(R.id.rel_menu);
        btm_notification=findViewById(R.id.btm_notification);
        btm_followers=findViewById(R.id.btm_followers);
        btm_menu=findViewById(R.id.btm_menu);
        camera=findViewById(R.id.camera);
        chat=findViewById(R.id.chat);

        btm_feed.setImageResource(R.drawable.feed_green);
        btm_followers.setImageResource(R.mipmap.followers_gray);
        btm_notification.setImageResource(R.mipmap.notification_gray);
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

        rel_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                HomeFeedFragment homeFeedFragment= new HomeFeedFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, homeFeedFragment, "TAG");
                fragmentTransaction.commit();

            }
        });

        rel_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FollowersFragment followersFragment= new FollowersFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NewsFeedActivity");
                fragmentTransaction.replace(R.id.frame, followersFragment, "TAG");
                fragmentTransaction.commit();
            }
        });

        rel_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                NotificationsFragment notificationsFragment= new NotificationsFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NewsFeedActivity");
                fragmentTransaction.replace(R.id.frame, notificationsFragment, "TAG");
                fragmentTransaction.commit();
            }
        });

        rel_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

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
                Uri photoURI = FileProvider.getUriForFile(this,"com.eleganzit.amigo.provider", photoFile);
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
