package com.wwf.component.presenter.fragment.mine;

import com.wwf.common.bean.BaseBean;
import com.wwf.common.mvp.presenter.Presenter;
import com.wwf.common.constant.Constant;
import com.wwf.common.util.GsonUtil;
import com.wwf.component.bean.PersonalCenterBean;
import com.wwf.component.iview.mine.ITestView;

import java.util.HashMap;
import java.util.Map;

public class TestPresenter extends Presenter<ITestView> {
    public void requestPersonalCenter() {
        Map params = new HashMap();
        post(Constant.Url.PERSONAL_CENTER, params, Constant.UrlFlag.PERSONAL_CENTER, PersonalCenterBean.class);
    }

    @Override
    protected void onSuccess(BaseBean baseBean, int urlflag) {
        super.onSuccess(baseBean, urlflag);
        mView.onRequestPersonalCenter();

    }

    @Override
    protected void onFailure(String msg, int urlflag) {
        super.onFailure(msg, urlflag);

    }
}
