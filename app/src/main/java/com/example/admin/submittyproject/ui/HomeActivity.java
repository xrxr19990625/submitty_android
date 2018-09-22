package com.example.admin.submittyproject.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.admin.submittyproject.R;
import com.example.admin.submittyproject.ui.homepage.ForumFragment;
import com.example.admin.submittyproject.ui.homepage.GradeFragment;
import com.example.admin.submittyproject.ui.homepage.NotificationFragment;
import com.example.admin.submittyproject.ui.homepage.PagerAdapter;
import com.example.admin.submittyproject.ui.homepage.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tl)
    android.support.design.widget.TabLayout tl;
    private PagerAdapter mPagerAdapter;
    private List<String> mTitles;
    private List<Fragment> mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        mTitles.add("Forum");
        mTitles.add("Notification");
        mTitles.add("Grades");
        mTitles.add("Me");
        mFragments.add(ForumFragment.getInstance());
        mFragments.add(NotificationFragment.getInstance());
        mFragments.add(GradeFragment.getInstane());
        mFragments.add(PersonalFragment.getInstance());
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        vp.setAdapter(mPagerAdapter);
        tl.setupWithViewPager(vp);
        for (int i = 0;i < tl.getTabCount();i ++){
            TabLayout.Tab tab = tl.getTabAt(i);
            tab.setCustomView(R.layout.tab_item_layout);
            TextView textView = tab.getCustomView().findViewById(R.id.tv_tabItemName);
            textView.setText(mTitles.get(i));
        }
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tv_tabItemName);
                textView.setTextColor(getResources().getColor(R.color.themeColor));
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tv_tabItemName);
                textView.setTextColor(getResources().getColor(R.color.gray));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //判断是否有焦点
        if(hasFocus){
            final View decorView = getWindow().getDecorView();
            final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int i) {
                    if((i & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                    {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }
}
