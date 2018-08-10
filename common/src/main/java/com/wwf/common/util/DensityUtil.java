package com.wwf.common.util;

import android.content.res.Resources;

/**
 * dp和px转换工具类
 */
public class DensityUtil {

    private DensityUtil(){}
    /**
     * dp 转成 px
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }

    /**
     * px 转成 dp
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (px*1f/density+0.5f);
    }

    /**
     * px 转成 sp
     * @param px
     * @return
     */
    public static int px2sp(int px) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (px * 1f / scaledDensity + 0.5f);
    }
    /**
     * sp 转成 px
     * @param sp
     * @return
     */
    public static int sp2px(int sp) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }
}
