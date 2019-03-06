package com.eleganzit.amigo;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import com.eleganzit.amigo.adapter.SearchAdapter;
import com.eleganzit.amigo.adapter.SearchPagesAdapter;
import com.eleganzit.amigo.adapter.SearchPhotosAdapter;
import com.eleganzit.amigo.adapter.SearchPlacesAdapter;
import com.eleganzit.amigo.model.PagesData;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.model.PlacesData;
import com.eleganzit.amigo.model.SearchData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rc_search;
    ArrayList<SearchData> ar_search =new ArrayList<>();

    ImageView chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rc_search=findViewById(R.id.rc_search);

        chat=findViewById(R.id.chat);

        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rc_search.setLayoutManager(layoutManager1);

        SearchData searchData=new SearchData("","","");

        ar_search.add(searchData);
        ar_search.add(searchData);
        ar_search.add(searchData);
        ar_search.add(searchData);
        ar_search.add(searchData);
        ar_search.add(searchData);

        rc_search.setAdapter(new SearchAdapter(ar_search,SearchActivity.this));

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
