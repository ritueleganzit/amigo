package com.eleganzit.amigo.utils;

import android.content.Context;
import android.graphics.Typeface;

import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TextViewRobotoBold extends TextView {

    public TextViewRobotoBold(Context context) {
        super(context);
    }

    public TextViewRobotoBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Bold.ttf"));
    }
}