package com.v_care.TextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lenovo on 15-Nov-16.
 */
public class MyCustomTextview extends TextView
{

    public MyCustomTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyCustomTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomTextview(Context context) {
        super(context);
        init();
    }

    private void init()  {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/Calibri.ttf");
        setTypeface(tf);
    }
}
