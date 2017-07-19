package com.example.admin.androidtree.activity.fragment.knowledge.customcontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.activity.fragment.BaseFragment;
import com.example.admin.androidtree.base.widget.CarouselView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义控件之  轮播图demo展示
 *
 * @author Diana
 * @date 2017/7/9
 */

public class CarouselFragment extends BaseFragment {
    public static final int INDEX = 0;

    private CarouselView carouselView1;
    private CarouselView carouselView2;
    private CarouselView carouselView6;
    private CarouselView carouselView7;

    private List<Integer> mData;

    @Override
    public String getTabTitle() {
        return "轮播图";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carousel, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

        initCarouselView1(view);
        initCarouselView2(view);
        initCarouselView3(view);
        initCarouselView4(view);
        initCarouselView5(view);
        initCarouselView6(view);
        initCarouselView7(view);
        initCarouselView8(view);
    }

    private void initData() {
        mData = new ArrayList<>();
        mData.add(R.drawable.a);
        mData.add(R.drawable.b);
        mData.add(R.drawable.c);
        mData.add(R.drawable.d);
        mData.add(R.drawable.e);
    }

    @Override
    public void onStart() {
        super.onStart();
        carouselView1.startTimer();
        carouselView2.startTimer();
        carouselView6.startTimer();
        carouselView7.startTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        carouselView1.stopTimer();
        carouselView2.stopTimer();
        carouselView6.stopTimer();
        carouselView7.stopTimer();
    }

    //1.不支持滑动，循环滑动，自动播放，无事件监控
    private void initCarouselView1(View view) {
        carouselView1 = (CarouselView) view.findViewById(R.id.cv_images1);
        carouselView1.setDate(mData)
                .setIsSlide(false)
                .setIsCycle(true)
                .setIsWheel(true);
    }

    //2.不支持滑动，不循环滑动，自动播放，播放|滑动到最后监控
    private void initCarouselView2(View view) {
        carouselView2 = (CarouselView) view.findViewById(R.id.cv_images2);
        carouselView2.setDate(mData)
                .setIsSlide(false)
                .setIsCycle(false)
                .setIsWheel(true);
        carouselView2.setOnCarouselViewLastListener(new CarouselView.OnCarouselViewLastListener() {
            @Override
            public void onShowLastView() {
                showSnackbar("已是最后一页");
            }
        });
    }

    //3.不支持滑动，循环滑动，不自动播放，播放|滑动到最后监控
    private void initCarouselView3(View view) {
        CarouselView carouselView3 = (CarouselView) view.findViewById(R.id.cv_images3);
        carouselView3.setDate(mData)
                .setIsSlide(false)
                .setIsCycle(true)
                .setIsWheel(false);
        carouselView3.setOnCarouselViewLastListener(new CarouselView.OnCarouselViewLastListener() {
            @Override
            public void onShowLastView() {
                showSnackbar("已是最后一页");
            }
        });
    }

    //4.支持滑动，不循环滑动，不自动播放，无事件监控
    private void initCarouselView4(View view) {
        CarouselView carouselView4 = (CarouselView) view.findViewById(R.id.cv_images4);
        carouselView4.setDate(mData);
    }

    // 5.支持滑动，循环滑动，不自动播放，无事件监控
    private void initCarouselView5(View view) {
        CarouselView carouselView5 = (CarouselView) view.findViewById(R.id.cv_images5);
        carouselView5.setDate(mData)
                .setIsCycle(true)
                .setIsWheel(false);
    }

    //6.支持滑动，不循环滑动，自动播放，无事件监控
    private void initCarouselView6(View view) {
        carouselView6 = (CarouselView) view.findViewById(R.id.cv_images6);
        carouselView6.setDate(mData)
                .setIsCycle(false)
                .setIsWheel(true);
    }

    // 7.支持滑动，循环滑动，自动播放，无事件监控
    private void initCarouselView7(View view) {
        carouselView7 = (CarouselView) view.findViewById(R.id.cv_images7);
        carouselView7.setDate(mData)
                .setIsCycle(true)
                .setIsWheel(true);
    }

    //8.支持滑动，循环滑动，不自动播放，点击事件监听
    private void initCarouselView8(View view) {
        CarouselView carouselView8 = (CarouselView) view.findViewById(R.id.cv_images8);
        carouselView8.setDate(mData)
                .setIsCycle(true);
        carouselView8.setOnItemClickListener(new CarouselView.OnCarouselViewItemClickListener() {
            @Override
            public void onCarouselViewItemClick(int position) {
                showSnackbar("position：" + position);
            }
        });
    }


    public void showSnackbar(String msg) {
        Snackbar.make(getView().findViewById(R.id.cl_view), msg, Snackbar.LENGTH_SHORT).show();
    }
}
