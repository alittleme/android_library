package com.alittleme.j_library.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Guojie on 2016/12/15.
 */
public class J_ToastUtils {
    //静态toast
    private static Toast toast;
    private static Context mContext;

    /**
     * Application 中初始化
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
    }
    public static void showToast(String text) {
        if (mContext != null) {
            if (toast == null) {
                toast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
            } else {
                toast.setText(text);
            }
            toast.show();
        }
    }
}
