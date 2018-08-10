package com.wwf.component.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wwf.common.mvp.presenter.Presenter;
import com.wwf.common.mvp.view.IView;
import com.wwf.component.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment<P extends Presenter> extends SupportFragment implements BaseActivity.OnNetworkListener {

    private Unbinder mUnbinder;
    protected P mPresenter;
    private ImageView mIvActionBarLeftBack;
    protected BaseActivity mBaseActivity;

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
        String simpleName = this.getClass().getSimpleName();
        //界面被创建之前
//        Toasty.normal(mBaseActivity, "我被创建了 " + simpleName, Toast.LENGTH_SHORT).show();
        beforCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onViewCreated();
        }
        //注册网络状态监听器
        mBaseActivity.setOnNetworkListener(this);
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
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();//解绑butterknife
        if (mPresenter != null) {
            mPresenter.onDestroyView();
            mPresenter = null;
            mBaseActivity = null;
        }
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        if (mPresenter != null) {
            mPresenter.onNewBundle(args);
        }
    }

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

    //设置左侧返回按钮的图片, 等于0时, 不设置, 使用默认的图片
    protected int setActionBarLeftBack() {
        return 0;
    }

    //界面被创建之前, 执行
    private void beforCreate(Bundle savedInstanceState) {

    }

    //初始化presenter时, 调用setPresenter方法即可
    protected abstract void initPresenter();

    //设置presenter方法
    protected <V extends IView> void setPresenter(@NonNull V view, @NonNull P presenter) {
        this.mPresenter = presenter;
        mPresenter.setView(getContext(), view);
    }


    //来个布局id
    public abstract int getLayoutResId();

    //初始化数据
    protected abstract void initData();

    //初始化监听
    protected abstract void initListener();

    public void finishPage() {
        this.pop();
    }

    //网络状态监听器
    @Override
    public void onNetwork(boolean isHasNetwork) {

    }
}
