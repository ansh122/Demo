package com.v_care.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.squareup.picasso.Picasso;
import com.v_care.Adapters.NavigationAdapter;
import com.v_care.Adapters.PagerAdapter;
import com.v_care.Fragments.DashBoard_Fragmant;
import com.v_care.Fragments.Information_Fragments;
import com.v_care.Fragments.Question_Fragmants;
import com.v_care.Fragments.Tabs_Fragment;
import com.v_care.Fragments.UditaY_Fragment;
import com.v_care.Fragments.Videos_fragments;
import com.v_care.R;
import com.v_care.Utilities.Constant;
import com.v_care.Utilities.LocaleHelper;
import com.v_care.Utilities.MySharedPrefrencesManagers;
import com.v_care.Utilities.Webservices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 22-Mar-17.
 */
public class MainPageActivity extends AppCompatActivity
{
    static Context mcontext;
    static Fragment fragment;
    static FragmentManager fragmentManager;
    static FragmentTransaction ft;

    public static DrawerLayout drawer;
    static TextView opp_name,btn_logout,icn_notification;
    public static Toolbar toolbar;
    ListView menulist;
    private Menu menu;
    static ActionBarDrawerToggle toggle;
    static RelativeLayout toolbar_relative;
    public static String device_token;
    static RelativeLayout rel_heading;
    static TextView heading;
    static MainPageActivity mainPageActivity;
    public static boolean isnotify;
    public static String chatsender;
    static String type;
    static String msg;
    public static FragmentManager fm;
    public static int countnotify = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_dashboard);
        mcontext=this;
        mainPageActivity=this;
        Constant.chatFragmentScreen = 0;
        toolbar = (Toolbar) findViewById(R.id.toolbar);  //finding toolbar by id
        fragmentManager = getSupportFragmentManager();
        //displayview(viewMapFragment, null);
        setSupportActionBar(toolbar);
        /*drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar_relative=(RelativeLayout)findViewById(R.id.toolbar_relative);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        //toolbar.setNavigationIcon(R.drawable.menu);
        //Dash_heading = (TextView) findViewById(R.id.Dash_heading);
        opp_name = (TextView) findViewById(R.id.opp_name);//opp_name     //finding widgets by id's
        btn_logout = (TextView) findViewById(R.id.btn_logout);
        icn_notification = (TextView) findViewById(R.id.icn_notification);
        rel_heading=(RelativeLayout)findViewById(R.id.rel_heading);
        heading=(TextView)findViewById(R.id.heading);
        displayview(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //showmenubutton(mcontext);

//        if (android.os.Build.VERSION.SDK_INT >= 21) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.Top_Bar_Dashboard_Background_Color));
//        }
        btn_logout.setOnClickListener(new View.OnClickListener() {      //creating onClickListener
            @Override
            public void onClick(View view) {
                MySharedPrefrencesManagers.Clear_emailprefrence(mcontext);
                startActivity(new Intent(mcontext,Login_Activity.class));
                finishActivity();
            }
        });
        //user_name = (TextView) findViewById(R.id.user_name);

       /* menulist = (ListView) findViewById(R.id.menu_list_view);
        menulist.setAdapter(new NavigationAdapter(mcontext, fragmentManager));
        menulist.setOnItemClickListener(new DrawerItemClickListener());*/
    }
       //methods created for different options
    public static void showbackbutton(Context mcontext) {
        toolbar.setNavigationIcon(R.drawable.back_btn);
    }

    public static void showmenubutton(Context mcontext) {
        toolbar.setNavigationIcon(R.drawable.icn_bars);

    }
    public static void showLogout_btn(Context mcontext) {
       btn_logout.setVisibility(View.VISIBLE);
    }

    public static void HideLogout_btn(Context mcontext) {
        btn_logout.setVisibility(View.GONE);

    }

    public static void hidetoolbar(Context mcontext,String txtheading)
    {
        toolbar.setVisibility(View.GONE);
        toolbar_relative.setVisibility(View.GONE);
        rel_heading.setVisibility(View.VISIBLE);
        heading.setText(txtheading);

    }
    public static void showtoolbar(Context mcontext)
    {
        toolbar.setVisibility(View.VISIBLE);
        toolbar_relative.setVisibility(View.VISIBLE);
        rel_heading.setVisibility(View.GONE);
        heading.setVisibility(View.GONE);
    }

    @Override     //some overrided methods
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume()
    {
        Constant.chatFragmentScreen = 0;

        if (countnotify == 1) {

        } else
            {
                if (getIntent() != null) {
                    Bundle extra = getIntent().getExtras();
                    if (extra != null) {
                        type = extra.getString("type");
                        msg = extra.getString("msg");

                        if (type.equals("2")) {
                            int notificationId = extra.getInt("notificationId", 0);

                            if (notificationId != 0) {

                                startActivity(new Intent(mcontext,Chat_Activity.class));

                                setIntent(null);

                            } else {
                                Intent intent1 = new Intent(mcontext, Login_Activity.class); //intent created
                                startActivity(intent1);
                                finish();
                            }
                        }
                    }
                }

        }
        super.onResume();


    }
    /*private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }*/
    public static void setHeaderTitle(Context mcontext, String heading) {     //method created
        opp_name.setText(heading);
    }


    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
    public static void lockDrawer(Context mcontext) {

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public static void displayview(int id) {
        fragment = null;
        if (fragmentManager != null) {
            ft = fragmentManager.beginTransaction();

            switch (id) {

                case 0:
                    fragment = DashBoard_Fragmant.getInstance(mcontext, fragmentManager);
                    if (fragment != null) {
                        ft.replace(R.id.frame, fragment, "Dashboard");

                        ft.addToBackStack(null);
                    }
                    ft.commit();
                    break;//valuer_associated

                case 1:
                    fragment = Question_Fragmants.getInstance(mcontext, fragmentManager);
                    if (fragment != null) {
                        ft.replace(R.id.frame, fragment, "Question");

                        ft.addToBackStack(null);
                    }
                    ft.commit();
                    break;//valuer_associated


                case 2:
                    fragment = Videos_fragments.getInstance(mcontext, fragmentManager);
                    if (fragment != null) {
                        ft.replace(R.id.frame, fragment, "Videos");

                        ft.addToBackStack(null);
                    }
                    ft.commit();
                    break;//valuer_associated

                case 3:
                    fragment = Information_Fragments.getInstance(mcontext, fragmentManager);
                    if (fragment != null) {
                        ft.replace(R.id.frame, fragment, "Information");

                        ft.addToBackStack(null);
                    }
                    ft.commit();
                    break;//valuer_associated

                case 4:
                    fragment = UditaY_Fragment.getInstance(mcontext, fragmentManager);
                    if (fragment != null) {
                        ft.replace(R.id.frame, fragment, "Udita");

                        ft.addToBackStack(null);
                    }
                    ft.commit();
                    break;

            }

        } else {
            Log.e("error", "fragmentManager null");
        }
    }
    public static void closeDrawer(Context mcontext) {
        drawer.closeDrawer(GravityCompat.START);
    }

    public static void OpenDrawer(Context mcontext) {
        drawer.openDrawer(GravityCompat.START);
    }

    public void finishActivity() {
       mainPageActivity.finish();

    }
    @Override
    public void onBackPressed() {
        if (fragmentManager != null) {
            if (fragmentManager.getBackStackEntryCount() == 1) {
                finish();
                return;
            }
            else
            {
                super.onBackPressed();
            }

            /*Fragment Dashboard_Fragment = fragmentManager.findFragmentByTag("Dashboard");

            if (Dashboard_Fragment != null && Dashboard_Fragment.isVisible())
            {
                finish();

            }*/
        }
    }

    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        isnotify = intent.getBooleanExtra("isnotify", false);
        chatsender = intent.getStringExtra("chatsender");
        if (isnotify)
        {
            fm.popBackStack();
        }

        int extraString = intent.getIntExtra("notificationId", 0);

        type = intent.getStringExtra("type");
        msg = intent.getStringExtra("msg");

        if (type.equals("2"))
        {
            int notificationId = intent.getIntExtra("notificationId",0);


            if (notificationId != 0)
            {
                startActivity(new Intent(mcontext,Chat_Activity.class));

            } else {

                Intent intent1 = new Intent(mcontext, Login_Activity.class);
                startActivity(intent1);
                finish();
            }
        }
    }


}
