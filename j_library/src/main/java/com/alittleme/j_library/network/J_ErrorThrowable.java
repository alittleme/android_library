package com.alittleme.j_library.network;

/**
 * Created by Guojie on 2018/3/30.
 * 请求出错抛出Rx异常
 */

public class J_ErrorThrowable extends Throwable {
    public int resCode;

    public J_ErrorThrowable(int resCode, String message) {
        super(message);
        this.resCode = resCode;
    }
}
