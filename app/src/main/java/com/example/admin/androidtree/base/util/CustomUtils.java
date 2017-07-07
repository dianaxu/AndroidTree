package com.example.admin.androidtree.base.util;

import android.content.Context;

/**
 * 通用方法类
 *
 * @author Diana
 * @date 2017/6/28
 */

public class CustomUtils {

    public static int dip2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
