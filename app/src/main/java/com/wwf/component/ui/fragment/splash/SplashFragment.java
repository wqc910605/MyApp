package com.wwf.component.ui.fragment.splash;

import com.wwf.component.R;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.presenter.SplashPresenter;
import com.wwf.component.view.splash.ISplashView;

//闪屏界面
public class SplashFragment extends BaseFragment<SplashPresenter> implements ISplashView {
    @Override
    protected void initPresenter() {
        setPresenter(this, new SplashPresenter());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
