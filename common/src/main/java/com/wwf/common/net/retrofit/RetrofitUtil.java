package com.wwf.common.net.retrofit;


import com.wwf.common.constant.Constant;
import com.wwf.common.net.interceptors.LogInterceptor;
import com.wwf.common.net.interceptors.NetworkInterceptor;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtil {
    private static RetrofitUtil instance;
    private OkHttpClient mOkHttpClient;
    private static final long TIMEOUT = 16;
    private Retrofit mRetrofit;

    private RetrofitUtil() {
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor())
                .addInterceptor(new LogInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil();
                }
            }
        }
        return getWeakReference(instance);
    }

    public static <T> T getSoftReference(T t) {
        SoftReference<T> tWeakReference = new SoftReference<T>(t);
        return tWeakReference.get();
    }
    public static <T> T getWeakReference(T t) {
        WeakReference<T> tWeakReference = new WeakReference<>(t);
        return tWeakReference.get();
    }


    public ApiService getApiService() {
        if (mRetrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(Constant.Url.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return mRetrofit.create(ApiService.class);
    }

}
