package com.alittleme.j_library.network.func;

import rx.Observable;

/**
 * Created by Guojie on 2018/3/30.
 * 自定义任务调度
 */

public class J_TokenTransformer<E> implements Observable.Transformer<E, E> {
    //网络出错前的请求
    private Observable observable;

    public J_TokenTransformer(Observable<E> observable) {
        this.observable = observable;
    }

    @Override
    public Observable<E> call(Observable<E> eObservable) {
        return eObservable
                .onErrorResumeNext(new J_OnErrorResumeNextFunc1(observable))//token是否有效的检验
                .retryWhen(new J_RetryWhenFunc1());//错误重试机制
    }
}
