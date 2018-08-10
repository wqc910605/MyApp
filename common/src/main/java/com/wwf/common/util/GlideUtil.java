package com.wwf.common.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wwf.common.R;

/**
 * glide 的工具类
 */
public class GlideUtil {
    /**
     * 设置圆形头像
     * @param context
     * @param url 图片地址
     * @param placeholder 占位图和地址错误时的默认图片
     * @param isCircle 是否圆形
     * @param imageView 控件
     */
    public static void setHeadImage(Context context, String url, int placeholder, boolean isCircle, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(placeholder);
        } else {
            if (url.endsWith(".jpg") || url.endsWith(".JPG") || url.endsWith(".png") || url.endsWith(".PNG")) {
                Glide.with(context).load(url).apply(getOptions(placeholder, isCircle)).into(imageView);
            } else {
                imageView.setImageResource(placeholder);
            }
        }
    }

    /**
     * 设置默认头像, 默认占位图, 默认圆形
     * @param context
     * @param url
     * @param imageView
     */
    public static void setCircleImage(Context context, String url, ImageView imageView) {
        setHeadImage(context, url, R.mipmap.ic_launcher, true, imageView);
    }
    /**
     * 设置默认头像, 默认占位图, 默认长方形正常图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void setRectangleImage(Context context, String url, ImageView imageView) {
        setHeadImage(context, url, R.mipmap.ic_launcher, false, imageView);
    }

    //设置RequestOptions的方法
    private static RequestOptions getOptions(int placeholder, boolean isCircle) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(placeholder).error(placeholder);
        if (isCircle) {
            requestOptions.apply(RequestOptions.bitmapTransform(new CircleCrop()));
        }
        return requestOptions;
    }
}
