package com.v_care.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.v_care.Beans.Questions_bean;
import com.v_care.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 31-Mar-17.
 */
public class Question_Adapter extends BaseAdapter {

    Context mContext;
    ArrayList<Questions_bean> question_array;
    View view;
    ViewHolder mholder;
    LayoutInflater inflator;


    public Question_Adapter(Context mContext, ArrayList<Questions_bean> question_array) {        //constructor created
        this.mContext = mContext;
        this.question_array = question_array;
        inflator = LayoutInflater.from(mContext);


    }
    //overriden methods
    @Override
    public int getCount() {
        return question_array.size();
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
            view = inflator.inflate(R.layout.question_custom, parent, false);
            mholder = new ViewHolder();   // object created for class viewholder

            mholder.num = (TextView) view.findViewById(R.id.num);
            mholder.qus_txt = (TextView) view.findViewById(R.id.qus_txt);


            view.setTag(mholder);
        } else {
            mholder = (ViewHolder) view.getTag();


        }

         mholder.num.setText(""+question_array.get(position).getSerialno());
         mholder.qus_txt.setText(question_array.get(position).getQuestion());

        return view;
    }
    // class viewholder created
    public class ViewHolder {

        TextView num;
        TextView qus_txt;

    }

}

