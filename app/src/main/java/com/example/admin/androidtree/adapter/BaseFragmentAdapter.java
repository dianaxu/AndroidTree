package com.example.admin.androidtree.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin.androidtree.activity.fragment.BaseFragment;

import java.util.List;

/**
 * @author Diana
 * @date 2017/7/8
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mDate;

    public BaseFragmentAdapter(FragmentManager fm, @NonNull List<BaseFragment> date) {
        super(fm);
        this.mDate = date;
    }

    @Override
    public BaseFragment getItem(int position) {
        return mDate.get(position);
    }

    @Override
    public int getCount() {
        return mDate.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDate.get(position).getTitle();
    }
}
