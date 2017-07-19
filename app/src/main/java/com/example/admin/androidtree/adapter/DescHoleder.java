package com.example.admin.androidtree.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author Diana
 * @date 2017/7/18
 */

public class DescHoleder extends RecyclerView.ViewHolder {
    public TextView tvDesc;

    public DescHoleder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        tvDesc = (TextView) itemView.findViewById(android.R.id.text1);
    }
}
