package com.example.admin.androidtree.base.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * @author Diana
 * @date 2017/6/28
 */

public class CarouselPagerAdapter extends PagerAdapter {
    private List<ImageView> mImages;
    private boolean mIsCycle;

    /**
     * @param images
     * @param isCycle true：循环滑动；false：不循环滑动
     */
    public CarouselPagerAdapter(@NonNull List<ImageView> images, @NonNull boolean isCycle) {

        this.mImages = images;
        this.mIsCycle = isCycle;
    }

    public void setIsCycle(boolean isCycle) {
        this.mIsCycle = isCycle;
    }

    @Override
    public int getCount() {
        if (mIsCycle) {
            return Integer.MAX_VALUE;
        } else {
            return mImages.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mImages == null || mImages.size() <= 0) {
            return null;
        }
        position %= mImages.size();
        ImageView view = mImages.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(mImages.get(position));
        return mImages.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
