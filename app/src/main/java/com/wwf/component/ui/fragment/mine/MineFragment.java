package com.wwf.component.ui.fragment.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.wwf.component.R;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.presenter.fragment.mine.MinePresenter;
import com.wwf.component.ui.fragment.TestFragment;
import com.wwf.component.ui.fragment.main.MainFragment;
import com.wwf.component.iview.mine.IMineView;

import butterknife.BindView;
import butterknife.Unbinder;

public class MineFragment extends BaseFragment<MinePresenter> implements IMineView {

    @BindView(R.id.tv_mine)
    TextView tvMine;

    Unbinder unbinder;

    //创建一个实例
    public static MineFragment newInstance(Bundle bundle) {
        MineFragment mineFragment = new MineFragment();
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Override
    protected void initPresenter() {
        setPresenter(this, new MinePresenter());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tvMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainFragment) getParentFragment()).start(new TestFragment());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
