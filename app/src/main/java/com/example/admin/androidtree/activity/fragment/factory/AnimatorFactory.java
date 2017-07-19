package com.example.admin.androidtree.activity.fragment.factory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.admin.androidtree.activity.fragment.HeartilyExpectFragment;
import com.example.admin.androidtree.activity.fragment.knowledge.animator.Animator1Fragment;

/**
 * 动画工厂
 * 统一创建动画选项，需要的界面
 *
 * @author Diana
 * @date 2017/7/18
 */

public class AnimatorFactory implements IBuilderFactory {
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
            case Animator1Fragment.INDEX:
                tag = Animator1Fragment.class.getSimpleName();
                fr = manager.findFragmentByTag(tag);
                if (fr == null)
                    fr = new Animator1Fragment();
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
