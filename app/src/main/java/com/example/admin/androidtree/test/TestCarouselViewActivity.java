package com.example.admin.androidtree.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.base.widget.CarouselView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

/**
 * sample 自定义轮播图
 *
 * @author Diana
 * @date 2017/6/29
 */

public class TestCarouselViewActivity extends Activity implements
        CarouselView.OnCarouselViewItemClickListener, CarouselView.OnCarouselViewLastListener {
    private CarouselView carouselView;

    private RollPagerView mRollViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_carousel_view);

        initView();
        initView2();
    }

    /**
     * 方式二 使用第三方框架
     * git :https://github.com/Jude95/RollViewPager
     */
    private void initView2() {
        mRollViewPager = (RollPagerView) findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);
    }

    /**
     * 方式一： 使用自定义控件
     */
    private void initView() {
        carouselView = (CarouselView) findViewById(R.id.cv_images);
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.a);
        list.add(R.drawable.b);
        list.add(R.drawable.c);
        carouselView.setIsCycle(false);
        carouselView.setIsWheel(true);
        carouselView.setIsSlide(true);
        carouselView.setDate(list);
        carouselView.setOnItemClickListener(this);
        carouselView.setOnCarouselViewLastListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carouselView.startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        carouselView.stopTimer();
    }

    @Override
    public void onCarouselViewItemClick(int position) {
        Toast.makeText(TestCarouselViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowLastView() {
        Toast.makeText(TestCarouselViewActivity.this, "true", Toast.LENGTH_SHORT).show();
    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.e,
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }

}
