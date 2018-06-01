package com.v_care.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.v_care.Beans.Info_bean;
import com.v_care.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 10-Apr-17.
 */
public class Information_adapter extends BaseAdapter
{

    Context mContext;
    ArrayList<Info_bean> information_array;
    View view;
    ViewHolder mholder;
    LayoutInflater inflator;


    public Information_adapter(Context mContext, ArrayList<Info_bean> information_array) {    //constructor created
        this.mContext = mContext;
        this.information_array = information_array;
        inflator = LayoutInflater.from(mContext);


    }
     // overriden methods
    @Override
    public int getCount() {
        return information_array.size();
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
            view = inflator.inflate(R.layout.custom_info, parent, false);
            mholder = new ViewHolder();         //object of class viewholder

            mholder.video_thumb = (ImageView) view.findViewById(R.id.video_thumb);
            mholder.list_image = (ImageView) view.findViewById(R.id.list_image);
            mholder.title = (TextView) view.findViewById(R.id.title);
            mholder.rel_video = (RelativeLayout) view.findViewById(R.id.rel_video);
            view.setTag(mholder);
        } else {
            mholder = (ViewHolder) view.getTag();


        }

         mholder.title.setText(information_array.get(position).getInformation_description());
         if(information_array.get(position).getInfo_file_type().equals("image"))
         {
             mholder.rel_video.setVisibility(View.GONE);
             mholder.list_image.setVisibility(View.VISIBLE);
             Picasso.with(mContext).load(information_array.get(position).getInfo_img_name()).into(mholder.list_image);
         }
        else
         {
             if(information_array.get(position).getInfo_file_type().equals("video"))
             {
                 mholder.rel_video.setVisibility(View.VISIBLE);
                 mholder.list_image.setVisibility(View.GONE);
                 Picasso.with(mContext).load(information_array.get(position).getVideo_thumbnail()).into(mholder.video_thumb);
             }
         }
        return view;
    }

    //viewholder class created
    public class ViewHolder
    {
       ImageView list_image;
       RelativeLayout rel_video;
        ImageView video_thumb;
       TextView title;
    }
}
