package com.wwf.common.net.listener;

public interface NetworkStateListener {

        void noNetwork();

        void mobileNetwork();

        void wifiNetwork();
    }