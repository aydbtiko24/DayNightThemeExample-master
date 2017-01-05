package com.nkdroid.day;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by AydbTiko on 11/3/2016.
 * this class is simple DialogFragment to switch current activated day night mode
 *
 * @see OnThemeChangedListener is simple interface that assignt value to MainActivity that user choice
 */

public class ThemePickDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.str_title_dialog));
        builder.setSingleChoiceItems(R.array.spinner_array_theme, HelperSharedPreference.getPreferenceValue(getActivity()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((OnThemeChangedListener) getActivity()).onThemeChanged(which);
                dismiss();
            }
        });
        
        return builder.create();

    }

    public interface OnThemeChangedListener {
        void onThemeChanged(int themeId);
    }

}

