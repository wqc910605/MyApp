package com.wwf.component.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;

import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;
import com.wwf.common.listener.PermissionListener;
import com.wwf.common.mvp.presenter.Presenter;
import com.wwf.common.mvp.view.IView;
import com.wwf.common.net.listener.NetworkStateListener;
import com.wwf.common.net.receiver.NetworkBroadcastReceiver;
import com.wwf.common.util.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @param <P>
 */
public abstract class BaseActivity<P extends Presenter> extends SupportActivity {

    protected P mPresenter;
    private Unbinder mUnbinder;
    private NetworkBroadcastReceiver mNetworkStateReceiver;
    private ImmersionBar mImmersionBar;
    private OnNetworkListener mOnNetworkListener;//网络状态监听器
    private PermissionListener mPermissionListener;//权限监听回调
    public static final String TAG = "lifecycle:: ";

    //网络状态, 是否有网络
    protected boolean hasNetwork;

    //网络状态监听
    private NetworkStateListener networkStateListener = new NetworkStateListener() {
        @Override
        public void noNetwork() {
            hasNetwork = false;
//            Toast.makeText(BaseActivity.this, "没有网络", Toast.LENGTH_SHORT).show();
            if (mOnNetworkListener != null) {
                mOnNetworkListener.onNetwork(false);
            }
        }

        @Override
        public void mobileNetwork() {
            hasNetwork = true;
            if (mOnNetworkListener != null) {
                mOnNetworkListener.onNetwork(true);
            }
//            Toast.makeText(BaseActivity.this, "移动网络", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void wifiNetwork() {
            hasNetwork = true;
            if (mOnNetworkListener != null) {
                mOnNetworkListener.onNetwork(true);
            }
//            Toast.makeText(BaseActivity.this, "wifi网络", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        beforCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG, this.getClass().getSimpleName()+" onCreate");
        setContentView(getLayoutResId());
        //状态栏
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        mUnbinder = ButterKnife.bind(this);
        mNetworkStateReceiver = new NetworkBroadcastReceiver(networkStateListener);
        //注册网络状态广播接收者
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateReceiver, intentFilter);

        initPresenter();
        mPresenter.onCreate(savedInstanceState);
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
        if (setUmengStatistics()) {
            MobclickAgent.onResume(this);
        }
        if (mPresenter != null) {
            mPresenter.onResume();
        }
        LogUtil.e(TAG, this.getClass().getSimpleName()+" onResume");
    }
    /**
     * 是否开启友盟统计, 默认开启
     * @return
     */
    private boolean setUmengStatistics() {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
        if (setUmengStatistics()) {
            MobclickAgent.onPause(this);
        }
        if (mPresenter != null) {
            mPresenter.onPause();
        }
        LogUtil.e(TAG, this.getClass().getSimpleName()+" onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
        LogUtil.e(TAG, this.getClass().getSimpleName()+" onStop");
    }

    @Override
    protected void onDestroy() {
        //释放状态栏
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        //取消网络广播注册
        unregisterReceiver(mNetworkStateReceiver);
        super.onDestroy();
        mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        mPermissionListener = null;
        networkStateListener = null;
        mOnNetworkListener = null;
        mPermissionListener = null;
        LogUtil.e(TAG, this.getClass().getSimpleName()+" onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        LogUtil.e(TAG, this.getClass().getSimpleName()+" onSaveInstanceState outPersistentState");
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.e(TAG, this.getClass().getSimpleName()+" onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.e(TAG, this.getClass().getSimpleName()+" onRestoreInstanceState");
    }

    //初始化presenter时, 调用setPresenter方法即可
    protected abstract void initPresenter();

    //设置presenter方法
    protected <V extends IView> void setPresenter(@NonNull V view, @NonNull P presenter) {
        this.mPresenter = presenter;
        mPresenter.setView(this, view);
    }

    //在创建实例之前执行
    protected void beforCreate(Bundle savedInstanceState) {

    }

    //给个布局id
    public abstract int getLayoutResId();

    //初始化数据
    protected abstract void initData();

    //初始化监听
    protected abstract void initListener();

    //返回键处理
    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mPresenter != null) {
            mPresenter.onNewIntent(intent);
        }
    }

    public void finishPage() {
        this.finish();
    }

    //网络监听器
    public interface OnNetworkListener{
        void onNetwork(boolean isHasNetwork);
    }

    public void setOnNetworkListener(OnNetworkListener onNetworkListener) {
        this.mOnNetworkListener = onNetworkListener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionListener != null) {
            mPermissionListener.onPermission(requestCode, grantResults);
        }
    }

    public void setPermissionListener(PermissionListener permissionListener) {
        this.mPermissionListener = permissionListener;
    }

}
