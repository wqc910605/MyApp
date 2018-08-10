package com.wwf.component.dialog;

import com.wwf.common.R;
import com.wwf.common.util.Util;
import com.wwf.component.base.BaseDialogFragment;

public class CustomDialog extends BaseDialogFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.dialog_custom_layout;
    }

    @Override
    protected int setTopMargin() {
        return Util.getDimen(getContext(), R.dimen.h100);
    }

}
