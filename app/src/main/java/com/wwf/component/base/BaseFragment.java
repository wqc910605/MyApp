package com.wwf.component.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.wwf.common.mvp.presenter.Presenter;
import com.wwf.common.mvp.view.IView;
import com.wwf.component.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 在module中, 无法使用butterknife ,所以基类放到app下面
 *
 * @param <P>
 */
public abstract class BaseFragment<P extends Presenter> extends SupportFragment {

    private Unbinder mUnbinder;
    protected P mPresenter;
    private ImageView mIvActionBarLeftBack;
    protected BaseActivity mBaseActivity;

    /**
     * 网络状态监听器
     *
     * @param isHasNetwork 是否有网络
     */
    private BaseActivity.OnNetworkListener mOnNetworkListener = new BaseActivity.OnNetworkListener() {
        @Override
        public void onNetwork(boolean isHasNetwork) {
            if (isVisible) {//可见时, 再执行网络状态变化
                onNetState(isHasNetwork);
            }
        }
    };
    private boolean isVisible = true;//fragment可见性
    private String mSimpleName;

    /**
     * 网络状态监听
     *
     * @param isHasNetwork
     */
    protected void onNetState(boolean isHasNetwork) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBaseActivity = (BaseActivity) getContext();
        View view = LayoutInflater.from(getContext()).inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initPresenter();
        if (mPresenter != null) {
            mPresenter.onCreateView(savedInstanceState);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        String simpleName = this.getClass().getSimpleName();
        //界面被创建之前
//        Toasty.normal(mBaseActivity, "我被创建了 " + simpleName, Toast.LENGTH_SHORT).show();
        mSimpleName = this.getClass().getSimpleName();
        beforCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onViewCreated();
        }
        //注册网络状态监听器
        mBaseActivity.setOnNetworkListener(mOnNetworkListener);
        initActionBar(view);
        initData();
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mSimpleName);
        isVisible = false;
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    public void onStop() {
        isVisible = false;
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();//解绑butterknife
        //注册网络状态监听器
        mBaseActivity.setOnNetworkListener(null);
        mOnNetworkListener = null;
        mBaseActivity = null;
        if (mPresenter != null) {
            mPresenter.onDestroyView();
            mPresenter = null;
            mBaseActivity = null;
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        isVisible = true;
        MobclickAgent.onPageStart(mSimpleName);
//        MobclickAgent.onResume(this);
    }

    /**
     * 当使用singleTask或者singleTop模式, 跳转界面时, 接受新意图, 类似于activity中的onNewIntent()方法
     *
     * @param args
     */
    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        if (mPresenter != null) {
            mPresenter.onNewBundle(args);
        }
    }

    /**
     * actionbar的统一处理
     *
     * @param view
     */
    protected void initActionBar(View view) {
        //返回键处理
        mIvActionBarLeftBack = view.findViewById(R.id.iv_actionbar_left_back);
        if (mIvActionBarLeftBack != null) {
            //设置返回按钮图片
            int imgResId = setActionBarLeftBack();
            if (imgResId > 0) {
                mIvActionBarLeftBack.setImageResource(imgResId);
            }
            mIvActionBarLeftBack.setOnClickListener(view1 -> {
                finishPage();
            });
        }
    }


    /**
     * 设置左侧返回按钮的图片, 等于0时, 不设置, 使用默认的图片
     *
     * @return
     */
    protected int setActionBarLeftBack() {
        return 0;
    }

    /**
     * 界面被创建之前, 执行
     *
     * @param savedInstanceState
     */
    private void beforCreate(Bundle savedInstanceState) {

    }

    /**
     * 初始化presenter时, 调用setPresenter方法即可
     */
    protected abstract void initPresenter();


    /**
     * 设置presenter对象的方法, 在子类中的initPresenter方法中调用
     *
     * @param view      实现View接口的对象
     * @param presenter presenter对象
     * @param <V>
     */
    protected <V extends IView> void setPresenter(@NonNull V view, @NonNull P presenter) {
        this.mPresenter = presenter;
        mPresenter.setView(getContext(), view);
    }

    /**
     * 来个布局资源id
     *
     * @return
     */
    public abstract int getLayoutResId();


    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 关闭当前界面
     */
    public void finishPage() {
        this.pop();
    }


}
