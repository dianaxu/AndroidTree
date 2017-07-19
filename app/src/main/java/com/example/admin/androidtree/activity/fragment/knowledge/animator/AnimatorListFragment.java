package com.example.admin.androidtree.activity.fragment.knowledge.animator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.activity.fragment.BaseFragment;
import com.example.admin.androidtree.activity.fragment.factory.AnimatorFactory;
import com.example.admin.androidtree.activity.fragment.factory.CustomControlsFactory;
import com.example.admin.androidtree.activity.knowledge.SampleAnimatorActivity;
import com.example.admin.androidtree.adapter.AnimatorListAdapter;
import com.example.admin.androidtree.base.util.Constant;
import com.example.admin.androidtree.base.util.CustomUtils;
import com.example.admin.androidtree.model.entity.AnimatorListEntity;
import com.example.admin.androidtree.model.event.CheckedItemEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * 动画demo
 *
 * @author Diana
 * @date 2017/7/8
 */

public class AnimatorListFragment extends BaseFragment implements AnimatorListAdapter.OnAnimatorListItemClickListener {

    private RecyclerView rvAnimList;
    private AnimatorListAdapter mAdapter;

    private boolean hasTwoPane = false;

    @Override
    public String getTabTitle() {
        return "动画";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animator_list, container, false);
        rvAnimList = (RecyclerView) view.findViewById(R.id.rv_anim_list);
        mAdapter = new AnimatorListAdapter(mContext);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvAnimList.setLayoutManager(manager);
        rvAnimList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        setData();
        mAdapter.setOnItemClickListener(this);
        rvAnimList.setAdapter(mAdapter);

        hasTwoPane = getResources().getBoolean(R.bool.has_two_pane);
        mBuilderFactory = new AnimatorFactory();
        return view;
    }

    private void setData() {
        Gson gson = new Gson();
        AnimatorListEntity entity = null;

        try {
            String json = CustomUtils.readerLocalJsonFile(mContext, "AnimatorListJson.json");
            entity = gson.fromJson(json, new TypeToken<AnimatorListEntity>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mAdapter.setData(entity.tagsEntityList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (hasTwoPane) {
            EventBus.getDefault().post(new CheckedItemEvent(0, mBuilderFactory));
        }
    }

    @Override
    public void onAnitmatorItemClick(AnimatorListEntity.TagInfo tagInfo) {
        if (!hasTwoPane) {
            Intent intent = new Intent(mContext, SampleAnimatorActivity.class);
            intent.putExtra(Constant.Extra.FRAGMENT_INDEX, tagInfo.tagIndex);
            startActivity(intent);
        } else {
            EventBus.getDefault().post(new CheckedItemEvent(tagInfo.tagIndex, mBuilderFactory));
        }
    }
}
