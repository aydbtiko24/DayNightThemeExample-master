package com.nkdroid.day;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ModeActivity extends AppCompatActivity {

    TextView txtModeType, txtTitle, txtDetail;
    int modeType;
    ImageView imageView;

    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_DETAIL = "extra_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cekTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_mode);
        txtModeType = (TextView) findViewById(R.id.txtModeType);
        imageView = (ImageView) findViewById(R.id.image_url_auto_mode);
        txtTitle = (TextView) findViewById(R.id.title_detail_activity);
        txtDetail = (TextView) findViewById(R.id.sub_title_detail_activity);

        Intent intent = getIntent();

        String title = intent.getStringExtra(EXTRA_TITLE);
        String detail = intent.getStringExtra(EXTRA_DETAIL);

        txtTitle.setText(title);
        txtDetail.setText(detail);

        modeType = AppCompatDelegate.getDefaultNightMode();

        if (modeType == AppCompatDelegate.MODE_NIGHT_AUTO) {
            txtModeType.setText(R.string.default_auto);
        } else if (modeType == AppCompatDelegate.MODE_NIGHT_YES) {
            txtModeType.setText(R.string.default_night);
        } else if (modeType == AppCompatDelegate.MODE_NIGHT_NO) {
            txtModeType.setText(R.string.default_day);
        }

        loadImage();
    }

    void loadImage() {
        String imgUrl = "https://static.pexels.com/photos/177707/pexels-photo-177707.jpeg";
        Glide.with(this)
                .load(imgUrl)
                .crossFade(200)
                .error(R.drawable.auto_image)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    private void cekTheme() {
        int activatedTheme = AppCompatDelegate.getDefaultNightMode();
        int pref = HelperSharedPreference.getPreferenceValue(this);
        if (activatedTheme != pref) {
            Toast.makeText(this, "Theme need update", Toast.LENGTH_SHORT).show();
            switch (HelperSharedPreference.getPreferenceValue(this)) {
                case 0:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                    break;
                case 1:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case 2:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        }
    }
}
