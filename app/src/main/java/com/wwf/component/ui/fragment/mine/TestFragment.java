package com.wwf.component.ui.fragment.mine;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wwf.common.util.ToastUtil;
import com.wwf.component.R;
import com.wwf.component.adapter.TestAdapter;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.bean.TestBean;
import com.wwf.component.presenter.fragment.mine.TestPresenter;
import com.wwf.component.ui.widget.CustomActionBar;
import com.wwf.component.view.mine.ITestView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestFragment extends BaseFragment<TestPresenter> implements ITestView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.custom_action_bar)
    CustomActionBar customActionBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_Layout)
    SmartRefreshLayout smartRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private List<TestBean> mDatas = new ArrayList<>();
    private TestAdapter mTestAdapter;

    @Override
    protected void initPresenter() {
        setPresenter(this, new TestPresenter());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_test;
    }


    @Override
    protected void initData() {
        //初始化适配器
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mTestAdapter = new TestAdapter(mDatas);
        recyclerView.setAdapter(mTestAdapter);
        mPresenter.requestPersonalCenter();
    }

    @Override
    protected void initListener() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
    }

    //刷新
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
    }
    //加载更多
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
    }

    //数据返回
    @Override
    public void onRequestPersonalCenter() {
        for (int i = 0; i < 20; i++) {
            mDatas.add(new TestBean());
        }
        mTestAdapter.setNewData(mDatas);
    }

    @Override
    public void onNetState(boolean isHasNetwork) {
        super.onNetState(isHasNetwork);
        if (isHasNetwork) {
            ToastUtil.show("有网络");
        } else {
            ToastUtil.show("没有网络");
        }
    }
}
