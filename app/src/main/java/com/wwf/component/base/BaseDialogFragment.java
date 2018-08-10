package com.wwf.component.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wwf.common.util.LogUtil;
import com.wwf.component.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 在module中, 无法使用butterknife ,所以基类放到app下面
 * 对话框基类
 */
public abstract class BaseDialogFragment extends DialogFragment {
    private Context mContext;
    protected AppCompatActivity mActivity;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = (AppCompatActivity) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(mContext, setDialogStyle());
//        View view = mInflater.inflate(R.layout.dialog_custom_layout, null, false);
        View view = LayoutInflater.from(mContext).inflate(getLayoutResId(), null, false);
        //初始化数据
        mUnbinder = ButterKnife.bind(this, view);
        initData();
        initListener();
        LogUtil.e("onCreateDialog: " + view);
        //设置取消标题
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置点击外部可以消失
        if (setCancelable()) {
            dialog.setCancelable(true);
        }
        if (setCanceledOnTouchOutside()) {
            dialog.setCanceledOnTouchOutside(true);
        }
        //获取activity所在的窗口
        Window window = dialog.getWindow();
        //设置软键盘弹出模式
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        //设置dialog宽度
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = setTopMargin();
        window.setAttributes(lp);
        dialog.setContentView(view);
        return dialog;
    }

    /**
     * 设置对话框样式
     * @return
     */
    private int setDialogStyle() {
        return R.style.dialog_fragment_style;
    }

    protected void initData() {
    }

    protected void initListener() {
    }

    /**
     * 界面销毁时, 释放资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContext = null;
        mActivity = null;
        mUnbinder.unbind();
    }

    /**
     * 设置自定义布局距离顶部的高度
     * @return
     */
    protected int setTopMargin() {
        return 0;
    }

    /**
     * 点击外侧是否可以取消, 默认可以取消
     * @return
     */
    protected boolean setCancelable() {
        return true;
    }

    protected boolean setCanceledOnTouchOutside() {
        return true;
    }

    /**
     * 获取布局id
     * @return
     */
    public abstract int getLayoutResId();

    /**
     * 显示对话框
     * @param fragmentManager
     */
    public void showDialog(FragmentManager fragmentManager) {
        show(fragmentManager, "dialog");
    }

    /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
        setStyle();
        View view = inflater.inflate(R.layout.dialog_custom_layout, container, false);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x80000000));
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.TOP;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        return view;
    }*/
}
