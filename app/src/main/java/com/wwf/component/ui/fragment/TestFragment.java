package com.wwf.component.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adapter.refreshlayout.OnLoadMoreListener;
import com.adapter.refreshlayout.OnRefreshListener;
import com.adapter.refreshlayout.SwipeToLoadLayout;
import com.wwf.common.util.ThreadUtil;
import com.wwf.common.util.ToastUtil;
import com.wwf.component.R;
import com.wwf.component.adapter.TestAdapter;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.bean.TestBean;
import com.wwf.component.iview.mine.ITestView;
import com.wwf.component.presenter.fragment.mine.TestPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestFragment extends BaseFragment<TestPresenter> implements ITestView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeToLoadLayout mRefreshLayout;
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
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTestAdapter = new TestAdapter(mDatas);
        mRecyclerView.setAdapter(mTestAdapter);
        mPresenter.requestPersonalCenter();
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }


    //数据返回
    @Override
    public void onRequestPersonalCenter() {
        for (int i = 0; i < 10; i++) {
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

    @Override
    public void onRefresh() {
        ThreadUtil.postDelayed(() -> {
            mRefreshLayout.setRefreshing(false);
        }, 1500);
    }

    @Override
    public void onLoadMore() {
        ThreadUtil.postDelayed(() -> {
            for (int i = 0; i < 10; i++) {
                mDatas.add(new TestBean());
            }
            mTestAdapter.notifyDataSetChanged();
            mRefreshLayout.setLoadingMore(false);
        }, 1500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ThreadUtil.removeCallback();
    }
}
