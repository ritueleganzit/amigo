package com.eleganzit.amigo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eleganzit.amigo.adapter.EducationsAdapter;
import com.eleganzit.amigo.adapter.WorksAdapter;
import com.eleganzit.amigo.model.EducationsData;
import com.eleganzit.amigo.model.WorksData;

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

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_works=findViewById(R.id.rc_works);
        rc_educations=findViewById(R.id.rc_educations);

        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_works.setLayoutManager(layoutManager1);

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_educations.setLayoutManager(layoutManager2);

        ArrayList<WorksData> arrayList=new ArrayList<>();
        ArrayList<EducationsData> arrayList1=new ArrayList<>();

        WorksData worksData=new WorksData("","");
        arrayList.add(worksData);
        arrayList.add(worksData);

        rc_works.setAdapter(new WorksAdapter(arrayList,this));

        EducationsData educationsData=new EducationsData("","");
        arrayList1.add(educationsData);
        arrayList1.add(educationsData);

        rc_educations.setAdapter(new EducationsAdapter(arrayList1,this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
