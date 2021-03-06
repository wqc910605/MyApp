package com.wwf.common.constant;

/**
 * 所有的常量统一管理
 */
public interface Constant {

    /**
     *  isDebug 是否打印日志,
     *  isLeakCanary 是否检测内存泄漏
     */
    interface Log {
        boolean isDebug = true;
        boolean isLeakCanary = true;
    }

    /**
     * 有关网络请求的
     */
     interface Url {
        /**
         * 基础地址
         */
         String BASE_URL = "http://10.1.139.119:9999/";
        /**
         * 个人中心
         */
         String PERSONAL_CENTER = "youth/app/personal_center";
    }

    /**
     * 网络请求对应的标记
     */
     interface UrlFlag {
        /**
         * 个人中心标记
         */
         int PERSONAL_CENTER = 1;
    }

    /**
     * 有关缓存的常量
     */
     interface Acache {

    }


}
