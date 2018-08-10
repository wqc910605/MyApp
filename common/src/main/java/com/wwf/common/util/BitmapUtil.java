package com.wwf.common.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by wzp on 2018/6/13.
 */

public class BitmapUtil {

    /**
     * 根据屏幕宽度等比例缩放图片
     *
     * @param bm
     * @param screenWidth 屏幕宽度
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bm, int screenWidth) {
        Bitmap newbm = null;
        if (bm != null) {
            // 获得图片的宽高
            int width = bm.getWidth();
            int height = bm.getHeight();
            // 计算缩放比例
            float scale = ((float) screenWidth) / width;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            // 得到新的图片   www.2cto.com
            newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        }
        return newbm;
    }

}
