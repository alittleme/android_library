package com.alittleme.j_library.network.func;

import android.os.SystemClock;

import com.alittleme.j_library.network.J_ErrorThrowable;
import com.alittleme.j_library.network.J_RetrofitConfig;
import com.alittleme.j_library.utils.J_LogUtils;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Guojie on 2018/3/30.
 * 一般与token有效期相结合，失效策略
 */

public class J_OnErrorResumeNextFunc1<E> implements Func1<E, Observable<E>> {
    //网络出错前的请求
    private Observable observable;
    //模拟token获取
    public static String token = null;

    public J_OnErrorResumeNextFunc1(Observable<E> observable) {
        this.observable = observable;
    }

    @Override
    public Observable<E> call(E e) {
        if (e instanceof J_ErrorThrowable) {//只拦截自定义错误
            if (((J_ErrorThrowable) e).resCode == J_RetrofitConfig.CODE_TOKEN) {//token失效
                //重新获取token
                return getToken().flatMap(new Func1<E, Observable<E>>() {
                    @Override
                    public Observable<E> call(E e) {
                        token = (String) e;
                        J_LogUtils.e(J_OnErrorResumeNextFunc1.class, "token获取成功：" + e.toString());
                        return observable;
                    }
                });
            }
        }
        //如果碰到网络状态错误，交由重试机制
        return Observable.error((Throwable) e);
    }

    /**
     * 模拟 token的获取
     *
     * @return
     */
    private Observable getToken() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SystemClock.sleep(1000);
                subscriber.onNext("token");
                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.io());
    }
}
