package com.v_care.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.v_care.Fragments.Information_Fragments;
import com.v_care.Fragments.Question_Fragmants;
import com.v_care.Fragments.Videos_fragments;

/**
 * Created by lenovo on 29-Mar-17.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {          //adapter created for respective java class
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {          //constructor created
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    //overriden methods
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Question_Fragmants tab1 = new Question_Fragmants();
                return tab1;
            case 1:
                Videos_fragments tab2 = new Videos_fragments();
                return tab2;
            case 2:
                Information_Fragments tab3 = new Information_Fragments();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
