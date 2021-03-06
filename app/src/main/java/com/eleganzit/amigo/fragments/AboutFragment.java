package com.eleganzit.amigo.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.UserProfileActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment implements OnMapReadyCallback {


    public AboutFragment() {
        // Required empty public constructor
    }

    MapView about_map;
    GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_about, container, false);

        UserProfileActivity.tab_home.setTextColor(Color.parseColor("#8c8c8c"));
        UserProfileActivity.tab_about.setTextColor(Color.parseColor("#0f2536"));
        UserProfileActivity.tab_photos.setTextColor(Color.parseColor("#8c8c8c"));
        UserProfileActivity.tab_events.setTextColor(Color.parseColor("#8c8c8c"));
        UserProfileActivity.tab_opportunity.setTextColor(Color.parseColor("#8c8c8c"));

        about_map=v.findViewById(R.id.about_map);

        about_map.getMapAsync(this);
        if(about_map != null)
        {
            about_map.onCreate(null);
            about_map.onResume();
            about_map.getMapAsync(this);
        }


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);

        LatLng loc2=new LatLng(23.0191711,72.4642893);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
        this.googleMap=googleMap;

    }
}
