package com.eleganzit.amigo.fragments;


import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.amigo.CreatePostActivity;
import com.eleganzit.amigo.MessageActivity;
import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.NotificationsActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.SearchActivity;
import com.eleganzit.amigo.adapter.NewsFeedAdapter;
import com.eleganzit.amigo.adapter.ViewPhotosAdapter;
import com.eleganzit.amigo.model.NewsFeedData;
import com.eleganzit.amigo.model.PhotosData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFeedFragment extends Fragment {

    EditText ed_status;
    RecyclerView rc_posts;
    NestedScrollView news_feed_scroll;
    ArrayList<NewsFeedData> dataArrayList=new ArrayList<>();
    ArrayList<String> imgArrayList=new ArrayList<>();
    ArrayList<PhotosData> ar_photos=new ArrayList<>();

    LinearLayout header_upload_photo;
    private static final int SELECT_PICTURE = 100;

    public HomeFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_home_feed, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.VISIBLE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.GONE);

        NewsFeedActivity.btm_feed.setImageResource(R.drawable.feed_green);
        NewsFeedActivity.btm_followers.setImageResource(R.mipmap.followers_gray);
        NewsFeedActivity.btm_notification.setImageResource(R.mipmap.notification_gray);
        NewsFeedActivity.btm_menu.setImageResource(R.drawable.menu_gray);

        news_feed_scroll=v.findViewById(R.id.news_feed_scroll);

        ed_status=v.findViewById(R.id.ed_status);
        rc_posts=v.findViewById(R.id.rc_posts);
        header_upload_photo=v.findViewById(R.id.header_upload_photo);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        news_feed_scroll.scrollTo(0,0);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rc_posts.setLayoutManager(layoutManager);

        ed_status.setLongClickable(false);

        ed_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CreatePostActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        header_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

        if(imgArrayList.size()>0)
        {
            imgArrayList.clear();
        }

        imgArrayList.add("https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        imgArrayList.add("https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        imgArrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");

        imgArrayList.add("https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        /*ImgarrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        */
        NewsFeedData newsFeedData=new NewsFeedData("zahir",imgArrayList);

        if(dataArrayList.size()>0)
        {
            dataArrayList.clear();
        }

        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);
        dataArrayList.add(newsFeedData);

        rc_posts.setAdapter(new NewsFeedAdapter(dataArrayList,getActivity()));

    }

    void openImageChooser() {
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int clumnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String mediapath = cursor.getString(clumnIndex);
                Bundle extra = new Bundle();
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    if(ar_photos.size()>0)
                    {
                        ar_photos.clear();
                    }
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        // Get the cursor
                        Cursor cursor2 = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor2.moveToFirst();

                        int columnIndex = cursor2.getColumnIndex(filePathColumn[0]);
                        mediapath  = cursor2.getString(columnIndex);
                        PhotosData photosData=new PhotosData("",mediapath);

                        ar_photos.add(photosData);

                        cursor2.close();

                    }

                    extra.putSerializable("objects", ar_photos);

                    startActivity(new Intent(getActivity(),CreatePostActivity.class).putExtra("imagesFilePath",extra));
                    getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    Log.d("LOG_TAG", "Selected Images" + mArrayUri.size());
                }

            }
        }
        if (resultCode==RESULT_CANCELED)
        {

        }

    }
}
