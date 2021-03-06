package com.xiaweizi.scrolldemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.touch.GestureTouchUtils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaweizi.scrolldemo.PullToRefreshLayout.SUCCEED;

public class ScrollActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PullToRefreshLayout refreshLayout;
    private MyCoordinatorLayout coordinatorLayout;
    private int verticalOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.RED);
        setContentView(R.layout.activity_scroll);
        initRefreshLayout();
        mViewPager = findViewById(R.id.viewPager);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                ScrollActivity.this.verticalOffset = Math.abs(verticalOffset);
            }
        });
        findViewById(R.id.bt_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestureTouchUtils.simulateScroll(ScrollActivity.this, 400, 400, 400, 400 + verticalOffset, 50, GestureTouchUtils.HIGH);
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> data = new ArrayList<>();
        String[] titles = new String[]{"fragment1", "fragment2", "fragment3"};

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            for (int i = 0; i < 3; i++) {
                data.add(new ContentFragment());
            }
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private void initRefreshLayout() {
        refreshLayout = findViewById(R.id.pull_refresh);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.refreshFinish(SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
    }
}
