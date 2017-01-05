package com.nkdroid.day;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by AydbTiko on 11/21/2016.
 * costume ViewPagerAdapter
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater layoutInflater;
    private int[] mLayouts;

    public ViewPagerAdapter(Context context, int[] layouts) {
        this.mContext = context;
        this.mLayouts = layouts;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(mLayouts[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mLayouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
