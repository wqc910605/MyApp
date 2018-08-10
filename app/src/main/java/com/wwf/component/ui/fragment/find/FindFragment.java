package com.wwf.component.ui.fragment.find;

import android.os.Bundle;

import com.wwf.component.R;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.view.find.IFindView;
import com.wwf.component.presenter.fragment.find.FindPresenter;

public class FindFragment extends BaseFragment<FindPresenter>  implements IFindView{

    //创建一个实例
    public static FindFragment newInstance(Bundle bundle) {
        FindFragment findFragment = new FindFragment();
        findFragment.setArguments(bundle);
        return findFragment;
    }

    @Override
    protected void initPresenter() {
        setPresenter(this, new FindPresenter());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
