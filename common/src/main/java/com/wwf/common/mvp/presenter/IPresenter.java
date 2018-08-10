package com.wwf.common.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by wzp on 2018/5/31.
 */

public interface IPresenter {

    void onCreate(Bundle savedInstanceState);

    void onCreateView(Bundle savedInstanceState);

    void onViewCreated();

    void onResume();

    void onPause();

    void onStop();

    void onDestroyView();

    void onDestroy();

    void onNewIntent(Intent intent);

    void onNewBundle(Bundle bundle);
}
