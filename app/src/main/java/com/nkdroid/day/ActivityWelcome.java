package com.nkdroid.day;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActivityWelcome extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    LinearLayout dotsLayout;
    int[] layouts;
    Button btnSkip, btnNext;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};

        viewPagerAdapter = new ViewPagerAdapter(this, layouts);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(viewPagerChangeListener);
        tabLayout.setupWithViewPager(viewPager);
        btnNext.setOnClickListener(onClickListener);
        btnSkip.setOnClickListener(onClickListener);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        HelperSharedPreference.setFirstTimeLaunch(this);
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            //changing the next button text 'NEXT' to 'GOT IT'
            if (position == layouts.length - 1) {
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    Button.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_next:
                    int current = getItem(+1);
                    if (current < layouts.length) {
                        viewPager.setCurrentItem(current);
                    } else {
                        launchHomeScreen();
                    }
                    break;
                case R.id.btn_skip:
                    launchHomeScreen();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
/*
            super.onBackPressed();
*/
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}
