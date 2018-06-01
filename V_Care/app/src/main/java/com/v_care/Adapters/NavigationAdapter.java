package com.v_care.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.v_care.Activities.Login_Activity;
import com.v_care.Activities.MainPageActivity;
import com.v_care.R;
import com.v_care.Utilities.MySharedPrefrencesManagers;



//Adpter class for Side Panel
public class NavigationAdapter extends BaseAdapter {         //adapter class created

	Context mContext;
	FragmentManager mFrgManager;
	ViewHolder viewHolder;
	MainPageActivity mainPageActivity;



	public NavigationAdapter(Context mContext, FragmentManager mFrgManager) {      //constructor created
		this.mContext = mContext;
		this.mFrgManager = mFrgManager;
		mainPageActivity=(MainPageActivity) mContext;



	}
    //overriden methods
	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.dash_draw_item, parent, false);
			viewHolder = new ViewHolder();          //object created for class viewholder

			viewHolder.home = (TextView) convertView.findViewById(R.id.my_home);
			viewHolder.my_calender = (TextView) convertView.findViewById(R.id.my_calender);
			viewHolder.user_name = (TextView) convertView.findViewById(R.id.user_name);
			viewHolder.user_ph = (TextView) convertView.findViewById(R.id.user_ph);
			viewHolder.setting = (TextView) convertView.findViewById(R.id.setting);
			viewHolder.suggest = (TextView) convertView.findViewById(R.id.suggest);
			viewHolder.help = (TextView) convertView.findViewById(R.id.help);
			viewHolder.logout = (TextView) convertView.findViewById(R.id.logout);


			convertView.setTag(viewHolder);


		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Click Litner for items in Side Panel
		viewHolder.home.setOnClickListener(addHome);
		viewHolder.my_calender.setOnClickListener(my_calender);
		viewHolder.logout.setOnClickListener(logout);
		viewHolder.setting.setOnClickListener(setting);
		viewHolder.suggest.setOnClickListener(suggest);
		viewHolder.help.setOnClickListener(help);

		return convertView;
	}
	//OnClickListener's created
	OnClickListener user_img=new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

	OnClickListener addHome = new OnClickListener() {

		@Override
		public void onClick(View v) {
			//DashBoardActivity.displayview(dashBoardActivity.Catgaory,null);
			MainPageActivity.closeDrawer(mContext);
		}
	};

	OnClickListener my_calender = new OnClickListener() {

		@Override
		public void onClick(View v) {
			//DashBoardActivity.displayview(DashBoardActivity.Post_ad,null);
			MainPageActivity.closeDrawer(mContext);
		}
	};

	OnClickListener logout = new OnClickListener() {

		@Override
		public void onClick(View v) {
			//DashBoardActivity.displayview(DashBoardActivity.Post_ad,null);
			MainPageActivity.closeDrawer(mContext);
			open(v);
		}
	};
	OnClickListener setting = new OnClickListener() {

		@Override
		public void onClick(View v) {
			//DashBoardActivity.displayview(dashBoardActivity.All_ads,null);
			MainPageActivity.closeDrawer(mContext);
		}
	};

	OnClickListener suggest = new OnClickListener() {

		@Override
		public void onClick(View v) {


			//DashBoardActivity.displayview(dashBoardActivity.My_ads,null);
			MainPageActivity.closeDrawer(mContext);


		}

	};

		OnClickListener help = new OnClickListener() {

			@Override
			public void onClick(View v) {

				MainPageActivity.closeDrawer(mContext);
			}
		};

         //method created
		public void open(View view) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
			alertDialogBuilder.setMessage("Are you Sure you Want to Logout");

			alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {

					MySharedPrefrencesManagers.saveUserEmail(mContext, "");
					//SessionManager.ClearPref_Login(mContext);


					//MySharedPrefrencesManagers.clearloginPref(mContext);

					mContext.startActivity(new Intent(mContext, Login_Activity.class));
					mainPageActivity.finish();


				}
			});

			alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
      //class viewholder created
	public class ViewHolder
	{

		TextView home;
		TextView user_name;
		TextView my_calender;
		TextView user_ph;
		TextView setting;
		TextView suggest;

		TextView logout;
		TextView help;


	}

}
