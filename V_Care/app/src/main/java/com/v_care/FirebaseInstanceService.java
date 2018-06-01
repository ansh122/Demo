package com.v_care;

import android.util.Log;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.v_care.Utilities.MySharedPrefrencesManagers;

/**
 * Created by lenovo on 03-Dec-16.
 */
public class FirebaseInstanceService extends FirebaseInstanceIdService
{

    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh()
    {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("token##########",refreshedToken);
        MySharedPrefrencesManagers.Save_FcmrefreshToken(getApplicationContext(),refreshedToken);
        //Toast.makeText(getApplicationContext() ,refreshedToken+"                  *********djd ", Toast.LENGTH_SHORT).show();
        String id =   FirebaseInstanceId.getInstance().getId();

    }

    private void sendRegistrationToServer(String token) {
        Toast.makeText(getApplicationContext() ,token+"djd ", Toast.LENGTH_SHORT).show();
    }
}
