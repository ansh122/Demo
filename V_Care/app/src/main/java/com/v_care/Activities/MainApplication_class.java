package com.v_care.Activities;

import android.app.Application;
import android.content.Context;

import com.v_care.Utilities.LocaleHelper;

/**
 * Created by admin1 on 1/6/17.
 */

public class MainApplication_class extends Application
{
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        LocaleHelper.onCreate(context,"hi");
        LocaleHelper.setLocale(context,"hi");
    }
}
