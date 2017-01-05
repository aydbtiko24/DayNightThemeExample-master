package com.nkdroid.day;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by AydbTiko on 11/4/2016.
 * this class is a Helper class used to get and put SharedPreference Value
 */

public class HelperSharedPreference {


    /**
     * Method to get current preference value
     *
     * @param context Context used to get SharedPreference
     * @return The theme that have activated integer type
     */
    static int getPreferenceValue(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(context.getResources().getString(R.string.key_theme_preference), context.getResources().getInteger(R.integer.default_value_theme_preference));
    }

    /**
     * Method to put what user choice to preference
     *
     * @param context Context used to get SharedPreference
     * @param value   Value what user selected
     */
    static void putSharedPreferenceValue(Context context, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt(context.getString(R.string.key_theme_preference), value).apply();
    }

    static String getTypeString(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int value = sharedPreferences.getInt(context.getResources().getString(R.string.key_theme_preference), context.getResources().getInteger(R.integer.default_value_theme_preference));
        String txt = null;
        switch (value) {
            case 0:
                txt = context.getString(R.string.default_auto);
                break;
            case 1:
                txt = context.getString(R.string.default_day);
                break;
            case 2:
                txt = context.getString(R.string.default_night);
                break;
        }
        return txt;

    }


    static void setFirstTimeLaunch(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(context.getString(R.string.launch_status_key), false)
                .apply();
    }

    static boolean isFirstTimeLaunch(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.launch_status_key), true);
    }


    static void animatedView(View view) {
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);

        Animator animator;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, initialRadius);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            view.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

}
