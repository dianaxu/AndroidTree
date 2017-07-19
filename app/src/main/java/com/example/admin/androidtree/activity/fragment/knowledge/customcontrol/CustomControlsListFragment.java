package com.example.admin.androidtree.activity.fragment.knowledge.customcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.activity.fragment.BaseFragment;
import com.example.admin.androidtree.activity.fragment.factory.CustomControlsFactory;
import com.example.admin.androidtree.activity.knowledge.SampleCustomControlsActivity;
import com.example.admin.androidtree.base.adapter.CommonRecyclerViewAdapter;
import com.example.admin.androidtree.base.adapter.RecyclerViewViewHolder;
import com.example.admin.androidtree.base.util.Constant;
import com.example.admin.androidtree.model.entity.CustomControl;
import com.example.admin.androidtree.model.event.CheckedItemEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 自定义控件
 *
 * @author Diana
 * @date 2017/7/8
 */

public class CustomControlsListFragment extends BaseFragment implements CommonRecyclerViewAdapter.
        OnRecycleViewItemClickListener {
    private static final String[] title = {"轮播图"};

    private RecyclerView rvControls;

    private ArrayList<CustomControl> mDate;
    private CommonRecyclerViewAdapter<CustomControl> mAdapter;


    private boolean hasTwoPane = false;

    @Override
    public String getTabTitle() {
        return "自定义控件";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_controls, container, false);
        rvControls = (RecyclerView) view.findViewById(R.id.rv_controls);
        hasTwoPane = getResources().getBoolean(R.bool.has_two_pane);
        mBuilderFactory = new CustomControlsFactory();
        return view;
    }


    private void initDate() {
        if (mDate == null) {
            mDate = new ArrayList<>();
        }
        mDate.clear();

        int len = title.length;
        for (int i = 0; i < len; i++) {
            mDate.add(new CustomControl(title[i]));
        }
    }


    private void initView(View view) {
        rvControls.setAdapter(mAdapter = new CommonRecyclerViewAdapter<CustomControl>(
                mContext, android.R.layout.simple_list_item_1, mDate) {
            @Override
            public void convert(RecyclerViewViewHolder helper, CustomControl item) {
                helper.getView(android.R.id.text1).setBackgroundResource(R.drawable.touch_bg_white_grey);
                helper.setText(android.R.id.text1, item.getTitle());
            }
        });
        mAdapter.setOnItemClickLinstener(this);

        /* LinearLayoutManager 现行管理器，支持横向、纵向。
        GridLayoutManager 网格布局管理器
        StaggeredGridLayoutManager 瀑布就式布局管理器*/

        //设置布局管理器， 控制其显示方式
        rvControls.setLayoutManager(new LinearLayoutManager(mContext));
        //设置item增加，移除的动画
        rvControls.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        rvControls.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBuilderFactory = new CustomControlsFactory();
        initDate();
        initView(getView());
    }

    @Override
    public void onRecycleViewItemClick(View view, int position) {
        if (!hasTwoPane) {
            Intent intent = new Intent(mContext, SampleCustomControlsActivity.class);
            intent.putExtra(Constant.Extra.FRAGMENT_INDEX, position);
            startActivity(intent);
        } else {
            EventBus.getDefault().post(new CheckedItemEvent(position, mBuilderFactory));
        }
    }
}
