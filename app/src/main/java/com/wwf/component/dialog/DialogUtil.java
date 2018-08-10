package com.wwf.component.dialog;

import android.content.DialogInterface;

import java.lang.ref.WeakReference;

public class DialogUtil {
    public static DialogUtil instance = null;
    public CustomDialog mCustomDialog = null;

    private DialogUtil() {}

    public static DialogUtil getInstance() {
        if (instance == null) {
            synchronized (DialogUtil.class) {
                if (instance == null) {
                    instance = new DialogUtil();
                }
            }
        }
        return getWeakReference(instance);
    }

    private static <T> T getWeakReference(T t) {
        WeakReference<T> tWeakReference = new WeakReference<>(t);
        return tWeakReference.get();
    }

    /**
     * 获取CustomDialog对象
     *
     * @return
     */
    public CustomDialog getCustomDialog() {
        if (mCustomDialog == null) {
            mCustomDialog = new CustomDialog();
//            mCustomDialog.onDismiss(mDialogInterface);
        }
        return mCustomDialog;
    }
    private DialogInterface mDialogInterface = new DialogInterface() {
        @Override
        public void cancel() {
            mCustomDialog = null;
        }

        @Override
        public void dismiss() {
            mCustomDialog = null;
        }
    };
}
