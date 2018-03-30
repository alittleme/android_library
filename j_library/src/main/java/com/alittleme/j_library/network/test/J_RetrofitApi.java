package com.alittleme.j_library.network.test;
import com.alittleme.j_library.network.J_RetrofitUtils;
import com.alittleme.j_library.network.bean.BeanResponse;
import com.alittleme.j_library.network.bean.BeanResponseBody;
import com.alittleme.j_library.network.func.J_DataFunc1;
import com.alittleme.j_library.network.func.J_TokenTransformer;

import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Guojie on 2018/3/30.
 * 示例- Api的具体使用
 */
public class J_RetrofitApi {
    private static J_RetrofitApi instance;
    private Retrofit retrofit;

    private J_RetrofitApi() {
        HashMap params = new HashMap();
        params.put("showapi_sign", "4d2024758fde4f6985bf8eeb6095dc89");
        params.put("showapi_appid", "45601");
        retrofit = J_RetrofitUtils.buildRetrofit(params, null);
    }

    public static J_RetrofitApi getInstance() {
        if (instance == null) {
            synchronized (J_RetrofitApi.class) {
                if (instance == null) {
                    instance = new J_RetrofitApi();
                }
            }
        }
        return instance;
    }

    /**
     * 最新的开奖结果查询(进行数据拦截)
     */
    public Observable<BeanResponseBody> getNewestOpen(String code) {
        J_ApiService apiService = retrofit.create(J_ApiService.class);
        Observable<BeanResponse<BeanResponseBody>> newestOpen = apiService.getNewestOpen(code);
        return newestOpen
                .subscribeOn(Schedulers.io())//io线程进行处理
                .flatMap(new J_DataFunc1())//数据基本处理，返回值的基本判断
                .compose(new J_TokenTransformer(newestOpen))//token的拦截，检测到token的失效会重新获取(单次有效性)
                .flatMap(new J_DataFunc1());//数据基本处理，返回值的基本判断
    }

    /**
     * 最新的开奖结果查询(不使用数据拦截)
     */
    public Observable<ResponseBody> getNewestOpen2(String code) {
        J_ApiService apiService = retrofit.create(J_ApiService.class);
        Observable<ResponseBody> newestOpen = apiService.getNewestOpen2(code);
        return newestOpen
                .subscribeOn(Schedulers.io())//io线程进行处理
                .compose(new J_TokenTransformer(newestOpen));//token的拦截，检测到token的失效会重新获取(单次有效性)
    }

}
