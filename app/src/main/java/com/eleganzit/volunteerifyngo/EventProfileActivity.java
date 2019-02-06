package com.eleganzit.volunteerifyngo;

import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventProfileActivity extends AppCompatActivity {

    ImageView img_interested;
    TextView txt_interested;
    boolean liked=false;
    LinearLayout event_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_profile);

        img_interested=findViewById(R.id.img_interested);
        txt_interested=findViewById(R.id.txt_interested);
        event_options=findViewById(R.id.event_options);

        img_interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(liked)
                {
                    liked=false;
                    img_interested.setImageResource(R.mipmap.icon_star_empty);
                    txt_interested.setTextColor(Color.parseColor("#919191"));
                }
                else
                {
                    liked=true;
                    img_interested.setImageResource(R.mipmap.icon_star_filled);
                    txt_interested.setTextColor(Color.parseColor("#0f2536"));
                }

            }
        });

        event_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(EventProfileActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.event_options_layout, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
            }
        });

    }
}
