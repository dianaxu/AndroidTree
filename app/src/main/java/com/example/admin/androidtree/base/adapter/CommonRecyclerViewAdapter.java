package com.example.admin.androidtree.base.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Diana
 * @date 2017/7/8
 */

public abstract class CommonRecyclerViewAdapter<T> extends
        RecyclerView.Adapter<RecyclerViewViewHolder> {
    private Context mContext;
    private int mItemLayoutId;
    private List<T> mData;

    private OnRecycleViewItemClickListener mOnItemClickListener;

    public CommonRecyclerViewAdapter(Context context, @LayoutRes int itemLayoutId, List<T> data) {
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        this.mData = data;
    }

    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolder(LayoutInflater.from(mContext)
                .inflate(mItemLayoutId, parent, false), mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerViewViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public void setOnItemClickLinstener(OnRecycleViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public abstract void convert(RecyclerViewViewHolder helper, T item);

    public interface OnRecycleViewItemClickListener {
        void onRecycleViewItemClick(View view, int position);
    }

}
