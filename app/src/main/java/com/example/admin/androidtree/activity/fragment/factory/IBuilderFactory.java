package com.example.admin.androidtree.activity.fragment.factory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.admin.androidtree.activity.fragment.BaseFragment;

/**
 * @author Diana
 * @date 2017/7/11
 */

public interface IBuilderFactory  {
    int getSectionPosition();

    Fragment builderFragment(FragmentManager manager, int index);
}
