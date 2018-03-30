package com.alittleme.j_library.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Guojie on 2018/3/27.
 * 基本接口响应
 * "showapi_res_code": -1,
 * "showapi_res_error": "验证有误!请先登录再使用接口，谢谢.",
 * "showapi_res_body": {}
 */
public class BeanResponse<T> {
    @SerializedName("showapi_res_code")
    public int resCode;

    @SerializedName("showapi_res_error")
    public String resError;

    @SerializedName("showapi_res_body")
    public T data;

}
