package com.alittleme.j_library.network.test;

import com.alittleme.j_library.network.bean.BeanResponse;
import com.alittleme.j_library.network.bean.BeanResponseBody;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Guojie on 2018/3/26.
 * 网络请求api(易源接口)
 * https://www.showapi.com/api/view/44/1
 */

public interface J_ApiService {
    @FormUrlEncoded
    @POST("44-1")
    Observable<BeanResponse<BeanResponseBody>> getNewestOpen(@Field("code") String code);

    @FormUrlEncoded
    @POST("44-1")
    Observable<ResponseBody> getNewestOpen2(@Field("code") String code);
}
