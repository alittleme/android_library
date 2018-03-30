package com.alittleme.j_library.network;

/**
 * Created by Guojie on 2018/3/30.
 * Retrofit配置文件
 */
public class J_RetrofitConfig {
    //Retrofit 基本请求URL
    public static String url = "http://route.showapi.com/";
    /*-------------------网络请求相关-------------*/
    //成功
    public static int CODE_SUCCESS = 0;
    //失败
    public static int CODE_ERROR = -1;
    //token失效
    public static int CODE_TOKEN = -10002;
    // 延迟
    public static long delay = 3000;

}
