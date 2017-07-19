package com.example.admin.androidtree.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.activity.fragment.knowledge.animator.AnimatorFragment;
import com.example.admin.androidtree.activity.fragment.knowledge.animator.AnimatorListFragment;
import com.example.admin.androidtree.activity.fragment.knowledge.customcontrol.CustomControlsListFragment;
import com.example.admin.androidtree.adapter.BaseFragmentAdapter;
import com.example.admin.androidtree.activity.fragment.BaseFragment;
import com.example.admin.androidtree.model.event.CheckedItemEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Diana
 * @date 2017/7/8
 */

public class TestTabLayoutActivity extends AppCompatActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment mCurrentFragment;

    private List<BaseFragment> mFragments = new ArrayList<>();
    private BaseFragmentAdapter mAdapter;

    private boolean hasTwoPane = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knowledge_layout);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        hasTwoPane = getResources().getBoolean(R.bool.has_two_pane);
        if (hasTwoPane) {
            EventBus.getDefault().register(this);
        }

        initFragments();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments));

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initFragments() {
        mFragments.add(new CustomControlsListFragment());
        mFragments.add(new AnimatorListFragment());
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(CheckedItemEvent event) {
        if (event == null || !hasTwoPane) return;
        if (event.getBuilderFactory() == null) {
            throw new NullPointerException(event.getBuilderFactory().toString() + " can not null");
        }
        mCurrentFragment = event.getBuilderFactory().builderFragment(getSupportFragmentManager(),
                event.getCheckItemIndex());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_detail, mCurrentFragment, mCurrentFragment.getTag())
                .commit();

    }
}
