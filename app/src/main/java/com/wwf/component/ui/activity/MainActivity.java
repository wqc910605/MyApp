package com.wwf.component.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.FrameLayout;

import com.wwf.common.eventbus.TabSelectedEvent;
import com.wwf.common.util.LogUtil;
import com.wwf.component.R;
import com.wwf.component.base.BaseActivity;
import com.wwf.component.presenter.main.MainActivityPresneter;
import com.wwf.component.ui.fragment.main.MainFragment;
import com.wwf.component.iview.main.IMainActivityView;
import com.wwf.component.ui.fragment.splash.SplashFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainActivityPresneter> implements IMainActivityView {

    @BindView(R.id.fl_main_container)
    FrameLayout flMainContainer;
    //记录Fragment位置
    public int position;

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
            loadRootFragment(R.id.fl_main_container, SplashFragment.newInstance(null));
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        position = savedInstanceState.getInt("position");
        LogUtil.e("lifecycle:: onRestoreInstanceState position: ", position+"");
        //发送上次选中的位置
        EventBus.getDefault().postSticky(new TabSelectedEvent(position));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //记录当前的position
        outState.putInt("position", position);
        LogUtil.e("lifecycle:: onSaveInstanceState position: ", position+"");
        super.onSaveInstanceState(outState);
    }

}
