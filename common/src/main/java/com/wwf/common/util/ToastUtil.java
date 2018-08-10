package com.wwf.common.util;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wwf.common.R;
import com.wwf.common.application.MyApplication;

/**
 * Created by wwf on 2017/10/12.
 * 吐司工具类
 */

public class ToastUtil {
    private ToastUtil(){}
    private static Toast sToast;
    private static View sView;
    /**
     * 静态吐司, 在任何线程都可以使用
     * @param msg
     */
    public static void show(final String msg) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = new Toast(MyApplication.sContext);
                    sView = LayoutInflater.from(MyApplication.sContext).inflate(R.layout.toast_content_layout, null, false);
                    sToast.setView(sView);
                    sToast.setDuration(Toast.LENGTH_SHORT);
                    sToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, DensityUtil.dp2px(100));
                }
                ((TextView) sView.findViewById(R.id.tv_toast)).setText(msg);
                sToast.show();
                ThreadUtil.postDelayed(() -> {
                    sView = null;
                    sToast = null;
                }, 100);
            }
        });
    }
}
