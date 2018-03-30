package com.alittleme.j_library.network.func;

import com.alittleme.j_library.J_Config;
import com.alittleme.j_library.R;
import com.alittleme.j_library.network.J_ErrorThrowable;
import com.alittleme.j_library.network.J_RetrofitConfig;
import com.alittleme.j_library.utils.J_LogUtils;
import com.alittleme.j_library.utils.J_UIHelp;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Guojie on 2018/3/30.
 * 错误重试机制
 */
public class J_RetryWhenFunc1<E> implements Func1<Observable<E>, Observable<E>> {
    //retry次数
    private int count = 0;

    @Override
    public Observable<E> call(Observable<E> observable) {
        return observable.flatMap(new Func1<E, Observable<E>>() {
            @Override
            public Observable<E> call(E e) {
                if (e instanceof ConnectException//连接失败
                        || e instanceof SocketTimeoutException//连接超时
                        || e instanceof TimeoutException//读写超时
                        || e instanceof UnknownHostException//未知的主机地址
                        && ++count <= 3) {//重试次数 为三次
                    J_LogUtils.e(J_RetryWhenFunc1.class, "get error, it will try after " + J_RetrofitConfig.delay + " millisecond, retry count " + count);
                    return (Observable<E>) Observable.timer(J_RetrofitConfig.delay * count, TimeUnit.MILLISECONDS);
                } else {//超过重试次数，或者其他错误
                    if (!J_UIHelp.isNetworkAvailable(J_Config.getContext()) && e instanceof UnknownHostException) {//没有网络
                        return Observable.error(new J_ErrorThrowable(-1, J_Config.getContext().getString(R.string.j_load_no_network)));
                    } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {//连接超时
                        return Observable.error(new J_ErrorThrowable(-1, J_Config.getContext().getString(R.string.j_load_time_out)));
                    }
                }
                return Observable.error((Throwable) e);
            }
        });
    }
}
