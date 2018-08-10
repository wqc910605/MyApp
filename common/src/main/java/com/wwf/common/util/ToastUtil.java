package com.wwf.common.util;

import android.widget.Toast;

import com.wwf.common.application.MyApplication;

/**
 * Created by wwf on 2017/10/12.
 */

public class ToastUtil {
    private static Toast sToast = null;

    public static void show(final String msg) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = sToast.makeText(MyApplication.sContext, msg, Toast.LENGTH_SHORT);
                }else{
					 sToast.setText(msg);
				}
                sToast.show();
            }
        });

    }
}
