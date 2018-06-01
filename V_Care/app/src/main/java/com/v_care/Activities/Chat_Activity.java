package com.v_care.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.v_care.Adapters.ChatListViewAdapter;
import com.v_care.Beans.ChatMsg_bean;
import com.v_care.R;
import com.v_care.Utilities.Constant;
import com.v_care.Utilities.MySharedPrefrencesManagers;
import com.v_care.Utilities.UtilMethod;
import com.v_care.Utilities.Webservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lenovo on 13-Apr-17.
 */
public class Chat_Activity extends Activity
{
    //Creating id refrences of widgets used in xml file
    ListView list_chat;
    EditText Messags_et;
    TextView Send_tv;
    Context mContext;
    Handler handler = new Handler();
    TimerTask scanTask;
    Timer t = new Timer();
    static ProgressDialog mProgressDialog;
    ArrayList<ChatMsg_bean> chatMsg_beanArrayList;
    ChatListViewAdapter chat_adpter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatxml);
        list_chat=(ListView)findViewById(R.id.list_chat); //find widgets by id's
        Messags_et=(EditText) findViewById(R.id.Messags_et);
        Send_tv=(TextView) findViewById(R.id.Send_tv);
        mContext=this;
        Constant.chatFragmentScreen = 1;
        Send_tv.setOnClickListener(new View.OnClickListener() {      //Creating OnClickListener for Send_tv text view
            @Override
            public void onClick(View view) {
                String message_text = Messags_et.getText().toString();

                if (UtilMethod.isStringNullOrBlank(message_text)) {

                   UtilMethod.showToast("Please Enter Message",mContext);

                } else if (UtilMethod.isNetworkAvailable(mContext))
                {
                   showProgressDialog();
                    SendChatMsg(message_text);                     //abstract method created

                }


                }
        });

        Setchat_msglist();
    }

     //method created for the above abstract method
    public void SendChatMsg(final String msg)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Webservices.chat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Login responce", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("message");
                            String status = jsonObject.getString("status");
                            if(status.equals("true"))
                            {
                                Messags_et.setText("");
                                Setchat_msglist();


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
                        Toast.makeText(Chat_Activity.this, error.toString(), Toast.LENGTH_LONG).show();
                        hideProgressDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",MySharedPrefrencesManagers.getUser_id(mContext));
                params.put("chat_message",msg);

                Log.e("chat parma", "" + params);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

 //method created
  public void Setchat_msglist()
    {
        handler = new Handler();
        scanTask=new TimerTask() {
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (UtilMethod.isNetworkAvailable(mContext)) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "abc.com",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //Log.e("responce", response);
                                            hideProgressDialog();
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String msg = jsonObject.getString("message");
                                                String status = jsonObject.getString("status");
                                                if(status.equals("true"))
                                                {
                                                   chatMsg_beanArrayList=getchatData_server(response);
                                                    chat_adpter=new ChatListViewAdapter(chatMsg_beanArrayList,mContext);
                                                    list_chat.setAdapter(chat_adpter);
                                                    chat_adpter.notifyDataSetChanged();
                                                    scrollDown();

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(Chat_Activity.this, error.toString(), Toast.LENGTH_LONG).show();
                                            hideProgressDialog();
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("user_id",MySharedPrefrencesManagers.getUser_id(mContext));


                                    //Log.e("chatList parma", "" + params);
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
                    }
                });

            }
        };

        t = new Timer(); // This is new

        t.schedule(scanTask, 0,5000); // execute in every 5sec
    }
          //WebServices Called
    public ArrayList<ChatMsg_bean> getchatData_server(String json)
    {
        ArrayList<ChatMsg_bean> getchat_list=new ArrayList<>();
        //creating object with try_catch block
        try
        {
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("response");

              // using for loop for iteration
            for (int j = 0; j < jarray.length(); j++)
            {
                ChatMsg_bean chatMsgBean=new ChatMsg_bean();
                chatMsgBean.setChat_id(jarray.getJSONObject(j).optString("chat_id"));
                chatMsgBean.setSent_by_admin(jarray.getJSONObject(j).optString("sent_by_admin"));
                chatMsgBean.setSent_to_admin(jarray.getJSONObject(j).optString("sent_to_admin"));
                chatMsgBean.setMessage(jarray.getJSONObject(j).optString("message"));
                chatMsgBean.setCreated_at(jarray.getJSONObject(j).optString("created_at"));


                getchat_list.add(chatMsgBean);
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getchat_list;
    }

    private void scrollDown() {
        list_chat.setSelection(list_chat.getCount() - 1);
    }
     //code for the progress dialog
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
