package com.example.admin.androidtree.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.androidtree.R;
import com.example.admin.androidtree.base.adapter.CommonRecyclerViewAdapter;
import com.example.admin.androidtree.base.adapter.SectionedRecyclerViewAdapter;
import com.example.admin.androidtree.base.util.CustomUtils;
import com.example.admin.androidtree.model.entity.AnimatorListEntity;

import java.util.ArrayList;

/**
 * @author Diana
 * @date 2017/7/18
 */

public class AnimatorListAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DescHoleder,
        RecyclerView.ViewHolder> {
    private static final int DEFAULT_OPEN_COUNT = 2;

    public ArrayList<AnimatorListEntity.TagsEntity> tagList;
    private Context mContext;
    private LayoutInflater mInflater;
    private SparseBooleanArray mBooleanArray;  //记录哪个section是被打开的
    private int mItemCountForSection;

    private OnAnimatorListItemClickListener mOnItemListener;

    public AnimatorListAdapter(Context context) {
        init(context, DEFAULT_OPEN_COUNT);
    }

    public AnimatorListAdapter(Context context, int itemCountForSection) {
        init(context, itemCountForSection);
    }

    private void init(Context context, int itemCountForSection) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mBooleanArray = new SparseBooleanArray();
        mItemCountForSection = itemCountForSection;
    }

    public void setData(ArrayList<AnimatorListEntity.TagsEntity> tagList) {
        this.tagList = tagList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnAnimatorListItemClickListener listener) {
        this.mOnItemListener = listener;
    }

    //用来计算我们一共有多少个section需要展示，返回值是我们最外称list的大小
    @Override
    protected int getSectionCount() {
        return CustomUtils.isEmpty(tagList) ? 0 : tagList.size();
    }

    //用来展示content内容区域，返回值是我们需要展示多少内容.我们超过2条数据只展示2条，
    // 点击展开后就会展示全部数据，逻辑就在这里控制。 对应数据结构为tagInfoList
    @Override
    protected int getItemCountForSection(int section) {
        int count = tagList.get(section).tagInfoList.size();
        if (count >= mItemCountForSection && !mBooleanArray.get(section)) {
            count = mItemCountForSection;
        }
        return count;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.header_anim_list, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected DescHoleder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        DescHoleder holeder = new DescHoleder(mInflater.inflate(
                android.R.layout.simple_list_item_1, parent, false));
        return holeder;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        holder.tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = mBooleanArray.get(section);
                String text = isOpen ? "展开" : "关闭";
                mBooleanArray.put(section, !isOpen);
                holder.tvOpen.setText(text);
                notifyDataSetChanged();
            }
        });

        holder.tvTitle.setText(tagList.get(section).tagsName);
        holder.tvOpen.setText(mBooleanArray.get(section) ? "关闭" : "展开");
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(DescHoleder holder, final int section, final int position) {
        holder.tvDesc.setText(tagList.get(section).tagInfoList.get(position).tagName);
        holder.tvDesc.setBackgroundResource(R.drawable.touch_bg_white_grey);
        holder.tvDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemListener != null) {
                    mOnItemListener.onAnitmatorItemClick(tagList.get(section).tagInfoList.get(position));
                }
            }
        });
    }


    public interface OnAnimatorListItemClickListener {
        void onAnitmatorItemClick(AnimatorListEntity.TagInfo tagInfo);
    }
}
