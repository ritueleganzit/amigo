package com.eleganzit.amigo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

public class OpportunityActivity extends AppCompatActivity {


    ImageView options;
    LinearLayout lin_applied,lin_rejected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity);
        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        options=findViewById(R.id.options);
        lin_applied=findViewById(R.id.lin_applied);
        lin_rejected=findViewById(R.id.lin_rejected);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(OpportunityActivity.this, options);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.opportunity_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        //Toast.makeText(ChatActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        lin_applied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpportunityActivity.this,AppliedCandidatesActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        lin_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpportunityActivity.this,RejectedCandidatesActivity.class));
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
