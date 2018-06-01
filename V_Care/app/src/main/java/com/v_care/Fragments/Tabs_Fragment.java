package com.v_care.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.v_care.Activities.Login_Activity;
import com.v_care.Adapters.PagerAdapter;
import com.v_care.R;
import com.v_care.Utilities.LocaleHelper;
import com.v_care.Utilities.MySharedPrefrencesManagers;
import com.v_care.Utilities.Webservices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 02-May-17.
 */
public class Tabs_Fragment extends Fragment implements TabLayout.OnTabSelectedListener
{
    public static Fragment fragment;
    static FragmentTransaction ft;
    static public FragmentManager fragmentmanger;
    static public Context mContext;
    View view;

    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    View view_question,view_videos,view_info;
    TabLayout.Tab question_tab;
    TabLayout.Tab videos_tab;
    TabLayout.Tab info_tab;

    TextView txt_question,txt_videos,txt_info;
    LinearLayout ask_expert,req_vcare;
    TextView log_out;
    static ProgressDialog mProgressDialog;
    public static Fragment getInstance(Context ct, FragmentManager fm) {

        mContext = ct;
        fragmentmanger = fm;
        fragment = new Tabs_Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.main_page,container,false);
        mContext=getActivity();
        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        ask_expert=(LinearLayout)view.findViewById(R.id.ask_expert);
        req_vcare=(LinearLayout)view.findViewById(R.id.req_vcare);
        log_out=(TextView)view.findViewById(R.id.log_out);


        LocaleHelper.onCreate(mContext,"hi");
        LocaleHelper.setLocale(mContext,"hi");

        req_vcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AskExpert();
            }
        });

        ask_expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        //Adding the tabs using addTab() method
        question_tab= tabLayout.newTab();
        videos_tab= tabLayout.newTab();
        info_tab= tabLayout.newTab();



        question_tab.setText(getResources().getString(R.string.question));
        videos_tab.setText(getResources().getString(R.string.Videos));
        info_tab.setText(getResources().getString(R.string.info));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addTab(question_tab,0);
        tabLayout.addTab(videos_tab,1);
        tabLayout.addTab(info_tab,2);

        //Initializing viewPager


        //Creating our pager adapter
        PagerAdapter adapter = new PagerAdapter(fragmentmanger, tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.setupWithViewPager(viewPager);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch (tab.getPosition())
        {


        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //webservices called
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
                params.put("user_id",MySharedPrefrencesManagers.getUser_id(mContext));

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

      //Method for dialog
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

    //create method for the progress dialog
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
