package com.v_care.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.v_care.R;
import com.v_care.Utilities.LocaleHelper;
import com.v_care.Utilities.MySharedPrefrencesManagers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 21-Mar-17.
 */
public class Login_Activity extends AppCompatActivity
{
    //Creating id refrences of widgets used in xml file
    EditText edEmailId_login,edPassword_login;
    TextView forgot_password;
    LinearLayout login_button;
    RelativeLayout rel_fbbtn,rel_gbtn;
    TextView signuphere;

    String device_gcm;
    //RequestQueue requestQueue;
    static ProgressDialog mProgressDialog;
    //declaring status as static for a fixed value
    public static int login_fbstatus=0;
    public static int login_Gstatus=0;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_xml);
        mContext=this;
        if(!isNetworkAvailable())
        {
            Toast.makeText(mContext,"No network",Toast.LENGTH_LONG).show(); //Toast message created to display on the screen
        }
        getSupportActionBar().hide();
        //find widgets by id's
        edEmailId_login=(EditText)findViewById(R.id.edEmailId_login);
        edPassword_login=(EditText)findViewById(R.id.edPassword_login);
        login_button=(LinearLayout) findViewById(R.id.login_button);
       /* rel_fbbtn=(RelativeLayout) findViewById(R.id.rel_fbbtn);
        rel_gbtn=(RelativeLayout) findViewById(R.id.rel_gbtn);*/
        forgot_password=(TextView) findViewById(R.id.forgot_password);
        signuphere=(TextView)findViewById(R.id.signuphere);

        //device_gcm= FirebaseInstanceId.getInstance().getToken();
        //code for changing the color on a particular page
//      if (android.os.Build.VERSION.SDK_INT >= 21) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.header_background));
//        }

        signuphere.setOnClickListener(new View.OnClickListener() {      //OnClickListener for signuphere textview
            @Override
            public void onClick(View view) {
                //Creating intent to reach on signup activity
                Intent intent = new Intent(Login_Activity.this, SignUp_Activity.class);
                intent.putExtra("name","");
                intent.putExtra("email","");
                intent.putExtra("login_status","3");
                startActivity(intent);
                //finish();
                //startActivity(new Intent(Login_Activity.this,SignUp_Activity.class));
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {        //onClickListener for login button
            @Override
            public void onClick(View v) {
                if(edEmailId_login.getText().toString().equals(""))
                {
                    edEmailId_login.setError("Email id cannot be blank");
                }
                else if(edPassword_login.getText().toString().equals(""))
                {
                    edPassword_login.setError("Password cannot be blank");
                }
                else
                {
                    Sendlogindata();
                    showProgressDialog();
                }
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {     //OnClickListener for forgotpassword textview
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(mContext);
                LayoutInflater li = (LayoutInflater) mContext.getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vi = li.inflate(R.layout.forgot_password, null, false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                final EditText  edEmailId_forgotpassword=(EditText)vi.findViewById(R.id.edEmailId_forgotpassword);
                //finding the text view
                TextView btnDone=(TextView)vi.findViewById(R.id.btnDone);
                TextView btnCancel=(TextView)vi.findViewById(R.id.btnCancel);

                //creating onClickListener for the above text view
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edEmailId_forgotpassword.getText().toString().equals("")) {
                            Toast.makeText(mContext, "Email Id Can't be Blank", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            PasswordRequest(edEmailId_forgotpassword.getText().toString());
                            dialog.dismiss();
                        }

                    }
                });
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(vi);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });




    }
    // calling webservices

    public void PasswordRequest(final String email)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"abc.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e("forgot responce",response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.optString("status");
                            String message=jsonObject.optString("message");
                            Toast.makeText(Login_Activity.this,message,Toast.LENGTH_LONG).show();

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
                params.put("email",email);
                Log.e("forgot parma",""+params);
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
    public void Sendlogindata() {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "abc.com",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Login responce", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("message");
                                String status = jsonObject.getString("status");
                                Toast.makeText(Login_Activity.this, msg, Toast.LENGTH_LONG).show();
                                if (status.equals("true")) {
                                    JSONObject jobject = jsonObject.getJSONObject("user_details");
                                    String u_id = jobject.optString("u_id");
                                    String u_fullname = jobject.optString("u_fullname");
                                    String u_mobile_no = jobject.optString("u_mobile_no");
                                    String u_email = jobject.optString("u_email");
                                    MySharedPrefrencesManagers.saveUserEmail(mContext,u_email);
                                    MySharedPrefrencesManagers.saveloginData(mContext, u_id, edPassword_login.getText().toString(), u_fullname, u_mobile_no);
                                    //MySharedPrefrencesManagers.saveUserEmail(mContext, vendor_email);
                                    Intent intent = new Intent(Login_Activity.this, MainPageActivity.class);
                                   /* intent.putExtra("name", vendor_fullname);
                                    intent.putExtra("email", vendor_email);
                                    intent.putExtra("Login_status", "1");
                                    intent.putExtra("type", "2");*/
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }


                                hideProgressDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                hideProgressDialog();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login_Activity.this, error.toString(), Toast.LENGTH_LONG).show();
                            hideProgressDialog();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", edEmailId_login.getText().toString());
                    params.put("password", edPassword_login.getText().toString());
                    params.put("gcm_id",MySharedPrefrencesManagers.Get_FcmrefreshToken(mContext));
                    params.put("device_name", "android");
                    Log.e("Login parma", "" + params);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocaleHelper.onCreate(mContext,"hi");
        LocaleHelper.setLocale(mContext,"hi");
    }

      // method for creating progress dialog
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext, R.style.Custom);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
