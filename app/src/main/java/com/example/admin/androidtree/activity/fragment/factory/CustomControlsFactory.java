package com.example.admin.androidtree.activity.fragment.factory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.admin.androidtree.activity.fragment.HeartilyExpectFragment;
import com.example.admin.androidtree.activity.fragment.knowledge.customcontrol.CarouselFragment;

import java.io.Serializable;

/**
 * 自定义控件工厂
 * 统一创建出各种自定义控件选项，需要的界面
 *
 * @author Diana
 * @date 2017/7/11
 */

public class CustomControlsFactory implements IBuilderFactory, Serializable {
    private int mCurrentIndex = 0;

    @Override
    public int getSectionPosition() {
        return mCurrentIndex;
    }

    @Override
    public Fragment builderFragment(FragmentManager manager, int index) {
        mCurrentIndex = index;
        String tag = null;
        Fragment fr = null;
        switch (index) {
            case CarouselFragment.INDEX:
                tag = CarouselFragment.class.getSimpleName();
                fr = manager.findFragmentByTag(tag);
                if (fr == null)
                    fr = new CarouselFragment();
                break;
            default:
                tag = HeartilyExpectFragment.class.getSimpleName();
                fr = manager.findFragmentByTag(tag);
                if (fr == null)
                    fr = new HeartilyExpectFragment();
                break;
        }
        return fr;
    }

}
