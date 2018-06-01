package com.v_care;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.v_care.Activities.Login_Activity;
import com.v_care.Activities.MainPageActivity;
import com.v_care.Utilities.Constant;
import com.v_care.Utilities.MySharedPrefrencesManagers;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by lenovo on 03-Dec-16.
 */
public class Firebasemessagingservice extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Context context;
    String msg;
    String sound_check = null;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        if (remoteMessage.getNotification() != null) {
            String data = remoteMessage.getNotification().getBody();
            try {
                //CreateNotification(getApplicationContext(),data,"");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("HJelloo", remoteMessage.getData().toString());
            String data = remoteMessage.getData().toString();
            if (remoteMessage.getData().size() > 0) {
                Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

                try {
                    JSONObject json = new JSONObject(remoteMessage.getData().toString());
                    //handleDataMessage(json);

                    JSONObject str_data = json.getJSONObject("data");
                    String notification_message = str_data.getString("notification_message");
                    Log.e("notification_message", notification_message);

                    String notification_type = str_data.getString("notification_type");
                    int numMessages = 0;

                    Log.e(TAG, "push json: " + json.toString());
                    if (notification_type.equals("1")) {
                        sendNotification(notification_message);
                    } else if (notification_type.equals("2")) {
                        if (Constant.chatFragmentScreen == 0) {
                            ShowChat_Notification(getApplicationContext(), notification_message, notification_type, ++numMessages);
                        }
                    }


                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }
        }

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        /*Intent intent = new Intent(this, Login_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);*/

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icn_logo_circal)
                .setContentTitle("V Care")
                .setContentText(messageBody)
                .setAutoCancel(true)

                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public void CreateNotification(Context context, String message, String notification_type) {

        if (isAppOnForeground(context)) {
            if (MySharedPrefrencesManagers.getUser_id(context) == null) {

            } else {


                int requestId = (int) System.currentTimeMillis();
                Intent intentPush = new Intent(context, MainPageActivity.class);
                intentPush.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentPush.putExtra("notificationId", requestId);
                intentPush.putExtra("msg", message);
                intentPush.putExtra("type", notification_type);
                //context.startActivity(intentPush);
                //MainPageActivity.showPrivateMessageAlert(intentPush);
                Log.e("forground", "yes");

            }
        }
        //}
        else {

            Log.e("forground", "No");
            PendingIntent pendingIntent = null;
            Intent myIntent = null;
            int requestId = (int) System.currentTimeMillis();


            if (MySharedPrefrencesManagers.getUser_id(context) == null) {
                //if (SessionManager.getDialogstatus(mContext) == 0) {
                myIntent = new Intent(context, Login_Activity.class);
                myIntent.putExtra("notificationId", requestId);
                myIntent.putExtra("msg", message);
                myIntent.putExtra("type", notification_type);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(context, requestId, myIntent, 0);

                NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Resources res = context.getResources();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

                builder.setContentIntent(pendingIntent)

                        .setSmallIcon(R.drawable.icn_logo_circal)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.icn_logo_circal))
                        .setTicker("V Care")
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentTitle("V Care")
                        .setContentText(message);
                Notification n = builder.getNotification();

                n.defaults |= Notification.DEFAULT_ALL;
                nm.notify(requestId, n);

                // }
            } else {
                myIntent = new Intent(context, MainPageActivity.class);
                myIntent.putExtra("notificationId", requestId);
                myIntent.putExtra("msg", message);
                myIntent.putExtra("type", notification_type);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(context, requestId, myIntent, 0);

                NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Resources res = context.getResources();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

                builder.setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.icn_logo_circal)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.icn_logo_circal))
                        .setTicker("V Care")
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentTitle("V Care")
                        .setContentText(message);
                Notification n = builder.getNotification();

                n.defaults |= Notification.DEFAULT_ALL;
                nm.notify(requestId, n);

            }
        }
    }

    public void ShowChat_Notification(Context context, String message,String notification_type,int numMessages)

    {

        PendingIntent pendingIntent = null;
        Intent myIntent = null;
        int requestId =1;


        if (MySharedPrefrencesManagers.getUser_id(context) == null) {
            //if (SessionManager.getDialogstatus(mContext) == 0) {
            myIntent = new Intent(context, Login_Activity.class);
            myIntent.putExtra("notificationId", requestId);
            myIntent.putExtra("msg", message);
            myIntent.putExtra("type", notification_type);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, requestId, myIntent, 0);

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Resources res = context.getResources();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setContentIntent(pendingIntent)

                    .setSmallIcon(R.drawable.icn_logo_circal)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.icn_logo_circal))
                    .setTicker("V Care")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle("V Care")
                    .setContentText("You've received new messages.");

            //builder.setContentText("You've received new messages.").setNumber(++numMessages);
            Notification n = builder.getNotification();

            n.defaults |= Notification.DEFAULT_ALL;
            nm.notify(requestId, n);
            //nm.notify(requestId, builder.build());

            // }
        } else {
            myIntent = new Intent(context, MainPageActivity.class);
            myIntent.putExtra("notificationId", requestId);
            myIntent.putExtra("msg", message);
            myIntent.putExtra("type", notification_type);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, requestId, myIntent, 0);

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Resources res = context.getResources();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.icn_logo_circal)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.icn_logo_circal))
                    .setTicker("V Care")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle("V Care")
                    .setContentText("You've received new messages");
            //int numMessages = 0;
            //builder.setContentText("You've received new messages.").setNumber(++numMessages);
            Notification n = builder.getNotification();

            n.defaults |= Notification.DEFAULT_ALL;
            nm.notify(requestId, n);

            //nm.notify(requestId, builder.build());

        }
    }


    public boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {

            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}
