package com.alittleme.j_library.utils;

import android.content.Context;
import android.widget.Toast;

import com.alittleme.j_library.J_Config;

/**
 * Created by Guojie on 2016/12/15.
 */
public class J_ToastUtils {
    //静态toast
    private static Toast toast;

    public static void showToast(String text) {
        if (J_Config.getContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(J_Config.getContext(), text, Toast.LENGTH_LONG);
            } else {
                toast.setText(text);
            }
            toast.show();
        }
    }
}
