package com.v_care.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.v_care.R;
import com.v_care.Utilities.LocaleHelper;
import com.v_care.Utilities.MySharedPrefrencesManagers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Splash_Activity extends Activity
{
    Context context;
    String device_gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        context=this;

        //device_gcm= FirebaseInstanceId.getInstance().getToken();
        LocaleHelper.onCreate(context,"hi");
        LocaleHelper.setLocale(context,"hi");
        //Log.e("check email",MySharedPrefrencesManagers.getUserEmail(context));

        //printHashKey();
    }
       //some overrriden methods
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (MySharedPrefrencesManagers.getUserEmail(context)!=null)
                {


                    startActivity(new Intent(context, MainPageActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(context, Login_Activity.class));
                    finish();
                }

            }

        }, 5 * 1000);

    }

   //method created
    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.v_care",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");//huY7M+E85+XH/RzuUWqZCytKk8I=
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                //showAlertDialog(""+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
