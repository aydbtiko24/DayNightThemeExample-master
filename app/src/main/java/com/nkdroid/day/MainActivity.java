package com.nkdroid.day;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity implements ThemePickDialog.OnThemeChangedListener {
    TextView modeTypeTv, title, subTitle;
    CardView cView;
    ImageView imageView, img_view;
    Button notBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            setDefaultNightMode();
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ClickListener clickListener = new ClickListener(this);

        cView = (CardView) findViewById(R.id.cardView);
        imageView = (ImageView) findViewById(R.id.image_url);
        img_view = (ImageView) findViewById(R.id.img_view);

        title = (TextView) findViewById(R.id.title_main_activity);
        subTitle = (TextView) findViewById(R.id.sub_title_main_activity);

        Intent intent = getIntent();

        whatIsDefaultTextSize();

        notBut = (Button) findViewById(R.id.notif);

        modeTypeTv = (TextView) findViewById(R.id.txt_mode_type);
        modeTypeTv.setText(HelperSharedPreference.getTypeString(this));

        notBut.setOnClickListener(clickListener);

        cView.setOnClickListener(clickListener);

        loadImage();

        if (HelperSharedPreference.isFirstTimeLaunch(this)) {
            this.startActivity(new Intent(this, ActivityWelcome.class));
        }

    }


    void loadImage() {
        String imgUrl = getString(R.string.url);
        Glide.with(this)
                .load(imgUrl)
                .crossFade(200)
                .error(R.drawable.auto_image)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }


    private void setDefaultNightMode() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.ic_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_search:
                onSearchRequested();
                return true;
            case R.id.spinnerThemeAction:
                ThemePickDialog themePickDialog = new ThemePickDialog();
                getSupportFragmentManager().beginTransaction().add(themePickDialog, "dialog").commit();
                return true;
            case R.id.action1:
                startActivity(new Intent(this, SearchResultActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onThemeChanged(int themeId) {
        int activatedTheme = AppCompatDelegate.getDefaultNightMode();
        if (activatedTheme != themeId) {
            switch (themeId) {
                case 0:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    subTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    recreate();
                    break;
                case 1:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    subTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    recreate();
                    break;
                case 2:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
                    subTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    recreate();
                    break;
            }
            HelperSharedPreference.putSharedPreferenceValue(this, themeId);
        }
    }

    private void whatIsDefaultTextSize() {
        switch (HelperSharedPreference.getPreferenceValue(this)) {
            case 0:
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                subTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                break;
            case 1:
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                subTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                break;
            case 2:
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
                subTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                break;
        }
    }
}
