package com.v_care.Utilities;

import android.content.Context;
import android.content.SharedPreferences;



import java.lang.reflect.Type;

/**
 * Created by administrator on 6/6/16.
 */
public class MySharedPrefrencesManagers
{
    //Shared pref
    public static void saveloginData(Context mContext, String user_id, String password, String name, String phno) {
        SharedPreferences pref = mContext.getSharedPreferences("login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("user_id", user_id);
        //edit.putString("email",email_id);
        edit.putString("password", password);
        edit.putString("name", name);
        edit.putString("phno", phno);
        edit.commit();
    }



    public  static void saveUserEmail(Context mContext, String email_id)
    {
        SharedPreferences pref=mContext.getSharedPreferences("UserEmail", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();

        edit.putString("email",email_id);

        edit.commit();
    }

    public static String getUserEmail(Context mContext)
    {
        SharedPreferences pref=mContext.getSharedPreferences("UserEmail", Context.MODE_PRIVATE);
        return pref.getString("email",null);
    }
    public static void Clear_emailprefrence(Context mContext)
    {
        SharedPreferences pref=mContext.getSharedPreferences("UserEmail", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.clear();
        edit.commit();
    }
    public static String getUser_id(Context mContext)
    {
        SharedPreferences pref=mContext.getSharedPreferences("login_data", Context.MODE_PRIVATE);
        return pref.getString("user_id",null);
    }

    public static void Save_FcmrefreshToken(Context mContext, String token)
    {
        SharedPreferences preferences=mContext.getSharedPreferences("FCMREFRESH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }
    public static String Get_FcmrefreshToken(Context mContext)
    {
        SharedPreferences preferences=mContext.getSharedPreferences("FCMREFRESH", Context.MODE_PRIVATE);
        return preferences.getString("token",null);
    }


}
