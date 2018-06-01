package com.v_care.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.v_care.Activities.MainPageActivity;
import com.v_care.Activities.Video_viewActivity;
import com.v_care.Adapters.Information_adapter;
import com.v_care.Adapters.Video_adpater;
import com.v_care.Beans.Info_bean;
import com.v_care.Beans.Video_bean;
import com.v_care.R;
import com.v_care.Utilities.Webservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lenovo on 29-Mar-17.
 */
public class Information_Fragments extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    public static Fragment fragment;
    static FragmentTransaction ft;
    static public FragmentManager fragmentmanger;
    static public Context mContext;
    ArrayList<Info_bean> information_beanArrayList;
    ListView info_list;
    static ProgressDialog mProgressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    public static Fragment getInstance(Context ct, FragmentManager fm) {

        mContext = ct;
        fragmentmanger = fm;
        fragment = new Information_Fragments();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View v=inflater.inflate(R.layout.info,container,false);
         mContext=getActivity();
         info_list=(ListView)v.findViewById(R.id.info_list);
         swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh_layout);
         information_beanArrayList=new ArrayList<>();
         swipeRefreshLayout.setOnRefreshListener(this);

        MainPageActivity.setHeaderTitle(mContext,getResources().getString(R.string.info));
        MainPageActivity.HideLogout_btn(mContext);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        GetInformation_list();
                                        showProgressDialog();
                                    }
                                }
        );

         return v;
    }

    //WebServices Called
    public void GetInformation_list()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"abc.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        information_beanArrayList = getInfo(response);
                        Information_adapter adapter = new Information_adapter(mContext, information_beanArrayList);

                        info_list.setAdapter(adapter);
                        info_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String video=information_beanArrayList.get(i).getInfo_file_type();

                                if (video.equals("video"))
                                {
                                    String video_link=information_beanArrayList.get(i).getInfo_img_name();
                                    Intent intent=new Intent(mContext, Video_viewActivity.class);
                                    intent.putExtra("video",video_link);
                                    startActivity(intent);
                                }



                            }
                        });

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Login_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                        hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });




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

    public ArrayList<Info_bean> getInfo(String json)
    {
        ArrayList<Info_bean> getinformation_list=new ArrayList<>();
        try
        {
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("response");


            for (int j = 0; j < jarray.length(); j++)
            {
                Info_bean infoBean_bean=new Info_bean();
                infoBean_bean.setInformation_id(jarray.getJSONObject(j).optString("information_id"));
                infoBean_bean.setInfo_img_name(jarray.getJSONObject(j).optString("info_img_name"));
                infoBean_bean.setInformation_title(jarray.getJSONObject(j).optString("information_title"));
                infoBean_bean.setInformation_description(jarray.getJSONObject(j).optString("information_description"));
                infoBean_bean.setInfo_file_type(jarray.getJSONObject(j).getString("info_file_type"));
                infoBean_bean.setVideo_thumbnail(jarray.getJSONObject(j).getString("video_thumbnail"));


                getinformation_list.add(infoBean_bean);
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getinformation_list;
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

    @Override
    public void onRefresh()
    {
     GetInformation_list();
    }
}
