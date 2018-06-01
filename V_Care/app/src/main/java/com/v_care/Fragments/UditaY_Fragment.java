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
import com.v_care.Activities.Answer_queActivity;
import com.v_care.Activities.MainPageActivity;
import com.v_care.Activities.Udita_Ans;
import com.v_care.Adapters.Question_Adapter;
import com.v_care.Beans.Questions_bean;
import com.v_care.R;
import com.v_care.Utilities.Webservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin1 on 12/6/17.
 */

public class UditaY_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    public static Fragment fragment;
    static FragmentTransaction ft;
    static public FragmentManager fragmentmanger;
    static public Context mContext;

    ArrayList<Questions_bean> questions_beanArrayList;
    ListView ques_list;
    static ProgressDialog mProgressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    /*public static Question_Fragmants newInstance(int page, String title) {
        Question_Fragmants fragmentFirst = new Question_Fragmants();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }*/

    public static Fragment getInstance(Context ct, FragmentManager fm) {

        mContext = ct;
        fragmentmanger = fm;
        fragment = new UditaY_Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.queston,container,false);
        mContext=getActivity();
        ques_list=(ListView)v.findViewById(R.id.ques_list);
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh_layout);
        MainPageActivity.setHeaderTitle(mContext,getResources().getString(R.string.udita));
        MainPageActivity.HideLogout_btn(mContext);
        questions_beanArrayList=new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        GetQuestion_list();
                                        showProgressDialog();
                                    }
                                }
        );


        return v;
    }

    //webservices called
    public void GetQuestion_list()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "abc.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        questions_beanArrayList = getquestions(response);
                        Question_Adapter adapter = new Question_Adapter(mContext, questions_beanArrayList);

                        ques_list.setAdapter(adapter);
                        ques_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String que_id=questions_beanArrayList.get(i).getQuestion_id();
                                Intent intent=new Intent(mContext, Udita_Ans.class);
                                intent.putExtra("que_id",que_id);
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

    public ArrayList<Questions_bean> getquestions(String json)
    {
        ArrayList<Questions_bean> getanother_list=new ArrayList<>();
        try
        {
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("response");


            for (int j = 0; j < jarray.length(); j++)
            {
                Questions_bean questions_bean=new Questions_bean();
                questions_bean.setQuestion_id(jarray.getJSONObject(j).optString("question_id"));
                questions_bean.setQuestion(jarray.getJSONObject(j).optString("question"));
                questions_bean.setAnswer(jarray.getJSONObject(j).optString("answer"));
                questions_bean.setSerialno(j+1);


                getanother_list.add(questions_bean);
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getanother_list;
    }

    //method for creating progress dialog
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
        GetQuestion_list();
    }
}
