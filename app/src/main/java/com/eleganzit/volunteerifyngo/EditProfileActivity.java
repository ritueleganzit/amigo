package com.eleganzit.volunteerifyngo;

import android.os.Bundle;

import com.eleganzit.volunteerifyngo.model.EducationsData;
import com.eleganzit.volunteerifyngo.model.WorksData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditProfileActivity extends AppCompatActivity {

    RecyclerView rc_works,rc_educations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        rc_works=findViewById(R.id.rc_works);
        rc_educations=findViewById(R.id.rc_educations);

        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_works.setLayoutManager(layoutManager1);

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_educations.setLayoutManager(layoutManager2);

        ArrayList<WorksData> arrayList=new ArrayList<>();
        ArrayList<EducationsData> arrayList1=new ArrayList<>();



    }
}
