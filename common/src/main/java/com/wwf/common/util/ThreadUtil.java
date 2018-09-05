package com.wwf.common.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wwf on 2017/10/12.
 * 线程工具类, 用来切换线程, 和执行延时任务
 */

public final class ThreadUtil {
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Executor sExecutor = Executors.newCachedThreadPool();

    //主线程
    public static void runOnUiThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    //子线程
    public static void runOnSubThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    //执行延时任务
    public static void postDelayed(Runnable runnable, long mills) {
        sHandler.postDelayed(runnable, mills);
    }

    //取消所有任务
    public static void removeCallback() {
        sHandler.removeCallbacksAndMessages(null);
    }
}
