package com.wwf.common.constant;

/**
 * 所有的常量统一管理
 */
public final class Constant {

    /**
     *  isDebug 是否打印日志,
     *  isLeakCanary 是否检测内存泄漏
     */
    public static final class Log {
        public static boolean isDebug = true;
        public static boolean isLeakCanary = true;
    }

    /**
     * 有关网络请求的
     */
    public static final class Url {
        /**
         * 基础地址
         */
        public static final String BASE_URL = "http://10.1.139.119:9999/";
        /**
         * 个人中心
         */
        public static final String PERSONAL_CENTER = "youth/app/personal_center";
    }

    /**
     * 网络请求对应的标记
     */
    public static final class UrlFlag {
        /**
         * 个人中心标记
         */
        public static final int PERSONAL_CENTER = 1;
    }

    /**
     * 有关缓存的常量
     */
    public static final class Acache {

    }


}
