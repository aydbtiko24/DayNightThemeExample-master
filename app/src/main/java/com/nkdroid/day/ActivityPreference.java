package com.nkdroid.day;

import android.annotation.TargetApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class ActivityPreference extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    final static String TAG = ActivityPreference.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*using deprecated class just for support older version of android os*/
        addPreferencesFromResource(R.xml.setting_preference);
        /*for name preference setting*/
        bindPreferenceSummaryToValue(findPreference(getString(R.string.key_theme)));
        Log.d(TAG, "onCreate");
    }


    private void bindPreferenceSummaryToValue(Preference preference) {
        //Set the listener to watch value changes
        preference.setOnPreferenceChangeListener(this);

        //Trigger the listener immediately with the preference's current value
        setPreferenceSummary(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }


    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            /*for list preferences, look up the correct display value in
            * the preference's 'entries' list (since they have separate labels/values)*/
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        Log.d(TAG, "onPreferenceChange ");
    }

    /**
     * This gets called before the preference is changed
     *
     * @param preference Preference
     * @param value      value of preference
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        setPreferenceSummary(preference, value);
        return true;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }


}
