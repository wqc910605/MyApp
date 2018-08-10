package com.wwf.component.ui.fragment.message;

import android.os.Bundle;

import com.wwf.component.R;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.presenter.fragment.message.MessagePresenter;
import com.wwf.component.view.message.IMessageView;

public class MessageFragment extends BaseFragment<MessagePresenter> implements IMessageView {
    //创建一个实例
    public static MessageFragment newInstance(Bundle bundle) {
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments(bundle);
        return messageFragment;
    }

    @Override
    protected void initPresenter() {
        setPresenter(this, new MessagePresenter());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
