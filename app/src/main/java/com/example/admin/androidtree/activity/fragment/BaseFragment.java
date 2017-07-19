package com.example.admin.androidtree.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.activity.fragment.factory.IBuilderFactory;

/**
 * @author Diana
 * @date 2017/7/8
 */

public class BaseFragment extends Fragment {
    public Context mContext;
    public IBuilderFactory mBuilderFactory;

    public OnActionBarUpdateListener mBarUpdateListener;

    public String getTitle() {
        return getTabTitle();
    }

    public String getTabTitle() {
        return getResources().getString(R.string.tab_default_title);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    public interface OnActionBarUpdateListener {
        void updateActionBar(Toolbar toolbar);
    }
}
