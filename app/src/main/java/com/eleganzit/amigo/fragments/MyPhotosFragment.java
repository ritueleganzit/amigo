package com.eleganzit.amigo.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.amigo.MyProfileActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.UserProfileActivity;
import com.eleganzit.amigo.adapter.MyPhotosAdapter;
import com.eleganzit.amigo.adapter.SearchPhotosAdapter;
import com.eleganzit.amigo.databinding.ActivityMyProfileBinding;
import com.eleganzit.amigo.model.PhotosData;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPhotosFragment extends Fragment {


    public MyPhotosFragment() {
        // Required empty public constructor
    }

    RecyclerView rc_photos;

    ArrayList<PhotosData> ar_photosData =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_photos, container, false);

        MyProfileActivity.binding.tabPhotos.setTextColor(Color.parseColor("#0f2536"));
        MyProfileActivity.binding.tabFollowing.setTextColor(Color.parseColor("#8c8c8c"));
        MyProfileActivity.binding.tabEvents.setTextColor(Color.parseColor("#8c8c8c"));
        MyProfileActivity.binding.tabMilestone.setTextColor(Color.parseColor("#8c8c8c"));

        rc_photos=v.findViewById(R.id.rc_photos);

        RecyclerView.LayoutManager layoutManager3=new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);

        rc_photos.setLayoutManager(layoutManager3);

        PhotosData photosData1=new PhotosData("","https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        PhotosData photosData2=new PhotosData("","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");
        PhotosData photosData3=new PhotosData("","https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        PhotosData photosData4=new PhotosData("","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1");
        PhotosData photosData5=new PhotosData("","https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg");
        PhotosData photosData6=new PhotosData("","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE");

        ar_photosData.add(photosData1);
        ar_photosData.add(photosData2);
        ar_photosData.add(photosData3);
        ar_photosData.add(photosData4);
        ar_photosData.add(photosData5);
        ar_photosData.add(photosData6);

        rc_photos.setAdapter(new MyPhotosAdapter(ar_photosData,getActivity()));


        return v;
    }

}
