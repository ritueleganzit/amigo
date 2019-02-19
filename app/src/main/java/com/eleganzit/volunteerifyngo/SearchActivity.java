package com.eleganzit.volunteerifyngo;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import com.eleganzit.volunteerifyngo.adapter.SearchPagesAdapter;
import com.eleganzit.volunteerifyngo.adapter.SearchPhotosAdapter;
import com.eleganzit.volunteerifyngo.adapter.SearchPlacesAdapter;
import com.eleganzit.volunteerifyngo.model.PagesData;
import com.eleganzit.volunteerifyngo.model.PhotosData;
import com.eleganzit.volunteerifyngo.model.PlacesData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rc_pages,rc_places,rc_photos;
    ArrayList<PagesData> ar_pagesData =new ArrayList<>();
    ArrayList<PlacesData> ar_placesData =new ArrayList<>();
    ArrayList<PhotosData> ar_photosData =new ArrayList<>();
    ImageView chat,notification_bell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rc_pages=findViewById(R.id.rc_pages);
        rc_places=findViewById(R.id.rc_places);
        rc_photos=findViewById(R.id.rc_photos);
        chat=findViewById(R.id.chat);
        notification_bell=findViewById(R.id.notification_bell);

        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager layoutManager3=new GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false);

        rc_pages.setLayoutManager(layoutManager1);
        rc_places.setLayoutManager(layoutManager2);
        rc_photos.setLayoutManager(layoutManager3);

        PagesData pagesData=new PagesData("","","","","");

        PlacesData placesData=new PlacesData("","","","","");

        PhotosData photosData1=new PhotosData("","https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        PhotosData photosData2=new PhotosData("","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        PhotosData photosData3=new PhotosData("","https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        PhotosData photosData4=new PhotosData("","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        PhotosData photosData5=new PhotosData("","https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        PhotosData photosData6=new PhotosData("","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");

        ar_pagesData.add(pagesData);
        ar_pagesData.add(pagesData);
        ar_pagesData.add(pagesData);

        ar_placesData.add(placesData);

        ar_photosData.add(photosData1);
        ar_photosData.add(photosData2);
        ar_photosData.add(photosData3);
        ar_photosData.add(photosData4);
        ar_photosData.add(photosData5);
        ar_photosData.add(photosData6);

        rc_pages.setAdapter(new SearchPagesAdapter(ar_pagesData,SearchActivity.this));
        rc_places.setAdapter(new SearchPlacesAdapter(ar_placesData,SearchActivity.this));
        rc_photos.setAdapter(new SearchPhotosAdapter(ar_photosData,SearchActivity.this));

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SearchActivity.this,NotificationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SearchActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
