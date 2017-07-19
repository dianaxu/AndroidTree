package com.example.admin.androidtree.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.androidtree.R;

/**
 * @author Diana
 * @date 2017/7/18
 */

public class HeaderHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public TextView tvOpen;

    public HeaderHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvOpen = (TextView) itemView.findViewById(R.id.tv_open);
    }
}
