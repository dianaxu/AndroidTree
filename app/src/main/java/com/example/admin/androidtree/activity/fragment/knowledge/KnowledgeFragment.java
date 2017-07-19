package com.example.admin.androidtree.activity.fragment.knowledge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.activity.fragment.BaseFragment;
import com.example.admin.androidtree.activity.fragment.factory.IBuilderFactory;
import com.example.admin.androidtree.activity.fragment.knowledge.animator.AnimatorListFragment;
import com.example.admin.androidtree.activity.fragment.knowledge.customcontrol.CustomControlsListFragment;
import com.example.admin.androidtree.adapter.BaseFragmentAdapter;
import com.example.admin.androidtree.model.event.CheckedItemEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识库主页
 *
 * @author Diana
 * @date 2017/7/7
 */

public class KnowledgeFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment mDetailFragment;

    private List<BaseFragment> mFragments = new ArrayList<>();
    private BaseFragmentAdapter mAdapter;

    private boolean hasTwoPane = false;

    @Override
    public void onAttach(Context context) {
        if (context instanceof OnActionBarUpdateListener) {
            mBarUpdateListener = (OnActionBarUpdateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActionBarUpdateListener");
        }
        super.onAttach(context);
    }


    private void initFragments() {
        mFragments.add(new AnimatorListFragment());
        mFragments.add(new CustomControlsListFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knowledge_layout, container, false);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (mToolbar != null && mBarUpdateListener != null) {
            mBarUpdateListener.updateActionBar(mToolbar);
        }
        hasTwoPane = getResources().getBoolean(R.bool.has_two_pane);
        if (hasTwoPane) {
            EventBus.getDefault().register(this);
        }

        initFragments();
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments));
        mViewPager.setOffscreenPageLimit(0);

        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(CheckedItemEvent event) {
        if (event == null || !hasTwoPane) return;
        if (event.getBuilderFactory() == null) {
            throw new NullPointerException(event.getBuilderFactory().toString() + " can not null");
        }
        Fragment newFragment = event.getBuilderFactory().builderFragment(getChildFragmentManager(),
                event.getCheckItemIndex());
        if (mDetailFragment != null && newFragment.getClass().getSimpleName().equals(
                mDetailFragment.getClass().getSimpleName())) {
            return;
        }
        mDetailFragment = newFragment;
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_detail, mDetailFragment, mDetailFragment.getClass().getSimpleName())
                .commit();

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //如果是桌面设备,将右侧的fragment填充 或者移除
        if (hasTwoPane) {
            int position = tab.getPosition();
            IBuilderFactory factory = mAdapter.getItem(position).mBuilderFactory;
            if (factory != null) {
                mDetailFragment = factory.builderFragment(getChildFragmentManager(), factory.getSectionPosition());
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fl_detail, mDetailFragment, mDetailFragment.getClass().getSimpleName())
                        .commit();
            } else if (mDetailFragment != null) {
                getChildFragmentManager().beginTransaction()
                        .remove(mDetailFragment).commit();
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
