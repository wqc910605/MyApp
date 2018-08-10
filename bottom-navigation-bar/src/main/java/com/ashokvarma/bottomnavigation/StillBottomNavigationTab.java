package com.ashokvarma.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see BottomNavigationTab
 * @since 19 Mar 2016
 * 静止不动的bottomnavigationtab
 */
class StillBottomNavigationTab extends BottomNavigationTab {

    public StillBottomNavigationTab(Context context) {
        super(context);
    }

    public StillBottomNavigationTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StillBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StillBottomNavigationTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    void init() {
        paddingTopActive = (int) getResources().getDimension(R.dimen.fixed_height_top_padding_active);
        paddingTopInActive = (int) getResources().getDimension(R.dimen.fixed_height_top_padding_inactive);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fixed_bottom_navigation_item, this, true);
        containerView = view.findViewById(R.id.fixed_bottom_navigation_container);
        labelView = (TextView) view.findViewById(R.id.fixed_bottom_navigation_title);
        iconView = (ImageView) view.findViewById(R.id.fixed_bottom_navigation_icon);
        iconContainerView = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_icon_container);
        badgeView = (BadgeTextView) view.findViewById(R.id.fixed_bottom_navigation_badge);
        super.init();
    }

    @Override
    public void select(boolean setActiveColor, int animationDuration) {
//        paddingTopActive = 0;
//        paddingTopInActive = 0;
//        super.select(setActiveColor, animationDuration);
        isActive = true;
        iconView.setSelected(true);
        if (setActiveColor) {
            labelView.setTextColor(mActiveColor);
        } else {
            labelView.setTextColor(mBackgroundColor);
        }

        if (badgeItem != null) {
            badgeItem.select();
        }
    }

    @Override
    public void unSelect(boolean setActiveColor, int animationDuration) {
//        paddingTopActive = 0;
//        paddingTopInActive = 0;
//        super.unSelect(setActiveColor, animationDuration);
        isActive = false;
        labelView.setTextColor(mInActiveColor);
        iconView.setSelected(false);

        if (badgeItem != null) {
            badgeItem.unSelect();
        }
    }

    @Override
    protected void setNoTitleIconContainerParams(FrameLayout.LayoutParams layoutParams) {
        layoutParams.height = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_container_height);
        layoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_container_width);
    }

    @Override
    protected void setNoTitleIconParams(LayoutParams layoutParams) {
        layoutParams.height = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_height);
        layoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.fixed_no_title_icon_width);
    }
}
