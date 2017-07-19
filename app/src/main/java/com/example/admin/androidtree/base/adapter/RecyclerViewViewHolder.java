package com.example.admin.androidtree.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * @author Diana
 * @date 2017/7/9
 */

public class RecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View mConvertView;
    private SparseArray<View> mViews;

    private CommonRecyclerViewAdapter.OnRecycleViewItemClickListener mOnItemClickListener;

    public RecyclerViewViewHolder(View itemView, CommonRecyclerViewAdapter.
            OnRecycleViewItemClickListener listener) {
        super(itemView);
        this.mViews = new SparseArray<View>();
        this.mConvertView = itemView;
        mOnItemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        if (TextUtils.isEmpty(text)) {
            view.setText("");
        } else {
            view.setText(text);
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onRecycleViewItemClick(v, getLayoutPosition());
        }
    }

}
