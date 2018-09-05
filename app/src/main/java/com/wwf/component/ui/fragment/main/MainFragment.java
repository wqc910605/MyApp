package com.wwf.component.ui.fragment.main;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wwf.component.R;
import com.wwf.common.eventbus.TabSelectedEvent;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.presenter.main.MainFragmentPresenter;
import com.wwf.component.ui.activity.MainActivity;
import com.wwf.component.ui.fragment.find.FindFragment;
import com.wwf.component.ui.fragment.home.HomeFragment;
import com.wwf.component.ui.fragment.message.MessageFragment;
import com.wwf.component.ui.fragment.mine.MineFragment;
import com.wwf.component.iview.main.IMainFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

public class MainFragment extends BaseFragment<MainFragmentPresenter>
        implements IMainFragmentView, BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bot_nav_bar)
    BottomNavigationBar botNavBar;

    private List<BaseFragment> mFragments = new ArrayList<>();
    private int prePosition;//前一个选中的tab项
    private MainActivity mMainActivity;

    //创建一个实例
    public static MainFragment newInstance(Bundle bundle) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Toasty.normal(getContext(), "我是MainFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initPresenter() {
        setPresenter(this, new MainFragmentPresenter() );
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        mMainActivity = (MainActivity) getActivity();
        EventBus.getDefault().register(this);
        //初始化fragment对象
        initFragment();
        //初始化底部导航按钮
        initBotNav();
    }

    private void initFragment() {
        HomeFragment homeFragment = findChildFragment(HomeFragment.class);
        if (homeFragment == null) {
            mFragments.add(HomeFragment.newInstance(null));
            mFragments.add(MessageFragment.newInstance(null));
            mFragments.add(FindFragment.newInstance(null));
            mFragments.add(MineFragment.newInstance(null));

            loadMultipleRootFragment(R.id.fl_container, 0,
                    mFragments.get(0), mFragments.get(1),
                    mFragments.get(2), mFragments.get(3)
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments.clear();
            mFragments.add(homeFragment);
            mFragments.add(findChildFragment(MessageFragment.class));
            mFragments.add(findChildFragment(FindFragment.class));
            mFragments.add(findChildFragment(MineFragment.class));
        }
    }

    private void initBotNav() {
        botNavBar.addItem(new BottomNavigationItem(R.mipmap.tab_home, R.string.tab_home))
                .addItem(new BottomNavigationItem(R.mipmap.tab_message, R.string.tab_message))
                .addItem(new BottomNavigationItem(R.mipmap.tab_find, R.string.tab_find))
                .addItem(new BottomNavigationItem(R.mipmap.tab_mine, R.string.tab_mine))
                .setActiveColor(R.color.blue)
                .setInActiveColor(R.color.gray)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STILL)
                .setMode(BottomNavigationBar.MODE_STILL)
                .initialise();
        botNavBar.selectTab(mMainActivity.position);
    }

    @Override
    protected void initListener() {
        botNavBar.setTabSelectedListener(this);
    }

    //底部导航是否选中监听
    @Override
    public void onTabSelected(int position) {
        showHideFragment(mFragments.get(position), mFragments.get(prePosition));
        prePosition = position;
        //在mainactivity中记录选中的位置
        mMainActivity.position = position;
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
        // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
        EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
    }

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
//        ToastUtil.show("我是MainFragment");
//        Toast.makeText(mBaseActivity, "你好啊", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        mMainActivity = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onSelectedPosition(TabSelectedEvent tabSelectedEvent) {
        if (tabSelectedEvent != null) {
            botNavBar.selectTab(tabSelectedEvent.position );
        }
        //移除粘性事件
        EventBus.getDefault().removeStickyEvent(tabSelectedEvent);
    }
}
