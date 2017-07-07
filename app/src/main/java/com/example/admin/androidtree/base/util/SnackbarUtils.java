package com.example.admin.androidtree.base.util;

import android.support.annotation.ColorInt;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 *
 * @author Diana
 * @date 2017/7/7
 */

public class SnackbarUtils {
    private static final int color_danger = 0XFFA94442;
    private static final int color_success = 0XFF3C763D;
    private static final int color_info = 0XFF31708F;
    private static final int color_warning = 0XFF8A6D3B;

    private static final int color_action = 0XFFCDC5BF;
    private Snackbar mSnackbar;

    private SnackbarUtils(Snackbar snackbar) {
        this.mSnackbar = snackbar;
    }

    public static SnackbarUtils makeShort(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        return new SnackbarUtils(snackbar);
    }

    public static SnackbarUtils makeLong(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        return new SnackbarUtils(snackbar);
    }

    private View getSnackbarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private Snackbar setSnackBarBackColor(@ColorInt int colorId) {
        View snackbarView = getSnackbarLayout(mSnackbar);
        if (snackbarView != null) {
            snackbarView.setBackgroundColor(colorId);
        }
        return mSnackbar;
    }

    public void info(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_info);
        show(actionText, listener);
    }

    public void warning() {
        setSnackBarBackColor(color_warning);
        show();
    }

    public void warning(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_warning);
        show(actionText, listener);
    }

    public void danger() {
        setSnackBarBackColor(color_danger);
        show();
    }

    public void danger(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_danger);
        show(actionText, listener);
    }

    public void success() {
        setSnackBarBackColor(color_success);
        show();
    }

    public void success(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_success);
        show(actionText, listener);
    }

    public void show() {
        mSnackbar.show();
    }

    public void show(String actionText, View.OnClickListener listener) {
        mSnackbar.setActionTextColor(color_action);
        mSnackbar.setAction(actionText, listener).show();
    }
}
