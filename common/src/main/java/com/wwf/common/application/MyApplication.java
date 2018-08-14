package com.wwf.common.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.wwf.common.R;
import com.wwf.common.util.Util;

import cn.jpush.android.api.JPushInterface;

import static com.wwf.common.constant.Constant.Log.isDebug;
import static com.wwf.common.constant.Constant.Log.isLeakCanary;

public class MyApplication extends Application {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static Handler sHandler;
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sHandler = new Handler();
        sContext = this;
        //初始化友盟统计
        initUmeng();
        //初始化LeakCanary
        initLeakCanary();
        //初始化bugly
        initBugly();
        //初始化激光推送
        initJPush();
    }


    private void initUmeng() {
        /**
         注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
         appkey
         channel
         deviceType
         pushSecret 推送时, 使用
         */
        UMConfigure.init(this, "5b63f6388f4a9d5623000062", "", UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);
        //普通统计
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        // secretkey设置接口，防止AppKey被盗用，secretkey需要网站申请。申请方法见AppKey保护策略(Secret)介绍。
        MobclickAgent.setSecret(this, "s10bacedtyz");
        // isEnable: true-回传错误信息, false-不回传错误信息。
        MobclickAgent.setCatchUncaughtExceptions(true);

//        程序退出时，用于保存统计数据的API。
//        如果开发者调用kill或者exit之类的方法杀死进程，请务必在此之前调用onKillProcess方法，用来保存统计数据。
//        MobclickAgent.onKillProcess(getApplicationContext());
    }

    private void initLeakCanary() {
        if (isLeakCanary) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

    private void initBugly() {
        /*为了保证运营数据的准确性，建议不要在异步线程初始化Bugly。

        第三个参数为SDK调试模式开关，调试模式的行为特性如下：

        输出详细的Bugly SDK的Log；
        每一条Crash都会被立即上报；
        自定义日志将会在Logcat中输出。
        建议在测试阶段建议设置成true，发布时设置为false。*/

       /* 如果App使用了多进程且各个进程都会初始化Bugly（例如在Application类onCreate()中初始化Bugly），
        那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费。

        因此，为了节省流量、内存等资源，建议初始化的时候对上报进程进行控制，
        只在主进程下上报数据：判断是否是主进程（通过进程名是否为包名来判断），
        并在初始化Bugly时增加一个上报进程的策略配置。*/
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = Util.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setBuglyLogUpload(processName == null || processName.equals(packageName));
        // 初始化Bugly
//        CrashReport.initCrashReport(context, "注册时申请的APPID", isDebug, strategy);
//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false);
    }

    private void initJPush() {
        // JPush SDK 提供的 API 接口，都主要集中在 cn.jpush.android.api.JPushInterface 类里。
        JPushInterface.init(this);
        JPushInterface.setDebugMode(isDebug);
    }


}
