package com.alittleme.j_library.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by lxh on 16/7/22.
 */
public class J_SystemBarUtils {
    public static void setBackground(Activity ac, int color) {
        int currApiVersion = Build.VERSION.SDK_INT;
        if (currApiVersion >= 23) {
            setSysBarColorWithApi_23(ac, color);
        } else if (currApiVersion >= 19 && currApiVersion < 23) {
            setSysBarColorWithApi_19(ac, color);
        }
    }
    /**
     * 设置状态栏颜色 api>=23
     *
     * @param activity
     * @param statusColor
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setSysBarColorWithApi_23(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusColor);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {

            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
        mChildView.setFitsSystemWindows(true);
    }

    /**
     * 设置状态栏颜色 19=>api<=21
     *
     * @param activity
     * @param statusColor
     */
    public static void setSysBarColorWithApi_19(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int statusBarHeight = getStatusBarHeight(activity);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
                lp.topMargin += statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }
        View statusBarView = mContentView.getChildAt(0);
        if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
            statusBarView.setBackgroundColor(statusColor);
            return;
        }
        statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusBarView.setBackgroundColor(statusColor);
        statusBarView.setTag("statusBarView");
        mContentView.addView(statusBarView, 0, lp);
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
}
