package com.alittleme.j_library.utils;

import android.util.Log;

import com.alittleme.j_library.BuildConfig;

/**
 * Created by Guojie on 2017/3/14.
 */

public class J_LogUtils {
    private static int M = 0;
    private static int e = 1;
    private static int w = 2;
    private static int i = 3;
    private static int d = 4;
    private static int v = 5;

    static {
        if (!BuildConfig.DEBUG) {
            M = 6;
        }
    }

    public static void d(Class clazz, String msg) {
        if (M > d) {
            String strClazz = J_UIHelp.getActivityName(clazz.getName());
            Log.d("J_==>" + strClazz, "||" + msg);
        }
    }

    public static void i(Class clazz, String msg) {
        if (M > i) {
            String strClazz = J_UIHelp.getActivityName(clazz.getName());
            Log.i("J_==>" + strClazz, "||" + msg);
        }
    }

    public static void w(Class clazz, String msg) {
        if (M > w) {
            String strClazz = J_UIHelp.getActivityName(clazz.getName());
            Log.w("J_==>" + strClazz, "||" + msg);
        }
    }

    public static void e(Class clazz, String msg) {
        if (M > e) {
            String strClazz = J_UIHelp.getActivityName(clazz.getName());
            Log.e("J_==>" + strClazz, "||" + msg);
        }
    }

    public static void v(Class clazz, String msg) {
        if (M > v) {
            String strClazz = J_UIHelp.getActivityName(clazz.getName());
            Log.v("J_==>" + strClazz, "||" + msg);
        }
    }
}