package com.eleganzit.volunteerifyngo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewRobotoRegular extends android.support.v7.widget.AppCompatTextView {

    public TextViewRobotoRegular(Context context) {
        super(context);
    }

    public TextViewRobotoRegular(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Regular.ttf"));
    }
}