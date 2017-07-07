package com.example.admin.androidtree.base.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.base.adapter.CarouselPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义控件--轮播图V1
 * <p>
 * 支持的功能介绍：
 * 1、是否支持滑动,调用{@link #setIsSlide(boolean)}进行设置;默认支持滑动{@link #isSlide}
 * 2、是否支持循环滑动,调用{@link #setIsCycle(boolean)}进行设置;默认不支持循环滑动{@link #isCycle}
 * 3、是否支持自动播放，调用{@link #setIsWheel(boolean)}进行设置；默认不支持自动播放{@link #isWheel}
 * 4、如果需要对点击图片做相应的操作，请注册监听OnCarouselViewItemClickListener  调用
 * {@link #setOnItemClickListener(OnCarouselViewItemClickListener)}，在onCarouselViewItemClick
 * 方法中实现你想要做的事情；
 * 5、如果你需要对已经播放到最后或者滑动到最后一个view时做一些操作，那么请注册监听OnCarouselViewLastListener
 * {@link #setOnCarouselViewLastListener(OnCarouselViewLastListener)},在onShowLastView方法中
 * 实现你想要做的事情；
 * 6、可自定义设置自动播放的时间间隔{@link #setSwitchTime(int)};默认自动播放时间间隔为3000毫秒；
 * 7、可设置指示器是否显示 {@link #setDotsVisility(int)}; 默认显示；
 * <p>
 * 支持的场景：
 * 1：启动导航页，逐页跳转到最后，在跳转到主页面；
 * 2：广告位,无限轮播；
 * <p>
 * 注：
 * 1、必须调用{@link #setDate(List)} ；
 * 2、目前暂未支持url图片及自定义属性设置，下一版敬请期待；
 *
 * @author Diana
 * @date 2017/6/28
 */

public class CarouselView extends FrameLayout implements ViewPager.OnPageChangeListener,
        View.OnTouchListener {

    /**
     * 轮播切换小圆点宽度 ---默认宽度
     */
    private static final int DOT_DEFAULT_W = 5;

    /**
     * 轮播图切换小圆点宽度---选中时宽度
     */
    private static final int DOT_SELECTED_W = 8;

    /**
     * 默认的轮播时间
     */
    private static final int DEFAULT_TIME = 3000;

    /**
     * 正常时小圆点宽度
     */
    private int IndicatorDotWidth = DOT_DEFAULT_W;

    /**
     * 选中时小圆点宽度
     */
    private int IndicatorDotSelectedWidth = DOT_SELECTED_W;

    /**
     * 设置轮播时间
     */
    private int switchTime = DEFAULT_TIME;

    private Context mContext;
    private ViewPager vpImages;
    private LinearLayout llDots;

    private CarouselPagerAdapter mAdapter;
    private List<ImageView> mImageViews;

    private int mShowCount;       //需要显示的数量
    private int mCurrentIndex;   //记录之前位置
    private OnCarouselViewItemClickListener mItemClickListener;
    private OnCarouselViewLastListener mLastListener;

    private int flag = MotionEvent.ACTION_MOVE;  //设置touchEvent标识

    //循环播放属性
    private boolean isCycle = false;    //是否循环平滑
    private boolean isWheel = false;   //是否轮播
    private boolean isUserTouched = false;  //用户是否干预
    private boolean isSlide = true;      //用户是否可以滑动
    /**
     * 用户是否干预
     */
    Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            vpImages.setCurrentItem(vpImages.getCurrentItem() + 1);
            vpImages.postDelayed(runnable, switchTime);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isUserTouched) {
                return;
            }
            if (mCurrentIndex == mAdapter.getCount() - 1) {
                stopTimer();
                return;
            }
            //跳转到下一个图片
            mHandle.sendEmptyMessage(1);
        }
    };




    /**
     * 设置数据
     *
     * @param list
     */
    public void setDate(List<Integer> list) {
        mShowCount = list.size();

        //创建ImageViews
        createImageViewsByResId(list);
        //创建点
        createDotViews();

        mAdapter.notifyDataSetChanged();
        resetCurrentIndex();
    }

    /**
     * 设置是否循环
     *
     * @param isCycle
     */
    public void setIsCycle(boolean isCycle) {
        this.isCycle = isCycle;
        vpImages.setAdapter(mAdapter = new CarouselPagerAdapter(mImageViews, isCycle));
        mAdapter.notifyDataSetChanged();
        resetCurrentIndex();
    }

    /**
     * @param isSlide
     */
    public void setIsSlide(final boolean isSlide) {
        this.isSlide = isSlide;
    }

    /**
     * 设置循环播放
     *
     * @param isWheel
     */
    public void setIsWheel(boolean isWheel) {
        this.isWheel = isWheel;
    }

    /**
     * 设置指示器是否显示
     *
     * @param visibility View.GONE,View.VISIBILITY,View.NONE
     */
    public void setDotsVisility(int visibility) {
        llDots.setVisibility(visibility);
    }

    /**
     * 可自定义设置轮播图切换时间，单位毫秒
     *
     * @param switchTime millseconds
     */
    public void setSwitchTime(int switchTime) {
        this.switchTime = switchTime;
    }

    /**
     * 设置view点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(OnCarouselViewItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    /**
     * 设置显示到最后一页时监听
     *
     * @param listener
     */
    public void setOnCarouselViewLastListener(OnCarouselViewLastListener listener) {
        this.mLastListener = listener;
    }

    /**
     * 开始播放
     */
    public void startTimer() {
        if (isWheel) {
            vpImages.postDelayed(runnable, switchTime);
        }
    }

    /**
     * 停止播放
     */
    public void stopTimer() {
        if (isWheel) {
            vpImages.removeCallbacks(runnable);
        }
    }

    public CarouselView(Context context) {
        super(context);
        this.mContext = context;
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(mContext).inflate(R.layout.widget_carousel_view, null);
        vpImages = (ViewPager) view.findViewById(R.id.gallery);
        llDots = (LinearLayout) view.findViewById(R.id.CarouselLayoutPage);

        mImageViews = new ArrayList<>();
        mShowCount = 0;
        vpImages.setAdapter(mAdapter = new CarouselPagerAdapter(mImageViews, isCycle));
        vpImages.addOnPageChangeListener(this);
        vpImages.setOnTouchListener(this);
        addView(view);
    }

    //重置当前项
    private void resetCurrentIndex() {
        if (mImageViews != null && mShowCount > 0) {
            //设置当前选中项
            mCurrentIndex = mAdapter.getCount() / 2 - (mAdapter.getCount() / 2) % mShowCount;
            vpImages.setCurrentItem(mCurrentIndex);

            setDotSelected(mCurrentIndex);
        }
    }

    /**
     * 加载本地资源图片
     *
     * @param list
     */
    private void createImageViewsByResId(List<Integer> list) {
        if (mImageViews == null) {
            mImageViews = new ArrayList<>();
        }
        mImageViews.clear();
        int len = list.size();
        for (int i = 0; i < len; i++) {
            ImageView view = new ImageView(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(list.get(i));
            mImageViews.add(view);
        }
    }

    //创建指示器
    private void createDotViews() {
        llDots.removeAllViews();
        if (mImageViews != null && mShowCount > 0) {
            createDotViews(mShowCount);
        }
    }

    private void createDotViews(int count) {
        for (int i = 0; i < count; i++) {
            View view = new View(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    IndicatorDotWidth, IndicatorDotWidth
            );
            params.setMargins(DOT_DEFAULT_W, 0, 0, 0);
            view.setLayoutParams(params);
            view.setSelected(false);
            view.setBackgroundResource(R.drawable.carousel_layout_dot);
            llDots.addView(view);
        }
    }

    //设置小圆点选中状态
    private void setDotSelected(int newPosition) {
        if (mImageViews == null || mShowCount == 0) {
            return;
        }
        newPosition %= mShowCount;
        setDotLayoutarams(IndicatorDotWidth, false, llDots.getChildAt(mCurrentIndex % mShowCount));
        setDotLayoutarams(IndicatorDotSelectedWidth, true, llDots.getChildAt(newPosition));
        mCurrentIndex = newPosition;
    }

    //这是小圆点样式
    private void setDotLayoutarams(int width, boolean selected, View view) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = width;
        view.setLayoutParams(params);
        view.setSelected(selected);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setDotSelected(position);

        boolean isLastView = position % mShowCount == mShowCount - 1;
        if (isLastView && mLastListener != null) {
            mLastListener.onShowLastView();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = MotionEvent.ACTION_DOWN;
                isUserTouched = false;
                break;
            case MotionEvent.ACTION_MOVE:
                flag = MotionEvent.ACTION_MOVE;
                isUserTouched = true;
                break;
            case MotionEvent.ACTION_UP:
                isUserTouched = false;
                //在自动播放情况下，需要触发继续轮询
                if (isWheel) {
                    stopTimer();
                    startTimer();
                }
                //表示用户点击了下
                if (flag == MotionEvent.ACTION_DOWN && mItemClickListener != null) {
                    mItemClickListener.onCarouselViewItemClick(vpImages.getCurrentItem() % mShowCount);
                }
                break;
        }
        if (isSlide) {
            return false;
        } else {
            return true;
        }
    }

    public interface OnCarouselViewItemClickListener {
        void onCarouselViewItemClick(int position);
    }

    public interface OnCarouselViewLastListener {
        void onShowLastView();
    }
}
