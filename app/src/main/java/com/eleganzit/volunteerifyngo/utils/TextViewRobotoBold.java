package com.eleganzit.volunteerifyngo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class TextViewRobotoBold extends android.support.v7.widget.AppCompatTextView {

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