package com.v_care.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.v_care.Activities.Chat_Activity;
import com.v_care.Activities.MainPageActivity;
import com.v_care.R;
import com.v_care.Utilities.MySharedPrefrencesManagers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin1 on 12/5/17.
 */

public class DashBoard_Fragmant extends Fragment
{
    public static Fragment fragment;
    static FragmentTransaction ft;
    static public FragmentManager fragmentmanger;
    static public Context mContext;
    View view;
    LinearLayout question_dash;
    LinearLayout videos_dash;
    LinearLayout info_dash;
    LinearLayout chat_dash;
    LinearLayout req_dash;
    LinearLayout signout_dash;
    static ProgressDialog mProgressDialog;
    MainPageActivity mainPageActivity;
    public static Fragment getInstance(Context ct, FragmentManager fm) {

        mContext = ct;
        fragmentmanger = fm;
        fragment = new DashBoard_Fragmant();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.dashboard,container,false);
        question_dash=(LinearLayout)view.findViewById(R.id.question_dash);
        videos_dash=(LinearLayout)view.findViewById(R.id.videos_dash);
        info_dash=(LinearLayout)view.findViewById(R.id.info_dash);
        chat_dash=(LinearLayout)view.findViewById(R.id.chat_dash);
        req_dash=(LinearLayout)view.findViewById(R.id.req_dash);
        signout_dash=(LinearLayout)view.findViewById(R.id.signout_dash);
        mainPageActivity=new MainPageActivity();
        MainPageActivity.setHeaderTitle(mContext,getResources().getString(R.string.dashboard));
        MainPageActivity.showLogout_btn(mContext);
        question_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                MainPageActivity.displayview(1);

            }
        });
        // set onClickListener
        videos_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPageActivity.displayview(2);
            }
        });
        info_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPageActivity.displayview(3);
            }
        });
        chat_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(mContext,Chat_Activity.class));

            }
        });
        req_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AskExpert();
            }
        });
        signout_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPageActivity.displayview(4);
            }
        });


        return view;
    }
   // Calling WebServices
    public void AskExpert()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "abc.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(" responce", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("message");
                            String status = jsonObject.getString("status");
                            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                            if (status.equals("true")) {
                                ShowRequest_Dialog();
                            }
                            hideProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
                        hideProgressDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", MySharedPrefrencesManagers.getUser_id(mContext));

                Log.e("parma", "" + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 10000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    //method to create dialog
    public void ShowRequest_Dialog()
    {
        final Dialog Msg_dialog = new Dialog(mContext);
        LayoutInflater li = (LayoutInflater) mContext.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = li.inflate(R.layout.popup_welcome, null, false);
        Msg_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        LinearLayout ok_button=(LinearLayout)vi.findViewById(R.id.ok_button);
        TextView welcome_text=(TextView)vi.findViewById(R.id.welcome_text);
        welcome_text.setText(getResources().getString(R.string.req_text));

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Msg_dialog.dismiss();
                //startActivity(new Intent(MainPageActivity.this,Login_Activity.class));
                // finish();
            }
        });

        Msg_dialog.setContentView(vi);
        Msg_dialog.setCancelable(false);
        Msg_dialog.setCanceledOnTouchOutside(false);
        Msg_dialog.show();
    }

    //method for progress dialog
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext, R.style.Custom);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
