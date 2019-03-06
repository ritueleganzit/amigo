package com.eleganzit.amigo.fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eleganzit.amigo.NewsFeedActivity;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.adapter.ViewPostImagesAdapter;
import com.eleganzit.amigo.model.PostImages;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPostFragment extends Fragment {


    public ViewPostFragment() {
        // Required empty public constructor
    }

    RecyclerView rc_view_post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_post, container, false);

        NewsFeedActivity.news_feed_toolbar.setVisibility(View.GONE);
        NewsFeedActivity.view_post_toolbar.setVisibility(View.VISIBLE);

        rc_view_post = v.findViewById(R.id.rc_view_post);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rc_view_post.setLayoutManager(layoutManager);

        String image1 = "https://images.pexels.com/photos/257360/pexels-photo-257360.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
        String image2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRV3S67M76jaxZTLUTtk8Wngtkfc7XC1zDE_qKZlmjClXs8AbE";
        String image3 = "https://images.pexels.com/photos/459225/pexels-photo-459225.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
        String image4 = "https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg";
/*        String image5 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDyu82j_yYelLzJd2tVDqHZmCXRFVyOcDt2wwr5Zfb8JvREdu1";
        String image6 = "https://i.ytimg.com/vi/2SAPrPZVTjs/hqdefault.jpg";*/
        PostImages postImages1 = new PostImages(image1);
        PostImages postImages2 = new PostImages(image2);
        PostImages postImages3 = new PostImages(image3);
        PostImages postImages4 = new PostImages(image4);
  /*      PostImages postImages5 = new PostImages(image5);
        PostImages postImages6 = new PostImages(image6);
*/
        ArrayList<PostImages> arrayList = new ArrayList<>();
        arrayList.add(postImages1);
        arrayList.add(postImages2);
        arrayList.add(postImages3);
        arrayList.add(postImages4);
  /*      arrayList.add(postImages5);
        arrayList.add(postImages6);*/

        rc_view_post.setAdapter(new ViewPostImagesAdapter(arrayList, getActivity()));

        return v;
    }

}
