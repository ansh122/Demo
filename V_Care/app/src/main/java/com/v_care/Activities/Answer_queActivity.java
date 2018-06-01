package com.v_care.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.v_care.Beans.Questions_bean;
import com.v_care.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 03-Apr-17.
 */
public class Answer_queActivity extends Activity
{
    //Creating id refrences of widgets used in xml file
    TextView qus_txt;
    TextView ans_txt;
    String que_id;
    Context mContext;
    static ProgressDialog mProgressDialog;
    ArrayList<Questions_bean> que_ansArraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_xml);

        //finding out the textview
        qus_txt=(TextView)findViewById(R.id.qus_txt);
        ans_txt=(TextView)findViewById(R.id.ans_txt);
        mContext=this;
        Intent intent=getIntent();               //creating intent
        que_id=intent.getStringExtra("que_id");
        que_ansArraylist=new ArrayList<>();
        GetQuestion_list(que_id);

    }
         // Calling WebServices using Volley
    public void GetQuestion_list(final String question_id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "abc.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(" responce",response);
                      //Using try-catch block
                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            String status=jsonObject.getString("status");
                            if(status.equals("true"))
                            {
                                JSONArray jsonArray=jsonObject.getJSONArray("response");
                                 for (int i=0;i<jsonArray.length();i++)
                                 {
                                     Questions_bean bean=new Questions_bean();
                                     String question=jsonArray.getJSONObject(i).getString("question");
                                     bean.setQuestion(question);

                                     String answer=jsonArray.getJSONObject(i).getString("answer");
                                     bean.setAnswer(answer);

                                     que_ansArraylist.add(bean);
                                 }

                                qus_txt.setText(que_ansArraylist.get(0).getQuestion());
                                ans_txt.setText(que_ansArraylist.get(0).getAnswer());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Login_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                        hideProgressDialog();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("question_id",question_id);

                Log.e("parma",""+params);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
