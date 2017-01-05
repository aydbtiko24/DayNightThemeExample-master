package com.nkdroid.day;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AutoCompleteTextView;

/**
 * Created by AydbTiko on 11/11/2016.
 */

public class AutoCompleteTextPreference extends EditTextPreference implements LoaderManager.LoaderCallbacks<Cursor> {
    AutoCompleteTextView completeTextView = null;
    private static final int REQUEST_READ_CONTACTS = 0;
    Context mContext;

    public AutoCompleteTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        completeTextView = new AutoCompleteTextView(context, attrs);
        completeTextView.setThreshold(0);

    }

    @Override
    protected void onBindDialogView(View view) {
        AutoCompleteTextView editText = completeTextView;
        ViewParent oldParent = editText.getParent();
        if (oldParent != view) {
            if (oldParent != null) {
                ((ViewGroup) oldParent).removeView(editText);
            }
            onAddEditTextToDialogView(view, editText);
        }
        super.onBindDialogView(view);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
