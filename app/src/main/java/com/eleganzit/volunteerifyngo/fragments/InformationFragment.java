package com.eleganzit.volunteerifyngo.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.UserProfileActivity;
import com.eleganzit.volunteerifyngo.ViewCampaignActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment  implements OnMapReadyCallback {


    public InformationFragment() {
        // Required empty public constructor
    }

    boolean isOpen=false;
    MapView about_map;
    GoogleMap googleMap;
    LinearLayout drop_down_layout;
    ImageView drop_down;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_information, container, false);

        ViewCampaignActivity.tab_information.setTextColor(Color.parseColor("#0f2536"));
        ViewCampaignActivity.tab_campaigns.setTextColor(Color.parseColor("#8c8c8c"));
        ViewCampaignActivity.tab_offer.setTextColor(Color.parseColor("#8c8c8c"));
        ViewCampaignActivity.tab_news.setTextColor(Color.parseColor("#8c8c8c"));

        about_map=v.findViewById(R.id.about_map);
        drop_down=v.findViewById(R.id.drop_down);
        drop_down_layout=v.findViewById(R.id.drop_down_layout);

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drop_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isOpen)
                {
                    isOpen=false;
                    Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate0);

                    RotateAnimation rotate=new RotateAnimation(0,180);
                    rotate.setDuration(400);

                    drop_down.animate().rotation(0).start();

                    drop_down_layout.animate()
                            .translationY(drop_down_layout.getHeight())
                            .setDuration(400)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    drop_down_layout.setVisibility(View.GONE);
                                }
                            });
                }
                else
                {
                    isOpen=true;
                    Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate180);

                    RotateAnimation rotate=new RotateAnimation(0,180);
                    rotate.setDuration(400);

                    drop_down.animate().rotation(180).start();

                    drop_down_layout.animate()
                            .translationY(drop_down_layout.getHeight())
                            .setDuration(400)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    drop_down_layout.setVisibility(View.VISIBLE);
                                }
                            });
                }

            }
        });
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
