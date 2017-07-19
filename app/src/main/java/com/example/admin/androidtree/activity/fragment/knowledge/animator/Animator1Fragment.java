package com.example.admin.androidtree.activity.fragment.knowledge.animator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.activity.fragment.BaseFragment;

/**
 * @author Diana
 * @date 2017/7/18
 */

public class Animator1Fragment extends BaseFragment implements View.OnClickListener {
    public static final int INDEX = 0;

    private ImageView ivAnim1;
    private AnimationSet set2;

    @Override
    public String getTabTitle() {
        return "动画1";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animator1, container, false);
        ivAnim1 = (ImageView) view.findViewById(R.id.iv_anim1);

        Button btnStart1 = (Button) view.findViewById(R.id.btn_start1);
        btnStart1.setOnClickListener(this);
        Button btnStart2 = (Button) view.findViewById(R.id.btn_start2);
        btnStart2.setOnClickListener(this);

        initAnimator2();
        return view;
    }

    /**
     * AccelerateDecelerateInterpolator  动画始末速率较慢，中间加速
     * AccelerateInterpolator  动画开始速率较慢，之后慢慢加速
     * AnticipateInterpolator  	开始的时候从后向前甩
     * AnticipateOvershootInterpolator  类似上面AnticipateInterpolator
     * BounceInterpolator  动画结束时弹起
     * CycleInterpolator   循环播放速率改变为正弦曲线
     * DecelerateInterpolator  动画开始快然后慢
     * LinearInterpolator  动画匀速改变
     * OvershootInterpolator  向前弹出一定值之后回到原来位置
     * PathInterpolator 新增，定义路径坐标后按照路径坐标来跑
     */
    private void initAnimator2() {

        //移动
        TranslateAnimation ta = new TranslateAnimation(0f, 100f, 0f, 100f);
        ta.setDuration(100);

        //匀速动画
        ta.setInterpolator(new LinearInterpolator());

        //旋转
        RotateAnimation ra = new RotateAnimation(0f, 90f);
        ra.setDuration(400);

        //集合
        set2 = new AnimationSet(false);
        set2.setFillAfter(true);
        set2.setZAdjustment(Animation.ZORDER_NORMAL);
        set2.addAnimation(ta);
        set2.addAnimation(ra);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start1:
                startAnimation1();
                break;
            case R.id.btn_start2:
                startAnimation2();
                break;
        }
    }

    private void startAnimation1() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.animation_test);
        ivAnim1.startAnimation(animation);
    }

    private void startAnimation2() {
        ivAnim1.startAnimation(set2);
    }

}
