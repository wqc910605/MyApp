package com.wwf.component.ui.fragment.home;

import android.os.Bundle;

import com.wwf.component.R;
import com.wwf.common.eventbus.TabSelectedEvent;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.presenter.fragment.home.HomePresenter;
import com.wwf.component.view.home.IHomeView;

import org.greenrobot.eventbus.Subscribe;

public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView {
    //创建一个实例
    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected void initPresenter() {
        setPresenter(this, new HomePresenter());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        /*if (event.position != MainFragment.SECOND) return;

        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }*/
    }
}
