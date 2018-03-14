package com.alittleme.j_library.utils;

/**
 * Created by Guojie on 2018/1/30.
 */

public class J_UIHelp {
    //获取Activity的名字
    public static String getActivityName(String name){
        return name.substring(name.lastIndexOf(".")+1,name.length());
    }
}
