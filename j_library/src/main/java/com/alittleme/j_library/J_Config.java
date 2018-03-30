package com.alittleme.j_library;

import android.content.Context;

import com.alittleme.j_library.utils.J_LogUtils;

/**
 * Created by Guojie on 2018/3/26.
 * J_Library的必须配置类
 * 初始化当前的上下文对象
 */
public class J_Config {
    private static Context mContext;

    /**
     * Application 中初始化
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        if (mContext == null) {
            J_LogUtils.e(J_Config.class, "请先初始化J_Config,在程序开始的地方调用init()方法");
        }
        return mContext;
    }
}
