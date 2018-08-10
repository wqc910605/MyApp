package com.wwf.component.ui.activity;

import android.widget.FrameLayout;

import com.wwf.component.R;
import com.wwf.component.base.BaseActivity;
import com.wwf.component.presenter.main.MainActivityPresneter;
import com.wwf.component.ui.fragment.main.MainFragment;
import com.wwf.component.view.main.IMainActivityView;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainActivityPresneter> implements IMainActivityView {

    @BindView(R.id.fl_main_container)
    FrameLayout flMainContainer;

    @Override
    protected void initPresenter() {
        setPresenter(this, new MainActivityPresneter());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_main_container, MainFragment.newInstance(null));
        }
    }

    @Override
    protected void initListener() {

    }
}
