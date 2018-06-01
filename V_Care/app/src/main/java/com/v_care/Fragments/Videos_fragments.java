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
import com.v_care.Adapters.Question_Adapter;
import com.v_care.Adapters.Video_adpater;
import com.v_care.Beans.Questions_bean;
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
public class Videos_fragments extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    public static Fragment fragment;
    static FragmentTransaction ft;
    static public FragmentManager fragmentmanger;
    static public Context mContext;
    ArrayList<Video_bean> video_beanArrayList;
    ListView ques_list;
    static ProgressDialog  mProgressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    /*public static Videos_fragments newInstance(int page, String title) {
        Videos_fragments fragmentvideos = new Videos_fragments();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentvideos.setArguments(args);
        return fragmentvideos;
    }*/

    public static Fragment getInstance(Context ct, FragmentManager fm) {

        mContext = ct;
        fragmentmanger = fm;
        fragment = new Videos_fragments();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.video_list,container,false);
        mContext=getActivity();
        ques_list=(ListView)v.findViewById(R.id.video_list);
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh_layout);
        video_beanArrayList=new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(this);
        MainPageActivity.setHeaderTitle(mContext,getResources().getString(R.string.Videos));
        MainPageActivity.HideLogout_btn(mContext);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        Getvideos_list();
                                        showProgressDialog();
                                    }
                                }
        );


        return v;
    }

    //webservices called
    public void Getvideos_list()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "abc.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        video_beanArrayList = getvideos(response);
                        Video_adpater adapter = new Video_adpater(mContext, video_beanArrayList);

                        ques_list.setAdapter(adapter);
                        ques_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String video_link=video_beanArrayList.get(i).getVideo_name();

                                Intent intent=new Intent(mContext, Video_viewActivity.class);
                                intent.putExtra("video",video_link);
                                startActivity(intent);

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

    public ArrayList<Video_bean> getvideos(String json)
    {
        ArrayList<Video_bean> getanother_list=new ArrayList<>();
        try
        {
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("response");


            for (int j = 0; j < jarray.length(); j++)
            {
                Video_bean questions_bean=new Video_bean();
                questions_bean.setVideo_id(jarray.getJSONObject(j).optString("video_id"));
                questions_bean.setVideo_name(jarray.getJSONObject(j).optString("video_name"));
                questions_bean.setVideo_title(jarray.getJSONObject(j).optString("video_title"));
                questions_bean.setVideo_description(jarray.getJSONObject(j).optString("video_description"));
                questions_bean.setVideo_thumb(jarray.getJSONObject(j).getString("video_thumbnail"));


                getanother_list.add(questions_bean);
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getanother_list;
    }

    //method created for progress dialog
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
    public void onRefresh() {
        Getvideos_list();
    }
}
