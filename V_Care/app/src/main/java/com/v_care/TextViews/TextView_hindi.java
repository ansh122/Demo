package com.v_care.TextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lenovo on 28-Dec-16.
 */
public class TextView_hindi extends TextView {
    public TextView_hindi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView_hindi(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView_hindi(Context context) {
        super(context);
        init();
    }

    private void init()  {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/kruti-dev-021.ttf");
        setTypeface(tf);
    }
}
