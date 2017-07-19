package com.example.admin.androidtree.base.util;

import android.content.Context;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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

    public static <D> boolean isEmpty(List<D> list) {
        return list == null || list.isEmpty();
    }

    public static String readerLocalJsonFile(Context context, String fileName) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(context.getAssets().open(fileName), "UTF-8");
            br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
        }
        return builder.toString();
    }
}
