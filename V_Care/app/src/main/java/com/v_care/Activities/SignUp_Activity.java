package com.v_care.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;
import com.v_care.R;
import com.v_care.Utilities.MySharedPrefrencesManagers;
import com.v_care.Utilities.Webservices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 21-Mar-17.
 */
public class SignUp_Activity extends AppCompatActivity
{
    EditText edname_signup;
    EditText edEmailId_signup;
    EditText edPassword_signup;
    EditText edconfPassword_signup;
    EditText edphone_signup;
    EditText edaddress_signup;
    EditText edprofession_signup;
    ImageView imgCheckboxInactive_male,imgCheckboxActive_male;
    ImageView imgCheckboxInactive_female,imgCheckboxActive_female;
    TextView otpmob_no;
    RelativeLayout text_sign;
    Context mContext;
    AQuery aq;
    int gender_status=0;
    Dialog dialog;
    private SmsVerifyCatcher smsVerifyCatcher;
    String code;
    String device_gcm;
    EditText Otp;
    //int gender_status_female=0;
    static ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_xml);
        mContext=this;
        //finding view by id's
        edname_signup=(EditText)findViewById(R.id.edname_signup);
        edEmailId_signup=(EditText)findViewById(R.id.edEmailId_signup);
        edPassword_signup=(EditText)findViewById(R.id.edPassword_signup);
        edconfPassword_signup=(EditText)findViewById(R.id.edconfPassword_signup);
        edphone_signup=(EditText)findViewById(R.id.edphone_signup);
        edaddress_signup=(EditText)findViewById(R.id.edaddress_signup);
        edprofession_signup=(EditText)findViewById(R.id.edprofession_signup);
        text_sign=(RelativeLayout)findViewById(R.id.text_sign);
        getSupportActionBar().hide();
        imgCheckboxInactive_male=(ImageView)findViewById(R.id.imgCheckboxInactive_male);
        imgCheckboxActive_male=(ImageView)findViewById(R.id.imgCheckboxActive_male);
        imgCheckboxInactive_female=(ImageView)findViewById(R.id.imgCheckboxInactive_female);
        imgCheckboxActive_female=(ImageView)findViewById(R.id.imgCheckboxActive_female);

        //device_gcm=  FirebaseInstanceId.getInstance().getToken();
        aq = new AQuery(mContext);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.header_background));
        }

        smsVerifyCatcher = new SmsVerifyCatcher(this,new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                code= parseCode(message);//Parse verification code
                Otp.setText(""+code);

                //etCode.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });

        imgCheckboxInactive_male.setOnClickListener(new View.OnClickListener() {   //OnClickListener Created
            @Override
            public void onClick(View view) {
                imgCheckboxInactive_male.setVisibility(View.INVISIBLE);
                imgCheckboxActive_male.setVisibility(View.VISIBLE);
                imgCheckboxActive_female.setVisibility(View.INVISIBLE);
                imgCheckboxInactive_female.setVisibility(View.VISIBLE);
                gender_status=1;

            }
        });

        imgCheckboxActive_male.setOnClickListener(new View.OnClickListener() {   //OnClickListener Created
            @Override
            public void onClick(View view) {
                imgCheckboxInactive_male.setVisibility(View.VISIBLE);
                imgCheckboxActive_male.setVisibility(View.INVISIBLE);
                gender_status=0;

            }
        });
        imgCheckboxInactive_female.setOnClickListener(new View.OnClickListener() {  //OnClickListener Created
            @Override
            public void onClick(View view) {
                imgCheckboxInactive_female.setVisibility(View.INVISIBLE);
                imgCheckboxActive_female.setVisibility(View.VISIBLE);
                imgCheckboxActive_male.setVisibility(View.INVISIBLE);
                imgCheckboxInactive_male.setVisibility(View.VISIBLE);
                gender_status=2;

            }
        });

        imgCheckboxActive_female.setOnClickListener(new View.OnClickListener() {   //OnClickListener Created
            @Override
            public void onClick(View view) {
                imgCheckboxInactive_female.setVisibility(View.VISIBLE);
                imgCheckboxActive_female.setVisibility(View.GONE);
                gender_status=0;

            }
        });
        text_sign.setOnClickListener(new View.OnClickListener() {    //OnClickListener Created
            @Override
            public void onClick(View view) {

                 if(edname_signup.getText().toString().equals(""))
                {
                    edname_signup.setError("Name can not be blank");

                }
                else if(!isValidEmail(edEmailId_signup.getText().toString()))
                {
                    edEmailId_signup.setError("Email is not valid");

                }
                 else if(edPassword_signup.getText().toString().equals(""))
                 {
                     edPassword_signup.setError("Password can not be blank");

                 }
                 else if(edPassword_signup.getText().toString().length()<8)
                 {
                     edPassword_signup.setError("Password Should be minimum 8 char Long");

                 }
                 else if(edconfPassword_signup.getText().toString().equals(""))
                 {
                     edconfPassword_signup.setError("Confirm Password can not be blank");

                 }
                 else if(!edPassword_signup.getText().toString().equals(edconfPassword_signup.getText().toString()))
                 {
                     edconfPassword_signup.setError("Both Passwords Should Match");

                 }

                else if(edphone_signup.getText().toString().length()<10)
                {
                    edphone_signup.setError("Mobile No Not Valid");

                }
                 else if(edaddress_signup.getText().toString().equals(""))
                {
                    edaddress_signup.setError("Address can not be blank");

                }


                else if(gender_status==0)
                {
                     showToast();
                }


                else
                {
                    sendUser_Details();
                    showProgressDialog();
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            smsVerifyCatcher.onStop();

        }
        catch (Exception e)
        {

        }

    }
  // weservices called
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void sendUser_Details() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("u_fullname", edname_signup.getText().toString());
        params.put("u_phone_no", edphone_signup.getText().toString());

        params.put("u_profession", edprofession_signup.getText().toString());

        params.put("u_email", edEmailId_signup.getText().toString());
        params.put("u_password", edPassword_signup.getText().toString());
        params.put("u_gender",""+gender_status);
        if(MySharedPrefrencesManagers.Get_FcmrefreshToken(mContext)!=null)
        {
            params.put("gcm_id",MySharedPrefrencesManagers.Get_FcmrefreshToken(mContext));
        }
        else
        {
            params.put("gcm_id",device_gcm);
        }

        Log.e("reg parma", "" + params);
        aq.ajax(Webservices.user_registration, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.e("responce", object.toString());
                if (object != null) {

                    if (object.optString("status").equals("true")) {
                        try {
                            hideProgressDialog();
                            String msg = object.getString("message");
                            String user_id = object.optString("user_id");
                            MySharedPrefrencesManagers.saveloginData(mContext,user_id,edPassword_signup.getText().toString(),edname_signup.getText().toString(),edphone_signup.getText().toString());
                            //Toast.makeText(mContext,""+code,Toast.LENGTH_LONG).show();

                            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();

                            showOTPDialog(user_id);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(mContext, object.optString("message").toString(), Toast.LENGTH_LONG).show();
                        hideProgressDialog();
                    }

                } else {
                    hideProgressDialog();
                    Toast.makeText(mContext, "Something going wrong,Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // method created for OTP dialog
    public void showOTPDialog(final String vendor_id) {

        dialog = new Dialog(mContext);
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int dialogewidth = screenWidth - 20;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.demo_otppopup);
        dialog.getWindow().setLayout(dialogewidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        //finding view
        Otp=(EditText)dialog.findViewById(R.id.otp);
        TextView verify_button=(TextView)dialog.findViewById(R.id.verify_button);
        TextView regenrate_button=(TextView)dialog.findViewById(R.id.regenrate_button);
        otpmob_no =(TextView)dialog.findViewById(R.id.otpmob_no);
        otpmob_no.setText(edphone_signup.getText().toString());

        verify_button.setOnClickListener(new View.OnClickListener() {  //OnClickListener Created
            @Override
            public void onClick(View v)
            {
                if(Otp.getText().toString().equals(""))
                {
                    Toast.makeText(mContext,"Please Enter OTP",Toast.LENGTH_LONG).show();
                }
                else {
                    Verify_OTP(vendor_id, Otp.getText().toString());
                }


            }
        });

        regenrate_button.setOnClickListener(new View.OnClickListener() {   //OnClickListener Created
            @Override
            public void onClick(View view)
            {
               Regenrate_OTP(vendor_id,Otp.getText().toString());
            }
        });

        /*dialog.setContentView(vi);
        dialog.setCanceledOnTouchOutside(false);*/
        dialog.show();
    }
    //webservices called
    public void Verify_OTP(final String vendor_id, final String otp_cod)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "abc.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            Log.e("VERIFICATION RESPONCE",response);
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("message");
                            String status=jsonObject.getString("status");

                            if(status.equals("true"))
                            {
                                Toast.makeText(SignUp_Activity.this, msg, Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                MySharedPrefrencesManagers.saveUserEmail(mContext,edEmailId_signup.getText().toString());
                                //MySharedPrefrencesManagers.saveloginData(mContext);

                                ShowWelcome_Dialog();
                            }
                            else
                            {
                                if(msg.equals("Invalid OTP"))
                                {
                                    Toast.makeText(SignUp_Activity.this, "Invalid OTP!Please Try Again",Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }





                            hideProgressDialog();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                        hideProgressDialog();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",vendor_id);
                params.put("verification_code",otp_cod);
                params.put("u_phone_no",edphone_signup.getText().toString());


                Log.e("verify_otp parma",""+params);
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

    //webservice called
    public void Regenrate_OTP(final String vendor_id, final String otp_cod)

    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "abc.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            Log.e("Resend RESPONCE",response);
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("message");
                            String status=jsonObject.getString("status");
                            Toast.makeText(SignUp_Activity.this, msg, Toast.LENGTH_LONG).show();
                            if(status.equals("true"))
                            {
                                //dialog.dismiss();
                                //MySharedPrefrencesManagers.saveUserEmail(mContext,edEmailId_signup.getText().toString());
                                //MySharedPrefrencesManagers.saveloginData(mContext);

                                //ShowWelcome_Dialog();
                            }





                            hideProgressDialog();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                        hideProgressDialog();        //for progressdialog
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",vendor_id);
                params.put("u_phone_no",edphone_signup.getText().toString());
                Log.e("Resend parma",""+params);
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
    public void ShowWelcome_Dialog()
    {
        final Dialog Msg_dialog = new Dialog(mContext);
        LayoutInflater li = (LayoutInflater) mContext.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = li.inflate(R.layout.popup_welcome, null, false);
        Msg_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        LinearLayout ok_button=(LinearLayout)vi.findViewById(R.id.ok_button);
        TextView welcome_text=(TextView)vi.findViewById(R.id.welcome_text);

        welcome_text.setText("वी केयर से जुडने के लिए धन्यवाद ");

        ok_button.setOnClickListener(new View.OnClickListener() {              //OnClickListener Created
            @Override
            public void onClick(View view) {
                Msg_dialog.dismiss();
                startActivity(new Intent(SignUp_Activity.this,Login_Activity.class));    //intent called
                finish();
            }
        });
       // for dialog settings
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
    //another method called
    public void showToast()
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.msg);
        text.setText(getResources().getString(R.string.missing_toast));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }
    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
