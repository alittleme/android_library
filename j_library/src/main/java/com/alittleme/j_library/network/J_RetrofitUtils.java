package com.alittleme.j_library.network;

import com.alittleme.j_library.utils.J_LogUtils;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Guojie on 2018/3/30.
 * 创建retrofit工具类
 */
public class J_RetrofitUtils {
    public Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private int timeOutTime = 30;//SECONDS

    /**
     * 接受外部传入的参数 创建Retrofit
     *
     * @param params 参数
     * @param heads  头信息
     * @return Retrofit
     */
    public static Retrofit buildRetrofit(HashMap params, HashMap heads) {
        return new J_RetrofitUtils(params, heads).retrofit;
    }

    private J_RetrofitUtils(HashMap<String, String> params, HashMap<String, String> heads) {
        okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(timeOutTime, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(timeOutTime, TimeUnit.SECONDS)//写入超时时间
                .readTimeout(timeOutTime, TimeUnit.SECONDS)//读取超时时间
                .addInterceptor(getInterceptor(params, heads))//自定义请求拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))//请求日志拦截器
                .build();
        retrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(J_RetrofitConfig.url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加RxJava的支持
                .addConverterFactory(GsonConverterFactory.create())//添加Gson的支持
                .build();
    }

    /**
     * 拦截网络请求，组装请求参数和头信息
     *
     * @param params
     * @param heads
     * @return
     */
    public Interceptor getInterceptor(final HashMap<String, String> params, final HashMap<String, String> heads) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //添加请求头信息
                request = getRequestHeads(request, heads);
                //添加共有请求参数
                HttpUrl.Builder httpUrlBuilder = getRequestParams(request, params);
                //重新生成url
                HttpUrl httpUrl = httpUrlBuilder.build();
                request = request.newBuilder().url(httpUrl).build();
                //打印请求日志
                printRequestLog(request);
                Response response = chain.proceed(request);
                //打印响应日志
                printResponseLog(response);
                return response;
            }

            /**
             * 添加共有请求参数
             * @param request
             * @return
             */
            private HttpUrl.Builder getRequestParams(Request request, HashMap<String, String> params) {
                HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
                if (params != null && params.size() > 0) {//组装
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        //httpUrlBuilder.addQueryParameter("showapi_sign", "4d2024758fde4f6985bf8eeb6095dc89");
                        //httpUrlBuilder.addQueryParameter("showapi_appid", "45601");
                        httpUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                    }
                }
                return httpUrlBuilder;
            }

            /**
             * 拼接请求头
             * @param request
             * @param heads
             * @return
             */
            private Request getRequestHeads(Request request, HashMap<String, String> heads) {
                Request.Builder requestBuilder = request.newBuilder();
                if (heads != null && heads.size() > 0) {//组装
                    for (Map.Entry<String, String> entry : heads.entrySet()) {
                        //requestBuilder.addHeader("Accept", "application/json; q=0.5");
                        requestBuilder.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                request = requestBuilder.build();
                return request;
            }
        };
    }

    /**
     * 打印输出日志
     *
     * @param response
     * @throws IOException
     */
    private void printResponseLog(Response response) throws IOException {
        int code = response.code();

        J_LogUtils.d(J_RetrofitUtils.class, "<-- " + code + (response.isSuccessful() ? "OK" : "Failure"));

        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = Charset.forName("UTF8");
        okhttp3.MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF8"));
        }

        if (responseBody.contentLength() != 0) {
            String s = buffer.clone().readString(charset);

            J_LogUtils.d(J_RetrofitUtils.class, s.trim());
            J_LogUtils.d(J_RetrofitUtils.class, "<-- END HTTP");
        }
    }

    /**
     * 打印请求日志
     *
     * @param request
     * @throws IOException
     */
    private void printRequestLog(Request request) throws IOException {
        String method = request.method();
        String tag = "--> " + method + " ";
        J_LogUtils.d(J_RetrofitUtils.class, tag + request.url().toString());
        if (request.body() instanceof MultipartBody) return;
        Buffer buffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(buffer);
        }
        String decode = URLDecoder.decode(buffer.readUtf8(), "UTF-8");
        String[] split = decode.split("&");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : split) {
            stringBuilder.append(s + "\n");
        }
        J_LogUtils.d(J_RetrofitUtils.class, stringBuilder.toString());
        J_LogUtils.d(J_RetrofitUtils.class, "--> END " + method);
    }
}
