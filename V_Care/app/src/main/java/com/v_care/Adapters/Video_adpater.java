package com.v_care.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.v_care.Beans.Video_bean;
import com.v_care.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 31-Mar-17.
 */
public class Video_adpater extends BaseAdapter
{
    Context mContext;
    ArrayList<Video_bean> Video_array;
    View view;
    ViewHolder mholder;
    LayoutInflater inflator;

    //constructor created
    public Video_adpater(Context mContext, ArrayList<Video_bean> Video_array) {
        this.mContext = mContext;
        this.Video_array = Video_array;
        inflator = LayoutInflater.from(mContext);


    }
     //overriden methods
    @Override
    public int getCount() {
        return Video_array.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;

        if (view == null) {
            view = inflator.inflate(R.layout.video_custom, parent, false);
            mholder = new ViewHolder();      //object created for class viewholder

            mholder.video_thumb = (ImageView) view.findViewById(R.id.video_thumb);
            mholder.video_title = (TextView) view.findViewById(R.id.video_title);
            mholder.video_desc = (TextView) view.findViewById(R.id.video_desc);
            view.setTag(mholder);
        } else {
            mholder = (ViewHolder) view.getTag();


        }

        mholder.video_title.setText(Video_array.get(position).getVideo_title());
        mholder.video_desc.setText(Video_array.get(position).getVideo_description());
        Picasso.with(mContext).load(Video_array.get(position).getVideo_thumb()).into(mholder.video_thumb);

        return view;
    }

    //class viewholder created
    public class ViewHolder {

        ImageView video_thumb;
        TextView video_title;
        TextView video_desc;


    }

}
