package com.wwf.component.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wwf.common.util.Util;
import com.wwf.component.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


//青少年应用的actionbar
public class CustomActionBar extends ViewGroup implements View.OnClickListener {

    @BindView(R.id.tv_actionbar_title)
    TextView tvActionbarTitle;
    @BindView(R.id.iv_actionbar_left_back)
    ImageView ivActionbarLeftBack;
    @BindView(R.id.rl_action_bar)
    RelativeLayout rlActionBar;
    @BindView(R.id.view_status_bar)
    View mViewStatusBar;
    @BindView(R.id.view_actionbar_divider)
    View mActionbarDivider;//底部分割线

    private OnActionBarClickListener mOnActionBarClickListener;
    private Unbinder mUnbinder;
    private int textSize;
    private String text;
    private int textColor;
    private int bgColor;
    private boolean isDivider;
    private int statusColor;
    private boolean isShowBack;

    public CustomActionBar(Context context) {
        this(context, null);
    }

    public CustomActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public CustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性
        initAttrs(context, attrs);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.action_bar_layout, this, false);
        addView(view);
        mUnbinder = ButterKnife.bind(this, view);
        setAttrs();
        initListener();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量自己
//        measure(widthMeasureSpec, heightMeasureSpec);
        //测量子控件
        int childCount = getChildCount();
        int width = 0;
        int height = 0;
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                width = measuredWidth;
                height += measuredHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int top = 0;
        int width = 0;
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            //排版子控件
            View childAt = getChildAt(i);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            width += measuredWidth;
            height += measuredHeight;
            childAt.layout(0, top, width, height);
            top += measuredHeight;
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        //获取自定义属性。
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomActionBar);
////        获取字体大小,默认大小是16sp
        textSize = (int) ta.getInteger(R.styleable.CustomActionBar_textSize, 16);
        //获取文字内容
        text = ta.getString(R.styleable.CustomActionBar_text);
        //获取文字颜色
        textColor = ta.getColor(R.styleable.CustomActionBar_textColor, Color.BLACK);
        //获取背景色颜色，默认颜色是white
        bgColor = ta.getColor(R.styleable.CustomActionBar_bgColor, getResources().getColor(R.color.colorPrimary));
        //获取状态栏颜色
        statusColor = ta.getColor(R.styleable.CustomActionBar_statusColor, getResources().getColor(R.color.colorPrimary));
        //是否显示分割线
        isDivider = ta.getBoolean(R.styleable.CustomActionBar_isShowDivider, false);
        //是否显示左侧返回键
        isShowBack = ta.getBoolean(R.styleable.CustomActionBar_isShowBack, true);
        ta.recycle();
    }

    //设置属性值
    private void setAttrs() {
        tvActionbarTitle.setTextColor(textColor);
        tvActionbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tvActionbarTitle.setText(text);
        tvActionbarTitle.setBackgroundColor(bgColor);
        //actionbar左侧返回按钮是否显示
        ivActionbarLeftBack.setVisibility(isShowBack? VISIBLE: GONE);
        //actionbar底部分割线, 是否显示
        mActionbarDivider.setVisibility(isDivider? VISIBLE : GONE);
        post(() -> {
            int statusBarHeight = Util.getStatusBarHeight(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, statusBarHeight);
            mViewStatusBar.setLayoutParams(layoutParams);
            mViewStatusBar.setBackgroundColor(statusColor);
        });
    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left_back://左侧返回按钮
                if (mOnActionBarClickListener != null) {
                    mOnActionBarClickListener.onLeftBack(v);
                    //销毁界面时, 取消绑定
                    mUnbinder.unbind();
                }
                break;
        }
    }

    public abstract class OnActionBarClickListener {
        public abstract void onLeftBack(View view);//左侧返回按钮
//        public void OnActionBarClick(View view){};
//        public void OnActionBarClick(View view){};
//        public void OnActionBarClick(View view){};
    }

    public void setOnActionBarClickListener(OnActionBarClickListener onActionBarClickListener) {
        this.mOnActionBarClickListener = onActionBarClickListener;
    }
}
