package com.wwf.common.net.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.wwf.common.net.listener.NetworkStateListener;
import com.wwf.common.util.NetworkUtil;


//监听网络状态的广播接收者
public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private NetworkStateListener mNetworkStateListener;

    public NetworkBroadcastReceiver(NetworkStateListener networkStateListener) {
        mNetworkStateListener = networkStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetworkUtil.getNetWorkState(context);
            //处理网络状态
            checkNetworkState(context, netWorkState);
        }
    }

    private void checkNetworkState(Context context, int netWorkState) {
        switch (netWorkState) {
            case -1://无网络
                if (mNetworkStateListener != null) {
                    mNetworkStateListener.noNetwork();
                }
                break;
            case 0://移动网路
                if (mNetworkStateListener != null) {
                    mNetworkStateListener.mobileNetwork();
                }
                break;
            case 1://wifi网络
                if (mNetworkStateListener != null) {
                    mNetworkStateListener.wifiNetwork();
                }
                break;
        }
    }


}
