package com.v_care.TextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by lenovo on 21-Nov-16.
 */
public class EditText_text extends EditText
{
    public EditText_text(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditText_text(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_text(Context context) {
        super(context);
        init();
    }

    private void init()  {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/Calibri.ttf");
        setTypeface(tf);
    }
}
