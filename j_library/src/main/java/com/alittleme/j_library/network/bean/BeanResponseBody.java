package com.alittleme.j_library.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Guojie on 2018/3/27.
 * 响应返回列表
 */
public class BeanResponseBody<T> {
    @SerializedName("result")
    public List<T> list;

    @SerializedName("ret_code")
    public int code;
}
