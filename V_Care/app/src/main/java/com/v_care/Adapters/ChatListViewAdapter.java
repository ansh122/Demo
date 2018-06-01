package com.v_care.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.v_care.Beans.ChatMsg_bean;
import com.v_care.R;

import java.util.ArrayList;

public class ChatListViewAdapter extends BaseAdapter {   //adapter class for respective chat class

	ViewHolder mHolder;
	Context mContext;
	ArrayList<ChatMsg_bean> mList;    //create array
	LayoutInflater inflator;
	View view;

	public ChatListViewAdapter(ArrayList<ChatMsg_bean> list, Context ctx) {    //constructor called
		mList = list;
		mContext = ctx;
		inflator = LayoutInflater.from(mContext);
	}
	//overriden methods
	@Override
	public int getCount() {
		return mList.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		view = convertView;

		if (view == null) {
	
			view = inflator.inflate(R.layout.chat_custom, parent, false);
			mHolder = new ViewHolder();   //created object of viewholder
	
			mHolder.incoming_txt = (TextView) view.findViewById(R.id.incoming_txt);
			mHolder.outgoing_txt = (TextView) view.findViewById(R.id.outgoing_txt);
			mHolder.incoming_time = (TextView) view.findViewById(R.id.incoming_time);
			mHolder.outgoing_time = (TextView) view.findViewById(R.id.outgoing_time);
			mHolder.liner_incoming = (LinearLayout) view.findViewById(R.id.liner_incoming);
			mHolder.liner_outgoing = (LinearLayout) view.findViewById(R.id.liner_outgoing);

		
			view.setTag(mHolder);
			
		} else {
			
			mHolder = (ViewHolder) view.getTag();
		}
		
		if(mList.get(position).getSent_by_admin().equals("1"))
		{
			mHolder.incoming_txt.setText(mList.get(position).getMessage());
			mHolder.incoming_time.setText(mList.get(position).getCreated_at());
			mHolder.liner_outgoing.setVisibility(View.GONE);
			mHolder.liner_incoming.setVisibility(View.VISIBLE);

		}
		else
		{
			if(mList.get(position).getSent_to_admin().equals("1"))
			{

				mHolder.outgoing_txt.setText(mList.get(position).getMessage());
				mHolder.outgoing_time.setText(mList.get(position).getCreated_at());
				mHolder.liner_outgoing.setVisibility(View.VISIBLE);
				mHolder.liner_incoming.setVisibility(View.GONE);
			}
		}

		
		return view;
	}
   //class viewholder created
	public class ViewHolder {
		
		public TextView incoming_txt;
		public TextView outgoing_txt;
		public TextView incoming_time;
		public TextView outgoing_time;
		LinearLayout liner_incoming;
		LinearLayout liner_outgoing;

	}

}