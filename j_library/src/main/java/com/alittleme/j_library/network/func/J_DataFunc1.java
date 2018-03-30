package com.alittleme.j_library.network.func;

import com.alittleme.j_library.network.J_ErrorThrowable;
import com.alittleme.j_library.network.J_RetrofitConfig;
import com.alittleme.j_library.network.bean.BeanResponse;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Guojie on 2018/3/30.
 */

public class J_DataFunc1<E> implements Func1<E, Observable<E>> {
    @Override
    public Observable<E> call(E e) {
        if (e instanceof BeanResponse) {//（1）数据流处理（token拦截前）
            BeanResponse beanResponse = (BeanResponse) e;
            //模拟token失效
            if (J_OnErrorResumeNextFunc1.token == null) {
                return (Observable<E>) Observable.error(new J_ErrorThrowable(J_RetrofitConfig.CODE_TOKEN, beanResponse.resError));
            }
            //停止模拟
            if (beanResponse.resCode == J_RetrofitConfig.CODE_SUCCESS) {//请求成功
                return (Observable<E>) Observable.just(beanResponse.data);
            } else {//响应失败
                return (Observable<E>) Observable.error(new J_ErrorThrowable(beanResponse.resCode, beanResponse.resError));
            }
        } else {//（2）数据流处理 (token拦截后,或者没有使用BeanResponse)
            return Observable.just(e);
        }
    }
}
