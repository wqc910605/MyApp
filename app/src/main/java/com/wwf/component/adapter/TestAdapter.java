package com.wwf.component.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wwf.component.R;
import com.wwf.component.bean.TestBean;

import java.util.List;

public class TestAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder>{

    public TestAdapter(@Nullable List<TestBean> data) {
        super(R.layout.fragment_test_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {

    }
}
